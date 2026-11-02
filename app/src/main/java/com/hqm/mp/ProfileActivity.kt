package com.hqm.mp

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import java.util.Calendar
import java.util.Locale

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.profile_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val detailBtn : Button = findViewById(R.id.detail_btn)
        detailBtn.setOnClickListener {
            val checked = findViewById<RadioGroup>(R.id.gender_group).getCheckedRadioButtonId()

            if (checked == -1) {
                Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Agreement")
                .setMessage("I hereby declare that the personal data I provide is done so on a voluntary basis and " +
                        "I understand that the data will be used by the staff members and students who are participating " +
                        "in the service-learning project")
                .setPositiveButton("OK", dialogListener())
                .setNegativeButton("Cancel", dialogListener())
                .show()

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        val datepicker: Button = findViewById<Button>(R.id.datepicker)
        datepicker.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dateDialog : DatePickerDialog = DatePickerDialog(
                this,
                {_ , year, month, day ->
                    val formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", day, month + 1, year)
                    findViewById<EditText>(R.id.birth_date).setText(formattedDate)
                },
                year,
                month,
                day
            )

            dateDialog.show()
        }

        val galleryBtn : Button = findViewById(R.id.btn_gallery)
        galleryBtn.setOnClickListener {
            val intent = Intent(this, GalleryActivity::class.java)
            startActivity(intent)
        }

        val cities = arrayOf("Jakarta", "Bandung", "Surabaya")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cities)

        val listView = findViewById<ListView>(R.id.city)
        listView.adapter = adapter

        loadProfileDataIfAlwaysLoad()
    }

    override fun onResume() {
        super.onResume()
        loadProfileDataIfAlwaysLoad()
    }

    private fun loadProfileDataIfAlwaysLoad() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val alwaysLoad = prefs.getBoolean("always_load", false)

        if (alwaysLoad) {
            try {
                val file = openFileInput("profile.txt")
                val text = file.bufferedReader().use { it.readText() }
                val data = text.split("\n")
                if (data.size >= 4) {
                    findViewById<EditText>(R.id.name_text).setText(data[0])
                    findViewById<EditText>(R.id.major_text).setText(data[1])

                    val genderGroup = findViewById<RadioGroup>(R.id.gender_group)
                    val genderStr = data[2].trim()
                    if (genderStr.equals("Male", ignoreCase = true)) {
                        genderGroup.check(R.id.man_radio)
                    } else if (genderStr.equals("Female", ignoreCase = true)) {
                        genderGroup.check(R.id.women_radio)
                    }

                    findViewById<EditText>(R.id.birth_date).setText(data[3])
                }
                file.close()
            } catch (_: Exception) {
            }
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

    fun dialogListener(): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { dialog, which ->
            run {
                dialog.dismiss()
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    val name = findViewById<TextView>(R.id.name_text).text.toString()
                    val major = findViewById<TextView>(R.id.major_text).text.toString()
                    val checked =
                        findViewById<RadioGroup>(R.id.gender_group).getCheckedRadioButtonId()
                    val gender = findViewById<TextView>(checked).text.toString()
                    val birthDate = findViewById<TextView>(R.id.birth_date).text.toString()

                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra("NAME", name)
                    intent.putExtra("MAJOR", major)
                    intent.putExtra("GENDER", gender)
                    intent.putExtra("BIRTH_DATE", birthDate)
                    startActivity(intent)
                }

                if (which == DialogInterface.BUTTON_NEGATIVE){
                    dialog.dismiss()
                }
            }
        }
    }

    fun dateDialogListener(): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { dialog, which ->
            run {

            }
        }
    }
}