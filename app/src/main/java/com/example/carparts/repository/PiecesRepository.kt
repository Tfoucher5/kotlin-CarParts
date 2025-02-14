package com.example.carparts.repository

import android.content.Context
import com.example.carparts.data.Pieces
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class PiecesRepository {

    fun loadPiecesFromFile(context: Context): List<Pieces> {
        val pieces = mutableListOf<Pieces>()

        val file = createFile(context, "pieces.csv")

        try {
            val reader = BufferedReader(FileReader(file))
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                val plageData = line?.split("+")
                if (plageData != null && plageData.size == 8) {
                    val piece = Pieces(
                        plageData[0],
                        plageData[1],
                        plageData[2],
                        plageData[3],
                        plageData[4].toInt(),
                        plageData[5].toInt(),
                        plageData[6].toDouble(),
                        plageData[7].toDouble()
                    )
                    pieces.add(piece)
                }
            }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return pieces
    }

    fun addPiece(context: Context, piece: Pieces) {
        val file = createFile(context, "pieces.csv")

        try {
            val writer = FileWriter(file, true) // Append mode

            writer.write(
                "${piece.nom}+${piece.description}+${piece.image}+${piece.url}+" +
                        "${piece.longueur}+${piece.largeur}+${piece.latitude}+${piece.longitude}\n"
            )

            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun createFile(context: Context, fileName: String): File {
        val file = File(context.filesDir, fileName)

        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return file
    }
}
