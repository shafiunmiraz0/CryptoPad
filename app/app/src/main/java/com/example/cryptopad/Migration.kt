package com.example.cryptopad

import io.realm.DynamicRealm
import io.realm.RealmMigration

class Migration: RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        var realmScheme = realm.schema
        var oldVersion = oldVersion

        if (oldVersion == 0L ) {
            val userSchema = realmScheme.get("Note")
            userSchema?.renameField("deleteFlg", "isDelete")
            oldVersion++
        }
    }
}