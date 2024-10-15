package com.example.findwork.data

data class Offer(
    val id: String?,
    val title: String,
    val link: String,
    val button: Button?
)

data class Button(
    val text: String
)