package com.example.coin.domain.repository

import com.example.coin.data.data_source.dto.coinDetailDTO.CoinDetailDTO
import com.example.coin.data.data_source.dto.coinListDTO.CoinListDTO

interface CoinRepository {
    suspend fun getAllCoins(page: String): CoinListDTO
    suspend fun getCoinById(id: String): CoinDetailDTO
}