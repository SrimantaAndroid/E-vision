package com.evision.Dashboard.Adapter

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evision.Dashboard.Pojo.HotCategory
import com.evision.ProductList.ProductListActivity
import com.evision.R

class AdapterHotCategory(Hotlist: List<HotCategory>, mContext: Context) : RecyclerView.Adapter<AdapterHotCategory.Vholder>() {
    val Hotlist=Hotlist
    val mContext=mContext
    lateinit var adapt:AdapterHotItem
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Vholder {
    return Vholder(LayoutInflater.from(p0.context).inflate(R.layout.item_hot_category,p0,false))
    }

    override fun getItemCount(): Int {
        return Hotlist.size
    }

    override fun onBindViewHolder(p0: Vholder, p1: Int) {
        val cat = Hotlist.get(p1)
        p0.TXTname.setText(cat.cat_name)
        p0.Recv_h.layoutManager=LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false)
        adapt = AdapterHotItem(cat.product_list, mContext)
        p0.Recv_h.adapter=adapt
        p0.TXTname.setOnClickListener {
            mContext.startActivity(Intent(mContext, ProductListActivity::class.java).putExtra("pid", cat.cat_id).putExtra("cname", cat.cat_name))
        }
    }

    class Vholder(itemview: View):RecyclerView.ViewHolder(itemview){
        val TXTname=itemview.findViewById<TextView>(R.id.TXT_CatName)
        val Recv_h=itemview.findViewById<RecyclerView>(R.id.Recv_h)
    }
}