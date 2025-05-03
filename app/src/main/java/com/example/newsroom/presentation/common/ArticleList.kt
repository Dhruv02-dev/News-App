package com.example.newsroom.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.newsroom.domain.model.Article
import com.example.newsroom.presentation.Dimension.MediumPadding1
import com.loc.newsapp.presentation.common.EmptyScreen

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    article: LazyPagingItems<Article>,
    onClick:(Article) -> Unit
){
    val handelPagingResult = handelPagingResult(article = article)
    if(handelPagingResult){
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MediumPadding1),
            contentPadding = PaddingValues(all = 4.dp)
        ){
            items(count = article.itemCount){
                article[it].let {
                    if (it != null) {
                        ArticleCard(modifier = modifier, article = it, onClick = {onClick(it)})
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    article: List<Article>,
    onClick:(Article) -> Unit
){


    LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MediumPadding1),
            contentPadding = PaddingValues(all = 4.dp)
    ){
        items(count = article.size){
            val article = article[it]
            if (it != null) {
                ArticleCard(modifier = modifier, article = article, onClick = {onClick(article)})
            }
        }
    }
}



@Composable
fun handelPagingResult(
    article: LazyPagingItems<Article>,
):Boolean{

    val loadState = article.loadState
    val error = when{
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when{
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }
        error != null ->{
            EmptyScreen()
            false
        }
        else -> {
            true
        }
    }
}

@Composable
private fun ShimmerEffect(){
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        repeat(10){
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }

    }
}