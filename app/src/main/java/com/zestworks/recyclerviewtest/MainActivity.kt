package com.zestworks.recyclerviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mutableListOf = mutableListOf<String>()
        for(i in 0..10000){
            mutableListOf.add("item $i")
        }
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = TestAdapter(mutableListOf)
        recyclerView.smoothScrollToPosition(2000)
    }
}

class TestAdapter(val items: List<String>): RecyclerView.Adapter<TestViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder =
        TestViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_test, parent, false))

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        Log.d("recyclerView", "position : $position, holder = $holder")
        holder.textView.text = items[position]
    }
}

class TestViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.textView)
}
