package com.evision.CartManage

import android.content.Intent
import android.graphics.Color
import android.os.Bundle

import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.widget.Toast
import com.evision.CartManage.Adapter.AdapterCart
import com.evision.CartManage.Pojo.CartResponse
import com.evision.Login_Registration.LoginActivity
import com.evision.R
import com.evision.Utils.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.content_check_out_address.*
import org.json.JSONObject
import java.text.DecimalFormat
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.annotation.TargetApi
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CartActivity : AppCompatActivity() {
    lateinit var loader: AppDialog
    lateinit var adapterCart: AdapterCart
    lateinit var RECV:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        loader = AppDialog(this)
        toolbar.setTitle(R.string.title_add_toocart)
        toolbar.setTitleTextColor(Color.WHITE)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_white_back)
         RECV=findViewById<RecyclerView>(R.id.RECV)
        RECV.layoutManager = LinearLayoutManager(this)



        if (ShareData(this).getUser() == null) {
            startActivityForResult(Intent(this, LoginActivity::class.java), 21)
        } else {
            loadData()
        }

        TXT_AddMOre.setOnClickListener {
            finish()
        }

        TXT_precedtoPay.setOnClickListener {
            startActivity(Intent(this, CheckOutAddress::class.java))
        }

    }

    fun loadData() {
        val params = HashMap<String, Any>()
        params.put("customer_id", ShareData(this).getUser()!!.customerId)
        EvisionLog.E("##REQForCart", params.toString())
        onHTTP().POSTCALL(URL.GETCARTITEM, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                val data = Gson().fromJson(response, CartResponse::class.java)
                if (data.status == 400) {
                    Toast.makeText(this@CartActivity,data.message,Toast.LENGTH_LONG).show()
                    ll_no_value.visibility= View.VISIBLE
                    rl_main.visibility= View.GONE
                    TXT_qtyno.setText("0" + resources.getString(R.string.qty))
                    var logindata = ShareData(this@CartActivity).getUser()
                    logindata!!.cartCount =0
                    ShareData(this@CartActivity).SetUserData(Gson().toJson(logindata).toString())
//                    finish()
                }
                else {

                    ll_no_value.visibility= View.GONE
                    rl_main.visibility= View.VISIBLE
                    var logindata = ShareData(this@CartActivity).getUser()
                    logindata!!.cartCount =data.cart_count
                    ShareData(this@CartActivity).SetUserData(Gson().toJson(logindata).toString())

                    if (RECV.adapter == null) {
                        adapterCart = AdapterCart(this@CartActivity, data, loader)
                        RECV.adapter = adapterCart
                    }
                    adapterCart.notifyDataSetChanged()
                    TXT_qtyno.setText("" + data.cart_count + resources.getString(R.string.qty))
                    Log.e("@@MyValueSubtotal","==>"+data.cart_totals[0].subtotal)

                    val precision = DecimalFormat("0.00")
                    // dblVariable is a number variable and not a String in this case

                    val number2digits_subtotal: String? = precision.format(data.cart_totals[0].subtotal)
                    val number2digits_grandtotal: String? = precision.format(data.cart_totals[0].grand_total)

                    subtotal.setText(data.cart_totals[0].currency + number2digits_subtotal)
                    TACpercent.setText("" + data.cart_totals[0].tax_name)
                    tax.setText(data.cart_totals[0].currency + data.cart_totals[0].tax)
                    TOTAL.setText(data.cart_totals[0].currency + number2digits_grandtotal)
                }
                loader.dismiss()
            }

            override fun onError(error: String) {
                loader.dismiss()
            }

            override fun onStart() {
                if (!loader.isShowing)
                    loader.show()
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 21) {
            if (ShareData(this).getUser() == null) {
                finish()
            } else
                loadData()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
