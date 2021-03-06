package com.novatc.uptravel.model

import android.os.Parcel
import android.os.Parcelable

class User (
    val id: String = "",
    val username: String = "",
    val mail: String = "",
    val fcmToken: String = "",
    val ownPlaces: ArrayList<PlacesModel> = ArrayList(),
    val favPlaces: ArrayList<PlacesModel> = ArrayList()

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }
    fun addPlaceToUserList(place:PlacesModel){
        ownPlaces.add(place)
    }
    fun addPlaceToFavUserList(place:PlacesModel){
        favPlaces.add(place)
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(username)
        parcel.writeString(mail)
        parcel.writeString(fcmToken)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}