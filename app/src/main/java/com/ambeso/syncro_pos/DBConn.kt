package com.ambeso.syncro_pos

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.File
import java.security.AccessController.getContext

class DBConn (dbName: String, context: Context) {
    private var database: SQLiteDatabase? = null
    private var DB_NAME  = dbName
    private var NEW_DATABASE = context.getExternalFilesDir(null).toString()
    private var OLD_DATABASE = Environment.getExternalStorageDirectory().absolutePath

    val getDatabase : SQLiteDatabase?
    get() {


        database = SQLiteDatabase.openDatabase(OLD_DATABASE,null, SQLiteDatabase.OPEN_READWRITE)
        return database
    }


    companion object{
        var DATABASE_FILE_PATH = File.separator + "KasirToko" + File.separator + "database" + File.separator
        val TAG = "DBConn"

    }

    fun copyDB(){

        NEW_DATABASE = NEW_DATABASE + DATABASE_FILE_PATH + DB_NAME
        File(OLD_DATABASE).copyTo(File(NEW_DATABASE), true);
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
            Log.e("PATH", DATABASE_FILE_PATH)

//            copyDB()
            OLD_DATABASE = OLD_DATABASE + DATABASE_FILE_PATH + DB_NAME
            database = SQLiteDatabase.openDatabase(OLD_DATABASE,null, SQLiteDatabase.OPEN_READWRITE)


        }catch (ex: SQLiteException){

            Log.e(TAG, ex.message, ex)
        }

    }

}