package com.example.coin.domain.model

data class CoinDetail(
    val name : String,
    val image : String,
    val marketCap : Long,
    val price : Double,
    val pricePercentageChange : Double,
    val lowPrice : Double,
    val highPrice : Double,
    val description : String
)
