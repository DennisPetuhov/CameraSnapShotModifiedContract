package com.example.camerasnapshotmodifiedcontract

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.example.camerasnapshotmodifiedcontract.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {
    val content = registerForActivityResult(ModifyedContractCamera()) {
        if (it.first) {
            previewImage.setImageURI(it.second)
        }

    }
    private val myContentFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()){
            it?.let {
                previewImage.setImageURI(it)

            }

        }
    lateinit var binding: ActivityMainBinding
    private val previewImage by lazy { binding.image }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonCapturePhoto.setOnClickListener {
            openCamera()

        }
        binding.buttonOpenGallery.setOnClickListener {
            getImageFromGallery()
        }
    }

    private fun getImageFromGallery() {
        lifecycleScope.launch{
            myContentFromGallery.launch("image/*")
        }
    }

    private fun openCamera() {
        lifecycleScope.launch {
            createUri().let {
                content.launch(it)

            }


        }
    }

    @SuppressLint("WeekBasedYear")
    fun createUri(): Uri {
        val timeStamp: String = SimpleDateFormat("DD_MM_YYYY_hh_mm", Locale.GERMAN).format(Date())
        val externalDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val tmpFile = File.createTempFile("${timeStamp}", ".jpg", externalDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(
            this,
            "com.example.camerasnapshotmodifiedcontract.fileprovider",
            tmpFile
        )
    }
}