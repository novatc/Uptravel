package com.novatc.uptravel.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.novatc.uptravel.Firebase.FirestoreClass
import com.novatc.uptravel.R
import com.novatc.uptravel.model.BaseActivity
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        super.hideStatusBar()

        btn_intro_log_in.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        btn_intro_sign_up.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }


    }

}