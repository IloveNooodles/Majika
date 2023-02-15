package com.dba.majika.models.menu

class MenuItem(
    val name: String,
    val price: Int,
    val sold: Int,
    val desc: String,
    val type: String) : MenuListItem() {
    override fun getType(): Int {
        return TYPE_MENU
    }
}
