package com.example.newsroom.presentation.onboarding

import androidx.annotation.DrawableRes
import com.example.newsroom.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "Loreum Ispum is simply dummy",
        description = "Loreum ispum is simply dummy text of the priniting and typesetting industry",
        image = R.drawable.onboarding1
    ),

    Page(
        title = "Loreum Ispum is simply dummy",
        description = "Loreum ispum is simply dummy text of the priniting and typesetting industry",
        image = R.drawable.onboarding2
    ),

    Page(
        title = "Loreum Ispum is simply dummy",
        description = "Loreum ispum is simply dummy text of the priniting and typesetting industry",
        image = R.drawable.onboarding3
    ),
)