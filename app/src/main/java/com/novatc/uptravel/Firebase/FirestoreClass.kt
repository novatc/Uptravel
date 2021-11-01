package com.novatc.uptravel.Firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.novatc.uptravel.activities.SignUpActivity
import com.novatc.uptravel.constants.Constants

class FirestoreClass {
    private val mFirestore = Firebase.firestore

    fun registerUser(activity: SignUpActivity, userInfo: User) {
        mFirestore.collection(Constants.USER).document(getCurrentUserID())
            .set(userInfo, SetOptions.merge()).addOnSuccessListener {
                activity.userRegisteredSuccess()
            }
    }

    fun getCurrentUserID(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }
}