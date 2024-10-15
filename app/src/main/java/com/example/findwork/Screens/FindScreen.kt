package com.example.findwork.Screens

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.findwork.BottomNavBar
import com.example.findwork.BottomNavBarItems
import com.example.findwork.MainViewModel
import com.example.findwork.R
import com.example.findwork.data.Offer
import com.example.findwork.data.Vacancy
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindScreen(
    navController: NavController, viewModel: MainViewModel
    // , offers:List<Offer>
) {
    val viewState by viewModel.itemState
    val offers = viewState.listOffer
    val vacancies = viewState.listVacancy
    var showAllVacancies by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(color = colorScheme.background)) {
        Column(modifier = Modifier.weight(1f).padding(8.dp)) {
            FindField()
            when {
                viewState.loading -> {

                }

                viewState.error != null -> {
                    Text(
                        text = "ERROR OCCURRED: ${viewState.error}"
                    )
                }

                else -> {
                    if (viewState.listOffer.isNotEmpty() && !showAllVacancies) {
                        RecommendationBlock(offers)
                        Text(
                            "Вакансии для вас",
                            modifier = Modifier.padding(start = 8.dp, top = 16.dp, bottom = 8.dp),
                            color = colorScheme.onSecondary,
                            fontSize = 20.sp
                        )
                    }else if (showAllVacancies){
                        Row(modifier=Modifier.padding(8.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                            Text(
                                "${vacancies.size} " + if (vacancies.size == 1) "вакансия" else "вакансии", fontSize = 14.sp,
                                color = colorScheme.onSecondary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Row{
                                Text("По соответствию", fontSize = 14.sp,
                                    color = colorScheme.onTertiary,
                                    modifier = Modifier.padding(bottom = 8.dp))
                                Icon(painter = painterResource(R.drawable.sort), contentDescription = null, modifier = Modifier.height(20.dp).width(20.dp))
                            }


                        }

                    }

                    if (vacancies.isNotEmpty()) {
                        VacancyBlockFindScreen(vacancies, viewModel, showAllVacancies, onShowAllVacanciesChange = { showAllVacancies = it })
                    }


                }
            }

        }

        BottomNavBar(BottomNavBarItems.Find, navController)
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VacancyBlockFindScreen(vacancies: List<Vacancy>, viewModel: MainViewModel, showAllVacancies: Boolean, onShowAllVacanciesChange: (Boolean) -> Unit) {


    LazyColumn(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
        items(if (showAllVacancies) vacancies else vacancies.take(3)) { vacancy ->
            VacancyBox(vacancy, viewModel)

        }
        if(!showAllVacancies){
            item {
                Box(
                    modifier = Modifier.padding(8.dp).background(color = colorScheme.onTertiary, shape = RoundedCornerShape(8.dp))
                        .height(48.dp).fillMaxWidth().clickable {
                            onShowAllVacanciesChange(true)
                        }, contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Еще ${vacancies.size-3} " + if (vacancies.size-3 == 1) "вакансия" else "вакансии", fontSize = 16.sp,
                        color = colorScheme.onSecondary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VacancyBlock(vacancies: List<Vacancy>, viewModel: MainViewModel) {
    LazyColumn(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
        items(vacancies) { vacancy ->
            VacancyBox(vacancy, viewModel)

        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VacancyBox(vacancy: Vacancy, viewModel: MainViewModel) {
    val parsedDate = LocalDate.parse(vacancy.publishedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val formatter = DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))
    val formattedDate = parsedDate.format(formatter)
    var filledLikeSize by remember { mutableStateOf(0f) }
    var showEmptyLike by remember { mutableStateOf(true) }
    var isLiked by remember { mutableStateOf(viewModel.isVacancyLiked(vacancy)) }
    Box(
        modifier = Modifier.padding(8.dp).background(color = colorScheme.secondary, shape = RoundedCornerShape(8.dp))
            .wrapContentHeight().fillMaxWidth().padding(8.dp).clickable {
                //navController.navigate("EachVacancy")
            }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.padding(bottom = 8.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Сейчас просматривает ${vacancy.lookingNumber} человек",
                    fontSize = 14.sp,
                    color = colorScheme.onSurface
                )

                LaunchedEffect(isLiked) {
                    if (isLiked) {
                        animate(0f, 1f, animationSpec = spring()) { value, _ ->
                            if (value >= 1f) showEmptyLike = false
                            filledLikeSize = value
                        }
                    } else {
                        filledLikeSize = 0f
                        showEmptyLike = true
                    }
                }
                Box(modifier = Modifier.size(24.dp)) {
                    if (showEmptyLike) {
                        Icon(
                            Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                viewModel.toggleLikeVacancy(vacancy)
                                isLiked = true

                            },
                            colorScheme.surface
                        )
                    }
                    if (filledLikeSize > 0f) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = Modifier.scale(filledLikeSize).clickable {
                                viewModel.toggleLikeVacancy(vacancy)
                                isLiked = false

                            })
                    }
                }
            }
            Text(
                vacancy.title,
                fontSize = 16.sp,
                color = colorScheme.onSecondary,
                modifier = Modifier.padding(bottom = 8.dp).wrapContentWidth().wrapContentHeight()
            )
            Text(
                vacancy.address.town,
                fontSize = 14.sp,
                color = colorScheme.onSecondary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(modifier = Modifier.padding(bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(vacancy.company, fontSize = 14.sp, color = colorScheme.onSecondary)
                Icon(
                    painter = painterResource(R.drawable.checkmark),
                    contentDescription = null,
                    modifier = Modifier.height(16.dp).width(16.dp).padding(start =2.dp),
                    colorScheme.onSecondary
                )
            }
            Row(
                modifier = Modifier.padding(bottom = 8.dp).wrapContentWidth().wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.experience),
                    contentDescription = null,
                    modifier = Modifier.height(16.dp).width(16.dp).padding(end=2.dp),
                    colorScheme.onSecondary
                )
                Text(vacancy.experience.previewText, fontSize = 14.sp, color = colorScheme.onSecondary)

            }
            Text(
                "Опубликовано ${formattedDate}",
                fontSize = 14.sp,
                color = colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Box(
                modifier = Modifier.background(color = colorScheme.onSurface, shape = RoundedCornerShape(50.dp))
                    .height(32.dp).fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text("Откликнуться", fontSize = 14.sp, color = colorScheme.onSecondary)
            }

        }
    }
}

@Composable
fun RecommendationBlock(offers: List<Offer>) {
    LazyRow(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
        items(offers) { offer ->
            RecommendationBox(offer)

        }
    }
}

@Composable
fun RecommendationBox(offer: Offer) {
    val context = LocalContext.current
    val maxLines = remember { mutableStateOf(2) }
    Box(
        modifier = Modifier.padding(8.dp).background(color = colorScheme.secondary, shape = RoundedCornerShape(8.dp))
            .height(124.dp).width(132.dp).padding(8.dp).clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(offer.link))
                context.startActivity(intent)
            }
    ) {
        Column {
            retrieveImage(offer.id ?: "")
            maxLines.value = if (offer.button != null) 2 else 3
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = offer.title,
                fontSize = 14.sp,
                color = colorScheme.onSecondary,
                maxLines = maxLines.value,
                overflow = TextOverflow.Ellipsis
            )

            offer.button?.let {
                Text(
                    text = it.text,
                    fontSize = 14.sp,
                    color = colorScheme.onSurface
                )
            }
        }
    }

}


@Composable
fun retrieveImage(id: String) {
    when (id) {
        "near_vacancies" -> {
            Image(
                painter = painterResource(R.drawable.nearvacancies),
                contentDescription = null,
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
            )
        }

        "level_up_resume" -> {
            Image(
                painter = painterResource(R.drawable.levelupresume),
                contentDescription = null,
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
            )
        }

        "temporary_job" -> {
            Image(
                painter = painterResource(R.drawable.temporaryjob),
                contentDescription = null,
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
            )
        }

        else -> {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindField() {
    val findJobs = remember { mutableStateOf("") }
    Row(
        modifier = Modifier.padding(start = 8.dp, end = 12.dp, bottom = 8.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        TextField(
            value = findJobs.value,
            onValueChange = { findJobs.value = it },
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.findicon),
                    contentDescription = "Description of the image",
                    modifier = Modifier.height(20.dp).width(20.dp)

                )
            },
            modifier = Modifier.background(color = colorScheme.tertiary, shape = RoundedCornerShape(8.dp))
                .weight(1f).height(48.dp),
            placeholder = {
                Text(
                    "Должность, ключевые слова",
                    fontSize = 14.sp,
                    style = TextStyle(color = colorScheme.onSecondary),
                    overflow = TextOverflow.Ellipsis
                )
            },
            maxLines = 1,

            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledTextColor = colorScheme.onSurface,
                disabledIndicatorColor = Color.Transparent,
                containerColor = Color.Transparent,
                cursorColor = colorScheme.scrim,
            )
        )
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier.height(48.dp).width(48.dp)
                .background(color = colorScheme.tertiary, shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.filter),
                contentDescription = "Description of the image",
                modifier = Modifier.height(20.dp).width(20.dp)

            )
        }


    }
}