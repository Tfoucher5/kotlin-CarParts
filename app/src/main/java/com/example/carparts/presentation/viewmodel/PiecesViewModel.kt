package com.example.carparts.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.carparts.data.PieceDatabase
import com.example.carparts.data.Pieces
import com.example.carparts.repository.PiecesRepository
import kotlinx.coroutines.launch

class PiecesViewModel(application: Application) : AndroidViewModel(application) {
    private val database: PieceDatabase by lazy {
        PieceDatabase.getDatabase(application.applicationContext)
    }
    val repository: PiecesRepository by lazy {
        PiecesRepository(database.pieceDao())
    }

    val _pieces = MutableLiveData<List<Pieces>>()
    val pieces: LiveData<List<Pieces>> = _pieces

    init {
        loadPieces()
    }

    fun loadPieces() {
        viewModelScope.launch {
            _pieces.value = repository.getAllPieces()
        }
    }

    fun addPiece(piece: Pieces) {
        viewModelScope.launch {
            repository.insertPiece(piece)
            loadPieces()
        }
    }
}