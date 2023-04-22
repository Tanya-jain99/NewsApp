package com.tanya.newsapp.ui.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tanya.newsapp.NewsApplication
import com.tanya.newsapp.data.model.Language
import com.tanya.newsapp.databinding.FragmentLanguageBinding
import com.tanya.newsapp.di.component.DaggerLanguageComponent
import com.tanya.newsapp.di.module.LanguageModule
import com.tanya.newsapp.ui.adapter.LanguageAdapter
import com.tanya.newsapp.ui.base.BaseFragment
import com.tanya.newsapp.ui.viewmodel.LanguageViewModel
import com.tanya.newsapp.utils.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject


class LanguageFragment : BaseFragment<LanguageViewModel, FragmentLanguageBinding>() {

    @Inject
    lateinit var languageAdapter: LanguageAdapter


    companion object {
        const val TAG: String = "LanguageFragment"
        fun newInstance() =
            LanguageFragment().apply {
                arguments = Bundle()
            }
    }

    override fun setupUI() {
        val recyclerView: RecyclerView = binding.langList
        recyclerView.apply{
            adapter = languageAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.langSrcList
                    .collect {
                        when (it) {
                            is UiState.Success -> {
                                binding.progressBar.visibility = View.GONE
                                binding.langList.visibility = View.VISIBLE
                                it.data?.let { list -> renderList(list) }
                            }
                           is  UiState.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.langList.visibility = View.GONE

                            }
                            is UiState.Error -> {
                                ErrorFragment.addFragment(activity)
                            }
                        }
                    }
            }
        }
    }

    override fun injectDependencies(){
        DaggerLanguageComponent
            .builder()
            .languageModule(LanguageModule(this))
            .applicationComponent((requireContext().applicationContext as NewsApplication)
                .applicationComponent)
            .build()
            .injectDependencies(this)
    }

    private fun renderList(dataList : List<Language>) {
        languageAdapter.apply{
            addData(dataList)
            notifyDataSetChanged()
        }
    }

    override fun getViewBinding(): FragmentLanguageBinding {
        return FragmentLanguageBinding.inflate(layoutInflater)
    }
}