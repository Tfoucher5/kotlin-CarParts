package com.example.carparts.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carparts.data.Pieces
import com.example.carparts.repository.PiecesRepository

class PiecesViewModel : ViewModel() {
    private lateinit var _piecesRepository: PiecesRepository
    val _pieces = MutableLiveData<List<Pieces>>()
    private val _favorisList = MutableLiveData<List<Boolean>>()

    val pieces: LiveData<List<Pieces>> get() = _pieces
    val favorisList: LiveData<List<Boolean>> get() = _favorisList

    fun initRepository(context: Context) {
        _piecesRepository = PiecesRepository(context)
    }

    val piecesRepository: PiecesRepository
        get() = _piecesRepository

    fun updateFavoris(newFavorisList: List<Boolean>) {
        _favorisList.value = newFavorisList
    }

    fun addPiece(piece: Pieces) {
        _piecesRepository.addPiece(piece)

        val currentPieces = _pieces.value ?: emptyList()
        val updatedPieces = currentPieces + piece
        _pieces.value = updatedPieces
    }
}