package com.github.zharovvv.android.core.sandbox.menu

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.zharovvv.android.core.sandbox.R

class MenuExampleActivity : AppCompatActivity(R.layout.activity_menu_example) {

    private lateinit var checkBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkBox = findViewById(R.id.checkbox)
    }

    /**
     * Вызывается только при первом показе меню.
     * Создает меню и более не используется.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_example, menu)
        return true
    }

    /**
     * вызывается каждый раз перед отображением меню.
     * Здесь мы вносим изменения в уже созданное меню, если это необходимо
     */
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.setGroupVisible(R.id.menu_group_1, checkBox.isChecked)
        return super.onPrepareOptionsMenu(menu)
    }

    /**
     * вызывается при нажатии пункта меню. Здесь мы определяем какой пункт меню был нажат.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }
}