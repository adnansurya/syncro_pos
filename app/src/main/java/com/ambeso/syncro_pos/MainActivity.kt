@file:Suppress("DEPRECATION")

package com.ambeso.syncro_pos

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ambeso.syncro_pos.Adapters.ProductListAdapter
import com.ambeso.syncro_pos.Models.Product
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE

import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject


class MainActivity : AppCompatActivity() {


    var sql = "SELECT * FROM table_product"
    var order = ""
    var limit = ""
    var sort = ""
    var sqlQuery = ""


    private val WRITE_REQUEST_CODE = 201
    private val READ_REQUEST_CODE = 202
    private var readPermission= false
    private var writePermission= false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermission(WRITE_EXTERNAL_STORAGE, WRITE_REQUEST_CODE)
            // Permission is not granted
        }else{
            writePermission = true
        }

        if (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermission(READ_EXTERNAL_STORAGE, READ_REQUEST_CODE)
            // Permission is not granted
        }else{
            readPermission = true
        }

        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME or ActionBar.DISPLAY_SHOW_TITLE or ActionBar.DISPLAY_USE_LOGO)
        supportActionBar?.setIcon(R.mipmap.ic_mksrobotics)

        orderSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                order = getResources().getStringArray(R.array.order_spinner_sql)[position].toString()

//                Toast.makeText(this@MainActivity, order, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected

            }
        }

        sortSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                sort = getResources().getStringArray(R.array.sort_spinner_sql)[position].toString()

//                Toast.makeText(this@MainActivity, sort, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected

            }
        }

        limitSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                limit = getResources().getStringArray(R.array.limit_spinner_sql)[position].toString()

//                Toast.makeText(this@MainActivity, limit, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected

            }
        }



    }




    fun requestPermission(category : String, reqCode: Int){
        if (ContextCompat.checkSelfPermission(this,
                category)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    category)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(category),
                    reqCode)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            READ_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    readPermission = true
                    Toast.makeText(this, "READ OK", Toast.LENGTH_LONG).show()
                } else {
                    readPermission = false
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
            WRITE_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    writePermission = true
                    Toast.makeText(this, "WRITE OK", Toast.LENGTH_LONG).show()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    writePermission = false
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }


    fun viewList(view: View){

        sqlQuery = "$sql $order $sort $limit"
        Log.e("Query", sqlQuery)

        if(readPermission && writePermission){
            var produk : List<Product> = DBHandler(this).getProducts(sqlQuery)

//            val id_produk = Array<Int>(produk.size){0}
            val nama_produk = Array<String>(produk.size){"null"}
            val modal_produk = Array<String>(produk.size){"null"}
            val jual_produk = Array<String>(produk.size){"null"}
            val stok_produk = Array<String>(produk.size){"null"}
            val satuan_produk = Array<String>(produk.size){"null"}


            var index = 0
            for(i in produk){
//                id_produk[index] = i.id
                nama_produk[index] = i.name.toString()
                modal_produk[index] = i.basePrice.toString()
                jual_produk[index] = i.sellPrice.toString()
                stok_produk[index] = i.stockAmount.toString()
                satuan_produk[index] = i.unit.toString()
                index++
            }
            //creating custom ArrayAdapter
            val listAdapter = ProductListAdapter(this,nama_produk,modal_produk,jual_produk,stok_produk,satuan_produk)
            listView.adapter = listAdapter
        }else{
            Toast.makeText(this, "Permission NOT OK. Check Settings of this app", Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =  when (item.itemId){
        R.id.action_sync -> {
            syncFirebase()
            true
        }else -> {
            super.onOptionsItemSelected(item)
        }

    }


    fun syncFirebase(){
        var produk : List<Product> = DBHandler(this).getProducts("SELECT * FROM table_product") //
        var db : DatabaseReference = FirebaseDatabase.getInstance().getReference("product_data")

        db.setValue(produk)
    }
















}
