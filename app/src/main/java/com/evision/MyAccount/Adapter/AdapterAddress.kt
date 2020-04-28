package com.evision.MyAccount.Adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.evision.MyAccount.EditAddressFragment
import com.evision.MyAccount.MyAccActivity
import com.evision.MyAccount.SavedAddressFragment
import com.evision.R
import org.json.JSONObject

class AdapterAddress(mContext: Context, list: ArrayList<JSONObject>) : RecyclerView.Adapter<AdapterAddress.VHolder>() {
    val list = list
    val mContext = mContext
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_savedaddress, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val data = list[position]
        val dd = data.optString("customer_address") + "," +
                data.optString("city_name") + "," +
                data.optString("state_name") + "," +
                data.optString("country_name") + "," +
                data.optString("pincode") + "."

        holder.NAME.setText(data.optString("customer_name"))
        holder.ADDRESS.setText(dd)
        holder.TXT_edit.setOnClickListener {
            (mContext as AppCompatActivity).getSupportFragmentManager().beginTransaction().replace(R.id.acc_container, EditAddressFragment.newInstance("", "")).commit()

        }
    }

    class VHolder(itemV: View) : RecyclerView.ViewHolder(itemV) {
        val TXT_edit = itemV.findViewById<TextView>(R.id.TXT_edit)
        val NAME = itemV.findViewById<TextView>(R.id.NAME)
        val ADDRESS = itemV.findViewById<TextView>(R.id.ADDRESS)
    }
}