package com.example.walkapaw.LogicaActivities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.walkapaw.R
import com.example.walkapaw.databinding.ActivityHomeBinding
import com.example.walkapaw.databinding.FragmentActividadBinding
import com.example.walkapaw.databinding.FragmentCuentaBinding
import com.example.walkapaw.databinding.FragmentSolicitarBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    // 🚀 Launchers
    private lateinit var cameraLauncher: ActivityResultLauncher<Void?>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    // Guardamos la referencia del binding de Cuenta
    private var cuentaBinding: FragmentCuentaBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLaunchers()
        setupClickListeners()
        showSolicitarTab()
    }

    private fun setupLaunchers() {
        // Launcher para abrir la cámara
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                Toast.makeText(this, "Foto tomada correctamente ✅", Toast.LENGTH_SHORT).show()
                // 👉 Mostrar la foto en el ImageView del perfil
                cuentaBinding?.ivPerfilPerro?.setImageBitmap(bitmap)
            } else {
                Toast.makeText(this, "No se tomó ninguna foto ❌", Toast.LENGTH_SHORT).show()
            }
        }

        // Launcher para pedir permisos
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                Toast.makeText(this, "Permiso de cámara denegado ⚠️", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupClickListeners() {
        binding.tabSolicitar.setOnClickListener { showSolicitarTab() }
        binding.tabActividad.setOnClickListener { showActividadTab() }
        binding.tabCuenta.setOnClickListener { showCuentaTab() }
    }

    private fun showSolicitarTab() {
        updateTabSelection(0)

        val solicitarBinding = FragmentSolicitarBinding.inflate(
            layoutInflater, binding.frameContent, false
        )

        binding.frameContent.removeAllViews()
        binding.frameContent.addView(solicitarBinding.root)

        solicitarBinding.btnSolicitarPaseo.setOnClickListener {
            startActivity(Intent(this, PaseoActivity::class.java))
        }
    }

    private fun showActividadTab() {
        updateTabSelection(1)

        val actividadBinding = FragmentActividadBinding.inflate(
            layoutInflater, binding.frameContent, false
        )

        binding.frameContent.removeAllViews()
        binding.frameContent.addView(actividadBinding.root)

        // ⚠️ Placeholder Toast si en el futuro quieres acciones
        Toast.makeText(this, "Sección Actividad abierta 📋", Toast.LENGTH_SHORT).show()
    }

    private fun showCuentaTab() {
        updateTabSelection(2)

        cuentaBinding = FragmentCuentaBinding.inflate(
            layoutInflater, binding.frameContent, false
        )

        binding.frameContent.removeAllViews()
        binding.frameContent.addView(cuentaBinding!!.root)

        cuentaBinding!!.btnTomarFoto.setOnClickListener {
            checkPermissionAndOpenCamera()
        }

        cuentaBinding!!.btnEditarPerfil.setOnClickListener {
            Toast.makeText(this, "Editar perfil ⚙️ (en construcción)", Toast.LENGTH_SHORT).show()
        }

        cuentaBinding!!.btnConfiguracion.setOnClickListener {
            Toast.makeText(this, "Configuración ⚙️ (en construcción)", Toast.LENGTH_SHORT).show()
        }

        cuentaBinding!!.btnCerrarSesion.setOnClickListener {
            Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        }
    }

    // 🚀 Verificar permisos
    private fun checkPermissionAndOpenCamera() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                Toast.makeText(this, "Se necesita permiso para usar la cámara 📷", Toast.LENGTH_LONG).show()
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    // 🚀 Abrir cámara
    private fun openCamera() {
        cameraLauncher.launch(null)
    }

    private fun updateTabSelection(selectedTab: Int) {
        resetTabColors()
        when (selectedTab) {
            0 -> highlightTab(binding.tabSolicitar)
            1 -> highlightTab(binding.tabActividad)
            2 -> highlightTab(binding.tabCuenta)
        }
    }

    private fun resetTabColors() {
        setTabColor(binding.tabSolicitar, android.R.color.darker_gray)
        setTabColor(binding.tabActividad, android.R.color.darker_gray)
        setTabColor(binding.tabCuenta, android.R.color.darker_gray)
    }

    private fun highlightTab(tab: LinearLayout) {
        setTabColor(tab, R.color.color_primario)
    }

    private fun setTabColor(tab: LinearLayout, colorRes: Int) {
        val imageView = tab.getChildAt(0) as ImageView
        val textView = tab.getChildAt(1) as TextView
        val color = ContextCompat.getColor(this, colorRes)

        imageView.setColorFilter(color)
        textView.setTextColor(color)
    }
}
