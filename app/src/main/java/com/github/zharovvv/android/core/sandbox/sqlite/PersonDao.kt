package com.github.zharovvv.android.core.sandbox.sqlite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

class PersonDao(sqLiteDBProviderDelegate: PersonDatabase.SQLiteDBProviderDelegate) {

    companion object {
        const val TABLE_NAME = "person_table"
    }

    private val sqLiteDatabase: SQLiteDatabase by sqLiteDBProviderDelegate

    fun insert(person: Person): Long {
        val contentValues = ContentValues()
        contentValues.put("name", person.name)
        contentValues.put("age", person.age)
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues)
    }

    fun getPersons(): List<Person> {
        val cursor = sqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, null)
        val persons = mutableListOf<Person>()
        cursor.use {
            val idIndex = it.getColumnIndex("id")
            val nameIndex = it.getColumnIndex("name")
            val ageIndex = it.getColumnIndex("age")
            while (it.moveToNext()) {
                val person = Person(
                    it.getString(nameIndex),
                    it.getInt(ageIndex)
                )
                person.id = it.getLong(idIndex)
                persons.add(person)
            }
        }
        return persons
    }

    fun delete(person: Person): Boolean {
        return false
    }

    fun clearAllTable() {
        sqLiteDatabase.delete(TABLE_NAME, null, null)
    }

}