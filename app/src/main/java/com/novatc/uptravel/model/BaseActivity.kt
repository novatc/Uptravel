package com.novatc.uptravel.model

import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

open class BaseActivity : AppCompatActivity() {

    fun hideStatusBar() {
        //if Andorid version is too old, use deprecated features to get the same result as with a
        //modern skd
        if (Build.VERSION.SDK_INT < 30) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        } else {
            window.setDecorFitsSystemWindows(false)
            val controler = window.insetsController
            if (controler != null) {
                controler.hide(WindowInsets.Type.statusBars())
                controler.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_SWIPE
            }
        }
    }

    fun validateForm(name: String, mail: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                Toast.makeText(this, "Bitte Namen angeben", Toast.LENGTH_LONG)
                false
            }
            TextUtils.isEmpty(mail) -> {
                Toast.makeText(this, "Bitte Mail angeben", Toast.LENGTH_LONG)
                false
            }
            TextUtils.isEmpty(mail) -> {
                Toast.makeText(this, "Bitte Passwort angeben", Toast.LENGTH_LONG)
                false
            }
            else -> {
                true
            }
        }
    }

    fun validateForm(mail: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(mail) -> {
                Toast.makeText(this, "Bitte Mail angeben", Toast.LENGTH_LONG)
                false
            }
            TextUtils.isEmpty(mail) -> {
                Toast.makeText(this, "Bitte Passwort angeben", Toast.LENGTH_LONG)
                false
            }
            else -> {
                true
            }
        }
    }
    fun getCurrentUserID(): String{
        return FirebaseAuth.getInstance().currentUser!!.uid
    }
}