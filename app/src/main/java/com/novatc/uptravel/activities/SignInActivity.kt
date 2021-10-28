package com.novatc.uptravel.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.novatc.uptravel.R
import com.novatc.uptravel.model.BaseActivity

class SignInActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        super.hideStatusBar()
    }
}