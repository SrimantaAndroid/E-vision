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
import com.evision.Category.Pojo.Sub
import com.evision.ProductList.ProductListActivity
import com.evision.R

class AdapterSubCategory(mContext: Context, data: ArrayList<Sub>) : RecyclerView.Adapter<AdapterSubCategory.VHolder>() {
    val mContext = mContext
    val data = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(LayoutInflater.from(parent.context).inflate(R.layout.itemcategory, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val itemdata=data.get(position)
        holder.TXT_CatName.setText(itemdata.name)
        Glide.with(mContext).load(itemdata.category_image).apply(RequestOptions().error(R.drawable.ic_placeholder)).into(holder.IMG_CAT)

        holder.TXT_CatName.setTypeface(holder.TXT_CatName.typeface, Typeface.NORMAL)
        if (itemdata.sub.size>0)
            holder.IMG_more.visibility=View.VISIBLE
        else
            holder.IMG_more.visibility=View.INVISIBLE
        holder.REC_SUB.visibility=View.GONE
        holder.itemView.setOnClickListener {
            if (itemdata.sub.size>0) {
                if(holder.REC_SUB.visibility==View.GONE) {
                    holder.REC_SUB.visibility = View.VISIBLE
                    holder.TXT_CatName.setTypeface(holder.TXT_CatName.typeface, Typeface.BOLD)
                    holder.IMG_more.setImageResource(R.drawable.ic_indeterminate_red)
                    val adapterSubCategory = AdapterSubCategory(mContext, itemdata.sub as ArrayList)
                    holder.REC_SUB.layoutManager = LinearLayoutManager(mContext)
                    holder.REC_SUB.adapter = adapterSubCategory
                }else{
                    holder.REC_SUB.visibility = View.GONE
                    holder.TXT_CatName.setTypeface(holder.TXT_CatName.typeface, Typeface.NORMAL)
                    holder.IMG_more.setImageResource(R.drawable.ic_add_white)
                    notifyDataSetChanged()
                }
            }else{
                mContext.startActivity(Intent(mContext, ProductListActivity::class.java).putExtra("pid",itemdata.id).putExtra("cname",itemdata.name))
            }

        }
    }

    class VHolder(item: View) : RecyclerView.ViewHolder(item) {
        val TXT_CatName = item.findViewById<TextView>(R.id.TXT_CatName)
        val IMG_more = item.findViewById<ImageView>(R.id.IMG_more)
        val REC_SUB = item.findViewById<RecyclerView>(R.id.REC_SUB)
        val IMG_CAT = item.findViewById<ImageView>(R.id.IMG_CAT)
    }
}