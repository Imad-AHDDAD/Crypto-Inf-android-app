package com.example.coin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coin.databinding.ActivityMainBinding
import com.example.coin.domain.model.Coin
import com.example.coin.presentation.coinList.CoinAdapter
import com.example.coin.presentation.coinList.CoinListState
import com.example.coin.presentation.coinList.CoinListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CoinAdapter
    private lateinit var layoutManager: GridLayoutManager
    private val tempCoinList = arrayListOf<Coin>()
    private var page: Int = 1
    private val coinListViewModel: CoinListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
        callApi()
        binding.btnSort.setOnClickListener {
            tempCoinList.sortWith{o1, o2 -> o1.name.compareTo(o2.name)}
            adapter.setData(tempCoinList)
        }
        binding.searchET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s?.isEmpty()!!){
                    callApi()
                } else {
                    adapter.filter.filter(s)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setUpRecyclerView() {
        adapter = CoinAdapter(this@MainActivity, ArrayList())
        layoutManager = GridLayoutManager(this@MainActivity, 2).apply {

        }
        binding.coinRecyclerView.adapter = adapter
        binding.coinRecyclerView.layoutManager = layoutManager
        /*
        binding.coinRecyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.coinRecyclerView.context,
                (GridLayoutManager(this, 1)).orientation
            )
        )
        */
        binding.coinRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount - 1) {
                    page += 1
                    coinListViewModel.getAllCoins(page.toString())
                    callApi()
                }
            }

        })
    }

    private fun callApi() {
        lifecycleScope.launch {
            coinListViewModel.getAllCoins(page.toString())
            coinListViewModel._coinListValue.collectLatest { coinListState ->
                if (coinListState.isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.searchPanel.visibility = View.GONE
                } else {
                    if (coinListState.error.isNotBlank()) {
                        binding.progressBar.visibility = View.GONE
                        binding.searchPanel.visibility = View.GONE
                        Toast.makeText(this@MainActivity, "an unexpected error occurred, please try again : "+coinListState.error, Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        binding.searchPanel.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        tempCoinList.addAll(coinListState.coinList)
                        adapter.setData(tempCoinList as ArrayList<Coin>)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu?.findItem(R.id.menuSearch)
        val searchView = search?.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText?.isEmpty()!!){
            adapter.setData(tempCoinList)
        } else {
            adapter.filter.filter(newText)
        }
        return true
    }

}