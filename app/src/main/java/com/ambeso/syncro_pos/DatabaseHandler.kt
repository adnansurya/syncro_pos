package com.ambeso.syncro_pos

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.ambeso.syncro_pos.Models.Users

import android.database.sqlite.SQLiteException
import android.os.Environment
import java.io.File
import java.io.File.separator





class DatabaseHandler(context: Context): SQLiteOpenHelper(context, Environment.getExternalStorageState().toString() +  DB_NAME, null, DB_VERSION){

    private var mycontext :Context = context
    
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME "+
                "($ID Integer PRIMARY KEY, $FIRST_NAME TEXT, $LAST_NAME TEXT)"
//        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addUser(user: Users): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(FIRST_NAME, user.firstName)
        values.put(LAST_NAME, user.lastName)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.e("Inserting", "$_success")
        return  (Integer.parseInt("$_success") != -1)
    }

    fun getAllUsers(): String{
        var allUser: String = ""
//        val db = readableDatabase
        val filePath = DATABASE_FILE_PATH + File.separator + "KasirToko" + File.separator + "database" + File.separator + DB_NAME
        Log.e("PATH", filePath)

//        val db = SQLiteDatabase.openDatabase(filePath, null, SQLiteDatabase.OPEN_READWRITE )
        val db = DBConn(mycontext, DB_NAME).getDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db?.rawQuery(selectALLQuery, null)

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    var id = cursor.getString(cursor.getColumnIndex(ID))
//                    var firstName = cursor.getString(cursor.getColumnIndex(FIRST_NAME))
//                    var lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME))
                    var productName = cursor.getString(cursor.getColumnIndex(PRODUCT_NAME))

                    allUser = "$allUser\n$id $productName"
                }while (cursor.moveToNext())
            }
        }
        cursor?.close()
        db?.close()
        return allUser
    }

    companion object {
        private val DB_NAME = "product.db"
        private val DB_VERSION = 1
        private val TABLE_NAME = "table_product"
        private val ID = "product_id"
        private val FIRST_NAME = "FirstName"
        private val LAST_NAME = "LastName"
        private val PRODUCT_NAME = "product_name"
        var DATABASE_FILE_PATH = Environment.getExternalStorageDirectory().toString()

        var db:SQLiteDatabase? =  null
    }

//    init {
//        try {
////            DATABASE_FILE_PATH = context.getExternalFilesDir(null).toString()
//            DATABASE_FILE_PATH = Environment.getExternalStorageDirectory().toString()
//            var filePath = DATABASE_FILE_PATH + File.separator + "KasirToko" +
//                    File.separator + DB_NAME + ".db"
//            Log.e("PATH", filePath)
//            db = SQLiteDatabase.openDatabase(filePath, null, SQLiteDatabase.OPEN_READWRITE)
//
//        }
//
//        catch (ex:SQLiteException) {
//            Log.e(TAG, "error -- " + ex.message, ex)
//            // error means tables does not exits
////                createTables()
//        }
//        finally {
////            DBUtil.safeCloseDataBase(database)
//        }
//    }
}