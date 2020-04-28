package com.evision.MyAccount


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.evision.MyAccount.Dilogs.POJO.CITY
import com.evision.MyAccount.Dilogs.POJO.STATE
import com.evision.MyAccount.Dilogs.SlectCityFragment
import com.evision.MyAccount.Dilogs.SlectStateFragment

import com.evision.R
import com.evision.Utils.*
import kotlinx.android.synthetic.main.fragment_edit_address.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditAddressFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EditAddressFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var STATEID: String? = ""
    private var CITYID: String? = ""
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
        return inflater.inflate(R.layout.fragment_edit_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = AppDialog(activity!!)

        val params = HashMap<String, Any>()
        params.put("customer_id", ShareData(activity!!).getUser()!!.customerId)

        onHTTP().POSTCALL(URL.GETADDRESEDIT, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                loader.dismiss()
                if (JSONObject(response).optInt("status") == 200) {
                    val obj = JSONObject(response).optJSONArray("address_edit").optJSONObject(0)
                    EDX_FN.setText(obj.optString("first_name"))
                    EDX_LN.setText(obj.optString("last_name"))
                    EDX_ADD.setText(obj.optString("address_1"))
                    EDX_ADD2.setText(obj.optString("address_2"))
                    TXT_COUNTRY.setText(obj.optString("country_name"))
                    TXT_STATE.setText(obj.optString("state_name"))
                    TXT_CITY.setText(obj.optString("city_name"))
                    EDX_PIN.setText(obj.optString("pincode"))
                    EDX_MOB.setText(obj.optString("telephone"))
                    CITYID = obj.optString("city_id")
                    STATEID = obj.optString("state_id")
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
                            TXT_CITY.setHint("Choose City")
                            CITYID = ""
                            slectStateFragment.dialog.dismiss()
                        }

                    })
                    slectStateFragment.show(childFragmentManager, "")

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

            onHTTP().POSTCALL(URL.GETCITYS, params, object : OnHttpResponse {
                override fun onSuccess(response: String) {
                    loader.dismiss()
                    val selectcity = SlectCityFragment.newInstance(response, "")
                    selectcity.init(object : SlectCityFragment.OnCity {
                        override fun choosecity(city: CITY) {
                            TXT_CITY.setText(city.city_name)
                            CITYID = city.city_id
                            selectcity.dialog.dismiss()
                        }

                    })
                    selectcity.show(childFragmentManager, "")

                }

                override fun onError(error: String) {
                    loader.dismiss()
                }

                override fun onStart() {
                    loader.show()
                }

            })

        }

        BTN_SAVE.setOnClickListener {
            if (EDX_FN.text.toString().isNullOrEmpty()) {
                EDX_FN.requestFocus()
                EDX_FN.setHintTextColor(Color.RED)
                return@setOnClickListener
            }

            if (EDX_LN.text.toString().isNullOrEmpty()) {
                EDX_LN.requestFocus()
                EDX_LN.setHintTextColor(Color.RED)
                return@setOnClickListener
            }
            if (EDX_ADD.text.toString().isNullOrEmpty()) {
                EDX_ADD.requestFocus()
                EDX_ADD.setHintTextColor(Color.RED)
                return@setOnClickListener
            }
            if (EDX_ADD2.text.toString().isNullOrEmpty()) {
                EDX_ADD2.requestFocus()
                EDX_ADD2.setHintTextColor(Color.RED)
                return@setOnClickListener
            }
            if (TXT_STATE.text.toString().isNullOrEmpty()) {
                TXT_STATE.requestFocus()
                TXT_STATE.setHintTextColor(Color.RED)
                return@setOnClickListener
            }

            if (TXT_CITY.text.toString().isNullOrEmpty()) {
                TXT_CITY.requestFocus()
                TXT_CITY.setHintTextColor(Color.RED)
                return@setOnClickListener
            }

            /* if (EDX_PIN.text.toString().isNullOrEmpty()) {
                 EDX_PIN.requestFocus()
                 EDX_PIN.setHintTextColor(Color.RED)
                 return@setOnClickListener
             }*/

            if (EDX_MOB.text.toString().isNullOrEmpty()) {
                EDX_MOB.requestFocus()
                EDX_MOB.setHintTextColor(Color.RED)
                return@setOnClickListener
            }


            val params = HashMap<String, Any>()
            params.put("customer_id", ShareData(activity!!).getUser()!!.customerId)
            params.put("first_name", EDX_FN.text.toString())
            params.put("last_name", EDX_LN.text.toString())
            params.put("address_1", EDX_ADD.text.toString())
            params.put("address_2", EDX_ADD2.text.toString())
            params.put("country", 169)
            params.put("state", STATEID!!)
            params.put("city", CITYID!!)
            params.put("pincode", EDX_PIN.text.toString())
            params.put("telephone", EDX_MOB.text.toString())

            onHTTP().POSTCALL(URL.UPDATEADDRESS, params, object : OnHttpResponse {
                override fun onSuccess(response: String) {
                    Toast.makeText(activity!!, JSONObject(response).optString("message"), Toast.LENGTH_LONG).show()
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

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                EditAddressFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
