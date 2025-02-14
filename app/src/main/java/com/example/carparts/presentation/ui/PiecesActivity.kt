package com.example.carparts.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.carparts.R
import com.example.carparts.databinding.ActivityPiecesBinding
import org.osmdroid.config.Configuration
import java.io.File

class PiecesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPiecesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPiecesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.allerVersListePieces.setOnClickListener{
            findNavController(R.id.nav_host_fragment).navigate(R.id.listeFragment)
        }

        binding.allerVersCartePieces.setOnClickListener{
            findNavController(R.id.nav_host_fragment).navigate(R.id.carteFragment)
        }

        val osmConf = Configuration.getInstance()
        val basePath = File(getCacheDir().getAbsolutePath(), "osmdroid")
        osmConf.osmdroidBasePath = basePath
        val tileCache = File(osmConf.osmdroidBasePath.absolutePath, "tile")
        osmConf.osmdroidTileCache = tileCache
        osmConf.setUserAgentValue(packageName)

    }
}