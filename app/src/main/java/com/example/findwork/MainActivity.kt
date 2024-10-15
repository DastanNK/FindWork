package com.example.findwork

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.findwork.Screens.*
import com.example.findwork.ui.theme.FindWorkTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindWorkTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MyApp()
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp(){
    val navController= rememberNavController()
    val mainViewModel: MainViewModel = viewModel()

    NavHost(navController, startDestination = BottomNavBarItems.Find.route){
            composable(BottomNavBarItems.Find.route) {
                FindScreen(navController, mainViewModel)
            }
            composable(BottomNavBarItems.SavedItem.route) {
                SavedScreen(navController, mainViewModel)
            }
            composable(BottomNavBarItems.Responses.route) {
                ResponsesScreen(navController)
            }
            composable(BottomNavBarItems.Message.route) {
                MessageScreen(navController)
            }
            composable(BottomNavBarItems.Profile.route) {
                ProfileScreen(navController)
            }
    }



}




