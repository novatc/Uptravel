package com.novatc.uptravel.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.novatc.uptravel.R
import com.novatc.uptravel.model.BaseActivity
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        super.hideStatusBar()

        btn_into_skip.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}