package com.evision.Category.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.evision.Category.Pojo.CategoryData
import com.evision.Category.Pojo.Sub
import com.evision.ProductList.ProductListActivity
import com.evision.R

class AdapterCategory(mContext: Context, data: CategoryData) : RecyclerView.Adapter<AdapterCategory.VHolder>() {
    val mContext = mContext
    val data = data
    var itemp = -1
    lateinit var adapterSubCategory: AdapterSubCategory
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(LayoutInflater.from(parent.context).inflate(R.layout.itemcategory, parent, false))
    }

    override fun getItemCount(): Int {
        return data.menu.size
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val itemdata = data.menu.get(position)
        holder.TXT_CatName.setText(itemdata.name)
        if (itemdata.sub.size > 0)
            holder.IMG_more.visibility = View.VISIBLE
        else
            holder.IMG_more.visibility = View.INVISIBLE

        holder.REC_SUB.visibility = View.GONE
        holder.TXT_CatName.setTextColor(mContext.resources.getColor(R.color.black))
        if (itemdata.sub.size > 0 && itemdata.isOpen) {
            holder.REC_SUB.visibility = View.VISIBLE
            holder.IMG_more.setImageResource(R.drawable.ic_indeterminate_red)
            adapterSubCategory = AdapterSubCategory(mContext, itemdata.sub as ArrayList<Sub>)
            holder.TXT_CatName.setTextColor(mContext.resources.getColor(R.color.colorAccent))
            holder.REC_SUB.layoutManager = LinearLayoutManager(mContext)
            holder.REC_SUB.adapter = adapterSubCategory
        } else {
            holder.REC_SUB.visibility = View.GONE
            holder.TXT_CatName.setTypeface(holder.TXT_CatName.typeface, Typeface.NORMAL)
            holder.IMG_more.setImageResource(R.drawable.ic_add_white)
        }

        Glide.with(mContext).load(itemdata.category_image).apply(RequestOptions().error(R.drawable.ic_placeholder)).into(holder.IMG_CAT)
        holder.itemView.setOnClickListener {

            if (itemdata.sub.size > 0) {


                if (holder.REC_SUB.visibility == View.GONE) {
                    itemdata.isOpen=true
                    if (itemp >= 0) {
                        data.menu.get(itemp).isOpen=false
                    }
                    itemp=position
                } else {
                    itemdata.isOpen=false
                    itemp=-1
                }
                notifyDataSetChanged()
            } else {
                mContext.startActivity(Intent(mContext, ProductListActivity::class.java).putExtra("pid", itemdata.id).putExtra("cname", itemdata.name))
            }

        }
    }

    class VHolder(item: View) : RecyclerView.ViewHolder(item) {
        val TXT_CatName = item.findViewById<TextView>(R.id.TXT_CatName)
        val IMG_more = item.findViewById<ImageView>(R.id.IMG_more)
        val IMG_CAT = item.findViewById<ImageView>(R.id.IMG_CAT)
        val REC_SUB = item.findViewById<RecyclerView>(R.id.REC_SUB)

    }
}