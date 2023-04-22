package com.tanya.newsapp.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tanya.newsapp.databinding.TopHeadlineItemLayoutBinding
import com.tanya.newsapp.data.model.Article

class NewsListAdapter : PagingDataAdapter<Article, NewsListAdapter.DataViewHolder>(COMPARATOR) {

    class DataViewHolder(private val binding: TopHeadlineItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.textViewTitle.text = article.title
            binding.textViewDescription.text = article.description
            Glide.with(binding.imageViewBanner.context)
                .load(article.urlToImage)
                .into(binding.imageViewBanner)
            itemView.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(it.context, Uri.parse(article.url))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            TopHeadlineItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null) {
            holder.bind(article)
        }
    }
    companion object{
        private val COMPARATOR = object: DiffUtil.ItemCallback<Article>(){

            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

}