package com.tanya.newsapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanya.newsapp.NewsApplication
import com.tanya.newsapp.data.model.Source
import com.tanya.newsapp.databinding.FragmentNewsBaseBinding
import com.tanya.newsapp.di.component.DaggerNewsBaseComponent
import com.tanya.newsapp.di.module.NewsSourceModule
import com.tanya.newsapp.ui.adapter.NewsCategoryAdapter
import com.tanya.newsapp.ui.viewmodel.CategoryViewModel
import com.tanya.newsapp.utils.Status
import kotlinx.coroutines.launch
import javax.inject.Inject


class NewsSourceFragment : Fragment() {

    @Inject
    lateinit var categoryListViewModel: CategoryViewModel

    @Inject
    lateinit var adapter: NewsCategoryAdapter

    private lateinit var binding: FragmentNewsBaseBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentNewsBaseBinding.inflate(layoutInflater)
        setupUI()
        return binding.root
    }

    companion object {

        const val TAG = "NewsSourceFragment"
        fun newInstance(): NewsSourceFragment {
            val args = Bundle()
            val fragment = NewsSourceFragment()
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
        val recyclerView = binding.categoryList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }



    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                categoryListViewModel.srcList.collect {
                    when (it.status) {
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            it.data?.let { categoryList -> renderList(categoryList) }
                            binding.categoryList.visibility = View.VISIBLE
                        }
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.categoryList.visibility = View.GONE
                        }
                        Status.ERROR -> {
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

    private fun injectDependencies() {
        DaggerNewsBaseComponent
            .builder()
            .newsSourceModule(NewsSourceModule(this))
            .applicationComponent((requireContext().applicationContext as NewsApplication).applicationComponent)
            .build()
            .injectDependencies(this)
    }


}