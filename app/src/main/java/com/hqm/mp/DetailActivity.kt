package com.hqm.mp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.detail_screen)

        val name = intent.getStringExtra("NAME")
        val major = intent.getStringExtra("MAJOR")

        val nameText = findViewById<TextView>(R.id.name_textview)
        nameText.text = name

        val majorText = findViewById<TextView>(R.id.major_textview)
        majorText.text = major

        val gender = intent.getStringExtra("GENDER")
        val genderText = findViewById<TextView>(R.id.gender_text)
        genderText.text = gender

        val backBtn : Button = findViewById(R.id.button)
        backBtn.setOnClickListener {
            finish()
            }
        }
    }