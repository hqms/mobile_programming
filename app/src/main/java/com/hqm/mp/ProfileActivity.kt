package com.hqm.mp

import android.content.DialogInterface
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import android.content.Intent
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import android.widget.Toast

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

                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra("NAME", name)
                    intent.putExtra("MAJOR", major)
                    intent.putExtra("GENDER", gender)
                    startActivity(intent)
                }

                if (which == DialogInterface.BUTTON_NEGATIVE){
                    dialog.dismiss()
                }
            }
        }
    }
}