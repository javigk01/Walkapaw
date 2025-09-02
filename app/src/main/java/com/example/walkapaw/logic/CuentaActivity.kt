package com.example.walkapaw.logic

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.walkapaw.databinding.ActivityCuentaBinding

class CuentaActivity : AppCompatActivity() {

    private lateinit var b: ActivityCuentaBinding

    private lateinit var cameraLauncher: ActivityResultLauncher<Void?>
    private lateinit var requestCameraPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var galleryLauncher: ActivityResultLauncher<String>
    private lateinit var requestGalleryPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityCuentaBinding.inflate(layoutInflater)
        setContentView(b.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Cuenta"

        setupLaunchers()
        loadProfile()

        // Galería (en 10/11 normalmente no requiere permiso con GetContent)
        b.btnSubirFoto.setOnClickListener {
            val perm = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                Manifest.permission.READ_MEDIA_IMAGES
            else
                Manifest.permission.READ_EXTERNAL_STORAGE

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(this, perm) == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                requestGalleryPermissionLauncher.launch(perm)
            }
        }

        // Tocar imagen = abrir cámara (opcional)
        b.ivPerfilPerro.setOnClickListener { checkCameraAndOpen() }

        b.btnEditarPerfil.setOnClickListener {
            startActivity(Intent(this, EditarPerfilActivity::class.java))
        }

        b.btnConfiguracion.setOnClickListener {
            Toast.makeText(this, "Configuración (en construcción)", Toast.LENGTH_SHORT).show()
        }

        b.btnCerrarSesion.setOnClickListener {
            Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_SHORT).show()
            val i = Intent(this, LoginAndRegisterActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(i)
        }
    }

    private fun setupLaunchers() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bm ->
            if (bm != null) b.ivPerfilPerro.setImageBitmap(bm)
            else Toast.makeText(this, "No se tomó ninguna foto", Toast.LENGTH_SHORT).show()
        }

        requestCameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) openCamera() else Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                contentResolver.openInputStream(uri)?.use {
                    val bm = BitmapFactory.decodeStream(it)
                    b.ivPerfilPerro.setImageBitmap(bm)
                }
                Toast.makeText(this, "Imagen seleccionada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No seleccionaste imagen", Toast.LENGTH_SHORT).show()
            }
        }

        requestGalleryPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) openGallery() else Toast.makeText(this, "Permiso de galería denegado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadProfile() {
        val sp = getSharedPreferences("perfil_perro", MODE_PRIVATE)
        b.tvNombrePerro.text = sp.getString("nombre", "Lucas el perro")
        b.tvRazaPerro.text = sp.getString("raza", "Criollito")
        sp.getString("foto", null)?.let {
            val bytes = android.util.Base64.decode(it, android.util.Base64.DEFAULT)
            val bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            b.ivPerfilPerro.setImageBitmap(bm)
        }
    }

    private fun checkCameraAndOpen() {
        val perm = Manifest.permission.CAMERA
        if (ContextCompat.checkSelfPermission(this, perm) == PackageManager.PERMISSION_GRANTED) openCamera()
        else requestCameraPermissionLauncher.launch(perm)
    }

    private fun openCamera() = cameraLauncher.launch(null)
    private fun openGallery() = galleryLauncher.launch("image/*")

    override fun onResume() {
        super.onResume()
        // Si vuelves desde EditarPerfil, refresca:
        loadProfile()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
