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
import com.tanya.newsapp.data.model.Language
import com.tanya.newsapp.databinding.FragmentLanguageBinding
import com.tanya.newsapp.di.component.DaggerLanguageComponent
import com.tanya.newsapp.di.module.LanguageModule
import com.tanya.newsapp.ui.adapter.LanguageAdapter
import com.tanya.newsapp.ui.viewmodel.LanguageViewModel
import com.tanya.newsapp.utils.Status
import kotlinx.coroutines.launch
import javax.inject.Inject


class LanguageFragment : Fragment() {

    private lateinit var binding: FragmentLanguageBinding

    @Inject
    lateinit var languageAdapter: LanguageAdapter

    @Inject
    lateinit var languageViewModel : LanguageViewModel

    companion object {
        const val TAG: String = "LanguageFragment"
        fun newInstance() =
            LanguageFragment().apply {
                arguments = Bundle()
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLanguageBinding.inflate(layoutInflater)
        setUpUI()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setUpObserver()
    }

    private fun setUpUI() {
        val recyclerView: RecyclerView = binding.langList
        recyclerView.apply{
            adapter = languageAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                languageViewModel.langSrcList
                    .collect {
                        when (it.status) {
                            Status.SUCCESS -> {
                                binding.progressBar.visibility = View.GONE
                                binding.langList.visibility = View.VISIBLE
                                it.data?.let { list -> renderList(list) }
                            }
                            Status.LOADING -> {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.langList.visibility = View.GONE

                            }
                            Status.ERROR -> {
                                ErrorFragment.addFragment(activity)
                            }
                        }
                    }
            }
        }
    }

    private fun injectDependencies(){
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
}