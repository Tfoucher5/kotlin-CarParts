package com.example.carparts.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Pieces::class], version = 1)
abstract class PieceDatabase : RoomDatabase() {
    abstract fun pieceDao(): PieceDao

    companion object {
        @Volatile
        private var INSTANCE: PieceDatabase? = null

        fun getDatabase(context: Context): PieceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PieceDatabase::class.java,
                    "piece_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Dao
interface PieceDao {
    @Query("SELECT * FROM pieces")
    suspend fun getAllPieces(): List<Pieces>

    @Insert
    suspend fun insertPiece(piece: Pieces)

    @Query("SELECT * FROM pieces WHERE id = :pieceId")
    suspend fun getPieceById(pieceId: Int): Pieces?
}