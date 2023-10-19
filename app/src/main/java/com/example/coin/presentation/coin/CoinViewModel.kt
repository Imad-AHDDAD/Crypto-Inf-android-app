package com.example.coin.presentation.coin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coin.domain.usecases.CoinDetailUseCase
import com.example.coin.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val coinDetailUseCase: CoinDetailUseCase
) : ViewModel() {

    private val coinValue = MutableStateFlow(CoinState())
    var _coinValue : StateFlow<CoinState> = coinValue

    fun getCoin(id: String) = viewModelScope.launch(Dispatchers.IO) {
        coinDetailUseCase(id).collect {
            when(it) {
                is ResponseState.Success -> {
                    coinValue.value = CoinState(coinDetail = it.data)
                }
                is ResponseState.Loading -> {
                    coinValue.value = CoinState(isLoading = true)
                }
                is ResponseState.Error -> {
                    coinValue.value = CoinState(error = it.message?: "An Unexpected Error")
                }
            }
        }
    }

}