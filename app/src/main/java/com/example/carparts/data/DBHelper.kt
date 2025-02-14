package com.example.carparts.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(bd: SQLiteDatabase) {
        bd.execSQL(CREATE_BDD)
    }

    override fun onUpgrade(bd: SQLiteDatabase, version1: Int, version2: Int) {
        bd.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(bd)
    }

    companion object {
        val TABLE = "pieces"
        val COL_ID = "_id"

        val COL_NOM = "nom"
        val COL_DESCRIPTION = "description"
        val COL_IMAGE = "image"
        val COL_URL = "url"
        val COL_LARGE = "largeur"
        val COL_LONG = "longueur"
        val COL_LATITUDE = "latitude"
        val COL_LONGITUDE = "longitude"

        val CREATE_BDD = "CREATE TABLE $TABLE (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_NOM TEXT NOT NULL, " +
                "$COL_DESCRIPTION TEXT NOT NULL, " +
                "$COL_IMAGE TEXT NOT NULL, " +
                "$COL_URL TEXT NOT NULL, " +
                "$COL_LARGE INTEGER NOT NULL, " +
                "$COL_LONG INTEGER NOT NULL, " +
                "$COL_LATITUDE DOUBLE NOT NULL, " +
                "$COL_LONGITUDE DOUBLE NOT NULL);"
    }
}
