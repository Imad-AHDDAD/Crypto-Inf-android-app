package com.example.coin.presentation.coinList

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.coin.R
import com.example.coin.databinding.CoinItemBinding
import com.example.coin.domain.model.Coin
import com.example.coin.presentation.coin.CoinActivity
import com.squareup.picasso.Picasso

class CoinAdapter(private val context: Context, private var coinList: ArrayList<Coin>)
    : RecyclerView.Adapter<CoinAdapter.CoinViewHolder>(), Filterable {

    lateinit var filteredList: ArrayList<Coin>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinAdapter.CoinViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.coin_item, parent, false)
        return CoinViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinAdapter.CoinViewHolder, position: Int) {
        val coin = coinList[position]
        holder.binding.coinName.text = coin.name
        holder.binding.coinLinearLayout.setOnClickListener {
            val intent = Intent(context, CoinActivity::class.java)
            intent.putExtra("id", coin.id)
            context.startActivity(intent)
        }
        Picasso.get().load(coin.image).into(holder.binding.coinImageView)
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    fun setData(coins: ArrayList<Coin>) {
        this.filteredList = coins
        this.coinList = coins
        notifyDataSetChanged()
    }

    inner class CoinViewHolder(view: View) : ViewHolder(view) {
        val binding: CoinItemBinding = CoinItemBinding.bind(itemView)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val string = p0?.toString()?: ""
                if(string.isNotEmpty()) {
                    var arrayList = arrayListOf<Coin>()
                    filteredList.filter {
                        it.name.lowercase().contains(string.lowercase())
                    }.forEach {
                        arrayList.add(it)
                    }
                    filteredList.clear()
                    filteredList.addAll(arrayList)
                } else {
                    filteredList = coinList
                }
                return FilterResults().apply {
                    this.values = filteredList
                }
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                if(p1?.values == null) {
                    ArrayList<Coin>()
                }else {
                    setData(filteredList)
                }
            }

        }
    }
}