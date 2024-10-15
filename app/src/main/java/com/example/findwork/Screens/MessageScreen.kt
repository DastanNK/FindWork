package com.example.findwork.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.findwork.BottomNavBar
import com.example.findwork.BottomNavBarItems

@Composable
fun MessageScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().background(color = colorScheme.background)) {
        Column(modifier = Modifier.weight(1f)) {
            Text("MessageScreen")

        }

        BottomNavBar(BottomNavBarItems.Message, navController)
    }
}