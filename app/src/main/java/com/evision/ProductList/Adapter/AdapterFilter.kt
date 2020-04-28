package com.evision.ProductList.Adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.evision.ProductList.FilterBottomSheetFragment
import com.evision.ProductList.Interface.ManufacturerFilter
import com.evision.ProductList.Pojo.Manufacture
import com.evision.R

class AdapterFilter(farg:FilterBottomSheetFragment,mContext:Context,list:List<Manufacture>,maan:ManufacturerFilter): RecyclerView.Adapter<AdapterFilter.VHolder>() {
    var mContext=mContext
    var list=list
    val frag=farg
    var manufacturerFilter=maan
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
    return VHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_filter,parent,false))
    }

    override fun getItemCount(): Int {
    return list.size   }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
    holder.TXT_name.setText(list.get(position).manufacture_name)
        holder.CHECKBOX.isChecked = list[position].isselect
        holder.CHECKBOX.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                list.get(position).isselect = true
            else
                list.get(position).isselect = false
        }

    }


    class VHolder (itemView:View): RecyclerView.ViewHolder(itemView){
    val TXT_name=itemView.findViewById<TextView>(R.id.TXT_name)
        val CHECKBOX = itemView.findViewById<CheckBox>(R.id.CHECKBOX)
    }
}