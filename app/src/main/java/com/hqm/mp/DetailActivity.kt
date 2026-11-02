package com.hqm.mp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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

        val birthDate = intent.getStringExtra("BIRTH_DATE")
        val birthDateText = findViewById<TextView>(R.id.birth_date_text)
        birthDateText.text = birthDate

        val backBtn : Button = findViewById(R.id.button)
        backBtn.setOnClickListener {
            finish()
            }

        val saveBtn : Button = findViewById(R.id.save)
        saveBtn.setOnClickListener {
            val text = "$name\n$major\n$gender\n$birthDate"
            val file = openFileOutput("profile.txt", MODE_PRIVATE)
            file.write(text.toByteArray())
            file.close()

            Toast.makeText(this ,"Data saved to file", Toast.LENGTH_SHORT).show()
        }

        val loadBtn : Button = findViewById(R.id.load)
        loadBtn.setOnClickListener {
            val file = openFileInput("profile.txt")
            val text = file.bufferedReader().use { it.readText() }
            Toast.makeText(this, "Data loaded from file", Toast.LENGTH_SHORT).show()
            val data = text.split("\n")
            nameText.text = data[0]
            majorText.text = data[1]
            genderText.text = data[2]
            birthDateText.text = data[3]
            file.close()
        }
    }
}