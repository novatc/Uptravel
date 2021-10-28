package com.novatc.uptravel.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.novatc.uptravel.R
import com.novatc.uptravel.model.PlacesModel
import kotlinx.android.synthetic.main.activity_add_place.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddPlaceActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var dateSetLisener: DatePickerDialog.OnDateSetListener
    val db = Firebase.firestore

    @RequiresApi(Build.VERSION_CODES.N)
    private var cal = Calendar.getInstance()
    private var saveImageToInternalStorage: Uri? = null
    private var mLatitude: Double = 0.0
    private var mLongitude: Double = 0.0


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)

        FirebaseApp.initializeApp(this)

        setSupportActionBar(toolbar_add_place)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_add_place.setNavigationOnClickListener {
            onBackPressed()
        }

        dateSetLisener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, day)

            updateDateInView()
        }
        updateDateInView()
        et_date.setOnClickListener(this)
        tv_add_image.setOnClickListener(this)
        btn_save.setOnClickListener(this)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateDateInView() {
        val format = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        et_date.setText(sdf.format(cal.time).toString())
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.et_date -> {
                DatePickerDialog(
                    this@AddPlaceActivity, dateSetLisener, cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            R.id.tv_add_image -> {
                val image_dialog = AlertDialog.Builder(this)
                image_dialog.setTitle("Choose an Image")
                val pictureDialogItems = arrayOf("Choose from Gallery", "Take a Picture")
                image_dialog.setItems(pictureDialogItems) { dialog, which ->
                    when (which) {
                        0 -> choosePhotoFromGallery()
                        1 -> Toast.makeText(
                            this@AddPlaceActivity,
                            "under construction",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                image_dialog.show()
            }
            R.id.btn_save -> {
                when {
                    et_title.text.isNullOrEmpty() -> Toast.makeText(
                        this@AddPlaceActivity,
                        "Pls give it a name",
                        Toast.LENGTH_LONG
                    ).show()
                    et_description.text.isNullOrEmpty() -> Toast.makeText(
                        this@AddPlaceActivity,
                        "Pls give it a description",
                        Toast.LENGTH_LONG
                    ).show()
                    et_date.text.isNullOrEmpty() -> Toast.makeText(
                        this@AddPlaceActivity,
                        "Pls give it a date",
                        Toast.LENGTH_LONG
                    ).show()
                    et_location.text.isNullOrEmpty() -> Toast.makeText(
                        this@AddPlaceActivity,
                        "Pls give it a location",
                        Toast.LENGTH_LONG
                    ).show()
                    saveImageToInternalStorage == null -> Toast.makeText(
                        this@AddPlaceActivity,
                        "Pls take a picture",
                        Toast.LENGTH_LONG
                    ).show()
                    else -> {
                        val placeModel = PlacesModel(
                            0,
                            et_title.text.toString(),
                            saveImageToInternalStorage.toString(),
                            et_description.text.toString(),
                            et_date.text.toString(),
                            et_location.text.toString(),
                            mLatitude,
                            mLongitude
                        )
                        db.collection("Places").add(placeModel)
                            .addOnSuccessListener { documentReference ->
                                Toast.makeText(
                                    this@AddPlaceActivity,
                                    "\"DocumentSnapshot added with ID: ${documentReference.id}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }.addOnFailureListener { e ->
                                Toast.makeText(
                                    this@AddPlaceActivity,
                                    "Error, "+e,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    }

                }
            }
        }
    }

    private fun choosePhotoFromGallery() {
        Dexter.withContext(this).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                if (p0!!.areAllPermissionsGranted()) {
                    val galleryIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, GALLERY)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                showRationalDialogForPermissions()
            }

        }).onSameThread().check()
    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this).setMessage("No Permission granted, Check your App settings.")
            .setPositiveButton("Settings") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }.setNegativeButton("Cancle") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY) {
                if (data != null) {
                    val contentURI = data.data
                    try {
                        @Suppress("DEPRECATION")
                        val selectedImageBitmap =
                            MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                        saveImageToInternalStorage = saveImageToInternalStorage(selectedImageBitmap)
                        iv_place_image!!.setImageBitmap(selectedImageBitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap): Uri {

        // ContextWrapper Instanz erstellen
        val wrapper = ContextWrapper(applicationContext)

        // Neue Datei (File) initialisieren
        // getDir gibt Pfad zum internen Speicherort zurück
        /**
         * MODE_PRIVATE: Datei kann nur durch App (oder Apps, die die selbe Nutzer ID haben)
         * aufgerufen werden.
         */
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)

        // Datei (File) erstellen, um das Foto zu speichern
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // FileOutputStream der Datei in Variable speichern
            val stream: OutputStream = FileOutputStream(file)

            // Bitmap zum Speichern "verpacken" (Compress bitmap)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            stream.flush()

            // Stream schließen
            stream.close()
        } catch (e: IOException) { // Ausnahme abfangen und ausgeben
            e.printStackTrace()
        }

        // URI des gespeicherten Fotos zurückgeben
        return Uri.parse(file.absolutePath)
    }

    companion object {
        private const val GALLERY = 1
        private const val IMAGE_DIRECTORY = "UpTravel"
    }
}