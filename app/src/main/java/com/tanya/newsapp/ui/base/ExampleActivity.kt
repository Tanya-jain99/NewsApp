package com.tanya.newsapp.ui.base

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tanya.newsapp.R

class ExampleActivity : AppCompatActivity() {

    private var testKClass = ExampleActivity::class
    private var testClass = ExampleActivity::class.java
     companion object {
         private const val EXTRA_USER_ID = "user_id"
         fun getStartIntent(context : Context, userId : Int)  =
               Intent(context, ExampleActivity::class.java)
                 .apply {
                     putExtra(EXTRA_USER_ID, 1)
                 }

     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
    }

}
