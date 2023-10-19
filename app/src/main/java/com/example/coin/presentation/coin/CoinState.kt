package com.example.coin.presentation.coin

import com.example.coin.domain.model.CoinDetail

data class CoinState(
    val isLoading: Boolean = false,
    val coinDetail: CoinDetail? = null ,
    val error: String = ""
)
