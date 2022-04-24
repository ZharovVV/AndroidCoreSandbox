package com.github.zharovvv.android.core.sandbox.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.github.zharovvv.android.core.sandbox.R

class ColorArraySpinnerAdapter(
    context: Context,
    objects: List<Int>,
    private val resource: Int = R.layout.color_spinner_item
) : ArrayAdapter<Int>(context, resource, objects) {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return resource.createViewFromResource(inflater, position, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return resource.createViewFromResource(inflater, position, parent)
    }

    private fun Int.createViewFromResource(
        inflater: LayoutInflater,
        position: Int,
        parent: ViewGroup
    ): View {
        val layout = inflater.inflate(this, parent, false)
        val colorHolderView: View = layout.findViewById(R.id.color_holder_view)
        val color = context.resources.getColor(
            getItem(position)!!,
            context.theme
        )
        colorHolderView.setBackgroundColor(color)
        return layout
    }
}