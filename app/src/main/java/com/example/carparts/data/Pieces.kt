package com.example.carparts.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "pieces")
@Parcelize
class Pieces (val nom : String,
              val description : String,
              val image : String,
              val url : String,
              val largeur : Int,
              val longueur : Int,
              val latitude : Double,
              val longitude : Double): Parcelable {
                  @PrimaryKey(autoGenerate = true)
                  var id = 0
              }
