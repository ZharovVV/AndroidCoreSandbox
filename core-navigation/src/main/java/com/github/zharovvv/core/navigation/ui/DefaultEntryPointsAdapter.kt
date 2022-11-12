package com.github.zharovvv.core.navigation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.github.zharovvv.core.navigation.EntryPoint
import com.github.zharovvv.core.navigation.databinding.ListItemEntryPointDefaultBinding

class DefaultEntryPointsAdapter(
    private val onItemClick: (entryPoint: EntryPoint) -> Unit
) : ListAdapter<EntryPoint, DefaultEntryPointsViewHolder>(DefaultEntryPointsDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DefaultEntryPointsViewHolder {
        return DefaultEntryPointsViewHolder(
            binding = ListItemEntryPointDefaultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick = onItemClick
        )
    }

    override fun onBindViewHolder(holder: DefaultEntryPointsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}