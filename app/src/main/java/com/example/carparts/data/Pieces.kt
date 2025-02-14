package com.example.carparts.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Pieces (val nom : String,
              val description : String,
              val image : String,
              val url : String,
              val largeur : Int,
              val longueur : Int,
              val latitude : Double,
              val longitude : Double): Parcelable
