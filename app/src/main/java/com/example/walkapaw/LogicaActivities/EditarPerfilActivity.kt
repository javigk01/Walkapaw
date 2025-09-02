package com.example.walkapaw.LogicaActivities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.walkapaw.R
import com.example.walkapaw.databinding.ActivityEditarPerfilBinding
import java.io.ByteArrayOutputStream

class EditarPerfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditarPerfilBinding

    private lateinit var cameraLauncher: ActivityResultLauncher<Void?>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var selectedBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("perfil_perro", Context.MODE_PRIVATE)

        // Cargamos datos previos
        binding.etNombrePerro.setText(sharedPref.getString("nombre", ""))
        binding.etRazaPerro.setText(sharedPref.getString("raza", ""))

        // Mostramos foto guardada si existe
        val fotoBase64 = sharedPref.getString("foto", null)
        if (!fotoBase64.isNullOrEmpty()) {
            val bitmap = base64ToBitmap(fotoBase64)
            binding.ivPerfilPerro.setImageBitmap(bitmap)
            selectedBitmap = bitmap
        } else {
            binding.ivPerfilPerro.setImageResource(R.drawable.dog_placeholder)
        }

        setupCameraLaunchers()

        binding.btnTomarFoto.setOnClickListener {
            checkPermissionAndOpenCamera()
        }

        binding.btnEliminarFoto.setOnClickListener {
            selectedBitmap = null
            binding.ivPerfilPerro.setImageResource(R.drawable.dog_placeholder)
            Toast.makeText(this, "Foto eliminada", Toast.LENGTH_SHORT).show()
        }

        binding.btnGuardar.setOnClickListener {
            val nombre = binding.etNombrePerro.text.toString().trim()
            val raza = binding.etRazaPerro.text.toString().trim()

            if (nombre.isEmpty() || raza.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                sharedPref.edit().apply {
                    putString("nombre", nombre)
                    putString("raza", raza)

                    if (selectedBitmap != null) {
                        putString("foto", bitmapToBase64(selectedBitmap!!))
                    } else {
                        remove("foto") //Si no hay foto eliminamos la key
                    }

                    apply()
                }
                Toast.makeText(this, "Perfil guardado", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun setupCameraLaunchers() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                selectedBitmap = bitmap
                binding.ivPerfilPerro.setImageBitmap(bitmap)
                Toast.makeText(this, "Foto tomada", Toast.LENGTH_SHORT).show()
            }
        }

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPermissionAndOpenCamera() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> openCamera()
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                Toast.makeText(this, "Se necesita permiso para usar la cámara", Toast.LENGTH_LONG).show()
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun openCamera() {
        cameraLauncher.launch(null)
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }

    private fun base64ToBitmap(base64: String): Bitmap {
        val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
}
