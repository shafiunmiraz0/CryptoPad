package com.example.cryptopad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class EditActivity : AppCompatActivity() {


    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        realm = Realm.getDefaultInstance()



        val noteId = intent.getLongExtra("note_id", -1L)
        if (noteId != -1L) {
            val note = realm.where<Note>().equalTo("id", noteId).findFirst()
            titleEdit.setText(note?.title ?: "")
            contentEdit.setText(note?.content ?: "")
            delete.visibility = View.VISIBLE
        } else {
            delete.visibility = View.INVISIBLE
        }

        save.setOnClickListener {

            var isValid = true
            when (noteId) {
                -1L -> {

                    if (titleEdit.text.isEmpty()) {
                        titleEdit.error = "Title is Empty"
                        isValid = false
                    }

                    if (contentEdit.text.isEmpty()) {
                        contentEdit.error = "Detail Is Empty"
                        isValid = false
                    }

                    if (isValid) {
                        realm.executeTransaction {
                            val maxId = realm.where<Note>().max("id")
                            val nextId = (maxId?.toLong() ?: 0L) + 1
                            val note = realm.createObject<Note>(nextId)
                            note.title = titleEdit.text.toString()
                            note.content = contentEdit.text.toString()
                        }
                        alert("Saved") {
                                yesButton { finish() }
                        }.show()
                    }
                }
                else -> {

                    if (titleEdit.text.isEmpty()) {
                        titleEdit.error = "Title Empty"
                        isValid = false
                    }

                    if (contentEdit.text.isEmpty()) {
                        contentEdit.error = "Detail Empty"
                        isValid = false
                    }
                    if (isValid) {
                        realm.executeTransaction {
                            val note = realm.where<Note>().equalTo("id", noteId).findFirst()
                            note?.title = titleEdit.text.toString()
                            note?.content = contentEdit.text.toString()
                        }
                        alert("Edited") {
                                yesButton { finish() }
                        }.show()
                    }
                }
            }
        }

        delete.setOnClickListener {
            realm.executeTransaction {
                val note = realm.where<Note>().equalTo("id", noteId)?.findFirst()
                note?.deleteFromRealm()
            }
            alert("Delete") {
                yesButton { finish() }
            }.show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
