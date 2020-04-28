package com.evision.MyAccount


import android.graphics.Color
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.evision.R
import com.evision.Utils.*
import kotlinx.android.synthetic.main.fragment_edit.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EditFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var loader: AppDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = AppDialog(activity!!)
        val params = HashMap<String, Any>()
        params.put("customer_id", ShareData(activity!!).getUser()!!.customerId)

        onHTTP().POSTCALL(URL.EDITPROFILE, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                loader.dismiss()
                if (JSONObject(response).optInt("status") == 200) {
                    val obj = JSONObject(response).optJSONArray("profile_edit").optJSONObject(0)
                    EDX_NAME.setText(obj.optString("first_name"))
                    EDX_lname.setText(obj.optString("last_name"))
                    EDX_email.setText(obj.optString("email"))
                    EDX_MOB.setText(obj.optString("telephone"))
                } else {

                }

            }

            override fun onError(error: String) {
                loader.dismiss()
            }

            override fun onStart() {
                loader.show()
            }

        })


        BTN_UPDATE.setOnClickListener {

            if (EDX_NAME.text.toString().isNullOrEmpty()) {
                EDX_NAME.requestFocus()
                EDX_NAME.setHintTextColor(Color.RED)
                return@setOnClickListener
            }
            if (EDX_lname.text.toString().isNullOrEmpty()) {
                EDX_lname.requestFocus()
                EDX_lname.setHintTextColor(Color.RED)
                return@setOnClickListener
            }
            if (EDX_email.text.toString().isNullOrEmpty()) {
                EDX_email.requestFocus()
                EDX_email.setHintTextColor(Color.RED)
                return@setOnClickListener
            }
            if (EDX_MOB.text.toString().isNullOrEmpty()) {
                EDX_MOB.requestFocus()
                EDX_MOB.setHintTextColor(Color.RED)
                return@setOnClickListener
            }
            if (PWD1.text.toString().isNotEmpty()) {
                if (PWD2.text.toString().isNullOrEmpty()) {
                    PWD2.requestFocus()
                    PWD2.setHintTextColor(Color.RED)
                    return@setOnClickListener
                }

                if (PWD2.text.toString() != PWD1.text.toString()) {
                    PWD2.error = "Password are not same."
                    return@setOnClickListener
                }
            }



            params.put("customer_id", ShareData(activity!!).getUser()!!.customerId)
            params.put("first_name", EDX_NAME.text.toString())
            params.put("last_name", EDX_lname.text.toString())
            params.put("telephone", EDX_MOB.text.toString())
            params.put("password", PWD2.text.toString())

            onHTTP().POSTCALL(URL.UPDATEPROFILE, params, object : OnHttpResponse {
                override fun onSuccess(response: String) {
                    loader.dismiss()
                    Toast.makeText(activity!!, JSONObject(response).optString("message"), Toast.LENGTH_LONG).show()
                    if (JSONObject(response).optInt("status") == 200) {

                    } else {

                    }

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

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                EditFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
