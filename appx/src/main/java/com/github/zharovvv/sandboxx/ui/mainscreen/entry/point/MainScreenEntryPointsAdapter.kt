package com.github.zharovvv.sandboxx.ui.mainscreen.entry.point

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.zharovvv.core.navigation.EntryPoint
import com.github.zharovvv.sandboxx.databinding.ListItemMainScreenEntryPointBinding

class MainScreenEntryPointsAdapter(
    private val onItemClickBlock: (entryPoint: EntryPoint) -> Unit
) : ListAdapter<EntryPoint, MainScreenEntryPointsAdapter.MainScreenEntryPointsViewHolder>(
    MainScreenEntryPointsDiffUtil
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainScreenEntryPointsViewHolder {
        return MainScreenEntryPointsViewHolder(
            binding = ListItemMainScreenEntryPointBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClickBlock = onItemClickBlock
        )
    }

    override fun onBindViewHolder(holder: MainScreenEntryPointsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MainScreenEntryPointsViewHolder(
        private val binding: ListItemMainScreenEntryPointBinding,
        private val onItemClickBlock: (entryPoint: EntryPoint) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var _entryPoint: EntryPoint? = null

        init {
            binding.root.setOnClickListener { _entryPoint?.let(onItemClickBlock) }
        }

        fun bind(entryPoint: EntryPoint): Unit = with(binding) {
            _entryPoint = entryPoint
            entryPoint.iconResId?.let { entryPointIconImageView.setImageResource(it) }
            entryPointIconImageView.isVisible = entryPoint.iconResId != null
            entryPointTitleTextView.text = entryPoint.name
            entryPointDescriptionTextView.text = entryPoint.description
        }
    }

    private object MainScreenEntryPointsDiffUtil : DiffUtil.ItemCallback<EntryPoint>() {
        override fun areItemsTheSame(oldItem: EntryPoint, newItem: EntryPoint): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: EntryPoint, newItem: EntryPoint): Boolean =
            oldItem == newItem
    }
}