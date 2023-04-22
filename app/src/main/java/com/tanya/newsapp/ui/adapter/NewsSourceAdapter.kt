package com.tanya.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.tanya.newsapp.databinding.CategoryItemLayoutBinding
import com.tanya.newsapp.data.api.models.Source
import com.tanya.newsapp.ui.view.NewsListFragment

class NewsSourceAdapter (
    private val categoryList: ArrayList<Source>
) : RecyclerView.Adapter<NewsSourceAdapter.DataViewHolder>() {


    class DataViewHolder(private val binding: CategoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val CATEGORY = "sources"
        fun bind(item: Source) {
            binding.button.text = item.name
            itemView.setOnClickListener {
                val activity = it.context as AppCompatActivity
                NewsListFragment.addFragment(activity,
                    Pair(item.id.toString(), CATEGORY))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            CategoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(categoryList[position])

    fun addData(list: List<Source>) {
        categoryList.addAll(list)
    }

}