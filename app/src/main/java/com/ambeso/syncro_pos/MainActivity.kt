@file:Suppress("DEPRECATION")

package com.ambeso.syncro_pos

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ambeso.syncro_pos.Models.Users
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    var dbHandler : DatabaseHandler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermissions()



        dbHandler = DatabaseHandler(this)
        val path = this.getExternalFilesDir(null).toString()
//
//        Log.e("PATH",path )

        Toast.makeText(this, path, Toast.LENGTH_LONG).show()
        val letDirectory = File(path, "LET")
        letDirectory.mkdirs()
//        val file = File(letDirectory, "Records.txt")
        val file = File(letDirectory, "Records.txt")
        file.appendText("record goes here")


        val inputAsString = FileInputStream(file).bufferedReader().use { it.readText() }
        Log.e("BACA", Environment.getExternalStorageDirectory().toString()   )



        loadBtn.setOnClickListener{
            Toast.makeText(this, "hello", Toast.LENGTH_LONG).show()
        }

        loadBtn.setOnClickListener(View.OnClickListener {
            var user = dbHandler!!.getAllUsers()
            showTxt.setText(user)
        })
    }


    private val TAG = "PermissionState"
    private val RECORD_REQUEST_CODE = 101

    private fun setupPermissions() {
        val ReadPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            TODO("VERSION.SDK_INT < JELLY_BEAN")
        }

        if (ReadPermission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
            makeReadRequest()
        }

        val WritePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            TODO("VERSION.SDK_INT < JELLY_BEAN")
        }

        if (WritePermission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
            makeWriteRequest()
        }
    }

    private fun makeReadRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                RECORD_REQUEST_CODE)
        }
    }

    private fun makeWriteRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                RECORD_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                             permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            RECORD_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
        }
    }



}
