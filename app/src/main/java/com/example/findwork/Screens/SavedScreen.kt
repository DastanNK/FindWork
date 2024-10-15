package com.example.findwork.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.findwork.BottomNavBar
import com.example.findwork.BottomNavBarItems
import com.example.findwork.MainViewModel
import com.example.findwork.data.Vacancy

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SavedScreen(navController: NavController, viewModel: MainViewModel) {
    val vacancyList: List<Vacancy> by viewModel.vacancyListFlow.collectAsState()
    Column(modifier = Modifier.fillMaxSize().background(color = colorScheme.background)) {
        Column(modifier = Modifier.weight(1f).padding(8.dp)) {
            Column(modifier = Modifier.padding(8.dp)){
                Text("Избранное", fontSize = 20.sp,
                    color = colorScheme.onSecondary,
                    modifier = Modifier.padding(bottom = 16.dp))

                Text("${vacancyList.size} " + if (vacancyList.size == 1) "вакансия" else "вакансии", fontSize = 14.sp,
                    color = colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp))
            }


            VacancyBlock(vacancyList, viewModel)
        }
        BottomNavBar(BottomNavBarItems.SavedItem, navController)
    }
}