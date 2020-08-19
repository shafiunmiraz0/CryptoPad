package com.example.cryptopad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {


    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        realm = Realm.getDefaultInstance()

        val notes = realm.where<Note>().findAll()
        val recyclerView = noteList
        val adapter = NoteAdapter(this, notes) { note ->
            startActivity<EditActivity>("note_id" to note.id)
        }

        recyclerView.adapter = adapter


        val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        layout.stackFromEnd = true
        recyclerView.layoutManager = layout


        fab.setOnClickListener {
            startActivity<EditActivity>()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

}
