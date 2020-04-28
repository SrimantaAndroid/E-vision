package com.evision.ContactUs

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle

import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.evision.R
import com.evision.Utils.*
import kotlinx.android.synthetic.main.activity_contact_us.*
import kotlinx.android.synthetic.main.content_contact_us.*
import org.json.JSONObject
import androidx.appcompat.widget.Toolbar;

class ContactUsActivity : AppCompatActivity() {
    lateinit var loader: AppDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        var toolbar:Toolbar=findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.contact_us)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_white_back)
        loader = AppDialog(this)

        fbshare.setClickable(true);
        fbshare.setMovementMethod(LinkMovementMethod.getInstance());
        img_facebook.setOnClickListener {
            //String fburl=""
            var url:String = "https://www.facebook.com/EVision";
            var i:Intent=  Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        img_instragram.setOnClickListener {
            var url:String = "https://www.instagram.com/evisionpanama/";
            var i:Intent=  Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        }
        img_twitter.setOnClickListener {
            var url:String = "https://twitter.com/evisionstore";
            var i:Intent=  Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        }
        img_whatsapp.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=+507-6444-7679&text= Guarda nuestro número en tus contactos para recibir nuestra newsletter. Y después envía este mensaje para comenzar."
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)

        }
       // String text ="<a href='http://www.google.com'> Google </a>";
      //  fbshare.setText(Html.fromHtml("\"<a href='http://www.google.com'> Google </a>\";"));

        BTN_SAVE.setOnClickListener {

            if (EDX_NAME.text.isNullOrEmpty()) {
                EDX_NAME.setHintTextColor(Color.RED)
                EDX_NAME.requestFocus()
                return@setOnClickListener
            }

            if (EDX_EMAIL.text.isNullOrEmpty()) {
                EDX_EMAIL.setHintTextColor(Color.RED)
                EDX_EMAIL.requestFocus()
                return@setOnClickListener
            }

            if (Validation.isValidEmail(EDX_EMAIL.text.toString()) === false) {
                EDX_EMAIL.requestFocus()
                EDX_EMAIL.setError(resources.getString(R.string.entervalidemail))
                return@setOnClickListener
            }
            if (EDX_ph.text.isNullOrEmpty()) {
                EDX_ph.setHintTextColor(Color.RED)
                EDX_ph.requestFocus()
                return@setOnClickListener
            }

            if (EDX_question.text.isNullOrEmpty()) {
                EDX_question.setHintTextColor(Color.RED)
                EDX_question.requestFocus()
                return@setOnClickListener
            }


            val params = HashMap<String, Any>()
            params.put("name", EDX_NAME.text.toString())
            params.put("email", EDX_EMAIL.text.toString())
            params.put("telephone", EDX_ph.text.toString())
            params.put("message", EDX_question.text.toString())

            onHTTP().POSTCALL(URL.POSTCONTACTUS, params, object : OnHttpResponse {
                override fun onSuccess(response: String) {
                    loader.dismiss()
                    EDX_NAME.setText("")
                    EDX_EMAIL.setText("")
                    EDX_ph.setText("")
                    EDX_question.setText("")
                    EDX_NAME.requestFocus()
                    Toast.makeText(this@ContactUsActivity, JSONObject(response).optString("message"), Toast.LENGTH_LONG).show()
                    EvisionLog.D("## RESPONSE-", response)
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
