package com.example.coin.domain.usecases

import com.example.coin.domain.model.CoinDetail
import com.example.coin.domain.repository.CoinRepository
import com.example.coin.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class CoinDetailUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(id: String) : Flow<ResponseState<CoinDetail>> = flow {
        try {
            emit(ResponseState.Loading<CoinDetail>())
            val coinDetail = repository.getCoinById(id).toCoinDetail()
            emit(ResponseState.Success<CoinDetail>(coinDetail))
        }catch (e: HttpException) {
            emit(ResponseState.Error<CoinDetail>(message = e.localizedMessage?:"An Unexpected Error Occurred"))
        }catch (e: IOException) {
            emit(ResponseState.Error<CoinDetail>(message = "Error Occurred"))
        }
    }
}