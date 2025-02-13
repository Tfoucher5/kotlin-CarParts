package com.example.carparts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class PiecesRecyclerAdapter(
    private val context: Context,
    var piecesList: List<Pieces>,
    private val favorisList: MutableList<Boolean>,
    private val toggleFavoriteListener: (Int) -> Unit
) : RecyclerView.Adapter<PiecesRecyclerAdapter.PiecesViewHolder>() {

    inner class PiecesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val heartButton: CheckBox = itemView.findViewById(R.id.likeButton)
        val nomPiece: TextView = itemView.findViewById(R.id.nomPiece)
        val descriptionPiece: TextView = itemView.findViewById(R.id.descriptionPiece)
        val imagePiece: ImageView = itemView.findViewById(R.id.imagePiece)

        init {
            heartButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    toggleFavoriteListener(position)
                    heartButton.isSelected = favorisList[position]
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PiecesViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_piece, parent, false)
        return PiecesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PiecesViewHolder, position: Int) {
        val currentPiece = piecesList[position]
        holder.nomPiece.text = currentPiece.nom
        holder.descriptionPiece.text = currentPiece.description
        holder.heartButton.isChecked = favorisList[position]

        val imageResId = context.resources.getIdentifier(
            currentPiece.image,
            "drawable",
            context.packageName
        )
        if (imageResId != 0) {
            holder.imagePiece.setImageResource(imageResId)
        }

        holder.itemView.setOnClickListener {
            val action = ListeFragmentDirections.actionListeFragmentToDetailsFragment(position)
            findNavController(holder.itemView).navigate(action)
        }
    }

    fun updateFavoris(newFavoris: ArrayList<Boolean>) {
        favorisList.clear()
        favorisList.addAll(newFavoris)
        notifyDataSetChanged()
    }

    override fun getItemCount() = piecesList.size
}
