package com.example.findwork

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

sealed class BottomNavBarItems(val route: String, val icon: Int, val name: String) {
    object Find : BottomNavBarItems("find", R.drawable.findicon, "Поиск")
    object SavedItem : BottomNavBarItems("saved", R.drawable.heart, "Избранное")
    object Responses : BottomNavBarItems("responses", R.drawable.responsesicon, "Отклики")
    object Message : BottomNavBarItems("message", R.drawable.messageicon, "Сообщения")
    object Profile : BottomNavBarItems("profile", R.drawable.profileicon, "Профиль")
}

@Composable
fun BottomNavBar(selectedItem: BottomNavBarItems, navController: NavController) {
    val bottomNavBarItems = listOf(
        BottomNavBarItems.Find,
        BottomNavBarItems.SavedItem,
        BottomNavBarItems.Responses,
        BottomNavBarItems.Message,
        BottomNavBarItems.Profile
    )
    Column(modifier = Modifier.height(56.dp).fillMaxWidth()) {
        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(color = Color.DarkGray)
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(8.dp),
        ) {

            for (item in bottomNavBarItems) {
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Image(
                        rememberImagePainter(item.icon), contentDescription = null, colorFilter = ColorFilter.tint(
                            if (item == selectedItem) {
                                Color.Blue
                            } else {
                                Color.LightGray
                            }
                        ), modifier = Modifier
                            //.padding(start=16.dp, end=16.dp)
                            .height(24.dp).width(24.dp).clickable {
                            navController.navigate(item.route){
                                launchSingleTop = true
                            }
                        }
                    )
                    Text(item.name, fontSize = 12.sp, color = if (item == selectedItem) {
                        Color.Blue
                    } else {
                        Color.LightGray
                    })
                }


            }
        }
    }

}