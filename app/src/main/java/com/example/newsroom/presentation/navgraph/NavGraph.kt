package com.example.newsroom.presentation.navgraph


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.newsroom.presentation.bookmark.BookmarkScreen
import com.example.newsroom.presentation.bookmark.BookmarkViewModel
import com.example.newsroom.presentation.news_navigator.components.NewsNavigator
import com.example.newsroom.presentation.onboarding.OnBoardingViewModel
import com.example.newsroom.presentation.onboarding.components.OnBoardingScreen


@Composable
fun NavGraph(
  startDestination: String,

){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination ){
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ){
            composable(
                route = Route.OnBoardingScreen.route
            ){
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    event = viewModel::onEvent
                )
            }
        }
        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigatorScreen.route
        ){
            composable(
                route = Route.NewsNavigatorScreen.route
            ){
                NewsNavigator()
            }
        }
    }
}