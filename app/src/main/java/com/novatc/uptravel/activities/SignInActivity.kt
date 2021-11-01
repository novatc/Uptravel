package com.novatc.uptravel.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.novatc.uptravel.Firebase.FirestoreClass
import com.novatc.uptravel.R
import com.novatc.uptravel.model.BaseActivity
import com.novatc.uptravel.model.User
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
                    FirestoreClass().signInUser(this)
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

    fun signInSuccess(loggedInUse: User) {
        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
        finish()
    }


}