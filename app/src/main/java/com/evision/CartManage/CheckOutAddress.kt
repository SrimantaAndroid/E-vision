package com.evision.CartManage

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle

import android.view.MenuItem
import android.view.View
import com.evision.CartManage.Pojo.CustomerAddress
import com.evision.MyAccount.Dilogs.POJO.CITY
import com.evision.MyAccount.Dilogs.POJO.STATE
import com.evision.MyAccount.Dilogs.SlectCityFragment
import com.evision.MyAccount.Dilogs.SlectStateFragment
import com.evision.R
import com.evision.Utils.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_check_out_address.*
import kotlinx.android.synthetic.main.content_check_out_address.*
import org.json.JSONObject
import android.webkit.*
import android.widget.Toast
import com.evision.CartManage.Pojo.CustomerAddressBilling
import com.evision.MyAccount.Dilogs.CountryFragment
import com.evision.MyAccount.Dilogs.POJO.COUNTRY
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.content_check_out_address.BTN_NEXT
import kotlinx.android.synthetic.main.content_check_out_address.CHECKBOXDIFE
import kotlinx.android.synthetic.main.content_check_out_address.Ccp
import kotlinx.android.synthetic.main.content_check_out_address.Ccp2
import kotlinx.android.synthetic.main.content_check_out_address.EDX_Address
import kotlinx.android.synthetic.main.content_check_out_address.EDX_Address2
import kotlinx.android.synthetic.main.content_check_out_address.EDX_fname
import kotlinx.android.synthetic.main.content_check_out_address.EDX_lname
import kotlinx.android.synthetic.main.content_check_out_address.EDX_phone
import kotlinx.android.synthetic.main.content_check_out_address.EDX_telephone
import kotlinx.android.synthetic.main.content_check_out_address.LL_CONF
import kotlinx.android.synthetic.main.content_check_out_address.TXT_CITY
import kotlinx.android.synthetic.main.content_check_out_address.TXT_CITY2
import kotlinx.android.synthetic.main.content_check_out_address.TXT_COUNTRY
import kotlinx.android.synthetic.main.content_check_out_address.TXT_COUNTRY2
import kotlinx.android.synthetic.main.content_check_out_address.TXT_STATE
import kotlinx.android.synthetic.main.content_check_out_address.TXT_STATE2
import kotlinx.android.synthetic.main.content_check_out_address.first_name1
import kotlinx.android.synthetic.main.content_check_out_address.last_name2
import kotlinx.android.synthetic.main.content_check_out_address.web_map
import kotlinx.android.synthetic.main.content_check_out_address_new.*
import android.view.ViewGroup.LayoutParams.FILL_PARENT

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity


class CheckOutAddress : AppCompatActivity(), GoogleMap.OnMarkerClickListener {
    override fun onMarkerClick(p0: Marker?): Boolean {
        Toast.makeText(this,p0.toString(),Toast.LENGTH_LONG).show()
        return true
    }

