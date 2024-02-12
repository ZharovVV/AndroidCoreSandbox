package com.github.zharovvv.photo.editor.sandbox.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.appcompat.app.AppCompatActivity
import com.github.zharovvv.photo.editor.sandbox.databinding.ActivityPhotoEditorBinding


internal class PhotoEditorActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(this)
        val binding = ActivityPhotoEditorBinding.inflate(inflater)
        setContentView(binding.root)
        var pickedImageUri: Uri? = null
        val pickMedia = registerForActivityResult(PickVisualMedia()) { uri: Uri? ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                pickedImageUri = uri
                binding.pickedPhotoImageView.setImageURI(uri)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
        binding.pickPhotoButton.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }
        binding.pickedPhotoImageView.setOnClickListener {
            pickedImageUri?.let { imageUri ->
                startActivity(EditPhotoActivity.createIntent(this, imageUri))
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, PhotoEditorActivity::class.java)
    }
}