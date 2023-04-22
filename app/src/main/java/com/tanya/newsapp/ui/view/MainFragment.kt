package com.tanya.newsapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tanya.newsapp.R
import com.tanya.newsapp.databinding.FragmentMainBinding
import com.tanya.newsapp.utils.AppConstant.COUNTRY_US
import com.tanya.newsapp.utils.AppConstant.DEFAULT_CATEGORY


class MainFragment : Fragment() {


    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        binding.topHeadlineBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container,
                NewsListFragment.newInstance(Pair(COUNTRY_US, DEFAULT_CATEGORY)), NewsListFragment.TAG)?.
            addToBackStack(NewsListFragment.TAG)?.commit()
        }
        binding.countriesBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container,
                CountryFragment.newInstance(),
                CountryFragment.TAG)?.addToBackStack(CountryFragment.TAG)?.commit()
        }
        binding.languagesBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container,
                LanguageFragment.newInstance(),
                LanguageFragment.TAG)?.addToBackStack(LanguageFragment.TAG)?.commit()
        }
        binding.newsSourcesBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container,
            NewsSourceFragment.newInstance(),
            NewsSourceFragment.TAG)?.addToBackStack(NewsSourceFragment.TAG)?.commit()
        }
        binding.searchBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container,
                SearchFragment.newInstance(),
                SearchFragment.TAG)?.addToBackStack(SearchFragment.TAG)?.commit()

        }
        return binding.root
    }



    companion object {

        const val TAG = "MainFragment"

        fun newInstance(): MainFragment {
            val args = Bundle()
            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }
}