package com.dba.majika.models.menu

data class MenuHeaderItem(
    val name: String
) : MenuListItem() {
    override fun getType(): Int {
        return TYPE_HEADER
    }
}
