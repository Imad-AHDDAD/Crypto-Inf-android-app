package com.example.coin.presentation.coin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.coin.R
import com.example.coin.databinding.CoinActivityBinding
import com.example.coin.domain.model.Coin
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinActivity : AppCompatActivity() {

    private lateinit var binding: CoinActivityBinding
    private val coinViewModel: CoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CoinActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val coinId = intent.getStringExtra("id")
        if (!coinId.isNullOrBlank()) {
                coinViewModel.getCoin(id = coinId)
                observeCoinDetails()
        }

    }

    private fun observeCoinDetails() {
        lifecycleScope.launch {
            coinViewModel._coinValue.collectLatest { coinValue ->
                if (coinValue.isLoading) {
                    binding.progressBarCoin.visibility = View.VISIBLE
                    binding.allCoinInformations.visibility = View.GONE
                } else {
                    if (coinValue.error.isNotBlank()) {
                        binding.progressBarCoin.visibility = View.GONE
                        binding.allCoinInformations.visibility = View.VISIBLE
                    } else {
                        binding.progressBarCoin.visibility = View.GONE
                        binding.allCoinInformations.visibility = View.VISIBLE
                        Picasso.get().load(coinValue.coinDetail?.image).into(binding.imageCoin)
                        binding.coinName.text = coinValue.coinDetail?.name
                        binding.coinDescription.text = coinValue.coinDetail?.description
                        binding.coinPrice.text = "Price: ${coinValue.coinDetail?.price.toString()} $"
                        binding.coinLowPrice.text = "Low Price: ${coinValue.coinDetail?.lowPrice.toString()} $"
                        binding.coinHighPrice.text = "High Price: ${coinValue.coinDetail?.highPrice.toString()} $"
                        binding.coinPricePercent.text = "Change Percent: ${coinValue.coinDetail?.pricePercentageChange.toString()} $"
                    }
                }
            }
        }
    }
}