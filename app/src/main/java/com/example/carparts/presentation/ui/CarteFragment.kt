package com.example.carparts.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.carparts.databinding.FragmentCarteBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import android.content.Context
import com.example.carparts.presentation.viewmodel.PiecesViewModel


class CarteFragment : Fragment() {

    private val viewModel: PiecesViewModel by activityViewModels()
    private var _binding: FragmentCarteBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Configuration.getInstance().load(
            context,
            requireActivity().getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
        )

        map = binding.mapView
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setUseDataConnection(true)
        map.setMultiTouchControls(true)

        val mapController = map.controller
        mapController.setZoom(4.0)
        mapController.setCenter(GeoPoint(46.603354, 1.888334))

        viewModel.pieces.observe(viewLifecycleOwner, Observer { pieces ->
            map.overlays.clear()

            pieces.forEach { piece ->
                val marker = Marker(map).apply {
                    position = GeoPoint(piece.latitude, piece.longitude)
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    title = piece.nom
                    snippet = piece.description

                    setOnMarkerClickListener { marker, mapView ->
                        marker.showInfoWindow()
                        true
                    }
                }
                map.overlays.add(marker)
            }

            map.invalidate()
        })
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}