package com.tanya.newsapp.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanya.newsapp.NewsApplication
import com.tanya.newsapp.R
import com.tanya.newsapp.data.model.Article
import com.tanya.newsapp.databinding.FragmentNewsListBinding
import com.tanya.newsapp.di.component.DaggerNewsListComponent
import com.tanya.newsapp.di.module.NewsListModule
import com.tanya.newsapp.ui.adapter.TopHeadlineAdapter
import com.tanya.newsapp.ui.viewmodel.TopHeadlineViewModel
import com.tanya.newsapp.utils.AppConstant
import com.tanya.newsapp.data.api.NetworkHelper
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
                              savedInstanceState: Bundle?): View {
        binding = FragmentNewsListBinding.inflate(layoutInflater)
        setupUI()
        return binding.root
    }

    companion object {

        const val TAG = "NewsListFragment"
        private const val EXTRA_CATEGORY = "category"
        private const val EXTRA_ID = "id"
        fun newInstance(param : Pair<String, String>): NewsListFragment {
            val args = Bundle()
            args.apply {
                putString(EXTRA_ID, param.first)
                putString(EXTRA_CATEGORY, param.second)
            }
            val fragment = NewsListFragment()
            fragment.arguments = args
            return fragment
        }

        fun addFragment(activity: FragmentActivity?, param: Pair<String, String>){
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.fragment_container,
                newInstance(param) )?.addToBackStack(TAG)?.commit()
            }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setupObserver()
        getArgumentsAndFetchData()
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
                            binding.apply {
                                progressBar.visibility = View.VISIBLE
                                recyclerView.visibility = View.GONE
                            }
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            ErrorFragment.addFragment(activity)
                        }
                    }
                }
            }
        }
    }

    private fun renderList(articleList: List<Article>) {
        adapter.apply{
            addData(articleList)
            notifyDataSetChanged()
        }
    }

    private fun injectDependencies() {
        DaggerNewsListComponent
            .builder()
            .newsListModule(NewsListModule(this))
            .applicationComponent((requireContext().applicationContext as NewsApplication).applicationComponent)
            .build()
            .inject(this)
    }

    private fun getArgumentsAndFetchData(){
        val data: Map<String?, String?> = mapOf(
            Pair(arguments?.getString(EXTRA_CATEGORY, AppConstant.DEFAULT_CATEGORY),
                arguments?.getString(EXTRA_ID, AppConstant.COUNTRY)))
        if(NetworkHelper.isOnline(requireContext())) {
            newsListViewModel.fetchNews(data)
        } else {
            Log.e("NewsLisFragment::class","Not connected to internet")
            newsListViewModel.fetchNewsFromDB(data)
        }
    }

}