package com.hqm.mp

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GalleryActivity : AppCompatActivity() {

    private var index = 0
    private val imagesList = mutableListOf<Image>()
    private lateinit var imageSwitcher: ImageSwitcher

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            loadImagesFromMediaStore()
        } else {
            Toast.makeText(this, "Permission denied to read images", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.gallery_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageSwitcher = findViewById(R.id.imageSwitcher)
        imageSwitcher.setFactory {
            val imgView = ImageView(applicationContext)
            imgView.scaleType = ImageView.ScaleType.FIT_CENTER
            imgView.setPadding(8, 8, 8, 8)
            imgView
        }

        // Load animations
        imageSwitcher.inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        imageSwitcher.outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)

        val prev = findViewById<Button>(R.id.prev)
        prev.setOnClickListener {
            if (imagesList.isNotEmpty()) {
                index = if (index - 1 >= 0) index - 1 else imagesList.size - 1
                imageSwitcher.setImageURI(imagesList[index].contentUri)
            }
        }

        val next = findViewById<Button>(R.id.next)
        next.setOnClickListener {
            if (imagesList.isNotEmpty()) {
                index = if (index + 1 < imagesList.size) index + 1 else 0
                imageSwitcher.setImageURI(imagesList[index].contentUri)
            }
        }

        checkPermissionAndLoadImages()
    }

    private fun checkPermissionAndLoadImages() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            loadImagesFromMediaStore()
        } else {
            requestPermissionLauncher.launch(permission)
        }
    }

    private fun loadImagesFromMediaStore() {
        val mediaImages = mutableListOf<Image>()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE
        )

        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            "${MediaStore.Images.Media.DATE_ADDED} DESC"
        )

        cursor?.use { c ->
            val idColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val sizeColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)

            while (c.moveToNext()) {
                val id = c.getLong(idColumn)
                val name = c.getString(nameColumn) ?: ""
                val size = c.getInt(sizeColumn)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                mediaImages.add(Image(contentUri, name, size))
            }
        }

        imagesList.clear()
        imagesList.addAll(mediaImages)

        if (imagesList.isNotEmpty()) {
            index = 0
            imageSwitcher.setImageURI(imagesList[index].contentUri)
        } else {
            Toast.makeText(this, "No images found in device gallery", Toast.LENGTH_SHORT).show()
        }
    }
}
