package com.dba.majika.models.menu

abstract class MenuListItem {
    val TYPE_HEADER = 0
    val TYPE_MENU = 1

    abstract fun getType(): Int
}