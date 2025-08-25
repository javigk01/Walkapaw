package com.example.walkapaw.LogicaActivities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.walkapaw.databinding.ActivityPaseoBinding

class PaseoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaseoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityPaseoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // PORFA REVISAR: https://developer.android.com/develop/ui/views/layout/edge-to-edge?hl=es-419#kotlin
        val controller = WindowCompat.getInsetsController(window, binding.root)
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller.hide(WindowInsetsCompat.Type.systemBars())
    }

    // Volvemos a ocultar cuando nos volvemos a meter a esta activity
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            val c = WindowCompat.getInsetsController(window, binding.root)
            c.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            c.hide(WindowInsetsCompat.Type.systemBars())
        }
    }

}
