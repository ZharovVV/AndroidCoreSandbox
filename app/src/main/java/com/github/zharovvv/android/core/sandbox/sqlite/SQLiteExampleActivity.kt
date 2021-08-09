package com.github.zharovvv.android.core.sandbox.sqlite

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.zharovvv.android.core.sandbox.AndroidCoreSandboxApplication
import com.github.zharovvv.android.core.sandbox.LogLifecycleAppCompatActivity
import com.github.zharovvv.android.core.sandbox.R

class SQLiteExampleActivity : LogLifecycleAppCompatActivity(R.layout.activity_sqlite_example) {

    private lateinit var editTextPersonName: EditText
    private lateinit var editTextPersonAge: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonDelete: Button
    private lateinit var buttonClearAllTable: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var personListAdapter: PersonListAdapter
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editTextPersonName = findViewById(R.id.edit_text_column_person_name)
        editTextPersonAge = findViewById(R.id.edit_text_column_person_age)
        buttonSave = findViewById(R.id.button_save_person)
        buttonDelete = findViewById(R.id.button_delete_person)
        buttonClearAllTable = findViewById(R.id.button_clear_all_table)
        recyclerView = findViewById(R.id.recycler_view_columns)
        personListAdapter = PersonListAdapter()
        recyclerView.apply {
            adapter = personListAdapter
            layoutManager = LinearLayoutManager(this@SQLiteExampleActivity)
        }
        buttonSave.setOnClickListener {
            doAfterValidate { person ->
                insertPerson(person)
            }
        }
        buttonDelete.setOnClickListener {
            doAfterValidate {
                deletePerson(it)
            }
        }
        buttonClearAllTable.setOnClickListener {
            clearAllTable()
        }
        initLoad()
    }

    override fun onDestroy() {
        AndroidCoreSandboxApplication.personDatabase.close()
        super.onDestroy()
    }

    private inline fun doAfterValidate(block: (person: Person) -> Unit) {
        if (editTextPersonName.text.isNullOrEmpty()) {
            editTextPersonName.setHintTextColor(resources.getColor(R.color.red, theme))
            handler.postDelayed({
                editTextPersonName.setHintTextColor(resources.getColor(R.color.gray, theme))
            }, 500)
        } else {
            val stringAge = editTextPersonAge.text.toString()
            val person = Person(
                editTextPersonName.text.toString(),
                if (stringAge.isNotEmpty()) {
                    stringAge.toInt()
                } else {
                    null
                }
            )
            block(person)
        }
    }

    private fun initLoad() {
        Thread {
            val persons = AndroidCoreSandboxApplication.personDatabase.personDao.getPersons()
            handler.post {
                updatePersonList(persons)
            }
        }.start()
    }

    private fun insertPerson(person: Person) {
        Thread {
            val personDao = AndroidCoreSandboxApplication.personDatabase.personDao
            personDao.insert(person)
            val persons = personDao.getPersons()
            handler.post {
                updatePersonList(persons)
            }
        }.start()
    }

    private fun deletePerson(person: Person) {
        Thread {
            val personDao = AndroidCoreSandboxApplication.personDatabase.personDao
            if (personDao.delete(person)) {
                val persons = personDao.getPersons()
                handler.post {
                    updatePersonList(persons)
                }
            }
        }.start()
    }

    private fun clearAllTable() {
        Thread {
            val personDao = AndroidCoreSandboxApplication.personDatabase.personDao
            personDao.clearAllTable()
            handler.post {
                updatePersonList(null)
            }
        }.start()
    }

    private fun updatePersonList(persons: List<Person>?) {
        personListAdapter.submitList(persons)
    }
}