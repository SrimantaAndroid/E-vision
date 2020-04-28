package com.evision.MyAccount.Adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.evision.MyAccount.Pojo.OrderItem
import com.evision.R

class AdapterMyorder(mContext: Context, order_items: List<OrderItem>) : RecyclerView.Adapter<AdapterMyorder.VHolder>() {
    var mCotext = mContext
    var oredrlist = order_items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_oderitem, parent, false))
    }

    override fun getItemCount(): Int {
        return oredrlist.size
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val itm = oredrlist[position]
        holder.TXT_ModelNo.setText(itm.product_name as String)
        holder.TXT_PRICE.setText(itm.currency + itm.price)
        holder.TXT_qtyno.setText(itm.qty)

        holder.TXT_subtotal.setText(itm.currency + itm.row_total)
    }


    class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val TXT_ModelNo = itemView.findViewById<TextView>(R.id.TXT_ModelNo)
        val TXT_DESCRIPTION = itemView.findViewById<TextView>(R.id.TXT_DESCRIPTION)
        val TXT_PRICE = itemView.findViewById<TextView>(R.id.TXT_PRICE)
        val TXT_qtyno = itemView.findViewById<TextView>(R.id.TXT_qtyno)
        val TXT_subtotal = itemView.findViewById<TextView>(R.id.TXT_subtotal)
    }
}