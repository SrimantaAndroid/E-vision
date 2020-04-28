package com.evision.Dashboard.Adapter

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.evision.Dashboard.Pojo.HomeBrand
import com.evision.Dashboard.Pojo.HotCategory
import com.evision.ProductList.ProductListActivity
import com.evision.R

class AdapterHomeBrand(Homebrand: List<HomeBrand>, mContext: Context) : RecyclerView.Adapter<AdapterHomeBrand.Vholder>() {
    val Hotlist=Homebrand
    val mContext=mContext
    lateinit var adapt:AdapterHotItem
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Vholder {
    return Vholder(LayoutInflater.from(p0.context).inflate(R.layout.home_brand_item,p0,false))
    }

    override fun getItemCount(): Int {
        return Hotlist.size
    }

    override fun onBindViewHolder(p0: Vholder, p1: Int) {
        val homebrand = Hotlist.get(p1)
        p0.txt_homebrandname.text=homebrand.brand_name
        Glide.with(mContext).load(homebrand.brand_image).apply(RequestOptions().centerCrop().placeholder(R.drawable.ic_placeholder)).into(p0.img_homebrand)
        p0.img_homebrand.setOnClickListener {
          //  mContext.startActivity(Intent(mContext, ProductListActivity::class.java).putExtra("pid", cat.cat_id).putExtra("cname", cat.cat_name))
        }
    }

    class Vholder(itemview: View):RecyclerView.ViewHolder(itemview){
        val img_homebrand=itemview.findViewById<ImageView>(R.id.img_homebrand)
        val txt_homebrandname=itemview.findViewById<TextView>(R.id.txt_homebrandname)
    }
}