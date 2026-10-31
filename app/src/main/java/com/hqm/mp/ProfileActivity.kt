package com.hqm.mp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import android.content.Intent
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.ComponentActivity


class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.profile_screen)

        val detailBtn : Button = findViewById(R.id.detail_btn)
        detailBtn.setOnClickListener {
            val name = findViewById<TextView>(R.id.name_text).text.toString()
            val major = findViewById<TextView>(R.id.major_text).text.toString()
            val checked = findViewById<RadioGroup>(R.id.gender_group).getCheckedRadioButtonId()
            val gender = findViewById<TextView>(checked).text.toString()


            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("NAME", name)
            intent.putExtra("MAJOR", major)
            intent.putExtra("GENDER", gender)
            startActivity(intent)
        }
    }
}