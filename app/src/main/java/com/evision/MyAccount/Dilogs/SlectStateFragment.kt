package com.evision.MyAccount.Dilogs


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evision.MyAccount.Dilogs.POJO.STATE

import com.evision.R
import com.evision.Utils.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_slect_state.*
import org.json.JSONArray
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SlectStateFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SlectStateFragment : DialogFragment() {

    interface OnSate {
        fun onChosee(state: STATE)
    }
    // TODO: Rename and change types of parameters
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

    lateinit var onsate: OnSate
    fun INIT(onsate: OnSate) {
        this.onsate = onsate
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view:View=LayoutInflater.from(activity).inflate(R.layout.fragment_slect_state,null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RECV=view.findViewById(R.id.RECV)
        loader = AppDialog(activity!!)
        RECV.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager

        val array = JSONArray(param1)
        RECV.adapter = AdapterState(array, onsate)


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
        fun newInstance(param1: String, param2: String) =
                SlectStateFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }


    class AdapterState(arra: JSONArray, onsate: OnSate) : RecyclerView.Adapter<AdapterState.Vholder>() {
        val arr = arra
        val onsate = onsate
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vholder {
            return Vholder(LayoutInflater.from(parent.context).inflate(R.layout.textv, parent, false))
        }

        override fun getItemCount(): Int {
            return arr.length()
        }

        override fun onBindViewHolder(holder: Vholder, position: Int) {
            val objects = Gson().fromJson<STATE>(arr[position].toString(), STATE::class.java)
            holder.TXT_ITEM.setText(objects.state_name)
            holder.TXT_ITEM.setOnClickListener {
                onsate.onChosee(objects)
            }
        }

        class Vholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val TXT_ITEM = itemView.findViewById<TextView>(R.id.TXT_ITEM)
        }

    }
}
