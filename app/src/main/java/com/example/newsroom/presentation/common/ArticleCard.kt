package com.example.newsroom.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsroom.R
import com.example.newsroom.domain.model.Article
import com.example.newsroom.domain.model.Source
import com.example.newsroom.presentation.Dimension.ArticleCardSize
import com.example.newsroom.presentation.Dimension.ExtraSmallPadding
import com.example.newsroom.presentation.Dimension.ExtraSmallPadding2
import com.example.newsroom.presentation.Dimension.SmallIconSize
import com.example.newsroom.ui.theme.NewsRoomTheme

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick:() -> Unit
){
    val context = LocalContext.current

    Row(modifier = Modifier.clickable { onClick() }){

        AsyncImage(
            modifier = Modifier
                .size(ArticleCardSize)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context).data(article.urlToImage).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = 10.dp )
                .height(ArticleCardSize)
        ) {
            Text(
                text = article.title,
                style = MaterialTheme
                    .typography.bodyMedium,
                color = colorResource(
                    id = R.color.text_title
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row (
                verticalAlignment = Alignment.CenterVertically){
                article.source.name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme
                            .typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = colorResource(
                            id = R.color.body
                        )
                    )
                }
                Spacer(modifier = Modifier.width(ExtraSmallPadding2))
                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = null,
                    modifier = Modifier.size(SmallIconSize),
                    tint = colorResource(id = R.color.body)
                )
                Spacer(modifier = Modifier.width(ExtraSmallPadding2))

                article.publishedAt?.let {
                    Text(
                        text = it,
                        style = MaterialTheme
                            .typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = colorResource(
                            id = R.color.body
                        ),
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleCardPreview(){
    NewsRoomTheme {
        ArticleCard(article = Article(
            author = "",
            content = "",
            description = "",
            publishedAt = "2 hours",
            source = Source(id = "", name = "BBC"),
            title = "Her traint broke down. Her phone died. And the she met her saver in a",
            url = "",
            urlToImage = ""

        )) {

        }
    }
}