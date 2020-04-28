package com.evision.CartManage.Adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.evision.CartManage.PaymentActivity
import com.evision.R

class CardListAdapter(val cardlist: Array<String>, val paymentActivity: Context,val  param: OnitemClick): RecyclerView.Adapter<CardListAdapter.Viewholder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Viewholder {
        val view:View=LayoutInflater.from(paymentActivity!!).inflate(R.layout.cardlist_item,null)
        return  Viewholder(view)
    }

    override fun getItemCount(): Int {
       return cardlist.size
    }

    override fun onBindViewHolder(p0: Viewholder, p1: Int) {
        p0.tvcardname.setText(cardlist.get(p1))
        p0.tvcardname.setOnClickListener {
            param.OnClickItem(p1)
        }
    }

    class Viewholder(view: View) :RecyclerView.ViewHolder(view){
        val tvcardname:TextView=view.findViewById(R.id.tvcardname)

    }
}