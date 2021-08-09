package com.github.zharovvv.android.core.sandbox.sqlite

import android.database.sqlite.SQLiteDatabase
import java.io.Closeable
import kotlin.reflect.KProperty

class PersonDatabase(private val personDbOpenHelper: PersonDbOpenHelper) : Closeable {

    companion object {
        const val DATABASE_NAME = "PersonDatabase"
    }

    private val sqLiteDatabaseProviderDelegate =
        SQLiteDBProviderDelegate { personDbOpenHelper.writableDatabase }

    val personDao = PersonDao(sqLiteDatabaseProviderDelegate)

    override fun close() {
        personDbOpenHelper.close()
        sqLiteDatabaseProviderDelegate.releaseReference()
    }

    class SQLiteDBProviderDelegate(initializer: () -> SQLiteDatabase) {
        private var initializer: (() -> SQLiteDatabase)? = initializer
        private var _sqLiteDatabase: SQLiteDatabase? = null
        private val lock = this

        operator fun getValue(thisRef: Any?, prop: KProperty<*>): SQLiteDatabase {
            val v1 = _sqLiteDatabase
            if (v1 != null) {
                return v1
            }
            return synchronized(lock) {
                val v2 = _sqLiteDatabase
                if (v2 != null) {
                    v2
                } else {
                    val value = initializer!!()
                    _sqLiteDatabase = value
//                    initializer = null
                    value
                }
            }
        }

        fun releaseReference() {
            synchronized(lock) {
                _sqLiteDatabase = null
            }
        }
    }
}