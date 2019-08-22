package com.zestworks.recyclerviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        val testAdapter = TestAdapter(mutableListOf)
        recyclerView.adapter = testAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val from = linearLayoutManager.findFirstVisibleItemPosition()
                    val to = linearLayoutManager.findLastVisibleItemPosition()
                    testAdapter.scrollStateIsIdeal(from, to)
                }
            }
        })
    }
}

class TestAdapter(val items: List<String>): RecyclerView.Adapter<TestViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder =
        TestViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_test, parent, false))

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        Log.d("recyclerView", "position : $position, holder = $holder")
        holder.textView.text = items[position]
        holder.itemView.findViewById<ImageView>(R.id.image_view).setImageBitmap(null)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        payloads.forEach {
            if (it is ImagePayLoad){
                //This place is where we shall start the bitmap request
                holder.itemView.findViewById<ImageView>(R.id.image_view).setImageResource(R.mipmap.ic_launcher_round)
            }
        }
    }

    fun scrollStateIsIdeal(from: Int, to: Int) {
        notifyItemRangeChanged(from, (to - from) + 1, ImagePayLoad)
    }
}

class TestViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.textView)
}

sealed class PayLoad
object ImagePayLoad: PayLoad()
