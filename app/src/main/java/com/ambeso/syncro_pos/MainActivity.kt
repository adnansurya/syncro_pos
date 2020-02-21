@file:Suppress("DEPRECATION")

package com.ambeso.syncro_pos

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ambeso.syncro_pos.Adapters.ProductListAdapter
import com.ambeso.syncro_pos.Models.Product

class MainActivity : AppCompatActivity() {


    var sql = "SELECT * FROM table_product"
    var order = ""
    var limit = ""
    var sort = ""
    var sqlQuery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermissions()


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




    fun viewList(view: View){

        sqlQuery = "$sql $order $sort $limit"
        Log.e("Query", sqlQuery)
        val produk : List<Product> = DBHandler().getProducts(sqlQuery)

        val id_produk = Array<Int>(produk.size){0}
        val nama_produk = Array<String>(produk.size){"null"}
        val modal_produk = Array<String>(produk.size){"null"}
        val jual_produk = Array<String>(produk.size){"null"}
        val stok_produk = Array<String>(produk.size){"null"}
        val satuan_produk = Array<String>(produk.size){"null"}


        var index = 0
        for(i in produk){
            id_produk[index] = i.id
            nama_produk[index] = i.productName.toString()
            modal_produk[index] = i.productBasePrice.toString()
            jual_produk[index] = i.productSellPrice.toString()
            stok_produk[index] = i.productStockAmount.toString()
            satuan_produk[index] = i.productUnit.toString()
            index++
        }
        //creating custom ArrayAdapter
        val listAdapter = ProductListAdapter(this,id_produk,nama_produk,modal_produk,jual_produk,stok_produk,satuan_produk)
        listView.adapter = listAdapter
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
