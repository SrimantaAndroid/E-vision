package com.evision.MyAccount.Dilogs


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evision.MyAccount.Dilogs.POJO.CITY

import com.evision.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_slect_city.*
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SlectCityFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SlectCityFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var RECV:RecyclerView

    interface OnCity {
        fun choosecity(city: CITY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var onCity: OnCity
    fun init(onCity: OnCity) {
        this.onCity = onCity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slect_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RECV=view.findViewById(R.id.RECV)
        RECV.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager

        val array = JSONArray(param1)
        if (array.length() <= 0) {
            LLEXTRA.visibility = View.VISIBLE
            BTN_OK.setOnClickListener {
                if (EDX_CITY.text.toString().isNullOrEmpty()) {
                    EDX_CITY.requestFocus()
                    return@setOnClickListener
                }
                val objects = CITY("", EDX_CITY.text.toString())
                onCity.choosecity(objects)
            }
        }

        RECV.adapter = AdapterState(array, onCity)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SlectCityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SlectCityFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    class AdapterState(arra: JSONArray, onsate: OnCity) : RecyclerView.Adapter<AdapterState.Vholder>() {
        val arr = arra
        val onsate = onsate
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vholder {
            return Vholder(LayoutInflater.from(parent.context).inflate(R.layout.textv, parent, false))
        }

        override fun getItemCount(): Int {
            return arr.length()
        }

        override fun onBindViewHolder(holder: Vholder, position: Int) {
            val objects = Gson().fromJson<CITY>(arr[position].toString(), CITY::class.java)
            holder.TXT_ITEM.setText(objects.city_name)
            holder.TXT_ITEM.setOnClickListener {
                onsate.choosecity(objects)

            }
        }

        class Vholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val TXT_ITEM = itemView.findViewById<TextView>(R.id.TXT_ITEM)
        }

    }
}
