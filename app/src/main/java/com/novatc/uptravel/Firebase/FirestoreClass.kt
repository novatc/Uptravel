package com.novatc.uptravel.Firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.novatc.uptravel.activities.SignInActivity
import com.novatc.uptravel.activities.SignUpActivity
import com.novatc.uptravel.constants.Constants

class FirestoreClass {
    private val mFirestore = Firebase.firestore


    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser!= null){
            currentUserID = currentUser.uid
        }
        return currentUserID


    }

    fun registerUser(activity: SignUpActivity, userInfo: com.novatc.uptravel.model.User) {
        mFirestore.collection(Constants.USER).document(getCurrentUserID())
            .set(userInfo, SetOptions.merge()).addOnSuccessListener {
                activity.userRegisteredSuccess()
            }.addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName, "Error in Firestore (Register) ", e)
            }
    }


    fun signInUser(activity: SignInActivity) {
        mFirestore.collection(Constants.USER).document(getCurrentUserID()).get()
            .addOnSuccessListener { document ->
                Log.e(activity.javaClass.simpleName, document.toString())

                var loggedInUser = document.toObject(com.novatc.uptravel.model.User::class.java)
                activity.signInSuccess(loggedInUser!!)
            }.addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName, "Error in Firestore (LogIn) ", e)
            }
    }
}