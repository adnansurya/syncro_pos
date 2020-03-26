package com.ambeso.syncro_pos

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.util.Log
import com.ambeso.syncro_pos.Models.Category
import com.ambeso.syncro_pos.Models.Product
import kotlin.math.roundToInt

class DBHandler(context: Context){

    var mycontext = context


    fun getProducts(sqlString: String) : List<Product>{

        val productList:ArrayList<Product> = ArrayList<Product>()
        val db = DBConn("product.db", mycontext).getDatabase

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
        var timeStamp : Int

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
                    timeStamp = cursor.getInt(cursor.getColumnIndex("product_timestamp"))

                    val product = Product(type,category,uxid,code,name,unit,basePrice,sellPrice,stockAmount, timeStamp)
                    productList.add(product)
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
        db?.close()
        return productList
    }

    fun getCategories(sqlString: String) : List<Category>{

        val categoryList:ArrayList<Category> = ArrayList<Category>()
        val db = DBConn("category.db", mycontext).getDatabase

        var cursor: Cursor? = null

        try{
            cursor = db?.rawQuery(sqlString, null)
        }catch (e: SQLiteException) {
            Log.e("DBHandler", e.toString())
            db?.execSQL(sqlString)
            return ArrayList()
        }


        var uxid: String
        var name: String
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    uxid = cursor.getString(cursor.getColumnIndex("category_uxid"))
                    name = cursor.getString(cursor.getColumnIndex("category_name"))

                    val category = Category(uxid,name)
                    categoryList.add(category)
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
        db?.close()
        return categoryList
    }
}