package com.novatc.uptravel.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.novatc.uptravel.R
import com.novatc.uptravel.model.BaseActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        super.hideStatusBar()
        FirebaseApp.initializeApp(this)
        btn_sign_in.setOnClickListener { signInValidUser() }
    }

    private fun signInValidUser(){
        val mail: String = et_sign_in_mail.text.toString().trim { it <= ' ' }
        val password: String = et_sign_in_password.text.toString().trim { it <= ' ' }
        if (super.validateForm(mail, password)){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(mail, password).addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    Toast.makeText(
                        this@SignInActivity,
                        "Erfolgreich angemeldet",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                }else{
                    Toast.makeText(
                        this@SignInActivity,
                        "${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    fun userRegisteredSuccess(){
        Toast.makeText(this,"Successfully registered", Toast.LENGTH_LONG).show()
        FirebaseAuth.getInstance().signOut()
        finish()
    }



}