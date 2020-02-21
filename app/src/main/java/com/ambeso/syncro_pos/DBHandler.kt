package com.ambeso.syncro_pos

import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.util.Log
import com.ambeso.syncro_pos.Models.Product
import kotlin.math.roundToInt

class DBHandler {


    fun getProducts(sqlString: String) : List<Product>{

        val productList:ArrayList<Product> = ArrayList<Product>()
        val db = DBConn("product.db").getDatabase

        var cursor: Cursor? = null

        try{
            cursor = db?.rawQuery(sqlString, null)
        }catch (e: SQLiteException) {
            Log.e("DBHandler", e.toString())
            db?.execSQL(sqlString)
            return ArrayList()
        }

        var type: String
        var category: String
        var uxid: String
        var code: String
        var name: String
        var unit: String
        var id: Int
        var basePrice: Int
        var sellPrice: Int
        var stockAmount: Int

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    type = cursor.getString(cursor.getColumnIndex("product_type"))
                    category = cursor.getString(cursor.getColumnIndex("product_category_name"))
                    uxid = cursor.getString(cursor.getColumnIndex("product_uxid"))
                    code = cursor.getString(cursor.getColumnIndex("product_code"))
                    name = cursor.getString(cursor.getColumnIndex("product_name"))
                    unit = cursor.getString(cursor.getColumnIndex("product_unit"))
                    id = cursor.getInt(cursor.getColumnIndex("product_id"))
                    basePrice = cursor.getDouble(cursor.getColumnIndex("product_base_price")).roundToInt()
                    sellPrice = cursor.getDouble(cursor.getColumnIndex("product_sale_price")).roundToInt()
                    stockAmount = cursor.getDouble(cursor.getColumnIndex("product_stock_amount")).roundToInt()

                    val product = Product(id,type,category,uxid,code,name,unit,basePrice,sellPrice,stockAmount)
                    productList.add(product)
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
        db?.close()
        return productList
    }
}