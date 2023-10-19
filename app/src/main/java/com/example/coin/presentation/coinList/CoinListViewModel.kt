package com.example.coin.presentation.coinList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coin.domain.usecases.CoinListUseCase
import com.example.coin.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val coinListUseCase: CoinListUseCase
) : ViewModel(){
    private val coinListValue = MutableStateFlow(CoinListState())
    var _coinListValue : StateFlow<CoinListState> = coinListValue

    fun getAllCoins(page: String) = viewModelScope.launch(Dispatchers.IO) {
        coinListUseCase(page).collect {
            when(it) {
                is ResponseState.Success -> {
                    coinListValue.value = CoinListState(coinList = it.data?: emptyList())
                }
                is ResponseState.Loading -> {
                    coinListValue.value = CoinListState(isLoading = true)
                }
                is ResponseState.Error -> {
                    coinListValue.value = CoinListState(error = it.message?: "An Unexpected Error")
                }
            }
        }
    }

}