package com.github.zharovvv.android.core.sandbox.sqlite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

class PersonDao(sqLiteDBProviderDelegate: PersonDatabase.SQLiteDBProviderDelegate) {

    companion object {
        const val TABLE_NAME = "person_table"
    }

    private val sqLiteDatabase: SQLiteDatabase by sqLiteDBProviderDelegate

    fun savePerson(person: Person) {
        if (hasPerson(person)) {
            update(person)
        } else {
            insert(person)
        }
    }

    fun insert(person: Person): Long {
        val contentValues = ContentValues()
        contentValues.put("name", person.name)
        contentValues.put("age", person.age)
        if (person.id != null) {
            contentValues.put("id", person.id)
        }
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues)
    }

    fun update(person: Person) {
        val contentValues = ContentValues()
        contentValues.put("name", person.name)
        contentValues.put("age", person.age)
        sqLiteDatabase.update(TABLE_NAME, contentValues, "id = ?", arrayOf(person.id.toString()))
    }

    fun getPersons(): List<Person> {
        val cursor = sqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, null)
        val persons = mutableListOf<Person>()
        cursor.use {
            if (it.moveToFirst()) {
                val idIndex = it.getColumnIndex("id")
                val nameIndex = it.getColumnIndex("name")
                val ageIndex = it.getColumnIndex("age")
                do {
                    val person = Person(
                        it.getString(nameIndex),
                        it.getInt(ageIndex)
                    )
                    person.id = it.getLong(idIndex)
                    persons.add(person)
                } while (it.moveToNext())
            }
        }
        return persons
    }

    fun delete(person: Person): Boolean {
        return if (person.id != null) {
            sqLiteDatabase.delete(TABLE_NAME, "id = ${person.id}", null) != 0
        } else {
            false
        }
    }

    fun clearAllTable() {
        sqLiteDatabase.delete(TABLE_NAME, null, null)
    }

    private fun hasPerson(person: Person): Boolean {
        if (person.id == null) {
            return false
        } else {
            val cursor =
                sqLiteDatabase.rawQuery("SELECT id FROM $TABLE_NAME WHERE id=${person.id}", null)
            cursor.use {
                return it.moveToFirst()
            }
        }
    }

}