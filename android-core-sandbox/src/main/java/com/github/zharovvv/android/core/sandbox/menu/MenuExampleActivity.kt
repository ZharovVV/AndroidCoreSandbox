package com.github.zharovvv.android.core.sandbox.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.*
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.iterator
import com.github.zharovvv.android.core.sandbox.LogLifecycleAppCompatActivity
import com.github.zharovvv.android.core.sandbox.R

class MenuExampleActivity : LogLifecycleAppCompatActivity(R.layout.activity_menu_example) {

    private lateinit var checkBox: CheckBox
    private lateinit var checkBoxOverflow: CheckBox
    private lateinit var textView: TextView
    private lateinit var textView2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))
        checkBox = findViewById(R.id.checkbox)
        checkBoxOverflow = findViewById(R.id.checkbox_for_moving_into_overflow)
        checkBoxOverflow.setOnCheckedChangeListener { buttonView, isChecked ->
            invalidateOptionsMenu()
        }
        textView = findViewById(R.id.checkbox_description)
        registerForContextMenu(textView)// same textView.setOnCreateContextMenuListener(this)

        textView2 = findViewById(R.id.checkbox_2_description)
        textView2.setOnCreateContextMenuListener(CustomOnCreateContextMenuListener())
    }

    override fun onDestroy() {
        super.onDestroy()
        setSupportActionBar(null)
    }

    /**
     * Вызывается только при первом показе меню.
     * Создает меню и более не используется.
     */
    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (menu is MenuBuilder) {  //Без этого блока иконки не отображаются
            menu.setOptionalIconsVisible(true)
        }
        menuInflater.inflate(R.menu.menu_example, menu)
        return true
    }

    /**
     * вызывается каждый раз перед отображением меню.
     * Здесь мы вносим изменения в уже созданное меню, если это необходимо
     */
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.setGroupVisible(R.id.menu_group_1, checkBox.isChecked)
        if (checkBoxOverflow.isChecked) {
            for (item: MenuItem in menu) {
                when (item.itemId) {
                    R.id.menu_item_1, R.id.menu_item_2 -> {
                        item.setShowAsAction(SHOW_AS_ACTION_NEVER)
                    }
                }
            }
        } else {
            menu.getItem(0).setShowAsAction(SHOW_AS_ACTION_ALWAYS)
            menu.getItem(1).setShowAsAction(SHOW_AS_ACTION_IF_ROOM)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    /**
     * вызывается при нажатии пункта меню. Здесь мы определяем какой пункт меню был нажат.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(
            this,
            "item title: ${item.title}\nshowAsAction: ${item.showAsAction}",
            Toast.LENGTH_SHORT
        ).show()
        return super.onOptionsItemSelected(item)
    }

    /**
     * Контекстное меню вызывается в Андроид длительным нажатием на каком-либо экранном компоненте.
     * Обычно оно используется в списках, когда на экран выводится список однородных объектов
     * (например письма в почт.ящике) и, чтобы выполнить действие с одним из этих объектов,
     * мы вызываем контекстное меню для него.
     *
     * Метод создания [onCreateContextMenu] вызывается каждый раз перед показом меню.
     * На вход ему передается:
     * * [menu] : ContextMenu, в который мы будем добавлять пункты
     * * [v] : View - элемент экрана, для которого вызвано контекстное меню
     * * [menuInfo] : ContextMenu.ContextMenuInfo – содержит доп.информацию,
     * когда контекстное меню вызвано для элемента списка.
     */
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_example, menu)
    }

    /**
     * Метод обработки onContextItemSelected аналогичный методу onOptionsItemSelected для обычного меню.
     * На вход передается [item] MenuItem – пункт меню, который был нажат.
     */
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.context_menu_item_1 -> {
                Toast.makeText(this, "Нажат пункт контекстного меню Activity", Toast.LENGTH_SHORT)
                    .show()
            }
            CustomOnCreateContextMenuListener.MAIN_ITEM_ID -> {
                Toast.makeText(this, "Нажат пункт контекстного меню Custom", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {
                Toast.makeText(
                    this,
                    "Нажат пункт контекстного меню для неизвестного пункта",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        return super.onContextItemSelected(item)
    }

    private val MenuItem.showAsAction: String
        get() {
            return when (this.itemId) {
                R.id.menu_item_1 -> {
                    if (!checkBoxOverflow.isChecked) "always" else "never"
                }
                R.id.menu_item_2 -> {
                    if (!checkBoxOverflow.isChecked) "ifRoom" else "never"
                }
                R.id.menu_item_3 -> "ifRoom|withText"
                R.id.menu_item_4, R.id.menu_item_5 -> "never"
                else -> "unknown"
            }
        }
}