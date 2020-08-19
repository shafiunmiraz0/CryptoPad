package com.example.cryptopad

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class NoteAdapter(context: Context, private val notes: OrderedRealmCollection<Note>, val onItemClicked: (Note) -> Unit) :
    RealmRecyclerViewAdapter<Note, NoteAdapter.MyViewHolder>(notes, true) {


    private val inflater = LayoutInflater.from(context)


    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = inflater.inflate(R.layout.list_row, parent, false)

        val viewHolder = MyViewHolder(view)

        view.setOnClickListener {

            val position = viewHolder.adapterPosition

            val note = notes[position]
            onItemClicked(note)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val note = notes[position]

        holder.title.text = note.title
        holder.date.text = DateFormat.format("yyyy/MM/dd", note.date)
    }


    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val title = view.findViewById<TextView>(R.id.titleRow)
        val date = view.findViewById<TextView>(R.id.dateRow)
    }
}
