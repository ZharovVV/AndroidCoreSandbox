package com.github.zharovvv.android.core.sandbox.exoplayer

import android.os.Bundle
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.github.zharovvv.android.core.sandbox.databinding.ActivityExoPlayerBinding
import com.github.zharovvv.core.ui.activity.LogLifecycleAppCompatActivity

internal class ExoPlayerActivity : LogLifecycleAppCompatActivity() {

    private lateinit var binding: ActivityExoPlayerBinding
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExoPlayerBinding.inflate(layoutInflater)
        player = ExoPlayer.Builder(this).build()
        binding.exoPlayerView.player = player

        val mediaItem = MediaItem.fromUri("https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4")
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

}