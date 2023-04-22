package com.tanya.newsapp.ui.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanya.newsapp.NewsApplication
import com.tanya.newsapp.R
import com.tanya.newsapp.databinding.FragmentNewsListBinding
import com.tanya.newsapp.di.component.DaggerNewsListComponent
import com.tanya.newsapp.di.module.NewsListModule
import com.tanya.newsapp.ui.adapter.NewsListAdapter
import com.tanya.newsapp.ui.base.BaseFragment
import com.tanya.newsapp.ui.viewmodel.NewsListViewModel
import com.tanya.newsapp.utils.AppConstant
import com.tanya.newsapp.utils.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject


class NewsListFragment : BaseFragment<NewsListViewModel, FragmentNewsListBinding>() {


    @Inject
    lateinit var adapter: NewsListAdapter


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

    override fun setupUI() {
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

    override fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articleList.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                            it.data?.let { newsList -> adapter.submitData(newsList) }
                        }
                        is UiState.Loading -> {
                            binding.apply {
                                progressBar.visibility = View.VISIBLE
                                recyclerView.visibility = View.GONE
                            }
                        }
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            ErrorFragment.addFragment(activity)
                        }
                    }
                }
            }
        }
    }


    override fun injectDependencies() {
        DaggerNewsListComponent
            .builder()
            .newsListModule(NewsListModule(this))
            .applicationComponent((requireContext().applicationContext as NewsApplication).applicationComponent)
            .build()
            .inject(this)
    }

    private fun getArgumentsAndFetchData(){
        val data: LinkedHashMap<String, String> = LinkedHashMap()
        arguments?.let{
            data[it.getString(EXTRA_CATEGORY, AppConstant.DEFAULT_CATEGORY)] =
                it.getString(EXTRA_ID, AppConstant.COUNTRY_US)
        }
        viewModel.fetchNews(data)
    }

    override fun getViewBinding(): FragmentNewsListBinding {
        return FragmentNewsListBinding.inflate(layoutInflater)
    }

}