    private var STATEID: String? = ""
    private var STATEID2: String? = ""
    private var CITYID: String? = ""
    private var COUNTRYID:String?=""
    private var COUNTRYID2:String?="169"
    private var CITYID2: String? = ""
    lateinit var loader: AppDialog
    lateinit var customerAddress: CustomerAddress
    lateinit var customerAddress_billing: CustomerAddressBilling
    lateinit var customerAddress_Shipping: CustomerAddress

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out_address)
        var toolbar:Toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_white_back)
        loader = AppDialog(this)

       // countyrcodepicker.registerCarrierNumberEditText(EDX_phone)
        CHECKOUT()
        web_map.getSettings().setJavaScriptEnabled(true);
        web_map.setWebViewClient(WebViewClient())// enable javascript
        web_map.loadUrl("https://www.evisionstore.com/api/checkout/getmap.php")
        Ccp.registerPhoneNumberTextView(EDX_phone)
        Ccp2.registerPhoneNumberTextView(EDX_telephone)

       // val JSAndroidBindingClass = JavaScriptInterface1()
       // web_map.addJavascriptInterface(JSAndroidBindingClass, "locater")

     /* web_map.setWebViewClient(object : WebViewClient() {
            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
              //  Toast.makeText(this@CartActivity, description, Toast.LENGTH_SHORT).show()
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView, req: WebResourceRequest, rerr: WebResourceError) {
                onReceivedError(view, rerr.errorCode, rerr.description.toString(), req.url.toString())
            }

          override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
              return super.shouldOverrideUrlLoading(view, request)
              Toast.makeText(this@CheckOutAddress,view.toString(),Toast.LENGTH_LONG).show()
          }
        })
*/

       /* web_map.setOnTouchListener(OnTouchListener { v, event ->
            // TODO Auto-generated method stub
            Toast.makeText(this@CheckOutAddress,v.toString(),Toast.LENGTH_LONG).show()
            System.out.println("dasf "+v.toString())
            false
        })*/
        web_map.addJavascriptInterface(object : Any() {
            @JavascriptInterface           // For API 17+
            fun getMapAddress(strl: String) {
                if(strl!=null) {
                    // sendaddress(strl)
                  //  EDX_Address2.setLayoutParams(ViewGroup.LayoutParams(MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                    if (LL_CONF.visibility==View.VISIBLE)
                        EDX_Address2.setText(strl!!)
                    else
                      //  EDX_Address.setText(strl!!)
                        EDX_Address2.setText(strl!!)
                   // Toast.makeText(this@CheckOutAddress, strl, Toast.LENGTH_SHORT).show()
                }
                    else
                    Toast.makeText(this@CheckOutAddress, "Please Select address", Toast.LENGTH_SHORT).show()

            }
        }, "ok")


    }
    /*public  class JavaScriptInterface1{
        @JavascriptInterface
        fun getMapAddress(address: String ){
            System.out.println("address from map"+address)
           // Toast.makeText(this@CheckOutAddress,"hsduhsa",Toast.LENGTH_LONG).show()
        }

    }*/
   /* private fun sendaddress(strl: String) {
      *//*  val params = HashMap<String, Any>()
        params.put("customer_id", ShareData(this).getUser()!!.customerId)
        params.put("first_name", customerAddress.fisrt_name)
        onHTTP().POSTCALL(URL.ORDERPALCEINSTORE, params, object : OnHttpResponse {*//*
        loader.show()
        onHTTP().GETCALL(URL.SEND_ADDRESS,object : OnHttpResponse {
            override fun onSuccess(response: String) {
                loader.dismiss()
               // datacat= Gson().fromJson(response, CategoryData::class.java)
              //  adapter= AdapterCategory(this@CategoryActivity,datacat)
              //  Recv_cat.adapter=adapter
              //  EvisionLog.D("## TAG-",response)
            }

            override fun onError(error: String) {
                EvisionLog.D("## TAG-",error)
            }

            override fun onStart() {
                loader.show()
            }

        })
    }*/

    private fun CHECKOUT() {
        val params = HashMap<String, Any>()
        params.put("customer_id", ShareData(this).getUser()!!.customerId)
        onHTTP().POSTCALL(URL.CHECKOUTADDRESS, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                EvisionLog.D("## RESULT-", response)
                var obj = JSONObject(response)
                if (obj.optInt("status") == 200) {
                    customerAddress = Gson().fromJson(obj.getJSONArray("checkout_address").optJSONObject(0).toString(), CustomerAddress::class.java)
                    customerAddress.country_id = "169"
                    customerAddress.country_name = "Panama"
                    EDX_fname.setText(customerAddress.fisrt_name)
                    first_name1.setText(customerAddress.fisrt_name)
                    EDX_lname.setText(customerAddress.last_name)
                    last_name2.setText(customerAddress.last_name)
                    EDX_Address.setText(customerAddress.address)
                    EDX_Address2.setText(customerAddress.address)
                    TXT_COUNTRY.setText(customerAddress.country_name)
                    TXT_STATE.setText(customerAddress.state_name)
                    TXT_CITY.setText(customerAddress.city_name)
                    EDX_phone.setText(customerAddress.telephone)
                    CITYID = customerAddress.city_id
                    STATEID = customerAddress.state_id
                   /* TXT_COUNTRY.setOnClickListener {

                        val selectCountry=CountryFragment.newInstance("[\n" +
                                "  {\n" +
                                "    \"id\":\"169\",\n" +
                                "    \"name\":\"Panama\"\n" +
                                "   }\n" +
                                "]","")
                        selectCountry.INIT(object:CountryFragment.OnSate{
                            override fun onChosee(country: COUNTRY) {
                                TXT_COUNTRY.setText(country.name)
                                TXT_STATE.setText("")
                                TXT_CITY.setText("")
                               // TXT_STATE.setHint(resources.getString(R.string.choosestate))
                              //  TXT_CITY.setHint(resources.getString(R.string.choosecity))
                                COUNTRYID=country.id
                                CITYID = ""
                                STATEID = ""
                                selectCountry.dialog.dismiss()
                            }
                        })
                        selectCountry!!.show(supportFragmentManager, "")
                    }
*/
                    TXT_STATE.setOnClickListener {
                        val params = HashMap<String, Any>()
                        params.put("country_id", 169)

                        onHTTP().POSTCALL(URL.GETSTATES, params, object : OnHttpResponse {
                            override fun onSuccess(response: String) {
                                loader.dismiss()
                                val slectStateFragment = SlectStateFragment.newInstance(response, "")
                                slectStateFragment.INIT(object : SlectStateFragment.OnSate {
                                    override fun onChosee(state: STATE) {
                                        TXT_STATE.setText(state.state_name)
                                        STATEID = state.state_id
                                        TXT_CITY.setText("")
                                        TXT_CITY.setHint(resources.getString(R.string.selectcity))
                                        CITYID = ""
                                        slectStateFragment.dialog.dismiss()
                                    }

                                })
                                slectStateFragment.show(supportFragmentManager, "")

                            }

                            override fun onError(error: String) {
                                loader.dismiss()
                            }

                            override fun onStart() {
                                loader.show()
                            }

                        })

                    }


                    TXT_CITY.setOnClickListener {
                        val params = HashMap<String, Any>()
                        params.put("state_id", STATEID!!)
                        EvisionLog.D("## CITY REQ-", Gson().toJson(params))
                        onHTTP().POSTCALL(URL.GETCITYS, params, object : OnHttpResponse {
                            override fun onSuccess(response: String) {
                                EvisionLog.D("## CITY-", response)
                                loader.dismiss()
                                val selectcity = SlectCityFragment.newInstance(response, "")
                                selectcity.init(object : SlectCityFragment.OnCity {
                                    override fun choosecity(city: CITY) {
                                        TXT_CITY.setText(city.city_name)
                                        CITYID = city.city_id
                                        selectcity.dialog.dismiss()
                                    }

                                })
                                selectcity.show(supportFragmentManager, "")

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
                loader.dismiss()
            }

            override fun onError(error: String) {
                loader.dismiss()
            }

            override fun onStart() {
                loader.show()
            }

        })

        CHECKBOXDIFE.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                LL_CONF.visibility = View.VISIBLE
               // Toast.makeText(this,Ccp.fullNumberWithPlus,Toast.LENGTH_LONG).show()
               // TXT_CITY2.setText(customerAddress.city_name)
               // CITYID2 = customerAddress.city_id
               // TXT_STATE2.setText(customerAddress.state_name)
               // STATEID2 = customerAddress.state_id
               // TXT_COUNTRY2.setText(customerAddress.country_name)
            }
            else
                LL_CONF.visibility = View.GONE

        }
        TXT_COUNTRY2.setOnClickListener {
            onHTTP().POSTCALL(URL.GETCOUNTRY, params, object : OnHttpResponse {
                override fun onSuccess(response: String) {
                    loader.dismiss()
                   /* val selectCountry=CountryFragment.newInstance("[\n" +
                            "  {\n" +
                            "    \"id\":\"169\",\n" +
                            "    \"name\":\"Panama\"\n" +
                            "   }\n" +
                            "]","")*/
                    val selectCountry=CountryFragment.newInstance(response,"")
                    selectCountry.INIT(object:CountryFragment.OnSate{
                        override fun onChosee(country: COUNTRY) {
                            TXT_COUNTRY2.setText(country.country_name)
                            TXT_STATE2.setText("")
                            TXT_CITY2.setText("")
                            // TXT_STATE2.setHint(resources.getString(R.string.choosestate))
                            // TXT_CITY2.setHint(resources.getString(R.string.choosecity))
                            COUNTRYID2=country.country_id
                            STATEID2 = ""
                            CITYID2 = ""
                            selectCountry.dialog.dismiss()
                        }
                    })
                    selectCountry!!.show(supportFragmentManager, "")
                }
                override fun onStart() {
                    loader.show()
                }

                override fun onError(error: String) {
                    loader.dismiss()
                }
            })

        }

        TXT_STATE2.setOnClickListener {
            val params = HashMap<String, Any>()
            params.put("country_id", COUNTRYID2!!)

            onHTTP().POSTCALL(URL.GETSTATES, params, object : OnHttpResponse {
                override fun onSuccess(response: String) {
                    loader.dismiss()
                    val slectStateFragment = SlectStateFragment.newInstance(response, "")
                    slectStateFragment.INIT(object : SlectStateFragment.OnSate {
                        override fun onChosee(state: STATE) {
                            TXT_STATE2.setText(state.state_name)
                            STATEID2 = state.state_id
                            TXT_CITY2.setText("")
                            TXT_CITY2.setHint(resources.getString(R.string.selectcity))
                            CITYID2 = ""
                            slectStateFragment.dialog.dismiss()
                        }

                    })
                    slectStateFragment.show(supportFragmentManager, "")

                }

                override fun onError(error: String) {
                    loader.dismiss()
                }

                override fun onStart() {
                    loader.show()
                }

            })

        }


        TXT_CITY2.setOnClickListener {
            val params = HashMap<String, Any>()
            params.put("state_id", STATEID2!!)
            EvisionLog.D("## CITY REQ-", Gson().toJson(params))
            onHTTP().POSTCALL(URL.GETCITYS, params, object : OnHttpResponse {
                override fun onSuccess(response: String) {
                    EvisionLog.D("## CITY-", response)
                    loader.dismiss()
                    val selectcity = SlectCityFragment.newInstance(response, "")
                    selectcity.init(object : SlectCityFragment.OnCity {
                        override fun choosecity(city: CITY) {
                            TXT_CITY2.setText(city.city_name)
                            CITYID2 = city.city_id
                            selectcity.dialog.dismiss()
                        }

                    })
                    selectcity.show(supportFragmentManager, "")
                }

                override fun onError(error: String) {
                    loader.dismiss()
                }

                override fun onStart() {
                    loader.show()
                }

            })

        }


        BTN_NEXT.setOnClickListener {
           /* if (EDX_Address.text.trim().isNullOrEmpty()) {
                EDX_Address.hint = "Enter Address"
                EDX_Address.setHintTextColor(Color.RED)
                EDX_Address.requestFocus()
                return@setOnClickListener
            }

            if (EDX_phone.text.isNullOrEmpty()) {
                EDX_phone.setHintTextColor(Color.RED)
                EDX_phone.requestFocus()
                return@setOnClickListener
            }

            if (TXT_STATE.text.isNullOrEmpty()) {
                TXT_STATE.performClick()
                return@setOnClickListener
            }
            if (TXT_CITY.text.isNullOrEmpty()) {
                TXT_CITY.performClick()
                return@setOnClickListener
            }

            customerAddress.fisrt_name = EDX_fname.text.toString()
            customerAddress.last_name = EDX_lname.text.toString()
           //// if(LL_CONF.visibility==View.VISIBLE)
             //   customerAddress.address = EDX_Address2.text.toString()
          //  else
            customerAddress.address = EDX_Address.text.toString()
            customerAddress.address_1 = EDX_Address.text.toString()
            customerAddress.address_2 = EDX_Address.text.toString()
            customerAddress.state_name = TXT_STATE.text.toString()
            customerAddress.city_name = TXT_CITY.text.toString()
            customerAddress.city_id = CITYID!!
            customerAddress.state_id = STATEID!!
            customerAddress.telephone = EDX_phone.text.toString()
            customerAddress.phone_country_code=Ccp.selectedCountryCode
            customerAddress_Shipping=customerAddress

            if (LL_CONF.visibility == View.GONE){
                 customerAddress_billing = CustomerAddressBilling(customerAddress.address,customerAddress.address_1,customerAddress.address_2,customerAddress.city_id,customerAddress.city_name,
                         customerAddress.country_id,customerAddress.country_name,customerAddress.fisrt_name, customerAddress.last_name, customerAddress.state_id,customerAddress.state_name,customerAddress.telephone,customerAddress.phone_country_code)
                startActivity(Intent(this, DeliveryMethodActivity::class.java).putExtra("shipping", customerAddress_Shipping).putExtra("billing", customerAddress_billing).putExtra("is_same", CHECKBOXDIFE.isChecked))
            }

            else {*/
                if (first_name1.text.toString().isNullOrEmpty()) {
                    first_name1.setHintTextColor(Color.RED)
                    first_name1.requestFocus()
                    return@setOnClickListener
                }

                if (last_name2.text.isNullOrEmpty()) {
                    last_name2.setHintTextColor(Color.RED)
                    last_name2.requestFocus()
                    return@setOnClickListener
                }

                if (EDX_Address2.text.isNullOrEmpty()) {
                    EDX_Address2.setHintTextColor(Color.RED)
                    EDX_Address2.requestFocus()
                    return@setOnClickListener
                }
                if (TXT_COUNTRY2.text.isNullOrEmpty()) {
                    TXT_COUNTRY2.setHintTextColor(Color.RED)
                    TXT_COUNTRY2.requestFocus()
                    return@setOnClickListener
                }
                if (TXT_STATE2.text.isNullOrEmpty()) {
                    TXT_STATE2.setHintTextColor(Color.RED)
                    TXT_STATE2.requestFocus()
                    return@setOnClickListener
                }

                if (TXT_CITY2.text.isNullOrEmpty()) {
                    TXT_CITY2.setHintTextColor(Color.RED)
                    TXT_CITY2.requestFocus()
                    return@setOnClickListener
                }

                if (EDX_telephone.text.isNullOrEmpty()) {
                    EDX_telephone.setHintTextColor(Color.RED)
                    EDX_telephone.requestFocus()
                    return@setOnClickListener
                }

                customerAddress_billing = CustomerAddressBilling(EDX_Address2.text.toString(),EDX_Address2.text.toString(),EDX_Address2.text.toString(),
                        CITYID2!!,TXT_CITY2.text.toString(),COUNTRYID2!!, TXT_COUNTRY2.text.toString(),first_name1.text.toString(),last_name2.text.toString(),
                        STATEID2!!,TXT_STATE2.text.toString(), EDX_telephone.text.toString(),Ccp2.selectedCountryCode )

            if (LL_CONF.visibility == View.VISIBLE){
             /*   customerAddress_billing = CustomerAddressBilling(customerAddress.address,customerAddress.address_1,customerAddress.address_2,customerAddress.city_id,customerAddress.city_name,
                        customerAddress.country_id,customerAddress.country_name,customerAddress.fisrt_name, customerAddress.last_name, customerAddress.state_id,customerAddress.state_name,customerAddress.telephone,customerAddress.phone_country_code)*/

                customerAddress.fisrt_name = EDX_fname.text.toString()
                customerAddress.last_name = EDX_lname.text.toString()
                //// if(LL_CONF.visibility==View.VISIBLE)
                //   customerAddress.address = EDX_Address2.text.toString()
                //  else
                customerAddress.address = EDX_Address.text.toString()
                customerAddress.address_1 = EDX_Address.text.toString()
                customerAddress.address_2 = EDX_Address.text.toString()
                customerAddress.state_name = TXT_STATE.text.toString()
                customerAddress.city_name = TXT_CITY.text.toString()
                customerAddress.city_id = CITYID!!
                customerAddress.state_id = STATEID!!
                customerAddress.telephone = EDX_phone.text.toString()
                customerAddress.phone_country_code=Ccp.selectedCountryCode
                customerAddress_Shipping=customerAddress
                startActivity(Intent(this, DeliveryMethodActivity::class.java).putExtra("shipping", customerAddress_Shipping).putExtra("billing", customerAddress_billing).putExtra("is_same", CHECKBOXDIFE.isChecked))
            }
            else{
                customerAddress_Shipping= CustomerAddress(customerAddress_billing.address,customerAddress_billing.address_1,customerAddress_billing.address_2,customerAddress_billing.city_id,customerAddress_billing.city_name,
                        customerAddress_billing.country_id,customerAddress_billing.country_name,customerAddress_billing.fisrt_name,customerAddress_billing.last_name,customerAddress_billing.state_id,customerAddress_billing.state_name,customerAddress_billing.telephone,Ccp.selectedCountryCode)
              //  customerAddress=customerAddress_Shipping
                startActivity(Intent(this, DeliveryMethodActivity::class.java).putExtra("shipping", customerAddress_Shipping).putExtra("billing", customerAddress_billing).putExtra("is_same", CHECKBOXDIFE.isChecked))
            }


            /* customerAddress_billing.fisrt_name = first_name1.text.toString()
             customerAddress_billing.last_name = last_name2.text.toString()
            // if(LL_CONF.visibility==View.VISIBLE)
                // customerAddress_billing.address = EDX_Address2.text.toString()
            // else
                  customerAddress_billing.address = EDX_Address2.text.toString()
             customerAddress_billing.address_1 = EDX_Address2.text.toString()
             customerAddress_billing.address_2 = EDX_Address2.text.toString()
             customerAddress_billing.state_name = TXT_STATE2.text.toString()
             customerAddress_billing.city_name = TXT_CITY2.text.toString()
             customerAddress_billing.city_id = CITYID2!!
             customerAddress_billing.state_id = STATEID2!!
             customerAddress_billing.country_id=COUNTRYID2!!
             customerAddress_billing.telephone = EDX_telephone.text.toString()*/
              //  startActivity(Intent(this, DeliveryMethodActivity::class.java).putExtra("shipping", customerAddress_Shipping).putExtra("billing", customerAddress_billing).putExtra("is_same", CHECKBOXDIFE.isChecked))
           // }

            EvisionLog.D("## SHHS-", Gson().toJson(customerAddress_Shipping))
            EvisionLog.D("## SHHb-", Gson().toJson(customerAddress_billing))
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
