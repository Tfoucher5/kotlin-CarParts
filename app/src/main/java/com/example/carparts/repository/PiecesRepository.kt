package com.example.carparts.repository

import com.example.carparts.data.PieceDao
import com.example.carparts.data.Pieces

class PiecesRepository(private val pieceDao: PieceDao) {
    suspend fun getAllPieces(): List<Pieces> {
        return pieceDao.getAllPieces()
    }

    suspend fun insertPiece(piece: Pieces) {
        pieceDao.insertPiece(piece)
    }
}