package com.evision.CartManage

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.braintreepayments.cardform.view.CardForm
import com.evision.CartManage.Adapter.AdapterCartCheckout
import com.evision.MyAccount.Pojo.StoreOrderPlaceResponse
import com.evision.R
import com.evision.Utils.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.sss.*
import org.json.JSONObject
import android.widget.PopupWindow
import com.evision.CartManage.Adapter.CardListAdapter
import com.evision.CartManage.Adapter.OnitemClick
import com.evision.CartManage.Pojo.*
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment

import java.util.*
import kotlin.collections.HashMap
import android.app.Activity

import android.view.inputmethod.InputMethodManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText


class PaymentActivity : AppCompatActivity() {
    var DeliveryType = ""
    var DeliverySubType = ""
    var CUPONCODE = ""
    var CUPONPRICE = ""
    var DISCOUNTFOR=""
    var PRODUCT_IDS=""
    var is_same = false
    var expirationMonth=""
    var expirationYear=""
    lateinit var loader: AppDialog
    lateinit var cardv: CardForm
    lateinit var order_coupon:LinearLayout
    lateinit var customerAddress: CustomerAddress
    lateinit var customerAddress_billing: CustomerAddressBilling
    lateinit var adapterCart: AdapterCartCheckout
    lateinit var ordertotal: OrderTotal
    lateinit var RECV:RecyclerView
    lateinit var til_expairdate:TextInputEditText
    lateinit var til_card_no:TextInputEditText
    lateinit var cvv:TextInputEditText

    var cardtypelist= arrayOf("American express","uuuyuqyqeryqwury","whqudhoqwuyroi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sss)
        var toolbar=findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        DeliverySubType = intent.getStringExtra("delivery_sub_type")
        DeliveryType = intent.getStringExtra("delivery_type")
        customerAddress = intent.getParcelableExtra("shipping")
        customerAddress_billing = intent.getParcelableExtra("billing")
        is_same = intent.getBooleanExtra("is_same", false)
        loader = AppDialog(this)
        toolbar.setTitle(R.string.title_add_toocart)
        toolbar.setTitleTextColor(Color.WHITE)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_white_back)
        RECV=findViewById(R.id.RECV)
        til_expairdate=findViewById(R.id.til_expairdate)
        til_card_no=findViewById(R.id.til_card_no)
        cvv=findViewById(R.id.cvv)
      //  til_card_no.addTextChangedListener(FourDigitCardFormatWatcher())
        order_coupon=findViewById(R.id.order_coupon)
        RECV.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
       // cardv = findViewById(R.id.CARD_view) as CardForm
      //  val adapter = ArrayAdapter<String>(this,android.R.layout.select_dialog_item, cardtypelist)
        //tv_cardtype.setThreshold(1) //will start working from first character
       // tv_cardtype.setAdapter(adapter)
        tv_cardtype.setOnClickListener {
            openpopupdrop_downfoeweight()
        }

