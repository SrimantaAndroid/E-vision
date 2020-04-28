package com.evision.Dashboard.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.evision.Dashboard.Pojo.Product
import com.evision.ProductList.ProductDetailsActivity
import com.evision.R

class AdapterHotItem(hotCategory: List<Product>, mContext: Context) : RecyclerView.Adapter<AdapterHotItem.VHolder>() {
    val hotCategory=hotCategory
    val mContext=mContext

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VHolder {
    return VHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_hot_item,p0,false))
    }

    override fun getItemCount(): Int {
        return hotCategory.size
    }

    override fun onBindViewHolder(p0: VHolder, p1: Int) {
        val itemdata = hotCategory.get(p1)
        p0.TXT_name.setText(itemdata.product_name)
        Glide.with(mContext).load(itemdata.product_image).apply(RequestOptions().centerCrop().placeholder(R.drawable.ic_placeholder)).into(p0.IMG_item)
        p0.itemView.setOnClickListener {
            mContext.startActivity(Intent(mContext, ProductDetailsActivity::class.java).putExtra("pid", itemdata.product_id))
        }
    }


    class VHolder (itemview: View):RecyclerView.ViewHolder(itemview){
        val TXT_name=itemview.findViewById<TextView>(R.id.TXT_name)
        val IMG_item=itemView.findViewById<ImageView>(R.id.IMG_item)
    }
}