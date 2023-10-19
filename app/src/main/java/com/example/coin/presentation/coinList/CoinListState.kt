package com.example.coin.presentation.coinList

import com.example.coin.domain.model.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    val coinList: List<Coin> = emptyList(),
    val error: String = ""
)
