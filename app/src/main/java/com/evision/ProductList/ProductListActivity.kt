package com.evision.ProductList

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.evision.CartManage.CartActivity
import com.evision.ProductList.Adapter.AdapterProduct
import com.evision.ProductList.Interface.ManufacturerFilter
import com.evision.ProductList.Pojo.*
import com.evision.R
import com.evision.Search.POJO.SearchResulty
import com.evision.Utils.*
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import org.json.JSONObject
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProductListActivity : AppCompatActivity(), ManufacturerFilter {


    lateinit var menuCartItem: MenuItem
    lateinit var adapter: AdapterProduct
    lateinit var loader: AppDialog
    var cat_id: String? = ""
    var keyword: String? = ""
    var bottomSheetDialogFragment: FilterBottomSheetFragment? = null
    var Response = ""
    var isReadyforCourtCount = false
    lateinit var Rec_listP:RecyclerView
    lateinit var btn_filter:FloatingActionButton
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        var toolbar:Toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        loader = AppDialog(this)
        toolbar.setTitle("Category Name")
        toolbar.setTitleTextColor(Color.WHITE)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_white_back)
        Rec_listP=findViewById(R.id.Rec_listP)
        btn_filter=findViewById(R.id.btn_filter)
        supportActionBar!!.setTitle(intent.getStringExtra("cname"))
        Rec_listP.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?


        findViewById<FloatingActionButton>(R.id.btn_filter).setOnClickListener {

            bottomSheetDialogFragment = FilterBottomSheetFragment.newInstance(Response, "")
            bottomSheetDialogFragment!!.init(this@ProductListActivity)
            bottomSheetDialogFragment!!.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment")


            /*val params = HashMap<String, Any>()
            params.put("category_id", intent.getStringExtra("pid"))
            EvisionLog.D("## PARAMS-", Gson().toJson(params))
            onHTTP().POSTCALL(com.evision.Utils.URL.GETFILTER, params, object : OnHttpResponse {
                override fun onSuccess(response: String) {

//                arrlist=ArrayList()
                    EvisionLog.D("## DATA-",response)
                    val listdata = Gson().fromJson(response, FilterList::class.java)
                    if(listdata.status==200) {
                        val bottomSheetDialogFragment = FilterBottomSheetFragment.newInstance(response, "")
                        bottomSheetDialogFragment.init(this@ProductListActivity)
                        bottomSheetDialogFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment")
                    }
                    loader.dismiss()

                }

                override fun onError(error: String) {
                    loader.dismiss()

                }

                override fun onStart() {
                    loader.show()
                }

            })*/


        }



        cat_id = intent.getStringExtra("pid")
        if (cat_id == "BYONLINENAV") {
            cat_id = ""
            onHTTP().GETCALL(URL.GETONLINESALELST, object : OnHttpResponse {
                override fun onSuccess(response: String) {
                    Response = response
                    val plist = Gson().fromJson(response, OnlineResponse::class.java)
                    if (plist.code == 200) {
                        adapter = AdapterProduct(this@ProductListActivity, plist.online_products_all)
                        Rec_listP.adapter = adapter
                    } else {
                        Rec_listP.adapter = null

                    }
                    loader.dismiss()
                }

                override fun onError(error: String) {
                    loader.dismiss()
                }

                override fun onStart() {
                    loader.show()
                }

            })
        } else if (cat_id == "BYMODEL") {
                cat_id = intent.getStringExtra("cat_id")
                val modelo = intent.getStringExtra("model")
                SaerchByModel(cat_id!!, modelo)
            }
        else if (cat_id == "BYMODELFROMHOME") {
            cat_id = intent.getStringExtra("cat_id")
            val modelo = intent.getStringExtra("model")
           // SaerchByModel(cat_id!!, modelo)
            btn_filter!!.visibility=View.INVISIBLE
            searchbybrandname(modelo)
        }
        else
                if (cat_id == "BYSEARCH") {
                    cat_id = ""
                    val params = HashMap<String, Any>()
                    params.put("keyword", intent.getStringExtra("cname"))
                    keyword = intent.getStringExtra("cname")
                    EvisionLog.D("## PARAMS-", Gson().toJson(params))
                    onHTTP().POSTCALL(com.evision.Utils.URL.SEARCH, params, object : OnHttpResponse {
                        override fun onSuccess(response: String) {
                            Response = response
//                arrlist=ArrayList()
                            EvisionLog.D("## DATA-", response)


                            val listdata = Gson().fromJson(response, SearchResulty::class.java)
//                arrlist .addAll(listdata.productList)
                            if (listdata.status == 200) {

                                adapter = AdapterProduct(this@ProductListActivity, listdata.search_data)
                                Rec_listP.adapter = adapter
                            } else {
                                Rec_listP.adapter = null

                            }
                            loader.dismiss()
                        }

                        override fun onError(error: String) {
                            loader.dismiss()

                        }

                        override fun onStart() {
                            loader.show()
                        }

                    })
                } else {
                    cat_id = intent.getStringExtra("pid")
                    val params = HashMap<String, Any>()
                    params.put("category_id", intent.getStringExtra("pid"))
                    EvisionLog.D("## PARAMS-", Gson().toJson(params))
                    onHTTP().POSTCALL(com.evision.Utils.URL.GETPRODUCTLIST, params, object : OnHttpResponse {
                        override fun onSuccess(response: String) {
                            Response = response
//                arrlist=ArrayList()
                            EvisionLog.D("## DATA-", response)


                            val listdata = Gson().fromJson(response, Productlist::class.java)
//                arrlist .addAll(listdata.productList)
                            adapter = AdapterProduct(this@ProductListActivity, listdata.productList)
                            Rec_listP.adapter = adapter
                            loader.dismiss()
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

    private fun searchbybrandname(modelo: String) {
        val params = HashMap<String, Any>()
        params.put("keyword",modelo)
        keyword = intent.getStringExtra("cname")
        EvisionLog.D("## PARAMS-", Gson().toJson(params))
        onHTTP().POSTCALL(com.evision.Utils.URL.SEARCH, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                Response = response
//                arrlist=ArrayList()
                EvisionLog.D("## DATA-", response)


                val listdata = Gson().fromJson(response, SearchResulty::class.java)
//                arrlist .addAll(listdata.productList)
                if (listdata.status == 200) {

                    adapter = AdapterProduct(this@ProductListActivity, listdata.search_data)
                    Rec_listP.adapter = adapter
                } else {
                    Rec_listP.adapter = null

                }
                loader.dismiss()
            }

            override fun onError(error: String) {
                loader.dismiss()

            }

            override fun onStart() {
                loader.show()
            }

        })
    }

    /*************** MANAGE CART VIEW  */
    fun ManageCartView() {
        val i = ShareData(this).getUser()!!.cartCount
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        menuCartItem = menu.findItem(R.id.action_cart)
        if (ShareData(this).getUser() != null)
            ManageCartView()
        return true
    }

    override fun onResume() {
        super.onResume()
        if (ShareData(this).getUser() != null && isReadyforCourtCount)
            ManageCartView()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.action_call -> {

                Dexter.withActivity(this)
                        .withPermission(Manifest.permission.CALL_PHONE)
                        .withListener(object : PermissionListener {
                            @SuppressLint("MissingPermission")
                            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+507-3021030"))
                                startActivity(intent)
                            }

                            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                                token!!.continuePermissionRequest()
                            }

                            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                            }

                        }).check()

                return true
            }
            R.id.action_whatsapp -> {
                if (isWhatsapp()) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("http://api.whatsapp.com/send?phone=+919733759131&text=")
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Please make whatsapp to +507-6444-7679", Toast.LENGTH_SHORT).show()
                }
                return true
            }
            R.id.action_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isWhatsapp(): Boolean {
        val pm = packageManager
        try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }

        return false
    }

    override fun onItemSelect(name: String) {
        Response = name
        val listdata = Gson().fromJson(Response, FilterList::class.java)
        var barnd: String? = ""
        for (k in listdata.manufacture_list) {

            if (k.isselect) {
                barnd = barnd + if (k.isselect) k.manufacture_name + "," else ""
            }
        }
        if (barnd!!.isNotEmpty())
            barnd = barnd!!.substring(0, barnd.length - 1)
        val params = HashMap<String, Any>()
        params.put("brand", barnd!!)
        params.put("category_id", cat_id!!)
        params.put("keyword", keyword!!)
        params.put("min_price", listdata.price_filter[0].selectmin)
        params.put("max_price", listdata.price_filter[0].selectmax)
        EvisionLog.D("## REQ-", Gson().toJson(params))
        onHTTP().POSTCALL(URL.FILTER, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                EvisionLog.D("## DATA-", response)
                val listdata = Gson().fromJson(response, FilterResultdata::class.java)
                if (listdata.status == 200) {
                    adapter = AdapterProduct(this@ProductListActivity, listdata.productList)
                    Rec_listP.adapter = adapter
                } else {
                    Toast.makeText(this@ProductListActivity, JSONObject(response).optString("message"), Toast.LENGTH_LONG).show()
                }
                loader.dismiss()
            }

            override fun onError(error: String) {
                loader.dismiss()
            }

            override fun onStart() {
                loader.show()
            }

        })
//        SaerchByModel(cat_id, name)
    }

    private fun SaerchByModel(cat_id: String, name: String) {
        val params = HashMap<String, Any>()
        params.put("brand", name)
        params.put("category_id", cat_id)
        EvisionLog.D("## PARAMS-", Gson().toJson(params))
        onHTTP().POSTCALL(com.evision.Utils.URL.FILTERBYMANUFACTURER, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {

                EvisionLog.D("## DATA-", response)
                val listdata = Gson().fromJson(response, ManufacturarList::class.java)
                if (listdata.status == 200) {
                    adapter = AdapterProduct(this@ProductListActivity, listdata.productList)
                    Rec_listP.adapter = adapter
                } else {
                    Toast.makeText(this@ProductListActivity, JSONObject(response).optString("message"), Toast.LENGTH_LONG).show()
                }
                loader.dismiss()
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
