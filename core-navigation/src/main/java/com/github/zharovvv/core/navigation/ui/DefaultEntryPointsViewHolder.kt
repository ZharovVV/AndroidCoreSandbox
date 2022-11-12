package com.github.zharovvv.core.navigation.ui

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.zharovvv.core.navigation.EntryPoint
import com.github.zharovvv.core.navigation.databinding.ListItemEntryPointDefaultBinding

class DefaultEntryPointsViewHolder(
    private val binding: ListItemEntryPointDefaultBinding,
    private val onItemClick: (entryPoint: EntryPoint) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var _entryPoint: EntryPoint? = null

    init {
        binding.root.setOnClickListener { _entryPoint?.let(onItemClick) }
    }

    fun bind(entryPoint: EntryPoint): Unit = with(binding) {
        _entryPoint = entryPoint
        entryPoint.iconResId?.let { entryPointIconImageView.setImageResource(it) }
        entryPointIconImageView.isVisible = entryPoint.iconResId != null
        entryPointTitleTextView.text = entryPoint.name
        entryPointDescriptionTextView.text = entryPoint.description
    }
}