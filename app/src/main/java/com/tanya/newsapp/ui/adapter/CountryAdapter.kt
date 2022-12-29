package com.tanya.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tanya.newsapp.data.model.Country
import com.tanya.newsapp.databinding.CategoryItemLayoutBinding
import com.tanya.newsapp.ui.view.NewsListFragment


class CountryAdapter(
    private val countryList: ArrayList<Country>
) : RecyclerView.Adapter<CountryAdapter.DataViewHolder>() {
    class DataViewHolder(private val binding : CategoryItemLayoutBinding) :
        ViewHolder(binding.root) {
        private val CATEGORY : String = "country"
       fun bind(item : Country){
            binding.button.text = item.name
           itemView.setOnClickListener {
               val activity = it.context as AppCompatActivity
               NewsListFragment.addFragment(activity,
                   Pair(item.id, CATEGORY))
           }
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(CategoryItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(countryList[position])
    }

    override fun getItemCount(): Int = countryList.size

    fun addData(list: List<Country>) {
        countryList.addAll(list)
    }
}