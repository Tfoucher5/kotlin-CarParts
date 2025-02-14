package com.example.carparts.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context

class BaseService(context: Context) {

    var helper: DBHelper

    init {
        helper = DBHelper(
            context,
            "pieces",
            null,
            1
        )
    }

    fun insert(piece: Pieces) {
        val data = ContentValues().apply {
            put(DBHelper.COL_NOM, piece.nom)
            put(DBHelper.COL_DESCRIPTION, piece.description)
            put(DBHelper.COL_IMAGE, piece.image)
            put(DBHelper.COL_URL, piece.url)
            put(DBHelper.COL_LARGE, piece.largeur)
            put(DBHelper.COL_LONG, piece.longueur)
            put(DBHelper.COL_LATITUDE, piece.latitude)
            put(DBHelper.COL_LONGITUDE, piece.longitude)
        }

        helper.writableDatabase.insert(DBHelper.TABLE, null, data)
    }

    @SuppressLint("Range")
    fun query(): MutableList<Pieces> {
        val ret = mutableListOf<Pieces>()
        val curseur = helper.readableDatabase.query(
            DBHelper.TABLE,
            arrayOf(
                DBHelper.COL_NOM, DBHelper.COL_DESCRIPTION, DBHelper.COL_IMAGE, DBHelper.COL_URL,
                DBHelper.COL_LARGE, DBHelper.COL_LONG, DBHelper.COL_LATITUDE, DBHelper.COL_LONGITUDE
            ),
            null, null, null, null, DBHelper.COL_NOM
        )

        while (curseur.moveToNext()) {
            ret.add(
                Pieces(
                    curseur.getString(curseur.getColumnIndex(DBHelper.COL_NOM)),
                    curseur.getString(curseur.getColumnIndex(DBHelper.COL_DESCRIPTION)),
                    curseur.getString(curseur.getColumnIndex(DBHelper.COL_IMAGE)),
                    curseur.getString(curseur.getColumnIndex(DBHelper.COL_URL)),
                    curseur.getInt(curseur.getColumnIndex(DBHelper.COL_LARGE)),
                    curseur.getInt(curseur.getColumnIndex(DBHelper.COL_LONG)),
                    curseur.getDouble(curseur.getColumnIndex(DBHelper.COL_LATITUDE)),
                    curseur.getDouble(curseur.getColumnIndex(DBHelper.COL_LONGITUDE))
                )
            )
        }

        curseur.close()
        return ret
    }
}
