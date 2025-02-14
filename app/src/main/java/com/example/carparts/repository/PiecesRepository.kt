package com.example.carparts.repository

import android.content.Context
import com.example.carparts.data.BaseService
import com.example.carparts.data.Pieces

class PiecesRepository(private val context: Context) {
    private val baseService = BaseService(context)

    fun loadPieces(): List<Pieces> {
        return baseService.query()
    }

    fun addPiece(piece: Pieces) {
        baseService.insert(piece)
    }
}