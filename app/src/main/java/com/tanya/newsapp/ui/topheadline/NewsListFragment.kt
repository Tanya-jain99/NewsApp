package com.tanya.newsapp.ui.topheadline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanya.newsapp.NewsApplication
import com.tanya.newsapp.data.model.Article
import com.tanya.newsapp.databinding.FragmentNewsListBinding
import com.tanya.newsapp.di.component.DaggerNewsListComponent
import com.tanya.newsapp.di.module.NewsListModule
import com.tanya.newsapp.utils.Status
import kotlinx.coroutines.launch
import javax.inject.Inject


class NewsListFragment : Fragment() {

    @Inject
    lateinit var newsListViewModel: TopHeadlineViewModel

    @Inject
    lateinit var adapter: TopHeadlineAdapter

    private lateinit var binding: FragmentNewsListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentNewsListBinding.inflate(layoutInflater)
        setupUI()
        return binding.root
    }

    companion object {

        const val TAG = "NewsListFragment"

        fun newInstance(): NewsListFragment {
            val args = Bundle()
            val fragment = NewsListFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setupObserver()
    }

    private fun setupUI() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsListViewModel.articleList.collect {
                    when (it.status) {
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            it.data?.let { newsList -> renderList(newsList) }
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }
                        Status.ERROR -> {
                            //Handle Error
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(activity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(articleList: List<Article>) {
        adapter.addData(articleList)
        adapter.notifyDataSetChanged()
    }

    private fun injectDependencies() {
        DaggerNewsListComponent
            .builder()
            .newsListModule(NewsListModule(this))
            .applicationComponent((requireContext().applicationContext as NewsApplication).applicationComponent)
            .build()
            .inject(this)
    }

}