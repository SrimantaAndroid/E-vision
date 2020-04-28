package com.evision.CartManage

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.evision.Utils.EvisionLog
import com.evision.Utils.URL
import com.evision.mainpage.MainActivity
import kotlinx.android.synthetic.main.activity_payment_cedit_card.*


class PaymentCeditCardActivity : AppCompatActivity() {

    override fun onBackPressed() {
//        super.onBackPressed()
        startActivity(Intent(this@PaymentCeditCardActivity, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        finish()
    }

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.evision.R.layout.activity_payment_cedit_card)
        val url = intent.getStringExtra("loaderURL")
        WEBV.settings.javaScriptEnabled = true
        WEBV.getSettings().setLoadWithOverviewMode(true)
        WEBV.getSettings().setUseWideViewPort(true)
        WEBV.getSettings().setBuiltInZoomControls(true)
        WEBV.getSettings().setJavaScriptEnabled(true)
        WEBV.addJavascriptInterface(JavaScriptInterface(this), "HtmlViewer")

        WEBV.setWebViewClient(WebViewClient())

        WEBV.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                EvisionLog.D("## UORL-", url)
                if (url!!.startsWith(URL.BASE)) {
                    val inte = Intent(this@PaymentCeditCardActivity, OrderSucessActivity::class.java)
                    inte.putExtra("callurl", true)
                    inte.putExtra("data", url)
                    startActivity(inte)
                    finish()
                } else
                    WEBV.loadUrl(url)
                return false
//                if(Uri.parse(url).host=="www.example.com")
//                {
//                    return false
//                }
//
//                val intent= Intent(Intent.ACTION_VIEW,Uri.parse(url))
//                startActivity(intent)
//                return true

            }
        }

        WEBV.loadUrl(url)

    }

    inner class JavaScriptInterface internal constructor(internal var mContext: Context) {

        fun showToast(toast: String) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
        }
    }
}
