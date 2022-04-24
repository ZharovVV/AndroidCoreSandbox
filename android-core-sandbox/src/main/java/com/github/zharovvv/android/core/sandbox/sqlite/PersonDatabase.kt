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
    val sqliteDatabase: SQLiteDatabase by sqLiteDatabaseProviderDelegate

    /**
     * Нет ничего плохого в том, чтобы оставить соединение с базой данных открытым.
     * _(Это функция ядра Linux, на котором основан Android;
     * когда процесс (то есть ваше приложение) завершается, ОС очищает все,
     * что не было сохранено (например, на диск): вся память восстанавливается,
     * все дескрипторы закрываются и т. д.)_
     * Хорошей практикой является открывать connection к БД в Application#onCreate.
     */
    override fun close() {
        personDbOpenHelper.close()
        sqLiteDatabaseProviderDelegate.releaseReference()
    }

    //Никаких преимуществ от использования делегирования здесь нет.
    //Сделано исключительно с целью потренироваться в использовании делегирования в Kotlin.
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