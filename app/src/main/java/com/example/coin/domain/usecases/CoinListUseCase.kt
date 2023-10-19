package com.example.coin.domain.usecases

import com.example.coin.domain.model.Coin
import com.example.coin.domain.repository.CoinRepository
import com.example.coin.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class CoinListUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    operator fun invoke(page: String) : Flow<ResponseState<List<Coin>>> = flow {
        try {
            emit(ResponseState.Loading<List<Coin>>())
            val coins = repository.getAllCoins(page).map {
                it.toCoin()
            }
            emit(ResponseState.Success<List<Coin>>(coins))
        }catch (e: HttpException) {
            emit(ResponseState.Error<List<Coin>>(message = e.localizedMessage?:"An Unexpected Error Occurred"))
        }catch (e: IOException) {
            emit(ResponseState.Error<List<Coin>>(message = "Error Occurred"))
        }
    }

}