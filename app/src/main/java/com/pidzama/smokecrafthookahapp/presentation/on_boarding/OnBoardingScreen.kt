package com.pidzama.smokecrafthookahapp.presentation.on_boarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import com.pidzama.smokecrafthookahapp.navigation.Graph
//
//@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
//@Composable
//fun OnBoardingScreen(
//    navController: NavHostController,
//    onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
//) {
//    val pages = listOf(
//        OnBoardingPages.FirstPage,
//        OnBoardingPages.SecondPage,
//        OnBoardingPages.ThirdPage
//    )
//
//    val pagerState = rememberPagerState()
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        HorizontalPager(
//            modifier = Modifier.weight(10f),
//            count = pages.size,
//            state = pagerState,
//            verticalAlignment = Alignment.Top
//        ) { position ->
//            PageScreens(onBoardingPage = pages[position])
//        }
//        HorizontalPagerIndicator(
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .weight(1f),
//            pagerState = pagerState
//        )
//        FinishButton(
//            modifier = Modifier.weight(1f),
//            pagerState = pagerState
//        ) {
//            onBoardingViewModel.saveOnBoardingState(completed = true)
//            navController.popBackStack()
//            navController.navigate(Graph.AUTH)
//        }
//    }
//}
//
//@Composable
//fun PageScreens(onBoardingPage: OnBoardingPages) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top
//    ) {
//        Image(
//            modifier = Modifier
//                .fillMaxWidth(0.5f)
//                .fillMaxHeight(0.7f),
//            painter = painterResource(id = onBoardingPage.image),
//            contentDescription = "Pager Image"
//        )
//        Text(
//            modifier = Modifier
//                .fillMaxWidth(),
//            text = onBoardingPage.title,
//            fontSize = MaterialTheme.typography.h4.fontSize,
//            fontWeight = FontWeight.Bold,
//            textAlign = TextAlign.Center
//        )
//        Text(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 40.dp)
//                .padding(top = 20.dp),
//            text = onBoardingPage.description,
//            fontSize = MaterialTheme.typography.subtitle1.fontSize,
//            fontWeight = FontWeight.Medium,
//            textAlign = TextAlign.Center
//        )
//    }
//}
//
//@ExperimentalAnimationApi
//@ExperimentalPagerApi
//@Composable
//fun FinishButton(
//    modifier: Modifier,
//    pagerState: PagerState,
//    onClick: () -> Unit
//) {
//    Row(
//        modifier = modifier
//            .padding(horizontal = 40.dp),
//        verticalAlignment = Alignment.Top,
//        horizontalArrangement = Arrangement.Center
//    ) {
//        AnimatedVisibility(
//            modifier = Modifier.fillMaxWidth(),
//            visible = pagerState.currentPage == 2
//        ) {
//            Button(
//                onClick = onClick,
//                colors = ButtonDefaults.buttonColors(
//                    contentColor = Color.White
//                )
//            ) {
//                Text(text = "Finish")
//            }
//        }
//    }
//}


