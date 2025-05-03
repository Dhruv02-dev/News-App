package com.example.newsroom.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.paging.compose.LazyPagingItems
import com.example.newsroom.domain.model.Article
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsroom.R
import com.example.newsroom.presentation.Dimension.MediumPadding1
import com.example.newsroom.presentation.common.ArticleList
import com.example.newsroom.presentation.common.SearchBar
import com.example.newsroom.presentation.navgraph.Route

@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>,
    navigateToSearch: () -> Unit,
    navigateToDetails: (Article) -> Unit
){
   /* val titles by remember {
        derivedStateOf {
            if (articles.itemCount > 10){
                articles.itemSnapshotList.items
                    .slice(IntRange(start = 0, endInclusive = 2))
                    .joinToString(separator = "\uD83d\uDFE5"){it.title}
            }
            else{
                ""
            }
        }
    }*/

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = MediumPadding1)
        .statusBarsPadding()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(30.dp)
                .padding(horizontal = MediumPadding1)
        )

        Spacer(modifier = Modifier.height(MediumPadding1))


        SearchBar(
            modifier = Modifier.padding(horizontal = MediumPadding1),
            text = "",
            readOnly = true ,
            onValueChange = {},
            onClick = {
                    navigateToSearch()
            },
            onSearch = {}
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        /*Text(
           text = "",
           modifier = Modifier
             .fillMaxWidth()
                .padding(start = MediumPadding1),
            fontSize = 12.sp,
            color = colorResource(id = R.color.placeholder)
        )*/

        Spacer(modifier = Modifier.height(MediumPadding1) )

        ArticleList(
            modifier = Modifier.padding(horizontal = MediumPadding1),
            article = articles,
            onClick = {
               navigateToDetails(it)
            }
        )
    }
}