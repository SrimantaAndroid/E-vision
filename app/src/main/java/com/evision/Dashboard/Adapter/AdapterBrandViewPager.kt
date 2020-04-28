package com.evision.Dashboard.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.ColorSpace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.request.RequestOptions
import com.evision.Category.CategoryActivity
import com.evision.Dashboard.Pojo.HomeBrand
import com.evision.Dashboard.Pojo.TopBanner
import com.evision.ProductList.ProductDetailsActivity
import com.evision.ProductList.ProductListActivity
import com.evision.R

class AdapterBrandViewPager(mContext: Context, arrayList: List<HomeBrand>) : PagerAdapter() {

    val mContext=mContext
    val arraylist=arrayList
    val inflater = LayoutInflater.from(mContext)
    override fun isViewFromObject(p0: View, p1: Any): Boolean {
     return p0==p1    //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
       return arraylist.size//To change body of created functions use File | Settings | File Templates.
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val layout = inflater.inflate(R.layout.home_brand_item, container, false)!!
        val ImageView=layout.findViewById<ImageView>(R.id.img_homebrand)
        val txt_homebrandname=layout.findViewById<TextView>(R.id.txt_homebrandname)
        txt_homebrandname.setText(arraylist.get(position).brand_name)
        Glide.with(mContext).load(arraylist.get(position).brand_image).apply(RequestOptions().centerCrop().placeholder(R.drawable.ic_placeholder)).into(ImageView)
        container.addView(layout)
        ImageView.setOnClickListener {
        /*             if (!arraylist.get(position).product_id.equals(""))
            //  mContext.startActivity(Intent(mContext, ProductListActivity::class.java).putExtra("pid",arraylist.get(position).product_id ).putExtra("cname",arraylist.get(position).banner_title ))
                mContext.startActivity(Intent(mContext,ProductDetailsActivity::class.java).putExtra("pid",arraylist.get(position).product_id))
            else if(!arraylist.get(position).category_id.equals(""))
                mContext.startActivity(Intent(mContext, ProductListActivity::class.java).putExtra("pid",arraylist.get(position).category_id ).putExtra("cname",mContext.resources.getString(R.string.menu_onlinesale) ))
            //  mContext.startActivity(Intent(mContext, CategoryActivity::class.java))
*/
            val intent=Intent(mContext,ProductListActivity::class.java)
            intent.putExtra("pid","BYMODELFROMHOME");
            intent.putExtra("cname",arraylist.get(position).brand_name)
            intent.putExtra("cat_id",arraylist.get(position).brand_id.toString())
            intent.putExtra("model",arraylist.get(position).brand_name)
            mContext.startActivity(intent)
           //   mContext.startActivity(Intent(mContext,ProductListActivity::class.java).putExtra("pid","BYMODEL").putExtra("cat_id",arraylist.get(position).brand_id).putExtra("model",arraylist.get(position).brand_name))
        }

        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}