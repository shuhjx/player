package com.shuh.movie

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

class CommonAdapter<T>(val data: ArrayList<T>, val itemViewId: Int, val onBind: (ViewHolder, T) -> Unit) : RecyclerView.Adapter<CommonAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return data.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(View.inflate(parent.context, itemViewId, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        with(holder?.itemView!!){}
        onBind(holder, data[position])
    }


    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

}