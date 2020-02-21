package com.ambeso.syncro_pos

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.os.Environment
import android.util.Log

import java.io.File

class DatabaseHelper {

    private var database: SQLiteDatabase? = null

    //    public void close()
    //    {
    //        DBUtil.safeCloseDataBase(database);
    //    }

    val readableDatabase: SQLiteDatabase?
        get() {
            database = SQLiteDatabase.openDatabase(
                DATABASE_FILE_PATH
                        + File.separator + DATABASE_NAME, null,
                SQLiteDatabase.OPEN_READONLY
            )
            return database
        }

    val writableDatabase: SQLiteDatabase?
        get() {
            database = SQLiteDatabase.openDatabase(
                DATABASE_FILE_PATH
                        + File.separator + DATABASE_NAME, null,
                SQLiteDatabase.OPEN_READWRITE
            )
            return database
        }

    init {
        try {
            database = SQLiteDatabase.openDatabase(
                DATABASE_FILE_PATH
                        + File.separator + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE
            )
        } catch (ex: SQLiteException) {
            Log.e(TAG, "error -- " + ex.message, ex)
            // error means tables does not exits
            createTables()
        }

        //        finally
        //        {
        //            DBUtil.safeCloseDataBase(database);
        //        }
    }

    private fun createTables() {
        database!!.execSQL(TRACKS_TABLE_CREATE)
        database!!.execSQL(TRACK_INFO_TABLE_CREATE)
    }

    companion object {

        private val TAG = "DatabaseHelper"

        val DATABASE_FILE_PATH = Environment.getExternalStorageDirectory().toString()
        val DATABASE_NAME = "mydb"
        val TRACKS_TABLE = "tracks"
        val TRACK_INFO_TABLE = "track_info"

        private val TRACKS_TABLE_CREATE = ("create table "
                + TRACKS_TABLE
                + " (_id integer primary key autoincrement, title text not null, description text null, created_at date not null);")

        private val TRACK_INFO_TABLE_CREATE = ("create table "
                + TRACK_INFO_TABLE
                + " (_id integer primary key autoincrement, track_id integer not null, latitude real not null, longitude real not null, altitude real not null, created_at date not null);")
    }


}
