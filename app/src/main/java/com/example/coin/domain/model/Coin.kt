package com.example.coin.domain.model

data class Coin(
    val id : String,
    val name : String,
    val image : String,
    val marketCap : Long,
    val price : Double,
    val pricePercentageChange : Double,
    val lowPrice : Double,
    val highPrice : Double
)
