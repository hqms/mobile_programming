package com.hqm.mp

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import android.content.Intent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.home_screen)

        val profileBtn : Button = findViewById(R.id.profile_btn)
        profileBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val aboutBtn : Button = findViewById(R.id.about_btn)
        aboutBtn.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }

    // On Start, when activity is visible to user
    override fun onStart() {
        super.onStart()
    }

    // On Resume, when activity is visible to user and has focus
    override fun onResume() {
        super.onResume()
    }

    // On Pause, when activity is not visible to user
    override fun onPause() {
        super.onPause()
    }

    // On Stop, when activity is no longer visible to user
    override fun onStop() {
        super.onStop()
    }

}
