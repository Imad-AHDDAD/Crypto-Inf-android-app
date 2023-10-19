package com.example.coin.data.repository

import com.example.coin.data.data_source.CoinApi
import com.example.coin.data.data_source.dto.coinDetailDTO.CoinDetailDTO
import com.example.coin.data.data_source.dto.coinListDTO.CoinListDTO
import com.example.coin.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinApi: CoinApi
) : CoinRepository {

    override suspend fun getAllCoins(page: String): CoinListDTO {
        return coinApi.getAllCoins(page = page)
    }

    override suspend fun getCoinById(id: String): CoinDetailDTO {
        return coinApi.getCoinById(id = id)
    }
}