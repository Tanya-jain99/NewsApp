package com.tanya.newsapp.ui.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tanya.newsapp.NewsApplication
import com.tanya.newsapp.R
import com.tanya.newsapp.data.model.Country
import com.tanya.newsapp.databinding.FragmentCountryBinding
import com.tanya.newsapp.di.component.DaggerCountryComponent
import com.tanya.newsapp.di.module.CountryModule
import com.tanya.newsapp.ui.adapter.CountryAdapter
import com.tanya.newsapp.ui.base.BaseFragment
import com.tanya.newsapp.ui.viewmodel.CountryViewModel
import com.tanya.newsapp.utils.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject


class CountryFragment : BaseFragment<CountryViewModel, FragmentCountryBinding>() {


    @Inject
    lateinit var countryAdapter : CountryAdapter



    private fun renderList(dataList : List<Country>) {
        countryAdapter.apply{
            addData(dataList)
            notifyDataSetChanged()
        }
    }


    companion object {
        const val TAG: String = "CountryFragment"
        fun newInstance() =
            CountryFragment().apply {
                arguments = Bundle()
            }
    }

    override fun injectDependencies(){
        DaggerCountryComponent
        .builder()
        .countryModule(CountryModule(this))
        .applicationComponent((requireContext().applicationContext as NewsApplication)
            .applicationComponent)
        .build()
        .injectDependencies(this)
    }

    override fun getViewBinding(): FragmentCountryBinding {
        return FragmentCountryBinding.inflate(layoutInflater)
    }

    override fun setupUI() {
        val recyclerView: RecyclerView = binding.countryList
        recyclerView.apply{
            adapter = countryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.countrySrcList
                    .collect {
                        when (it) {
                            is UiState.Success -> {
                                binding.progressBar.visibility = View.GONE
                                binding.countryList.visibility = View.VISIBLE

                                it.data?.let { list -> renderList(list) }
                            }
                            is UiState.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.countryList.visibility = View.GONE
                            }
                            is UiState.Error -> {
                                activity?.supportFragmentManager
                                    ?.beginTransaction()
                                    ?.replace(
                                        R.id.fragment_container,
                                        ErrorFragment.newInstance(),
                                        ErrorFragment.TAG
                                    )
                                    ?.addToBackStack(ErrorFragment.TAG)
                                    ?.commit()
                            }
                        }
                    }
            }
        }
    }
}