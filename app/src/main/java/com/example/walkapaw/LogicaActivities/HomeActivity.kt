package com.example.walkapaw.LogicaActivities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ArrayAdapter
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
import com.example.walkapaw.databinding.FragmentMenuPrincipalBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    // Launchers
    private lateinit var cameraLauncher: ActivityResultLauncher<Void?>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    // Guardamos referencia del binding de Cuenta
    private var cuentaBinding: FragmentCuentaBinding? = null
    private var selectedTabIndex = 3 // Por defecto inicia en MenuPrincipal


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLaunchers()
        setupClickListeners()
        showMenuPrincipalTab()
    }

    private fun setupLaunchers() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                Toast.makeText(this, "Foto tomada correctamente ✅", Toast.LENGTH_SHORT).show()
                cuentaBinding?.ivPerfilPerro?.setImageBitmap(bitmap)
            } else {
                Toast.makeText(this, "No se tomó ninguna foto ❌", Toast.LENGTH_SHORT).show()
            }
        }

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                Toast.makeText(this, "Permiso de cámara denegado ⚠️", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openCamera() {
        cameraLauncher.launch(null)
    }

    private fun setupClickListeners() {
        binding.tabSolicitar.setOnClickListener { showSolicitarTab() }
        binding.tabActividad.setOnClickListener { showActividadTab() }
        binding.tabCuenta.setOnClickListener { showCuentaTab() }
        binding.tabMenuPrincipal.setOnClickListener { showMenuPrincipalTab() }
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
    }

    private fun showCuentaTab() {
        updateTabSelection(2)
        cuentaBinding = FragmentCuentaBinding.inflate(
            layoutInflater, binding.frameContent, false
        )

        binding.frameContent.removeAllViews()
        binding.frameContent.addView(cuentaBinding!!.root)

        val sharedPref = getSharedPreferences("perfil_perro", MODE_PRIVATE)
        val nombre = sharedPref.getString("nombre", "Lucas el perro")
        val raza = sharedPref.getString("raza", "Criollito")
        val fotoBase64 = sharedPref.getString("foto", null)

        cuentaBinding!!.tvNombrePerro.text = nombre
        cuentaBinding!!.tvRazaPerro.text = raza

        fotoBase64?.let {
            val bytes = android.util.Base64.decode(it, android.util.Base64.DEFAULT)
            val bitmap = android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            cuentaBinding!!.ivPerfilPerro.setImageBitmap(bitmap)
        }

        cuentaBinding!!.btnEditarPerfil.setOnClickListener {
            val intent = Intent(this, EditarPerfilActivity::class.java)
            startActivity(intent)
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


    private fun showMenuPrincipalTab() {
        updateTabSelection(3)

        val menuPrincipalBinding = FragmentMenuPrincipalBinding.inflate(
            layoutInflater, binding.frameContent, false
        )

        binding.frameContent.removeAllViews()
        binding.frameContent.addView(menuPrincipalBinding.root)

        val opcionesMenu = listOf(
            "Viajes",
            "Pagos",
            "Promociones",
            "Ayuda",
            "Configuración"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, opcionesMenu)
        menuPrincipalBinding.listViewOpciones.adapter = adapter

        menuPrincipalBinding.listViewOpciones.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "Seleccionaste: ${opcionesMenu[position]}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTabSelection(selectedTab: Int) {
        selectedTabIndex = selectedTab // 🔥 Guardamos el índice de la pestaña activa
        resetTabColors()
        when (selectedTab) {
            0 -> highlightTab(binding.tabSolicitar)
            1 -> highlightTab(binding.tabActividad)
            2 -> highlightTab(binding.tabCuenta)
            3 -> highlightTab(binding.tabMenuPrincipal)
        }
    }

    private fun resetTabColors() {
        setTabColor(binding.tabSolicitar, android.R.color.darker_gray)
        setTabColor(binding.tabActividad, android.R.color.darker_gray)
        setTabColor(binding.tabCuenta, android.R.color.darker_gray)
        setTabColor(binding.tabMenuPrincipal, android.R.color.darker_gray)
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

    override fun onResume() {
        super.onResume()
        if (selectedTabIndex == 2) {
            showCuentaTab() // Recarga los datos guardados
        }
    }

    // Método para saber si la pestaña Cuenta está seleccionada
    private fun isCuentaTabSelected(): Boolean {
        // Verifica el color/texto del tab seleccionado
        // O si quieres algo más simple:
        // Guarda una variable del tab actual
        return selectedTabIndex == 2
    }

}
