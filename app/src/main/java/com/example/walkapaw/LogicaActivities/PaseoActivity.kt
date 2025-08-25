package com.example.walkapaw.LogicaActivities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.walkapaw.databinding.ActivityPaseoBinding

class PaseoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaseoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaseoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
