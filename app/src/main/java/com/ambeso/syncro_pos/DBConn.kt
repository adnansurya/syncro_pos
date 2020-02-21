package com.ambeso.syncro_pos

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.File

class DBConn (dbName: String) {
    private var database: SQLiteDatabase? = null
    private var DB_NAME  = dbName

    val getDatabase : SQLiteDatabase?
    get() {
        database = SQLiteDatabase.openDatabase(DATABASE_FILE_PATH + DB_NAME,null, SQLiteDatabase.OPEN_READWRITE)
        return database
    }


    companion object{
        var DATABASE_FILE_PATH = Environment.getExternalStorageDirectory().toString()+
                File.separator + "KasirToko" + File.separator + "database" + File.separator
        val TAG = "DBConn"
    }

//    fun getQuery(tableName: String): Cursor? {
//        return database?.rawQuery("SELECT * FROM $tableName", null)
//    }
//
//    fun closeDB(){
//        database?.close()
//    }


    init {
        try {
            database = SQLiteDatabase.openDatabase(DATABASE_FILE_PATH + DB_NAME,null, SQLiteDatabase.OPEN_READWRITE)

        }catch (ex: SQLiteException){

            Log.e(TAG, ex.message, ex)
        }

    }

}