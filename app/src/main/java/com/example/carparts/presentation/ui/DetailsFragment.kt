package com.example.carparts.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.carparts.presentation.viewmodel.PiecesViewModel
import com.example.carparts.databinding.FragmentDetailsBinding
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.ScaleBarOverlay

class DetailsFragment : Fragment() {

    private val viewModel: PiecesViewModel by activityViewModels()
    private var pieceId: Int = 0
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pieceId = args.pieceId
        Log.d("DetailsFragment", "pieceId: $pieceId")

        val piece = viewModel.pieces.value?.get(pieceId)

        piece?.let {
            binding.detailTitreTv.text = it.nom
            binding.detailsDescriptionTv.text = it.description

            binding.largeurTv.text = "${it.largeur} mm"
            binding.longueurTv.text = "${it.longueur} mm"

            val startPoint = GeoPoint(it.latitude, it.longitude)
            binding.imageMapPlage.setUseDataConnection(true)
            binding.imageMapPlage.zoomController.setZoomInEnabled(true)
            binding.imageMapPlage.zoomController.setZoomOutEnabled(true)
            binding.imageMapPlage.setMultiTouchControls(true)
            binding.imageMapPlage.controller.setCenter(startPoint)
            binding.imageMapPlage.controller.setZoom(13.5)

            val myScaleBarOverlay = ScaleBarOverlay(binding.imageMapPlage)
            binding.imageMapPlage.overlays.add(myScaleBarOverlay)

            val startMarker = Marker(binding.imageMapPlage)
            startMarker.position = startPoint
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            binding.imageMapPlage.overlays.add(startMarker)
        }

        binding.detailsPlusInformationsTv.setOnClickListener {
            val url = piece?.url ?: ""
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
