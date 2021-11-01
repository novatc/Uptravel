package com.novatc.uptravel.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.novatc.uptravel.Firebase.FirestoreClass
import com.novatc.uptravel.R
import com.novatc.uptravel.model.BaseActivity
import com.novatc.uptravel.model.User
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        super.hideStatusBar()
        FirebaseApp.initializeApp(this)

        btn_sign_up_sign_up.setOnClickListener {
            registerUser()
        }
    }

    fun userRegisteredSuccess() {
        Toast.makeText(this, "Successfully registered", Toast.LENGTH_LONG).show()
        FirebaseAuth.getInstance().signOut()
        finish()
    }

    private fun registerUser() {
        val name: String = et_sign_up_name.text.toString().trim { it <= ' ' }
        val mail: String = et_sign_up_email.text.toString().trim { it <= ' ' }
        val password: String = et_sign_up_password.text.toString().trim { it <= ' ' }

        if (validateForm(name, mail, password)) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val registeredMail = firebaseUser.email!!

                        val  user = User(firebaseUser.uid, name, registeredMail)
                        FirestoreClass().registerUser(this@SignUpActivity, user)


                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this@SignUpActivity,
                            "${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()


                    }
                }
        }

    }


}