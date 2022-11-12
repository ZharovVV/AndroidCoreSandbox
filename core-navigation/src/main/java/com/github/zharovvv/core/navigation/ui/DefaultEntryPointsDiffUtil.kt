package com.github.zharovvv.core.navigation.ui

import androidx.recyclerview.widget.DiffUtil
import com.github.zharovvv.core.navigation.EntryPoint

internal object DefaultEntryPointsDiffUtil : DiffUtil.ItemCallback<EntryPoint>() {
    override fun areItemsTheSame(oldItem: EntryPoint, newItem: EntryPoint): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: EntryPoint, newItem: EntryPoint): Boolean =
        oldItem == newItem
}