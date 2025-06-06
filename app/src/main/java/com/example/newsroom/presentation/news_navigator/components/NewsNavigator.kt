package com.example.newsroom.presentation.news_navigator.components

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsroom.R
import com.example.newsroom.domain.model.Article
import com.example.newsroom.presentation.bookmark.BookmarkScreen
import com.example.newsroom.presentation.bookmark.BookmarkViewModel
import com.example.newsroom.presentation.details.components.DetailScreen
import com.example.newsroom.presentation.details.components.DetailsEvent
import com.example.newsroom.presentation.details.components.DetailsViewModel
import com.example.newsroom.presentation.home.HomeScreen
import com.example.newsroom.presentation.home.HomeViewModel
import com.example.newsroom.presentation.navgraph.Route
import com.example.newsroom.presentation.search.SearchScreen
import com.example.newsroom.presentation.search.SearchViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  NewsNavigator() {

    val bottomNavigationItem = remember{
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark")

        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value

    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem = remember(key1 = backStackState){
        when(backStackState?.destination?.route){
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.BookmarkScreen.route -> 2
            else -> 0
         }
    }

    val isBottomBarVisible = remember(key1 = backStackState){
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.SearchScreen.route ||
                backStackState?.destination?.route == Route.BookmarkScreen.route
    }


    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItem,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigationToTab(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1 -> navigationToTab(
                                navController = navController,
                                route = Route.SearchScreen.route
                            )

                            2 -> navigationToTab(
                                navController = navController,
                                route = Route.BookmarkScreen.route
                            )
                        }
                    }
                )
            }
        }
    ){
        val bottomPadding = it.calculateBottomPadding()

        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ){
            composable(route = Route.HomeScreen.route){
                val viewModel: HomeViewModel = hiltViewModel()
                val article = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = article,
                    navigateToSearch = {
                        navigationToTab(navController = navController, route= Route.SearchScreen.route)
                    },
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }

            composable(route = Route.SearchScreen.route){
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value

                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }

            composable(route = Route.DetailsScreen.route){
                val viewModel: DetailsViewModel = hiltViewModel()

                if(viewModel.sideEffect != null){
                    Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }

                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let { article ->
                    DetailScreen(
                        article = article,
                        event = viewModel::onEvent,
                        navigateUp = {navController.navigateUp()}
                    )
                }
            }

            composable(route = Route.BookmarkScreen.route){
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookmarkScreen(state = state, navigateToDetails = {article ->
                    navigateToDetails(navController = navController, article = article)
                })
            }
        }
    }
}


private fun navigationToTab(navController: NavController, route: String){
    navController.navigate(route){
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen){
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article){
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}