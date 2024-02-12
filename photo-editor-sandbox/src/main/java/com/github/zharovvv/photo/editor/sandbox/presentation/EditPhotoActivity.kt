package com.github.zharovvv.photo.editor.sandbox.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.github.zharovvv.photo.editor.sandbox.databinding.ActivityEditPhotoBinding

internal class EditPhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageUri = intent.getParcelableExtra<Uri>(IMAGE_URI_KEY)!!
        val binding = ActivityEditPhotoBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.imageEditorView.setImageUri(imageUri)
    }

    companion object {

        private const val IMAGE_URI_KEY = "IMAGE_URI_KEY"
        fun createIntent(context: Context, imageUri: Uri): Intent {
            return Intent(context, EditPhotoActivity::class.java)
                .apply {
                    putExtra(IMAGE_URI_KEY, imageUri)
                }
        }
    }
}