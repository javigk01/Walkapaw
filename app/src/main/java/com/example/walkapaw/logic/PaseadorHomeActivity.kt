package com.example.walkapaw.logic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.walkapaw.databinding.ActivityHomePaseadorBinding
class PaseadorHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomePaseadorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePaseadorBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}