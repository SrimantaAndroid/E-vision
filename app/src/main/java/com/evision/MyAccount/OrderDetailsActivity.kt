package com.evision.MyAccount

import android.graphics.Color
import android.os.Bundle

import android.text.Html
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evision.MyAccount.Adapter.AdapterMyorder
import com.evision.MyAccount.Pojo.OrderDetails
import com.evision.R
import com.evision.Utils.AppDialog
import com.evision.Utils.OnHttpResponse
import com.evision.Utils.URL
import com.evision.Utils.onHTTP
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.content_order_details.*
import androidx.appcompat.widget.Toolbar;

class OrderDetailsActivity : AppCompatActivity() {
    lateinit var apploader: AppDialog
    lateinit var adapter: AdapterMyorder
    lateinit var REC:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)
        var toolbar:Toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_white_back)
        REC=findViewById(R.id.REC)
        apploader = AppDialog(this)
        REC.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
        TXT_ORDERID.setText(resources.getString(R.string.oredrfor) + " #" + intent!!.getStringExtra("incrementid"))

        val params = HashMap<String, Any>()
        params.put("order_id", intent!!.getStringExtra("orderid"))
        onHTTP().POSTCALL(URL.ORDERDETAILS, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                apploader.dismiss()
                val oredrd = Gson().fromJson(response, OrderDetails::class.java)
                adapter = AdapterMyorder(this@OrderDetailsActivity, oredrd.order_items)
                REC.adapter = adapter
                TXT_TOTAL.setText(oredrd.order_totals[0].currency + oredrd.order_totals[0].subtotal)
                TAX.setText(oredrd.order_totals[0].currency + oredrd.order_totals[0].tax)
                TXT_DELICERY.setText(oredrd.order_totals[0].currency + oredrd.order_totals[0].delivery_cost)
                TXT_price.setText(oredrd.order_totals[0].currency + oredrd.order_totals[0].grand_total)
                TAXTEXT.setText(oredrd.order_totals[0].tax_name)
                TXT_billingadd.setText(Html.fromHtml(oredrd.order_details[0].billing_shipping_address))
                TXT_deliveryadd.setText(Html.fromHtml(oredrd.order_details[0].pickup_address))
                TXT_pm.setText(Html.fromHtml(oredrd.order_details[0].payment_method))
                TXT_od.setText(Html.fromHtml(oredrd.order_details[0].order_date))
                TXT_pmt.setText(Html.fromHtml(oredrd.order_details[0].payment_status))
            }

            override fun onError(error: String) {
                apploader.dismiss()
            }

            override fun onStart() {
                apploader.show()
            }

        })


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
