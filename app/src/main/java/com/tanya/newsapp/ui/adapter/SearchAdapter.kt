package com.tanya.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tanya.newsapp.data.api.models.NetworkArticle
import com.tanya.newsapp.databinding.SearchItemLayoutBinding
import com.tanya.newsapp.di.FragmentScope

@FragmentScope
class SearchAdapter(private val searchList : ArrayList<NetworkArticle>):
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    class SearchViewHolder(private val binding : SearchItemLayoutBinding): ViewHolder(binding.root) {
        fun bind(item : NetworkArticle){
            binding.headline.text = item.title

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(searchList[position])
    }

    override fun getItemCount(): Int = searchList.size

    fun replaceData(list: List<NetworkArticle>) {
        searchList.clear()
        searchList.addAll(list)
    }

    fun clear(){
        if(searchList.isNotEmpty()) {
            searchList.clear()
            notifyDataSetChanged()
        }
    }

}