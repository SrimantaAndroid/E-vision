package com.evision.CartManage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.evision.MyAccount.MyAccActivity
import com.evision.R
import com.evision.Utils.AppDialog
import com.evision.Utils.EvisionLog
import com.evision.Utils.OnHttpResponse
import com.evision.Utils.onHTTP
import com.evision.mainpage.MainActivity
import kotlinx.android.synthetic.main.activity_order_sucess.*
import org.json.JSONObject

class OrderSucessActivity : AppCompatActivity() {
    lateinit var loader: AppDialog
    var response = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_sucess)
        loader = AppDialog(this)



        if (intent.getBooleanExtra("callurl", false) == false) {
            EvisionLog.D("## ORDER ID-", intent.getStringExtra("data"))
            setViewData(intent.getStringExtra("data"))
        } else {
            onHTTP().GETCALL(intent.getStringExtra("data"), object : OnHttpResponse {
                override fun onSuccess(response: String) {
                    setViewData(response)
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

    private fun setViewData(stringExtra: String?) {
        EvisionLog.D("## ORDER ID-", stringExtra)
        val OBJECT = JSONObject(stringExtra)
        TXT_ORDERID.setText(resources.getString(R.string.orderno_) + " #" + OBJECT.optString("order_id"))
        TXT_ORDERSTATUS.setText(resources.getString(R.string.orderstatus)+" # " +OBJECT.optString("payment_status"))
        TXT_CONTINUTE1.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }

        TXT_TRACK1.setOnClickListener {
            startActivity(Intent(this, MyAccActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }

    }
}
