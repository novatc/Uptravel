package com.novatc.uptravel.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.novatc.uptravel.Firebase.FirestoreClass
import com.novatc.uptravel.R
import com.novatc.uptravel.model.BaseActivity

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        super.hideStatusBar()

        // Changes the activity to the intro activity after 2.5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val currentUserID = FirestoreClass().getCurrentUserID()
            if (currentUserID.isNotEmpty()) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
            }

            finish()
        }, 2500)
    }
}