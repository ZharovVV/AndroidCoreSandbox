package com.github.zharovvv.android.core.sandbox.content.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.provider.*
import android.util.Log
import com.github.zharovvv.android.core.sandbox.sqlite.PersonDao
import com.github.zharovvv.android.core.sandbox.sqlite.PersonDatabase
import com.github.zharovvv.android.core.sandbox.sqlite.PersonDatabaseProvider

/**
 *
 * # [ContentProvider]
 * Контент-провайдер или "Поставщик содержимого" (Content Provider) - это оболочка (wrapper),
 * в которую заключены данные.
 * Если ваше приложение использует базу данных SQLite, то только ваше приложение имеет к ней доступ.
 * Но бывают ситуации, когда данные желательно сделать общими.
 * Простой пример - ваши контакты из телефонной книги тоже содержатся в базе данных,
 * но вы хотите иметь доступ к данным, чтобы ваше приложение тоже могло выводить список контактов.
 * Так как вы не имеете доступа к базе данных чужого приложения, был придуман специальный механизм,
 * позволяющий делиться своими данными всем желающим.
 * Поставщик содержимого применяется лишь в тех случаях,
 * когда вы хотите использовать данные совместно с другими приложениями, работающих в устройстве.
 * Но даже если вы не планируете сейчас делиться данными,
 * то всё-равно можно подумать об реализации этого способа на всякий случай.
 *
 * В Android существует возможность выражения источников данных (или поставщиков данных)
 * при помощи передачи состояния представления - REST, в виде абстракций,
 * называемых поставщиками содержимого.
 * Базу данных SQLite можно заключить в поставщик содержимого.
 * Чтобы получить данные из поставщика содержимого или сохранить в нём новую информацию,
 * нужно использовать набор REST-подобных идентификаторов URI.
 * Например, если бы вам было нужно получить набор книг из поставщика содержимого,
 * в котором заключена электронная библиотека,
 * вам понадобился бы такой URI (по сути запрос к получению всех записей таблицы books):
 * * content://com.android.book.bookprovider/books
 *
 * Чтобы получить из библиотеки конкретную книгу (например, книгу №23),
 * будет использоваться следующий URI (отдельный ряд таблицы):
 * * content://com.android.book.bookProvider/books/23
 *
 * Любая программа, работающая в устройстве, может использовать такие URI для доступа к данным
 * и осуществления с ними определенных операций.
 * Следовательно, поставщики содержимого играют важную роль
 * при совместном использовании данных несколькими приложениями.
 *
 * #
 * # Встроенные поставщики
 * В Android используются встроенные поставщики содержимого (пакет [android.provider]).
 * [Browser], [CallLog], [Contacts], [MediaStore] и [Settings] - это отдельные базы данных SQLite,
 * инкапсулированные в форме поставщиков.
 * Обычно такие базы данных SQLite имеют расширение DB и доступ к ним открыт только
 * из специальных пакетов реализации (implementation package).
 * Любой доступ к базе данных из-за пределов этого пакета осуществляется через интерфейс поставщика содержимого.
 *
 * #
 * # Собственный поставщик содержимого
 * В манифесте мы зарегистрировали данный провайдер с тегом:
 * android:authorities="com.github.zharovvv.android.core.sandbox.content.provider.custom".
 * Таким образом базовый URI будет иметь следующий вид:
 * content://com.github.zharovvv.android.core.sandbox.content.provider.custom/
 * Итак, поставщики содержимого, как и веб-сайты, имеют базовое доменное имя, действующее как стартовая URL-страница.
 * ###
 * Необходимо отметить, что поставщики содержимого,
 * используемые в Android, могут иметь неполное имя источника.
 * Полное имя источника рекомендуется использовать только со сторонними поставщиками содержимого.
 * Поэтому вам иногда могут встретиться поставщики содержимого,
 * состоящие из одного слова, например contacts,
 * в то время как полное имя такого поставщика содержимого - com.google.android.contacts.
 *
 * #
 * # Структура унифицированных идентификаторов содержимого (Content URI)
 * Унифицированные идентификаторы содержимого (Content URI) в Android напоминают HTTP URI,
 * но начинаются с content и строятся по следующему образцу:
 * ```
 * content://authority-name/path-segment1/path-segment2/etc...
 * ```
 * После content: в URI содержится унифицированный идентификатор источника,
 * который используется для нахождения поставщика содержимого в соответствующем реестре.
 * Одной из функций поставщика содержимого является документирование
 * и интерпретация раздела и сегментов пути, содержащихся в URI.
 *
 * #
 * #  [UriMatcher]
 * Провайдер имеет специальный объект класса UriMatcher,
 * который получает данные снаружи и на основе полученной информации создаёт нужный запрос к базе данных.
 * Вам нужно задать специальные константы, по которым провайдер будет понимать дальнейшие действия.
 * Если используется одна таблица, то обычно применяют две константы - любые два целых числа,
 * например, 100 для таблицы и 101 для отдельного ряда таблицы.
 */
class CustomContentProvider : ContentProvider() {

    companion object {
        private const val LOG_TAG = "ContentProvider"

        const val AUTHORITY =
            "com.github.zharovvv.android.core.sandbox.content.provider.custom"
        private const val PERSON_CODE = 100
        private const val PERSON_ID_CODE = 101
        private val uriMatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)
            .apply {
                addURI(AUTHORITY, "persons", PERSON_CODE)
                addURI(AUTHORITY, "persons/#", PERSON_ID_CODE)
                //Символ решётки (#) отвечает за число, а символ звёздочки (*) за строку.
            }
    }

    private lateinit var personDatabase: PersonDatabase
    private lateinit var sqliteDatabase: SQLiteDatabase

    /**
     * ContentProvider создается раньше Application.
     */
    override fun onCreate(): Boolean {
        Log.i(LOG_TAG, "CustomContentProvider#onCreate")
        personDatabase = PersonDatabaseProvider.getPersonDatabase(context!!)
        sqliteDatabase = personDatabase.sqliteDatabase
        return true
    }

    /**
     * Пример:
     * URI: content://com.example.android.cathouse/cats/3
     * Projection: {"_id", "name"}
     * Selection: "_id=?"
     * Selection Args: {"3"}
     */
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        Log.i(LOG_TAG, "CustomContentProvider#query")
        when (uriMatcher.match(uri)) {
            PERSON_CODE -> {
            }
            PERSON_ID_CODE -> {
            }
            else -> throw IllegalArgumentException("Wrong URI: $uri")
        }
        return sqliteDatabase.query(
            PersonDao.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null, null,
            sortOrder
        )
    }

    override fun getType(uri: Uri): String? {
        Log.i(LOG_TAG, "CustomContentProvider#getType")
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.i(LOG_TAG, "CustomContentProvider#insert")
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.i(LOG_TAG, "CustomContentProvider#delete")
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        Log.i(LOG_TAG, "CustomContentProvider#update")
        TODO("Not yet implemented")
    }


}