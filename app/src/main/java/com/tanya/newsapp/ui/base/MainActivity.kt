package com.tanya.newsapp.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.tanya.newsapp.R
import com.tanya.newsapp.ui.view.ErrorFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment()
        Log.println(Log.DEBUG, "MainActivity", "Hello")

        Toast.makeText(this@MainActivity, "Hello", Toast.LENGTH_LONG).show()
    }

    private fun addFragment() {
        if(supportFragmentManager.findFragmentByTag(MainFragment.TAG) == null){
            supportFragmentManager.beginTransaction().add(R.id.fragment_container,
                MainFragment.newInstance(), MainFragment.TAG).commit()
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.findFragmentByTag(ErrorFragment.TAG) != null){
            supportFragmentManager.popBackStack()
        }
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

}