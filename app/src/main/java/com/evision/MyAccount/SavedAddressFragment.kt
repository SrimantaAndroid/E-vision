package com.evision.MyAccount


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evision.MyAccount.Adapter.AdapterAddress

import com.evision.R
import com.evision.Utils.*
import kotlinx.android.synthetic.main.fragment_saved_address.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SavedAddressFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var loader: AppDialog
    lateinit var RecV:RecyclerView
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
        return inflater.inflate(R.layout.fragment_saved_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = AppDialog(activity!!)
        RecV=view.findViewById(R.id.RecV)
        val params = HashMap<String, Any>()
        params.put("customer_id", ShareData(activity!!).getUser()!!.customerId)

        onHTTP().POSTCALL(URL.GETADDRES, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                loader.dismiss()
                if (JSONObject(response).optInt("status") == 200) {
                    val obj = JSONObject(response).optJSONArray("customer_address").optJSONObject(0)
                    val arra = ArrayList<JSONObject>()
                    arra.add(obj)
                    RecV.layoutManager = LinearLayoutManager(activity)
                    val adapter = AdapterAddress(activity!!, arra)
                    RecV.adapter = adapter
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



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SavedAddressFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
