package com.example.findwork

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findwork.data.Offer
import com.example.findwork.data.Vacancy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

class MainViewModel:ViewModel() {
    private val _itemState = mutableStateOf(ItemState())
    val itemState: State<ItemState> = _itemState

    private val lock:ReentrantReadWriteLock= ReentrantReadWriteLock()
    private val vacancyListSaved:MutableSet<Vacancy> = mutableSetOf()

    private val _vacancyListFlow = MutableStateFlow<List<Vacancy>>(emptyList())
    val vacancyListFlow: StateFlow<List<Vacancy>> get() = _vacancyListFlow

    private val likedVacancies: MutableSet<String> = mutableSetOf()

    init{
        fetchCategories()
        readVacancy()
    }

    fun isVacancyLiked(vacancy: Vacancy): Boolean {
        return likedVacancies.contains(vacancy.id)
    }

    fun toggleLikeVacancy(vacancy: Vacancy) {
        if (likedVacancies.contains(vacancy.id)) {
            removeVacancy(vacancy)
            likedVacancies.remove(vacancy.id)
        } else {
            addVacancy(vacancy)
            likedVacancies.add(vacancy.id)
        }
    }

    private fun fetchCategories(){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getItem()
                Log.d("ViewModel", "${response.offers.get(0).id}")
                _itemState.value = _itemState.value.copy(
                    listOffer = response.offers,
                    listVacancy=response.vacancies,
                    loading = false,
                    error = null
                )

            }catch (e: Exception){
                _itemState.value = _itemState.value.copy(
                    loading = false,
                    error = "Error fetching Categories ${e.message}"
                )
            }
        }
    }

    fun addVacancy(vacancy: Vacancy){
        lock.write {
            vacancyListSaved.add(vacancy)
            _vacancyListFlow.value = vacancyListSaved.toList()
        }
    }
    fun removeVacancy(vacancy: Vacancy){
        lock.write {
            vacancyListSaved.remove(vacancy)
            _vacancyListFlow.value = vacancyListSaved.toList()
        }
    }
    fun readVacancy(){
        lock.read {
            _vacancyListFlow.value = vacancyListSaved.toList()
        }

    }





    data class ItemState(
        val loading: Boolean = true,
        val listOffer: List<Offer> = emptyList(),
        val listVacancy: List<Vacancy> = emptyList(),
        val error: String? = null
    )
}