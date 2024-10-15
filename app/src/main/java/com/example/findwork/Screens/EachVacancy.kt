package com.example.findwork.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.findwork.MainViewModel

@Composable
fun EachVacancy(navController: NavController, viewModel: MainViewModel){
    Column {
        IconButton(onClick = {
            navController.navigateUp()
        }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Text("EachVacancy")
    }

}