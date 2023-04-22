package com.tanya.newsapp.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanya.newsapp.NewsApplication
import com.tanya.newsapp.data.api.models.NetworkArticle
import com.tanya.newsapp.databinding.FragmentSearchBinding
import com.tanya.newsapp.di.component.DaggerSearchComponent
import com.tanya.newsapp.di.module.SearchModule
import com.tanya.newsapp.ui.adapter.SearchAdapter
import com.tanya.newsapp.ui.viewmodel.SearchViewModel
import com.tanya.newsapp.utils.UiState
import com.tanya.newsapp.utils.getQueryTextChangeStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchFragment : Fragment() {

    @Inject
    lateinit var searchViewModel: SearchViewModel

    @Inject
    lateinit var searchAdapter: SearchAdapter

    private lateinit var binding: FragmentSearchBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        setUpObserver()
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.searchList
                    .collect {
                        when (it) {
                            is UiState.Success -> {
                                binding.recyclerView.visibility = View.VISIBLE
                                binding.progressBar.visibility = View.GONE
                                renderList(it.data)
                                Log.i("SearchFragment::class", "Success")
                            }
                            is UiState.Loading -> {
                                Log.i("SearchFragment::class", "Loading")
                                binding.apply {
                                    progressBar.visibility = View.VISIBLE
                                    recyclerView.visibility = View.GONE
                                }
                            }
                            is UiState.Error -> {
                                binding.recyclerView.visibility = View.GONE
                                searchAdapter.clear()
                                Log.i("SearchFragment::class", "Fail")
                                ErrorFragment.addFragment(activity)
                            }
                        }

                    }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(LayoutInflater.from(requireContext()))
        binding.recyclerView.adapter = searchAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchView.clearFocus()
        searchViewModel.getSearchResult(binding.searchView.getQueryTextChangeStateFlow())
        return binding.root
    }

    companion object {
        const val TAG: String = "SearchFragment"

        fun newInstance() = SearchFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    private fun renderList(articleList: List<NetworkArticle>) {
        searchAdapter.apply{
            replaceData(articleList)
            notifyDataSetChanged()
        }
    }


    fun injectDependencies(){
         DaggerSearchComponent.builder()
             .searchModule(SearchModule(this))
             .applicationComponent((requireContext().applicationContext as NewsApplication).applicationComponent)
             .build()
             .injectDependencies(this)
    }


}