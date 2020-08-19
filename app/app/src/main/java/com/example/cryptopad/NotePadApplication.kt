package com.example.cryptopad

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class NotePadApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val builder = RealmConfiguration.Builder()
        val config = builder.deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(config)
    }
}