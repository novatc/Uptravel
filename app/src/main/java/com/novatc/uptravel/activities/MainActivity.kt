package com.novatc.uptravel.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.novatc.uptravel.R
import com.novatc.uptravel.model.BaseActivity
import com.novatc.uptravel.model.PlacesModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    val db = Firebase.firestore
    val list : MutableList<PlacesModel> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        super.hideStatusBar()


        fab_mainActivity_add_Place.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AddPlaceActivity::class.java
                )
            )
        }
    }

    private fun getPlacesFromDB() {
        val list = db.collection("Places").get()

    }


}