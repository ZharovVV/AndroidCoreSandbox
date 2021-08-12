package com.github.zharovvv.android.core.sandbox.sqlite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.zharovvv.android.core.sandbox.R

class PersonListAdapter :
    ListAdapter<Person, PersonListAdapter.PersonItemHolder>(PERSON_DIFF_UTIL) {

    companion object {
        private val PERSON_DIFF_UTIL = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem == newItem
            }
        }
    }


    class PersonItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewPersonId: TextView = itemView.findViewById(R.id.text_view_person_id)
        private val textViewPersonName: TextView = itemView.findViewById(R.id.text_view_person_name)
        private val textViewPersonAge: TextView = itemView.findViewById(R.id.text_view_person_age)

        fun bind(person: Person) {
            val (name, age) = person
            textViewPersonId.text = person.id.toString()
            textViewPersonName.text = name
            age?.let { textViewPersonAge.text = it.toString() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonItemHolder {
        return PersonItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_person, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PersonItemHolder, position: Int) {
        holder.bind(getItem(position))
    }
}