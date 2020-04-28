package com.evision.Login_Registration

import android.app.Activity
import android.graphics.Color

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.evision.Login_Registration.Pojo.LoginResponse
import com.evision.R
import com.evision.Utils.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject

class SignUpActivity : AppCompatActivity() {
    lateinit var loader: AppDialog
    lateinit var shareData: ShareData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        loader = AppDialog(this)
        shareData = ShareData(this)
        CreateAc.setOnClickListener {
            if (Edx_fname.text.toString().trim().isEmpty()) {
                Edx_fname.requestFocus()
                Edx_fname.setHintTextColor(Color.RED)
                return@setOnClickListener
            }
            if (EDX_lname.text.toString().trim().isEmpty()) {
                EDX_lname.requestFocus()
                EDX_lname.setHintTextColor(Color.RED)
                return@setOnClickListener
            }
            if (EDX_email.text.toString().trim().isEmpty()) {
                EDX_email.requestFocus()
                EDX_email.setHintTextColor(Color.RED)
                return@setOnClickListener
            }
            if (!Validation.isValidEmail(EDX_email.text.toString())) {
                EDX_email.requestFocus()
                EDX_email.setError(resources.getString(R.string.entervalidemail))
                return@setOnClickListener
            }
            if (EDX_mob.text.toString().trim().isEmpty()) {
                EDX_mob.requestFocus()
                EDX_mob.setHintTextColor(Color.RED)
                return@setOnClickListener
            }
            if (EDX_password.text.toString().trim().isEmpty()) {
                EDX_password.requestFocus()
                EDX_password.setHintTextColor(Color.RED)
                return@setOnClickListener
            }
            Req_Register()
        }

        Login.setOnClickListener {
            finish()
        }
    }

    private fun Req_Register() {

        val params = HashMap<String, Any>()
        params.put("first_name", Edx_fname.text.toString())
        params.put("last_name", EDX_lname.text.toString())
        params.put("email", EDX_email.text.toString())
        params.put("password", EDX_password.text.toString())
        params.put("telephone", EDX_mob.text.toString())

        onHTTP().POSTCALL(URL.REGISTERNEWACC, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                loader.dismiss()
                val resp = Gson().fromJson(response, LoginResponse::class.java)

                EvisionLog.E("## REG-", response)

                Toast.makeText(this@SignUpActivity,resp.message,Toast.LENGTH_LONG).show()

                if (resp.status ==200) {
                    shareData.SetUserData(response)
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }

            override fun onError(error: String) {
                loader.dismiss()
                Toast.makeText(this@SignUpActivity, error, Toast.LENGTH_LONG).show()
            }

            override fun onStart() {
                loader.show()
            }

        })

    }
}
