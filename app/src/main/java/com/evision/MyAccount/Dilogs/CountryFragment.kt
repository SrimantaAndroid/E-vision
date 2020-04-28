package com.evision.MyAccount.Dilogs


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evision.MyAccount.Dilogs.POJO.COUNTRY
import com.evision.R
import com.evision.Utils.AppDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_slect_state.*
import org.json.JSONArray
private  val ARG_PARAM1 = "param1"
private  val ARG_PARAM2 = "param2"
class CountryFragment: DialogFragment() {

    interface OnSate {
        fun onChosee(country: COUNTRY)
    }

    private var param1: String? = null
    private var param2: String? = null
    lateinit var loader: AppDialog
    lateinit var RECV:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var onsate: CountryFragment.OnSate
    fun INIT(onsate: CountryFragment.OnSate) {
        this.onsate = onsate
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slect_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RECV=view.findViewById(R.id.RECV)
        loader = AppDialog(activity!!)
        RECV.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager

        val array = JSONArray(param1)
        RECV.adapter = CountryFragment.AdapterState(array, onsate)


    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SlectStateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = CountryFragment().apply {
            arguments=Bundle().apply { putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)}
        }

    }
    class AdapterState(arra: JSONArray, onsate: CountryFragment.OnSate) : RecyclerView.Adapter<AdapterState.Vholder>() {
        val arr = arra
        val onsate = onsate
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vholder {
            return Vholder(LayoutInflater.from(parent.context).inflate(R.layout.textv, parent, false))
        }

        override fun getItemCount(): Int {
            return arr.length()
        }

        override fun onBindViewHolder(holder: Vholder, position: Int) {
            val objects = Gson().fromJson<COUNTRY>(arr[position].toString(), COUNTRY::class.java)
            holder.TXT_ITEM.setText(objects.country_name)
            holder.TXT_ITEM.setOnClickListener {
                onsate.onChosee(objects)
            }
        }

        class Vholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val TXT_ITEM = itemView.findViewById<TextView>(R.id.TXT_ITEM)
        }

    }
}

