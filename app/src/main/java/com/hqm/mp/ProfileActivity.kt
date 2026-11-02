package com.hqm.mp

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import java.util.Calendar
import java.util.Locale
import android.widget.ArrayAdapter
import android.widget.ListView
class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.profile_screen)

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