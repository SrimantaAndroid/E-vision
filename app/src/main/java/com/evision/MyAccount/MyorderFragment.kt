package com.evision.MyAccount


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evision.MyAccount.Adapter.AdapterMyOrderList
import com.evision.MyAccount.Pojo.MyOrder

import com.evision.R
import com.evision.Utils.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_myorder.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [MyorderFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MyorderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var REC_ORDER: RecyclerView
    lateinit var loader: AppDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        loader = AppDialog(activity!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_myorder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        REC_ORDER = view.findViewById(R.id.REC_ORDER) as RecyclerView
        REC_ORDER.layoutManager = LinearLayoutManager(activity)

        val params = HashMap<String, Any>()
        params.put("customer_id", ShareData(activity!!).getUser()!!.customerId)

        onHTTP().POSTCALL(URL.MYORDERLIST, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                loader.dismiss()
                if (JSONObject(response).optInt("status") == 200) {
                    TXT_NOPRODUCT.visibility = View.GONE
                    val myoredr = Gson().fromJson(response, MyOrder::class.java)
                    REC_ORDER.adapter = AdapterMyOrderList(this@MyorderFragment, myoredr,activity!!)
                } else {
                    TXT_NOPRODUCT.visibility = View.VISIBLE
                    TXT_NOPRODUCT.setText(JSONObject(response).optString("message"))
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                MyorderFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
