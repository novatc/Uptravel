package com.novatc.uptravel.Firebase

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.novatc.uptravel.activities.AddPlaceActivity
import com.novatc.uptravel.activities.MainActivity
import com.novatc.uptravel.activities.SignInActivity
import com.novatc.uptravel.activities.SignUpActivity
import com.novatc.uptravel.constants.Constants
import com.novatc.uptravel.model.PlacesModel

class FirestoreClass {
    private val mFirestore = Firebase.firestore


    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID


    }

    fun getCurrentUser(): com.novatc.uptravel.model.User {
        var user = com.novatc.uptravel.model.User()

        mFirestore.collection(Constants.USER).document(getCurrentUserID()).get()
            .addOnSuccessListener { document ->
                val currentUser = document.toObject(com.novatc.uptravel.model.User::class.java)
                Log.e("FIREBASE USER ", "User:  $currentUser")
                user = currentUser!!
            }
        return user
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
                val loggedInUser = document.toObject(com.novatc.uptravel.model.User::class.java)
                activity.signInSuccess(loggedInUser!!)
            }.addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName, "Error in Firestore (LogIn) ", e)
            }
    }

    fun addPlaceToDB(activity: AddPlaceActivity, place: PlacesModel) {
        mFirestore.collection(Constants.PLACES).document().set(place, SetOptions.merge())
            .addOnSuccessListener { document ->
                Toast.makeText(activity, "Place saved!", Toast.LENGTH_LONG).show()
            }.addOnFailureListener { e ->
                Toast.makeText(activity, "Error while saving: $e", Toast.LENGTH_LONG).show()
            }
    }

    fun getUserPlaces(): ArrayList<PlacesModel> {
        val currentUser = getCurrentUser()

        for (i in currentUser.ownPlaces) {
            Log.e("Titel", i.title)
            Log.e("Description", i.description)
        }
        return currentUser.ownPlaces
    }

    fun getAllPlaces(activity: MainActivity) {
        mFirestore.collection(Constants.PLACES).get().addOnSuccessListener {
                document->
                Log.e(activity.javaClass.simpleName, document.documents.toString())
                val placeList:ArrayList<PlacesModel> = ArrayList()
                for (i in document.documents){
                    val place = i.toObject(PlacesModel::class.java)
                    place!!.documentID = i.id

                    placeList.add(place)
                }
                activity.populatePlacesListToUI(placeList)

            }.addOnFailureListener {
                Log.e("FIREBASE", "Failure with loading theplayes")
            }


    }


}
