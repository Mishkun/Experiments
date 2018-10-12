package com.test.livermor.myapplication.coordinator

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.test.livermor.myapplication.R
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        fab.setOnClickListener { view ->
            val visibility = if (tvTop2.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            Snackbar.make(view, "tvTop2.visibility will be set to $visibility", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            tvTop2.visibility = visibility
        }
    }
}
