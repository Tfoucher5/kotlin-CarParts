package com.example.carparts

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carparts.databinding.FragmentListeBinding

class ListeFragment : Fragment() {

    private val viewModel: PiecesViewModel by activityViewModels()
    private var _binding: FragmentListeBinding? = null
    private val binding get() = _binding!!
    private val favorisList = mutableListOf<Boolean>()
    private lateinit var notificationManager: NotificationManagerCompat
    private var notificationId = 0

    companion object {
        private const val REQUEST_NOTIF_PERMISSION = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListeBinding.inflate(inflater, container, false)
        notificationManager = NotificationManagerCompat.from(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                REQUEST_NOTIF_PERMISSION
            )
        }

        Log.d("ListeFragment", "onViewCreated initialized")

        ViewCompat.setOnApplyWindowInsetsListener(binding.listePieces) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.pieces.observe(viewLifecycleOwner) { lesPieces ->
            Log.d("ListeFragment", "Updating pieces list: ${lesPieces.size} items")

            if (savedInstanceState != null) {
                val savedFavoris = savedInstanceState.getBooleanArray("favorisList")
                if (savedFavoris != null) {
                    favorisList.clear()
                    favorisList.addAll(savedFavoris.toList())
                } else {
                    favorisList.clear()
                    favorisList.addAll(List(lesPieces.size) { false })
                }
            } else {
                favorisList.clear()
                favorisList.addAll(List(lesPieces.size) { false })
            }

            val adapter = PiecesRecyclerAdapter(
                requireActivity(),
                lesPieces,
                favorisList
            ) { position -> handleItemClick(position) }

            binding.listePieces.layoutManager = LinearLayoutManager(requireContext())
            binding.listePieces.adapter = adapter

            binding.allerVersCreatePieces.setOnClickListener {
                findNavController().navigate(R.id.createFragment)
            }

            setFragmentResultListener("request_key") { key, bundle ->
                val piece = bundle.getParcelable<Pieces>("pieces")
                try {
                    viewModel.addPiece(piece!!)
                } catch (e: Exception) {
                    Log.e("ListeFragment", "Erreur lors de l'ajout de la pièce: ${e.message}")
                }
            }
        }
    }

    private fun handleItemClick(position: Int) {
        Log.d("ListeFragment", "Item clicked at position: $position")
        toggleFavorite(position)
        sendNotification()
    }

    private fun sendNotification() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                REQUEST_NOTIF_PERMISSION
            )
            return
        }

        val builder = NotificationCompat.Builder(requireContext(), CarPartsApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Notification test")
            .setContentText("Ceci est une notification de test.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        notificationManager.notify(notificationId++, builder.build())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_NOTIF_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendNotification()
            }
        }
    }

    private fun toggleFavorite(position: Int) {
        if (position in 0 until favorisList.size) {
            favorisList[position] = !favorisList[position]
            binding.listePieces.adapter?.notifyItemChanged(position)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBooleanArray("favorisList", favorisList.toBooleanArray())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
