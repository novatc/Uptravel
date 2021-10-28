package com.novatc.uptravel.activities

import android.content.Intent
import android.os.Bundle
import com.novatc.uptravel.R
import com.novatc.uptravel.model.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        super.hideStatusBar()

        fab_mainActivity_add_Place.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AddPlaceActivity::class.java
                )
            )
        }
    }
}