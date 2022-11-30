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
import androidx.recyclerview.widget.RecyclerView
import com.tanya.newsapp.NewsApplication
import com.tanya.newsapp.R
import com.tanya.newsapp.data.model.Country
import com.tanya.newsapp.databinding.FragmentCountryBinding
import com.tanya.newsapp.di.component.DaggerCountryComponent
import com.tanya.newsapp.di.module.CountryModule
import com.tanya.newsapp.ui.adapter.CountryAdapter
import com.tanya.newsapp.ui.viewmodel.CountryViewModel
import com.tanya.newsapp.utils.Status
import kotlinx.coroutines.launch
import javax.inject.Inject


class CountryFragment : Fragment() {

    private lateinit var binding: FragmentCountryBinding

    @Inject
    lateinit var countryAdapter : CountryAdapter

    @Inject
    lateinit var countryViewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setUpObserver()
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countryViewModel.countrySrcList
                    .collect {
                        when (it.status) {
                            Status.SUCCESS -> {
                                binding.progressBar.visibility = View.GONE
                                binding.countryList.visibility = View.VISIBLE

                                it.data?.let { list -> renderList(list) }
                            }
                            Status.LOADING -> {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.countryList.visibility = View.GONE
                            }
                            Status.ERROR -> {
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

    private fun renderList(dataList : List<Country>) {
        countryAdapter.apply{
            addData(dataList)
            notifyDataSetChanged()
        }
    }

    private fun setUpUI() {
        val recyclerView: RecyclerView = binding.countryList
        recyclerView.apply{
            adapter = countryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryBinding.inflate(layoutInflater)
        setUpUI()
        return binding.root
    }

    companion object {
        const val TAG: String = "CountryFragment"
        fun newInstance() =
            CountryFragment().apply {
                arguments = Bundle()
            }
    }

    private fun injectDependencies(){
        DaggerCountryComponent
        .builder()
        .countryModule(CountryModule(this))
        .applicationComponent((requireContext().applicationContext as NewsApplication)
            .applicationComponent)
        .build()
        .injectDependencies(this)
    }
}