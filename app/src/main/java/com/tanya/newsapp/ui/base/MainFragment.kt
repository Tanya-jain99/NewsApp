package com.tanya.newsapp.ui.base

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.util.Log.DEBUG
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.tanya.newsapp.R
import com.tanya.newsapp.databinding.FragmentMainBinding
import com.tanya.newsapp.databinding.FragmentNewsListBinding
import com.tanya.newsapp.ui.topheadline.NewsListFragment
import javax.inject.Inject


class MainFragment : Fragment() {


    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        binding.topHeadlineBtn.setOnClickListener {
            Log.println(Log.DEBUG, "MainFragment", "Hey")

            Toast.makeText(requireContext(), "Heyy", Toast.LENGTH_LONG).show()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container,
                NewsListFragment.newInstance(), NewsListFragment.TAG)?.commit()
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.println(Log.DEBUG, "MainFragment", "Hello")

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