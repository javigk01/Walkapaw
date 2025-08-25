package com.example.walkapaw

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var frameContent: android.widget.FrameLayout
    private lateinit var tabSolicitar: LinearLayout
    private lateinit var tabActividad: LinearLayout
    private lateinit var tabCuenta: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        setupClickListeners()
        showSolicitarTab()
    }

    private fun initViews() {
        frameContent = findViewById(R.id.frameContent)
        tabSolicitar = findViewById(R.id.tabSolicitar)
        tabActividad = findViewById(R.id.tabActividad)
        tabCuenta = findViewById(R.id.tabCuenta)
    }

    private fun setupClickListeners() {
        tabSolicitar.setOnClickListener { showSolicitarTab() }
        tabActividad.setOnClickListener { showActividadTab() }
        tabCuenta.setOnClickListener { showCuentaTab() }
    }

    private fun showSolicitarTab() {
        updateTabSelection(0)
        val view = LayoutInflater.from(this).inflate(R.layout.fragment_solicitar, frameContent, false)
        frameContent.removeAllViews()
        frameContent.addView(view)

        view.findViewById<Button>(R.id.btnSolicitarPaseo).setOnClickListener {
            // Lógica para solicitar paseo
        }

        view.findViewById<Button>(R.id.btnPaseoCorto).setOnClickListener {
            // Lógica para paseo corto
        }

        view.findViewById<Button>(R.id.btnPaseoLargo).setOnClickListener {
            // Lógica para paseo largo
        }
    }

    private fun showActividadTab() {
        updateTabSelection(1)
        val view = LayoutInflater.from(this).inflate(R.layout.fragment_actividad, frameContent, false)
        frameContent.removeAllViews()
        frameContent.addView(view)

        // Configurar RecyclerView con el historial
    }

    private fun showCuentaTab() {
        updateTabSelection(2)
        val view = LayoutInflater.from(this).inflate(R.layout.fragment_cuenta, frameContent, false)
        frameContent.removeAllViews()
        frameContent.addView(view)

        view.findViewById<Button>(R.id.btnEditarPerfil).setOnClickListener {
            // Navegar a editar perfil
        }

        view.findViewById<Button>(R.id.btnConfiguracion).setOnClickListener {
            // Navegar a configuración
        }

        view.findViewById<Button>(R.id.btnCerrarSesion).setOnClickListener {
            // Cerrar sesión y regresar a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun updateTabSelection(selectedTab: Int) {
        // Resetear colores de todas las tabs
        resetTabColors()

        // Marcar la tab seleccionada
        when (selectedTab) {
            0 -> highlightTab(tabSolicitar)
            1 -> highlightTab(tabActividad)
            2 -> highlightTab(tabCuenta)
        }
    }

    private fun resetTabColors() {
        setTabColor(tabSolicitar, android.R.color.darker_gray)
        setTabColor(tabActividad, android.R.color.darker_gray)
        setTabColor(tabCuenta, android.R.color.darker_gray)
    }

    private fun highlightTab(tab: LinearLayout) {
        setTabColor(tab, R.color.primary_color)
    }

    private fun setTabColor(tab: LinearLayout, colorRes: Int) {
        val imageView = tab.getChildAt(0) as android.widget.ImageView
        val textView = tab.getChildAt(1) as android.widget.TextView

        val color = resources.getColor(colorRes, theme)
        imageView.setColorFilter(color)
        textView.setTextColor(color)
    }
}