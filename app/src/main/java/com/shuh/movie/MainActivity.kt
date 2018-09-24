package com.shuh.movie

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.tbruyelle.rxpermissions2.RxPermissions
import java.io.File
import java.util.*
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.SimpleAdapter
import android.widget.TextView
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    // /storage/emulated/0/bluetooth/
    val TAG = "MainActivity"
    private val movieFiles: ArrayList<File> = ArrayList()
    private lateinit var adapter: CommonAdapter<File>
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CommonAdapter(movieFiles, android.R.layout.simple_list_item_2, { holder, file ->
            holder.itemView.findViewById<TextView>(android.R.id.text1).text = file.name
            holder.itemView.findViewById<TextView>(android.R.id.text2).text = file.absolutePath
        })

        recyclerView.adapter = adapter
        val file = File(Environment.getExternalStorageDirectory(), "/bluetooth/吴恩达")
        RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe({ granted ->
                    if (granted) {
                        Log.d(TAG, Arrays.toString(file.list()))
                        movieFiles.clear()
                        movieFiles.addAll(file.listFiles { file -> !file.name.startsWith("._") })
                        adapter.notifyDataSetChanged()
                    }
                })
    }
}
