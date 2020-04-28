package com.evision.MyAccount.Adapter

import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.evision.MyAccount.MyorderFragment
import com.evision.MyAccount.OrderDetailsActivity
import com.evision.MyAccount.Pojo.MyOrder
import com.evision.R

class AdapterMyOrderList(myorderFragment: MyorderFragment, myOrder: MyOrder,val activity:FragmentActivity) : RecyclerView.Adapter<AdapterMyOrderList.VHOLDER>() {
    var myOrder = myOrder
    var myorderFragment = myorderFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHOLDER {
        return VHOLDER(LayoutInflater.from(parent.context).inflate(R.layout.item_myorder, parent, false))
    }

    override fun getItemCount(): Int {
        return myOrder.order_list.size
    }

    override fun onBindViewHolder(holder: VHOLDER, position: Int) {
        val item = myOrder.order_list[position]
        holder.TXT_DATE.setText(item.order_time)
        holder.TXT_STATTUS.setText(item.payment_status)
        holder.TXT_PRICE.setText(item.currency + item.order_amount)
        holder.TXT_ORDERID.setText(activity.resources.getString(R.string.orderid) + item.increment_id)
        holder.itemView.setOnClickListener {
            activity.startActivity(Intent(activity!!, OrderDetailsActivity::class.java).putExtra("orderid", item.order_id).putExtra("incrementid", item.increment_id))

        }
    }


    class VHOLDER(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val TXT_DATE = itemview.findViewById<TextView>(R.id.TXT_DATE)
        val TXT_ORDERID = itemview.findViewById<TextView>(R.id.TXT_ORDERID)
        val TXT_STATTUS = itemview.findViewById<TextView>(R.id.TXT_STATTUS)
        val TXT_PRICE = itemview.findViewById<TextView>(R.id.TXT_PRICE)
    }
}