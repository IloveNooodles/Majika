package com.dba.majika.models.menu;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
data class MenuDatabaseEntity constructor(
    @PrimaryKey
    val name: String,
    val price: Int,
    val sold: Int,
    val desc: String,
    val type: String
)
