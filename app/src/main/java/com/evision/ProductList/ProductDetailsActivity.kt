package com.evision.ProductList

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle

import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.evision.CartManage.CartActivity
import com.evision.Login_Registration.LoginActivity
import com.evision.ProductList.Pojo.PDetails
import com.evision.R
import com.evision.Utils.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_payment_cedit_card.*
import kotlinx.android.synthetic.main.activity_product_details.*
import org.json.JSONObject
import androidx.appcompat.widget.Toolbar;


class ProductDetailsActivity : AppCompatActivity() {
    lateinit var menuCartItem: MenuItem
    lateinit var loader: AppDialog
    private val REQ_LOGIN: Int = 12
    var sahrelink = ""
    var cat_id = ""
    var cat_name = ""
    var modelno = ""
    var isReadyforCourtCount = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        var toolbar:Toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        loader = AppDialog(this)

        //val strJunk = "AtrÃ©vete a SoÃ±ar"
      //  val arrByteForSpanish = strJunk.toByteArray(charset("ISO-8859-1"))
       // val strSpanish = String(arrByteForSpanish)
       // System.out.println("ghg"+strSpanish)

        toolbar.setTitleTextColor(Color.WHITE)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_white_back)

        val IMG_Product = findViewById<ImageView>(R.id.IMG_Product)

        val params = HashMap<String, Any>()
        params.put("product_id", intent.getStringExtra("pid"))
        EvisionLog.E("##params", params.toString())
        EvisionLog.E("##product_id", intent.getStringExtra("pid"))
        onHTTP().POSTCALL(URL.GETDETAILS, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                EvisionLog.E("##ProductDetailsResponse", response)
                if (response.isEmpty()){
                    loader.dismiss()
                    Toast.makeText(this@ProductDetailsActivity,"Este producto no está disponible en este momento. Volveremos pronto.",Toast.LENGTH_LONG).show()
                    finish()
                }else {
                    val objRes=JSONObject(response)
//                val details = Gson().fromJson(response, PDetails::class.java)
                    if (objRes.optInt("status") == 200) {
//                    toolbar.setTitle(details.product_view.get(0).category_name)
                        toolbar.setTitle(objRes.optJSONArray("product_view").optJSONObject(0).optString("category_name"))
//                    Glide.with(this@ProductDetailsActivity).load(details.product_view.get(0).product_image).apply(RequestOptions().placeholder(R.drawable.ic_placeholder)).into(IMG_Product)
                        Glide.with(this@ProductDetailsActivity).load(objRes.optJSONArray("product_view").optJSONObject(0).optString("product_image")).apply(RequestOptions().placeholder(R.drawable.ic_placeholder)).into(IMG_Product)
                        WebV.settings.javaScriptEnabled = true
                       // WebV.settings.setAppCacheEnabled(true)
                        val settings1 = WebV.getSettings()
                        settings1.setDefaultTextEncodingName("utf-8");
//                    WebV.loadData(details.product_view.get(0).descripcion, "text/html; charset=utf-8", "UTF-8")

                        val header = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
                         val ss:String=objRes.optJSONArray("product_view").optJSONObject(0).optString("descripcion")
                        val arrByteForSpanish = ss.toByteArray()
                        val strSpanish = String(arrByteForSpanish)
                        System.out.println("ghg"+strSpanish)
                       // WebV.loadData(ss, "text/html; charset=utf-8", null);
                      // WebV.loadDataWithBaseURL(null,objRes.optJSONArray("product_view").optJSONObject(0).optString("descripcion"), "text/html",  "utf-8", null)
                        //WebV.loadData(header+objRes.optJSONArray("product_view").optJSONObject(0).optString("descripcion"), "text/html; charset=utf-8", null);

                        // modified
                        WebV.webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                                view?.loadUrl(url)
                                return true
                            }
                        }
                        WebV.loadUrl(objRes.optJSONArray("product_view").optJSONObject(0).optString("desc2"))
                      //  WebV.loadDataWithBaseURL("",objRes.optJSONArray("product_view").optJSONObject(0).optString("descripcion"),"text/html","utf-8", null)
                       // WebV.loadData(strSpanish, "text/html; charset=UTF-8", null);


                        //  WebV.loadData(objRes.optJSONArray("product_view").optJSONObject(0).optString("descripcion"), "text/html; charset=utf-8", "utf-8")
                        val settings = WebV2.getSettings()
                        settings.setDefaultTextEncodingName("utf-8");
                        WebV2.settings.javaScriptEnabled = true
                       // WebV2.settings.setAppCacheEnabled(true)
                        WebV2.webChromeClient = object : WebChromeClient() {}
//                    WebV2.loadUrl("http://dev.indigitalsoft.com/evision/extradesc.php?brand=lg&model=32lk540bpda")
//                    WebV2.loadUrl(details.product_view[0].extra_html_description)
                       // WebV2.loadDataWithBaseURL(null, objRes.optJSONArray("product_view").optJSONObject(0).optString("extra_html_description"), "text/html", "utf-8", null);
                        WebV2.loadUrl(objRes.optJSONArray("product_view").optJSONObject(0).optString("extra_html_description"))
                       // WebV2.loadData(objRes.optJSONArray("product_view").optJSONObject(0).optString("extra_html_description"),"text/html; charset=utf-8","UTF-8")

//                    TXT_Pname.setText(details.product_view.get(0).product_name)
                        TXT_Pname.setText(objRes.optJSONArray("product_view").optJSONObject(0).optString("product_name"))
//                    TXT_ModelNo.setText(details.product_view.get(0).modelo)
//                    sahrelink = details.product_view[0].product_ink
                        sahrelink = objRes.optJSONArray("product_view").optJSONObject(0).optString("product_ink")
//                    cat_id = details.product_view[0].category_id
                        cat_id = objRes.optJSONArray("product_view").optJSONObject(0).optString("category_id")
//                    cat_name = details.product_view[0].category_name
                        cat_name = objRes.optJSONArray("product_view").optJSONObject(0).optString("category_name")
//                    modelno = details.product_view[0].modelo
                        modelno = objRes.optJSONArray("product_view").optJSONObject(0).optString("modelo")
//                    val spclprice = details.product_view.get(0).special_price.toDouble()
                        val spclprice = objRes.optJSONArray("product_view").optJSONObject(0).optString("special_price").toDouble()
                        if (spclprice > 0) {
//                        val newprice = "<b>" + details.product_view.get(0).currency + details.product_view.get(0).special_price + "</b>"
                            val newprice = "<b>" + objRes.optJSONArray("product_view").optJSONObject(0).optString("currency") + objRes.optJSONArray("product_view").optJSONObject(0).optString("special_price") + "</b>"
                            TXT_Price_new.setText(Html.fromHtml(newprice))
//                        TXT_Price_new.setText(details.product_view.get(0).currency + details.product_view.get(0).price)
                           // TXT_Price_new.setText(objRes.optJSONArray("product_view").optJSONObject(0).optString("currency") + objRes.optJSONArray("product_view").optJSONObject(0).optString("price"))
                            TXT_Price.paintFlags = TXT_Price.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG
                        }
                        //modified
                        TXT_Price.setText(objRes.optJSONArray("product_view").optJSONObject(0).optString("currency") + objRes.optJSONArray("product_view").optJSONObject(0).optString("price"))
                       // TXT_Price.paintFlags = TXT_Price.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG


                        bycategory.setText(resources.getString(R.string.seemore) +" "+ objRes.optJSONArray("product_view").optJSONObject(0).optString("category_name"))
                        bymodel.setText(resources.getString(R.string.seemore)+" " + objRes.optJSONArray("product_view").optJSONObject(0).optString("modelo"))
                        if (objRes.optJSONArray("product_view").optJSONObject(0).optInt("addtocart_option") == 1)
                            LL_addtocartView.visibility = View.VISIBLE
                        else
                            LL_addtocartView.visibility = View.GONE

                        loader.dismiss()
                    }
                }
              //  loader.dismiss()
            }

            override fun onError(error: String) {
                loader.dismiss()
            }

            override fun onStart() {
                loader.show()
            }

        })

        LL_addtocart.setOnClickListener {
            if (ShareData(this).getUser() == null) {
                startActivityForResult(Intent(this, LoginActivity::class.java), 11)
                return@setOnClickListener
            }
            val params = HashMap<String, Any>()
            params.put("customer_id", ShareData(this).getUser()!!.customerId)
            params.put("product_id", intent.getStringExtra("pid"))
            params.put("qty", EDX_cart.text.toString())
            onHTTP().POSTCALL(com.evision.Utils.URL.ADDtoCART, params, object : OnHttpResponse {
                override fun onSuccess(response: String) {
                    loader.dismiss()
                    if (JSONObject(response).optInt("code") == 200) {

                        val userdata = ShareData(this@ProductDetailsActivity).getUser()
                        EvisionLog.E("##userData", Gson().toJson(userdata))
                        userdata!!.cartCount = userdata.cartCount + EDX_cart.text.toString().toInt()
                        ShareData(this@ProductDetailsActivity).SetUserData(userdata)
                        ManageCartView(userdata.cartCount)

                    }
                    Toast.makeText(this@ProductDetailsActivity, JSONObject(response).optString("message"), Toast.LENGTH_LONG).show()
                }

                override fun onError(error: String) {
                    loader.dismiss()
                }

                override fun onStart() {
                    loader.show()
                }

            })
        }

        bycategory.setOnClickListener {
            startActivity(Intent(this, ProductListActivity::class.java).putExtra("pid", cat_id).putExtra("cname", cat_name))
            finish()
        }
        bymodel.setOnClickListener {
            startActivity(Intent(this, ProductListActivity::class.java).putExtra("pid", "BYMODEL").putExtra("cname", cat_name)
                    .putExtra("cat_id", cat_name)
                    .putExtra("model", modelno))
            finish()
        }


    }

    /*************** MANAGE CART VIEW  */
    fun ManageCartView(i: Int) {
        val inflatedFrame = layoutInflater.inflate(R.layout.cart_layout, null)
        val item = inflatedFrame.findViewById(R.id.TXT_Counter) as TextView
        if (i <= 0)
            item.visibility = View.GONE
        else
            item.visibility = View.VISIBLE
        item.text = i.toString() + ""
        val drawable = BitmapDrawable(resources, Converter.getBitmapFromView(inflatedFrame))
        menuCartItem.setIcon(drawable)
        isReadyforCourtCount = true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.action_sahre -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        sahrelink)
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }

            R.id.action_cart -> {
                val logindata = ShareData(this).getUser()
                if (logindata == null) {
                    startActivityForResult(Intent(this, LoginActivity::class.java), REQ_LOGIN)
                    return true
                }
                if (logindata!!.cartCount != null)
                    startActivity(Intent(this, CartActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        menuCartItem = menu!!.findItem(R.id.action_cart)
        if (ShareData(this).getUser() != null) {
            val logindata = ShareData(this).getUser()
            ManageCartView(logindata!!.cartCount)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        if (ShareData(this).getUser() != null && isReadyforCourtCount) {
            val logindata = ShareData(this).getUser()
            ManageCartView(logindata!!.cartCount)
        }

    }
}
