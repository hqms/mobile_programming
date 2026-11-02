package com.hqm.mp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager

class DetailActivity : AppCompatActivity() {

    private lateinit var nameText: TextView
    private lateinit var majorText: TextView
    private lateinit var genderText: TextView
    private lateinit var birthDateText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.detail_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        nameText = findViewById(R.id.name_textview)
        majorText = findViewById(R.id.major_textview)
        genderText = findViewById(R.id.gender_text)
        birthDateText = findViewById(R.id.birth_date_text)

        val backBtn: Button = findViewById(R.id.button)
        backBtn.setOnClickListener {
            finish()
        }

        val saveBtn: Button = findViewById(R.id.save)
        saveBtn.setOnClickListener {
            val text = "${nameText.text}\n${majorText.text}\n${genderText.text}\n${birthDateText.text}"
            val file = openFileOutput("profile.txt", MODE_PRIVATE)
            file.write(text.toByteArray())
            file.close()

            Toast.makeText(this, "Data saved to file", Toast.LENGTH_SHORT).show()
        }

        val loadBtn: Button = findViewById(R.id.load)
        loadBtn.setOnClickListener {
            loadFromFile(showToast = true)
        }

        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val alwaysLoad = prefs.getBoolean("always_load", false)

        if (alwaysLoad) {
            val loaded = loadFromFile(showToast = false)
            if (!loaded) {
                populateFromIntent()
            }
        } else {
            populateFromIntent()
        }
    }

    private fun populateFromIntent() {
        intent.getStringExtra("NAME")?.let { nameText.text = it }
        intent.getStringExtra("MAJOR")?.let { majorText.text = it }
        intent.getStringExtra("GENDER")?.let { genderText.text = it }
        intent.getStringExtra("BIRTH_DATE")?.let { birthDateText.text = it }
    }

    private fun loadFromFile(showToast: Boolean): Boolean {
        return try {
            val file = openFileInput("profile.txt")
            val text = file.bufferedReader().use { it.readText() }
            val data = text.split("\n")
            if (data.size >= 4) {
                nameText.text = data[0]
                majorText.text = data[1]
                genderText.text = data[2]
                birthDateText.text = data[3]
                if (showToast) {
                    Toast.makeText(this, "Data loaded from file", Toast.LENGTH_SHORT).show()
                }
                file.close()
                true
            } else {
                file.close()
                false
            }
        } catch (_: Exception) {
            if (showToast) {
                Toast.makeText(this, "No saved file found", Toast.LENGTH_SHORT).show()
            }
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}