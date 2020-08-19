package com.example.cryptopad

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Note: RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var title: String = ""
    var content: String = ""
    var date: Date = Date()
}