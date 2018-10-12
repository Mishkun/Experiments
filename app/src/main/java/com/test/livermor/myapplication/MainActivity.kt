package com.test.livermor.myapplication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.test.livermor.myapplication.coordinator.ScrollingActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, ScrollingActivity::class.java))
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
