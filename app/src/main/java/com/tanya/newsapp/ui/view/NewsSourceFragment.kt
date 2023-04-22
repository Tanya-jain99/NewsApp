package com.tanya.newsapp.ui.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanya.newsapp.NewsApplication
import com.tanya.newsapp.data.api.models.Source
import com.tanya.newsapp.databinding.FragmentNewsBaseBinding
import com.tanya.newsapp.di.component.DaggerNewsBaseComponent
import com.tanya.newsapp.di.module.NewsSourceModule
import com.tanya.newsapp.ui.adapter.NewsSourceAdapter
import com.tanya.newsapp.ui.base.BaseFragment
import com.tanya.newsapp.ui.viewmodel.CategoryViewModel
import com.tanya.newsapp.utils.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject


class NewsSourceFragment : BaseFragment<CategoryViewModel, FragmentNewsBaseBinding>() {

    @Inject
    lateinit var adapter: NewsSourceAdapter


    companion object {

        const val TAG = "NewsSourceFragment"
        fun newInstance(): NewsSourceFragment {
            val args = Bundle()
            val fragment = NewsSourceFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun setupUI() {
        val recyclerView = binding.categoryList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }



    override fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.srcList.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            it.data?.let { categoryList -> renderList(categoryList) }
                            binding.categoryList.visibility = View.VISIBLE
                        }
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.categoryList.visibility = View.GONE
                        }
                        is UiState.Error -> {
                            //Handle Error
                            binding.progressBar.visibility = View.GONE
                            ErrorFragment.addFragment(activity)
                        }
                    }
                }
            }
        }
    }

    private fun renderList(categoryList: List<Source>) {
        adapter.apply {
            addData(categoryList)
            notifyDataSetChanged()
        }
    }

    override fun injectDependencies() {
        DaggerNewsBaseComponent
            .builder()
            .newsSourceModule(NewsSourceModule(this))
            .applicationComponent((requireContext().applicationContext as NewsApplication).applicationComponent)
            .build()
            .injectDependencies(this)
    }

    override fun getViewBinding(): FragmentNewsBaseBinding {
       return  FragmentNewsBaseBinding.inflate(layoutInflater)
    }


}