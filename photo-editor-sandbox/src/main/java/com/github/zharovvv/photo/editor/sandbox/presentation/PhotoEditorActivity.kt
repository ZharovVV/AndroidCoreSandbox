package com.github.zharovvv.photo.editor.sandbox.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.github.zharovvv.photo.editor.sandbox.R


internal class PhotoEditorActivity : AppCompatActivity(R.layout.activity_photo_editor) {

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, PhotoEditorActivity::class.java)
    }
}