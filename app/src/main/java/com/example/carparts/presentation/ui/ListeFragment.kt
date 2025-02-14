package com.example.carparts.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carparts.CarPartsApplication
import com.example.carparts.R
import com.example.carparts.data.Pieces
import com.example.carparts.databinding.FragmentListeBinding
import com.example.carparts.presentation.adapter.PiecesRecyclerAdapter
import com.example.carparts.presentation.viewmodel.PiecesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListeFragment : Fragment() {

    private val viewModel: PiecesViewModel by activityViewModels()
    private var _binding: FragmentListeBinding? = null
    private val binding get() = _binding!!
    private val favorisList = mutableListOf<Boolean>()
    private lateinit var notificationManager: NotificationManagerCompat
    private var notificationId = 0

    companion object {
        private const val REQUEST_NOTIF_PERMISSION = 1
        private const val TAG = "ListeFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListeBinding.inflate(inflater, container, false)
        notificationManager = NotificationManagerCompat.from(requireContext())
        viewModel.initRepository(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNotificationPermission()
        setupWindowInsets()
        setupViews()
        setupObservers()
        loadInitialData()

        Log.d(TAG, "onViewCreated initialized")
    }

    private fun setupNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            !hasNotificationPermission()
        ) {
            requestNotificationPermission()
        }
    }

    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun requestNotificationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            REQUEST_NOTIF_PERMISSION
        )
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.listePieces) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupViews() {
        binding.allerVersCreatePieces.setOnClickListener {
            findNavController().navigate(R.id.createFragment)
        }

        setFragmentResultListener("request_key") { _, bundle ->
            val piece = bundle.getParcelable<Pieces>("pieces")
            try {
                piece?.let {
                    viewModel.addPiece(it)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erreur lors de l'ajout de la pièce: ${e.message}")
            }
        }
    }

    private fun setupObservers() {
        viewModel.pieces.observe(viewLifecycleOwner) { lesPieces ->
            Log.d(TAG, "Updating pieces list: ${lesPieces.size} items")
            updatePiecesList(lesPieces)
        }
    }

    private fun loadInitialData() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val currentPieces = viewModel.pieces.value
                if (currentPieces.isNullOrEmpty()) {
                    val pieces = viewModel.piecesRepository.loadPieces()
                    withContext(Dispatchers.Main) {
                        viewModel._pieces.value = pieces
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erreur lors du chargement des pièces: ${e.message}")
            }
        }
    }

    private fun updatePiecesList(lesPieces: List<Pieces>) {
        favorisList.clear()
        favorisList.addAll(List(lesPieces.size) { false })

        val adapter = PiecesRecyclerAdapter(
            requireActivity(),
            lesPieces,
            favorisList
        ) { position -> handleItemClick(position) }

        binding.listePieces.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    private fun handleItemClick(position: Int) {
        Log.d(TAG, "Item clicked at position: $position")
        toggleFavorite(position)
        sendNotification()
    }

    private fun toggleFavorite(position: Int) {
        if (position in 0 until favorisList.size) {
            favorisList[position] = !favorisList[position]
            binding.listePieces.adapter?.notifyItemChanged(position)
        }
    }

    private fun sendNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission()) {
            requestNotificationPermission()
            return
        }

        try {
            val builder = NotificationCompat.Builder(requireContext(), CarPartsApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Notification test")
                .setContentText("Ceci est une notification de test.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)

            notificationManager.notify(notificationId++, builder.build())
        } catch (e: SecurityException) {
            Log.e(TAG, "Permission to send notifications is not granted: ${e.message}")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_NOTIF_PERMISSION &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            sendNotification()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}