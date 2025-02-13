package com.example.carparts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.carparts.databinding.FragmentCreatePiecesBinding

class CreatePiecesFragment : Fragment() {

    private lateinit var binding: FragmentCreatePiecesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatePiecesBinding.inflate(inflater, container, false)

        binding.submitButton.setOnClickListener {
            val pieces = Pieces(
                binding.pieceName.text.toString(),
                binding.description.text.toString(),
                binding.image.text.toString(),
                binding.url.text.toString(),
                binding.largeur.text.toString().toInt(),
                binding.longueur.text.toString().toInt(),
                binding.latitude.text.toString().toDouble(),
                binding.longitude.text.toString().toDouble()
            )

            setFragmentResult("request_key", bundleOf("pieces" to pieces))

            findNavController().navigateUp()
        }

        return binding.root
    }
}
