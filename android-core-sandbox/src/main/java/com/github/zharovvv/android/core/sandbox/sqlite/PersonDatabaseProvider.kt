package com.github.zharovvv.android.core.sandbox.sqlite

import android.content.Context

object PersonDatabaseProvider {

    private var personDatabase: PersonDatabase? = null
    private val lock = this

    fun getPersonDatabase(context: Context): PersonDatabase {
        val v1 = personDatabase
        if (v1 != null) {
            return v1
        }
        return synchronized(lock) {
            val v2 = personDatabase
            if (v2 != null) {
                v2
            } else {
                val value = PersonDatabase(PersonDbOpenHelper(context, 1))
                personDatabase = value
                value
            }
        }
    }
}