package com.tanya.newsapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.tanya.newsapp.R
import com.tanya.newsapp.databinding.FragmentErrorBinding
import com.tanya.newsapp.ui.base.MainActivity


class ErrorFragment : Fragment() {

    private lateinit var binding: FragmentErrorBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentErrorBinding.inflate(layoutInflater)
        binding.tryAgain.setOnClickListener {
            val activity = activity as MainActivity
            activity.onBackPressed()
        }
        return binding.root
    }

    companion object {
        const val TAG = "ErrorFragment"

        fun newInstance() : ErrorFragment{
            val args = Bundle()
            val fragment = ErrorFragment()
            fragment.arguments = args
            return fragment
        }
        fun addFragment(activity: FragmentActivity?){
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, newInstance(), TAG)?.addToBackStack(TAG)?.commit()
        }
    }
}