package com.github.zharovvv.android.core.sandbox.menu

import android.view.ContextMenu
import android.view.View

class CustomOnCreateContextMenuListener : View.OnCreateContextMenuListener {

    companion object {
        const val MAIN_ITEM_ID = 1
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu?.add(0, MAIN_ITEM_ID, 0, "Перенос первых элементов в Overflow")
    }
}