package com.example.sforum

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.part_list_item.view.*

class PartAdapter (val partItemList: List<PartData>, val clickListener: (PartData) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.part_list_item, parent, false)
        return PartViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Populate ViewHolder with data that corresponds to the position in the list
        // which we are told to load
        (holder as PartViewHolder).bind(partItemList[position], clickListener)
    }

    override fun getItemCount() = partItemList.size

    class PartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(part: PartData, clickListener: (PartData) -> Unit) {
            itemView.tv_part_item_name.text = part.name
            itemView.tv_part_index.text = part.index.toString()
            itemView.setOnClickListener { clickListener(part)}
        }
    }
}