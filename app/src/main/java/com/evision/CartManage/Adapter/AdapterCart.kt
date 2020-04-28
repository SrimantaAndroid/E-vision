package com.evision.CartManage.Adapter

import android.content.Context


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.evision.CartManage.CartActivity
import com.evision.CartManage.Pojo.CartItem
import com.evision.CartManage.Pojo.CartResponse
import com.evision.R
import com.evision.Utils.*
import com.google.gson.Gson
import org.json.JSONObject
import java.text.DecimalFormat

class AdapterCart(mCotext: Context, arrayList: CartResponse, loader: AppDialog) : RecyclerView.Adapter<AdapterCart.ViewHolder>() {
    val arrayList = arrayList
    val mContext = mCotext
    val loader = loader
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCart.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayList.cart_items.size
    }

    override fun onBindViewHolder(holder: AdapterCart.ViewHolder, position: Int) {
        val itemData = arrayList.cart_items.get(position)

        Glide.with(mContext).load(itemData.image_path).apply(RequestOptions().centerCrop().placeholder(R.drawable.ic_placeholder)).into(holder.IMG_item)
        holder.TXT_PName.setText(itemData.name)
        holder.TXT_MODEL.setText(itemData.modelo)

        val precision = DecimalFormat("0.00")
        val number2digits: String? = precision.format(itemData.price)

        holder.TXT_Price.setText(itemData.currency + number2digits)
        holder.TXT_cratcount.setText(itemData.qty.toString())
        holder.MINUS.setOnClickListener {
            val cart = itemData.qty
            if (cart == 1) {
                holder.Dlete.performClick()
                return@setOnClickListener
            }
            itemData.qty=cart-1
            MINUSCART(itemData, false)
        }

        holder.PLUS.setOnClickListener {
            val cart = itemData.qty
            itemData.qty=cart+1
            PLUSCART(itemData)
        }

        holder.Dlete.setOnClickListener {
            val params = HashMap<String, Any>()
            params.put("customer_id", ShareData(mContext).getUser()!!.customerId)
            params.put("product_id", itemData.product_id)
            onHTTP().POSTCALL(com.evision.Utils.URL.DELTECART, params, object : OnHttpResponse {
                override fun onSuccess(response: String) {
                    EvisionLog.D("##DeleteCartResponse",response)
//                    loader.dismiss()
                    if (JSONObject(response).optInt("code") == 200) {
                        val userdata = ShareData(mContext).getUser()
                        userdata!!.cartCount = userdata.cartCount - (itemData.qty.toInt())
                        EvisionLog.D("##userdata", Gson().toJson(userdata))
                        ShareData(mContext).SetUserData(userdata)
                        arrayList.cart_items as MutableList
                        arrayList.cart_items.remove(itemData)
                        notifyDataSetChanged()
                        mContext as CartActivity
                        mContext.loadData()
                    }
                    Toast.makeText(mContext, JSONObject(response).optString("message"), Toast.LENGTH_LONG).show()
                }

                override fun onError(error: String) {
                    loader.dismiss()
                }

                override fun onStart() {
                    loader.show()
                }

            })
        }

    }

    private fun PLUSCART(itemData: CartItem) {
        val params = HashMap<String, Any>()
        params.put("customer_id", ShareData(mContext).getUser()!!.customerId)
        params.put("product_id", itemData.product_id)
        params.put("qty", 1)
        EvisionLog.D("## ERRR-", Gson().toJson(itemData))
        onHTTP().POSTCALL(com.evision.Utils.URL.UPDATECART, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                EvisionLog.D("## DATA-", response)
//                loader.dismiss()
                if (JSONObject(response).optInt("code") == 200) {
                    val userdata = ShareData(mContext).getUser()
                    userdata!!.cartCount = JSONObject(response).optInt("cart_count")
                    ShareData(mContext).SetUserData(userdata)
//                    itemData.qty++
                    notifyDataSetChanged()
                    mContext as CartActivity
                    mContext.loadData()
                }
                Toast.makeText(mContext, JSONObject(response).optString("message"), Toast.LENGTH_LONG).show()
            }

            override fun onError(error: String) {
                loader.dismiss()

            }

            override fun onStart() {
                loader.show()
            }

        })
    }

    private fun MINUSCART(itemData: CartItem, isadded: Boolean) {
        val params = HashMap<String, Any>()
        params.put("customer_id", ShareData(mContext).getUser()!!.customerId)
        params.put("product_id", itemData.product_id)
        params.put("qty", 1)
        EvisionLog.D("## ERRR-", Gson().toJson(itemData))
        onHTTP().POSTCALL(com.evision.Utils.URL.MINUSCART, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                EvisionLog.E("##DecreaseItemQuantity",response)
//                loader.dismiss()
                if (JSONObject(response).optInt("code") == 200) {
                    val userdata = ShareData(mContext).getUser()
                    userdata!!.cartCount = JSONObject(response).optInt("cart_count")
                    ShareData(mContext).SetUserData(userdata)
//                    itemData.qty--
                    notifyDataSetChanged()
                    mContext as CartActivity
                    mContext.loadData()
                }
                Toast.makeText(mContext, JSONObject(response).optString("message"), Toast.LENGTH_LONG).show()

            }

            override fun onError(error: String) {
                loader.dismiss()

            }

            override fun onStart() {
                loader.show()
            }

        })
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val IMG_item = itemView.findViewById<ImageView>(R.id.IMG_item)
        val Dlete = itemView.findViewById<ImageView>(R.id.Dlete)
        val MINUS = itemView.findViewById<ImageView>(R.id.MINUS)
        val PLUS = itemView.findViewById<ImageView>(R.id.PLUS)
        val TXT_PName = itemView.findViewById<TextView>(R.id.TXT_PName)
        val TXT_Price = itemView.findViewById<TextView>(R.id.TXT_Price)
        val TXT_cratcount = itemView.findViewById<TextView>(R.id.TXT_cratcount)
        val TXT_MODEL = itemView.findViewById<TextView>(R.id.TXT_MODEL)

    }
}