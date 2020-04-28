package com.evision.CartManage.Adapter

import android.content.Context


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.evision.CartManage.Pojo.OrderReview
import com.evision.R
import com.evision.Utils.AppDialog
import android.R.attr.name
import android.R.id
import android.text.Spannable
import android.graphics.Typeface
import android.text.Html
import android.text.style.StyleSpan
import android.text.SpannableString
import androidx.recyclerview.widget.RecyclerView

class AdapterCartCheckout(mCotext: Context, arrayList: List<OrderReview>, loader: AppDialog) : RecyclerView.Adapter<AdapterCartCheckout.ViewHolder>() {
    val arrayList = arrayList
    val mContext = mCotext
    val loader = loader
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCartCheckout.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cart_checkout, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: AdapterCartCheckout.ViewHolder, position: Int) {
        val itemData = arrayList.get(position)
        holder.TXT_MODEL.setText(itemData.model)
       /* if (itemData.coupon_text.equals(""))
            holder.Coupon_Apply.visibility=View.GONE
        else {
            holder.Coupon_Apply.visibility = View.VISIBLE
            holder.Coupon_Apply.setText("("+itemData.coupon_text+")")
        }*/

        if (itemData.coupon_text.equals(""))
            holder.TXT_MODEL.setText(itemData.model)
        else {

            val normalText = itemData.model+"\n"
            val boldtext="(" + itemData.coupon_text + ")"
            //val str = SpannableString( normalText+boldtext)
           // str.setSpan(StyleSpan(Typeface.BOLD), normalText.length, boldtext.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
           // holder.TXT_MODEL.setText(str)
            val string:String=normalText+"\n"+"<b>" + boldtext+ "</b> "
            holder.TXT_MODEL.setText(Html.fromHtml(string))
           // holder.TXT_MODEL.setText(itemData.model + "\n" + "(" + itemData.coupon_text + ")")
        }

        holder.TXT_ProductDescription.setText(itemData.decripcion)
        holder.TXT_Price.setText(itemData.currency + itemData.price)
        holder.TXT_subtotal.setText(itemData.currency + itemData.total)
        holder.TXT_QTY.setText(itemData.qty.toString())
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val TXT_Price = itemView.findViewById<TextView>(R.id.TXT_PRICE)
        val TXT_ProductDescription = itemView.findViewById<TextView>(R.id.TXT_ProductDescription)
        val TXT_MODEL = itemView.findViewById<TextView>(R.id.TXT_MODEL)
        val TXT_QTY = itemView.findViewById<TextView>(R.id.TXT_QTY)
        val TXT_subtotal = itemView.findViewById<TextView>(R.id.TXT_subtotal)
      //  val Coupon_Apply=itemView.findViewById<TextView>(R.id.Coupon_Apply)

    }
}