package com.github.zharovvv.android.core.sandbox.noncore.launcher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.zharovvv.android.core.sandbox.R

class LaunchersListAdapter :
    ListAdapter<Launcher, LaunchersListAdapter.LauncherItemHolder>(LAUNCHER_ITEM_DIFF) {

    companion object {
        private val LAUNCHER_ITEM_DIFF = object : DiffUtil.ItemCallback<Launcher>() {
            override fun areItemsTheSame(oldItem: Launcher, newItem: Launcher): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Launcher, newItem: Launcher): Boolean {
                return oldItem == newItem
            }
        }
    }

    class LauncherItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val launcherButton: Button = itemView.findViewById(R.id.launcher_button)
        private var currentLauncher: Launcher? = null

        init {
            launcherButton.setOnClickListener {
                currentLauncher?.launch()
            }
        }

        fun bind(launcher: Launcher) {
            currentLauncher = launcher
            launcherButton.text = launcher.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LauncherItemHolder {
        return LauncherItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_launcher, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LauncherItemHolder, position: Int) {
        holder.bind(getItem(position))
    }
}