       /* cardv.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(false)

//                .postalCodeRequired(true)
//                .mobileNumberRequired(true)
//                .mobileNumberExplanation("SMS is required on this number")
                .actionLabel("Purchase")
                .setup(this)
        cardv.visibility = View.GONE*/
        RG_PAY.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.RB_CARD -> {
                   // cardv.visibility = View.VISIBLE
                    llcardholdername.visibility=View.VISIBLE
                    llcard_view.visibility=View.VISIBLE
                }
                R.id.RB_store -> {
                   // cardv.visibility = View.GONE
                    llcardholdername.visibility=View.GONE
                    llcard_view.visibility=View.GONE
                }
            }
        }


        loadData()
        RECV.layoutManager = LinearLayoutManager(this)

        EDX_apply.setOnClickListener {
            if (EDX_COUPON.text.toString().isEmpty()) {
                EDX_COUPON.setHintTextColor(Color.RED)
                return@setOnClickListener
            }

            UPDATECUPON(EDX_COUPON.text.toString())
        }

        BTN_pay.setOnClickListener {

               ORDERPLACE()
        }


        til_expairdate.setOnClickListener {
          //  til_expairdate.setShowSoftInputOnFocus(false);
            hideSoftKeyboard(this)
            showmonthyeardialog();

        }

    }

    private fun showmonthyeardialog() {
        val calendar = Calendar.getInstance()
        val yearSelected: Int
        val monthSelected: Int

//Set default values
       // val calendar = Calendar.getInstance()
        yearSelected = calendar.get(Calendar.YEAR)
        monthSelected = calendar.get(Calendar.MONTH)
        calendar.clear()
        calendar.set(yearSelected, monthSelected, 1) // Set minimum date to show in dialog
        val minDate = calendar.getTimeInMillis() // Get milliseconds of the modified date

        calendar.clear()
        calendar.set(2048, 11, 31) // Set maximum date to show in dialog
        val maxDate = calendar.getTimeInMillis() // Get milliseconds of the modified date

// Create instance with date ranges values
        val dialogFragment = MonthYearPickerDialogFragment
                .getInstance(monthSelected, yearSelected, minDate, maxDate)

        dialogFragment.show(supportFragmentManager, null)

        dialogFragment.setOnDateSetListener { year, monthOfYear ->
            expirationMonth=formatnumber(monthOfYear)
            expirationYear=year.toString()
            til_expairdate.setText(formatnumber(monthOfYear+1)+"/"+year.toString())
            // do something
        }
    }
   private fun formatnumber(number:Int): String{
       return  String.format("%02d", number);

   }
    fun hideSoftKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    private fun openpopupdrop_downfoeweight() {
        val popUpView =this.getLayoutInflater()?.inflate(R.layout.drop_down_layout, null)
        val  listPopupWindow = PopupWindow(popUpView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true
                // ViewGroup.LayoutParams.WRAP_CONTENT, false
        )
        val reclyerview_dropdown: RecyclerView =popUpView!!.findViewById(R.id.reclyerview_dropdown)
        val cartlistadapter:CardListAdapter=CardListAdapter(cardtypelist,this,object :OnitemClick{
            override fun OnClickItem(position: Int) {
                super.OnClickItem(position)
                tv_cardtype.setText(cardtypelist.get(position))
                listPopupWindow.dismiss()
            }

        })
        reclyerview_dropdown?.layoutManager= LinearLayoutManager(this)
        reclyerview_dropdown?.adapter=cartlistadapter
        listPopupWindow?.showAsDropDown(tv_Card);

    }


    private fun ORDERPLACE() {
        /*if (cardv.visibility == View.VISIBLE) {
            if (cardv.cardEditText.text.isNullOrEmpty()) {
                cardv.cardEditText.requestFocus()
                return
            }

            if (cardv.expirationDateEditText.text.isNullOrEmpty()) {
                cardv.expirationDateEditText.requestFocus()
                return
            }
            if (cvvnumber.visibility==View.VISIBLE) {
                if (cvv.text.toString().equals("")){
                    cvv.requestFocus()
                    return
                }

            }
           *//* if (cardv.cvvEditText.text.isNullOrEmpty()) {
                cardv.cvvEditText.requestFocus()
                return
            }*//*
        }*/
        if (llcard_view.visibility == View.VISIBLE) {
            if (til_card_no.text.isNullOrEmpty()) {
                til_card_no.requestFocus()
                return
            }
            if (til_card_no.text!!.length<16) {
                til_card_no.requestFocus()
                return
            }

            if (til_expairdate.text.isNullOrEmpty()) {
                til_expairdate.requestFocus()
                return
            }
            if (cvvnumber.visibility==View.VISIBLE) {
                if (cvv.text.toString().equals("")){
                    cvv.requestFocus()
                    return
                }

            }
            /* if (cardv.cvvEditText.text.isNullOrEmpty()) {
                 cardv.cvvEditText.requestFocus()
                 return
             }*/
        }

        val params = HashMap<String, Any>()
        params.put("customer_id", ShareData(this).getUser()!!.customerId)
        params.put("first_name", customerAddress.fisrt_name)
        params.put("last_name", customerAddress.last_name)
        params.put("address_1", customerAddress.address)
        params.put("address_2", customerAddress.address)
        params.put("city_id", customerAddress.city_id)
        params.put("state_id", customerAddress.state_id)
        params.put("country_id", 169)
        params.put("telephone", customerAddress.telephone)
//        params.put("address_ship", if (is_same) 1 else 0)
        params.put("address_ship", 1)
//        if (is_same)
//        {
        params.put("shipping_address", customerAddress_billing.address)
        params.put("city_ship", customerAddress_billing.city_id)
        params.put("state_ship", customerAddress_billing.state_id)
        params.put("country_ship", customerAddress_billing.country_id)
        params.put("telephone_ship", customerAddress_billing.telephone)
//        }

        params.put("zone", if (DeliveryType.equals("delivery")) DeliverySubType.split(" ")[1] else "")
        params.put("delivery_cost", ordertotal.delivery)
        params.put("delivery_type", DeliveryType)
        params.put("pickup_id", if (DeliveryType.equals("delivery")) "1" else DeliverySubType)

        params.put("discount_for",DISCOUNTFOR)
        params.put("product_ids", PRODUCT_IDS)
        params.put("discount_amount",CUPONPRICE)

        if (llcard_view.visibility == View.VISIBLE)
           params.put("payment_method","online")
        else
            params.put("payment_method","offline")

        EvisionLog.D("## URL-", URL.ORDERPALCEINSTORE)
        EvisionLog.D("## REQ PLACE-", Gson().toJson(params))
        onHTTP().POSTCALL(URL.ORDERPALCEINSTORE, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                EvisionLog.D("## ORDER RESPONSE-", response)
                val data = Gson().fromJson(response, StoreOrderPlaceResponse::class.java)
                Toast.makeText(this@PaymentActivity, data.message, Toast.LENGTH_SHORT).show()
                if (data.code == 200) {
                    val orderID = JSONObject(response).optString("order_id")
                    if (llcard_view.visibility == View.VISIBLE) {
                        /**** TEMPORARY DATA *******/
                        var logindata = ShareData(this@PaymentActivity).getUser()
                        logindata!!.cartCount = 0
                        ShareData(this@PaymentActivity).SetUserData(Gson().toJson(logindata).toString())
                        /***** ***************/
                        val URL = URL.BASE + "checkout/payment.php?order_id=" + orderID + "&ccnumber="+til_card_no.text.toString()+"&ccexp="+expirationMonth+expirationYear+"&cvv="+cvv.text.toString()
                        startActivity(Intent(this@PaymentActivity, PaymentCeditCardActivity::class.java).putExtra("loaderURL", URL))
                        finish()
                    } else {
                        var logindata = ShareData(this@PaymentActivity).getUser()
                        logindata!!.cartCount = 0
                        ShareData(this@PaymentActivity).SetUserData(Gson().toJson(logindata).toString())
                        val inte = Intent(this@PaymentActivity, OrderSucessActivity::class.java).putExtra("response", response)
                        inte.putExtra("callurl", false)
                        inte.putExtra("data", response)
                        startActivity(inte)
                        finish()
                    }
                } else {
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

    private fun UPDATECUPON(coupon: String) {
        val params = HashMap<String, Any>()
        params.put("customer_id", ShareData(this).getUser()!!.customerId)
        params.put("coupon_code", coupon)
        onHTTP().POSTCALL(URL.UPDATECOUPN, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                EvisionLog.D("## DATA-", response)
                if (JSONObject(response).has("message")) {
                    Toast.makeText(this@PaymentActivity, JSONObject(response).optString("message"), Toast.LENGTH_SHORT).show()
                    CUPONCODE=JSONObject(response).optString("coupon_code")
                    CUPONPRICE=JSONObject(response).optString("discount_amount")
                    DISCOUNTFOR=JSONObject(response).optString("discount_for")
                    PRODUCT_IDS=JSONObject(response).optString("proids")
                }
                val data = Gson().fromJson(response, CartResponse::class.java)

                loadData()
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

    fun loadData() {
        val params = HashMap<String, Any>()
        params.put("customer_id", ShareData(this).getUser()!!.customerId)
        if(DeliverySubType.equals("Zone A"))
          params.put("delivery_type", "A")
        else if(DeliverySubType.equals("Zone B"))
            params.put("delivery_type", "B")
        else
            params.put("delivery_type", "A")
        params.put("coupon_code", CUPONCODE)
        params.put("discount_for",DISCOUNTFOR)
        params.put("product_ids",PRODUCT_IDS)
        params.put("coupon_amount", CUPONPRICE)
        onHTTP().POSTCALL(URL.GETREVIEW, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                EvisionLog.D("## DATA DELIVERY-", response)
                val data = Gson().fromJson(response, CHECKOUTDATA::class.java)
                if (data.status == 400)
                    finish()
                else {
                   // if (RECV.adapter == null) {
                        adapterCart = AdapterCartCheckout(this@PaymentActivity, data.order_review, loader)
                        RECV.adapter = adapterCart
                   // }
                    adapterCart.notifyDataSetChanged()
                    ordertotal = data.order_totals[0]
//                    TXT_qtyno.setText("" + data.cart_count + "Qty")
                    subtotal.setText(data.order_totals[0].currency + data.order_totals[0].subtotal)
                    TACpercent.setText("" + data.order_totals[0].tax_name)
                    tax.setText(data.order_totals[0].currency + data.order_totals[0].tax)
                    tv_delivery.setText("Delivery Charge "+"( "+data.order_totals[0].delivery_type+" )")
                    tv_deliveryamount.setText(data.order_totals[0].currency + data.order_totals[0].delivery)
                    TOTAL.setText(data.order_totals[0].currency + data.order_totals[0].grand_total)

                    if (DISCOUNTFOR.equals("orders")){
                        order_coupon.visibility=View.VISIBLE
                        tv_couponamount.setText(CUPONPRICE)
                    }else
                        order_coupon.visibility=View.GONE
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


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class CreditCardNumberFormattingTextWatcher(val tilCardNo: TextInputEditText) : TextWatcher {
       var  len:Int = 0
        var lock:Boolean=false
       var space = ' ';
        override fun afterTextChanged(s: Editable?) {
           /* var str: String = tilCardNo.getText().toString()
            if (lock || str.length > 16) {
                return;
            }
            lock = true;
            for ( i in 4 until str.length step  5){
                if (s.toString().get(i) != ' ') {
                    s!!.insert(i, " ");
                }
            }
            lock = false;*/
           /* if (s!!.length > 0 && (s.length % 5) == 0) {
             var c:Char = s!!.get(s.length - 1);
            if (space == c) {
                s.delete(s.length - 1, s.length);
            }
        }
        // Insert char where needed.
        if (s.length > 0 && (s.length % 5) == 0) {
            val c :Char= s.get(s.length - 1);
            // Only if its a digit where there should be a space we insert a space
            if (Character.isDigit(c) && TextUtils.split(s.toString(), space.toString()).size <= 3) {
                s.insert(s.length - 1, space.toString());
            }
        }
*/
          /*  if((str.length==5 && len <str.length) || (str.length==10 && len <str.length) || (str.length==15 && len <str.length)){
                tilCardNo.append(" "); //append space
            }*/
     }


        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
           // var str :String= tilCardNo.getText().toString()
                         //len = str.length
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    }
}
