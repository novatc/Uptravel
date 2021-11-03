package com.novatc.uptravel.activities

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.novatc.uptravel.Adapter.PlacesAdapter
import com.novatc.uptravel.Firebase.FirestoreClass
import com.novatc.uptravel.R
import com.novatc.uptravel.model.BaseActivity
import com.novatc.uptravel.model.PlacesModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        super.hideStatusBar()
        FirestoreClass().getAllPlaces(this)

        fab_mainActivity_add_Place.setOnClickListener {
            val intent = Intent(this, AddPlaceActivity::class.java)
            startActivityForResult(intent, ADD_PLACE_ACTIVITY_REQUEST_CODE)
        }
    }

    fun populatePlacesListToUI(placesList: ArrayList<PlacesModel>){
        if (placesList.size>0){
            for (i in placesList){
                Log.e("Titel", i.title)
                Log.e("User", i.createdBy)
            }
            rv_places_list.visibility = View.VISIBLE
            tv_no_record_available.visibility = View.GONE

            rv_places_list.layoutManager = LinearLayoutManager(this)
            rv_places_list.setHasFixedSize(false)

            val adapter = PlacesAdapter(this, placesList)
            rv_places_list.adapter = adapter
        }else{
            rv_places_list.visibility = View.GONE
            tv_no_record_available.visibility = View.VISIBLE
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Prüfen, ob der Anfragencode der gleiche, wie der in 'ADD_PLACE_ACTIVITY_REQUEST_CODE'
        if (requestCode == ADD_PLACE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                FirestoreClass().getAllPlaces(this)
            } else {
                Log.e("Activity", "Cancelled or Back Pressed")
            }
        }
    }

    companion object{
        private const val ADD_PLACE_ACTIVITY_REQUEST_CODE = 1
    }
}