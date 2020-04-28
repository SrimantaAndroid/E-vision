package com.evision.ProductList


import android.graphics.Color
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evision.ProductList.Adapter.AdapterFilter
import com.evision.ProductList.Interface.ManufacturerFilter
import com.evision.ProductList.Pojo.FilterList
import com.evision.R
import com.evision.Utils.AppDialog
import com.evision.Utils.EvisionLog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar
import kotlinx.android.synthetic.main.fragment_filter_bottom_sheet.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FilterBottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FilterBottomSheetFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var arrayFilter=ArrayList<Any>()
    lateinit var loader: AppDialog
    lateinit var manui: ManufacturerFilter
    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }

        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    fun init(manu:ManufacturerFilter)
    {
        manui=manu
    }
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
        return inflater.inflate(R.layout.fragment_filter_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val REC_manufact = view.findViewById<RecyclerView>(R.id.REC_manufact) as RecyclerView
        REC_manufact.layoutManager= LinearLayoutManager(activity)
        val listdata = Gson().fromJson(param1.toString(), FilterList::class.java)
        if (listdata.status == 200) {
            REC_manufact.adapter = AdapterFilter(this, activity!!, listdata.manufacture_list, manui)

        }

        REC_manufact.visibility = View.GONE
        TXT_MENU_BRAND.setBackgroundColor(Color.LTGRAY)
        TXT_MENU_PRICE.setBackgroundColor(Color.WHITE)
        TXT_MENU_PRICE.setOnClickListener {
            RL_PRICE.visibility = View.VISIBLE
            REC_manufact.visibility = View.GONE
            TXT_MENU_BRAND.setBackgroundColor(Color.LTGRAY)
            TXT_MENU_PRICE.setBackgroundColor(Color.WHITE)
        }

        TXT_MENU_BRAND.setOnClickListener {
            RL_PRICE.visibility = View.GONE
            REC_manufact.visibility = View.VISIBLE
            TXT_MENU_BRAND.setBackgroundColor(Color.WHITE)
            TXT_MENU_PRICE.setBackgroundColor(Color.LTGRAY)
        }

        EvisionLog.D("## PRICE-", Gson().toJson(listdata.price_filter[0]))
        if (listdata.price_filter[0].selectmax == null)
            listdata.price_filter[0].selectmax = listdata.price_filter[0].max_price
        if (listdata.price_filter[0].selectmin == null)
            listdata.price_filter[0].selectmin = listdata.price_filter[0].min_price


        /* rangeSeekbar1.setMaxValue(listdata.price_filter[0].max_price.toFloat())
         rangeSeekbar1.setMinValue(listdata.price_filter[0].min_price.toFloat())
         rangeSeekbar1.setMinStartValue(listdata.price_filter[0].selectmin.toFloat())
         rangeSeekbar1.setMaxStartValue(listdata.price_filter[0].selectmax.toFloat())
         rangeSeekbar1.setOnRangeSeekbarChangeListener(object : OnRangeSeekbarChangeListener {
             override fun valueChanged(minValue: Number?, maxValue: Number?) {
                 TXTMin.setText(listdata.price_filter[0].currency + minValue)
                 TXTMax.setText(listdata.price_filter[0].currency + maxValue)
             }

         })*/


        val min = listdata.price_filter[0].min_price.toFloat().toInt()
        val max = listdata.price_filter[0].max_price.toFloat().toInt()

        val selectMin = listdata.price_filter[0].selectmin.toFloat().toInt()
        val selectMax = listdata.price_filter[0].selectmax.toFloat().toInt()
        EvisionLog.D("## DAT", " min- $min max- $max  selectmin-$selectMin selectmax-$selectMax")

        // Setup the new range seek bar
        val rangeSeekBar = RangeSeekBar<Int>(activity)
        // Set the range
        rangeSeekBar.setRangeValues(min, max)
        rangeSeekBar.setSelectedMinValue(selectMin)
        rangeSeekBar.setSelectedMaxValue(selectMax)
        TXTMin.setText(listdata.price_filter[0].currency + selectMin)
        TXTMax.setText(listdata.price_filter[0].currency + selectMax)

//        rangeSeekBar.setRangeValues(0,201)
//        rangeSeekBar.setSelectedMaxValue(130)
//        rangeSeekBar.setSelectedMinValue(30)
        seekbar_placeholder.addView(rangeSeekBar)
        rangeSeekBar.setOnRangeSeekBarChangeListener(object : RangeSeekBar.OnRangeSeekBarChangeListener<Int> {
            override fun onRangeSeekBarValuesChanged(bar: RangeSeekBar<*>?, minValue: Int?, maxValue: Int?) {
                TXTMin.setText(listdata.price_filter[0].currency + minValue)
                TXTMax.setText(listdata.price_filter[0].currency + maxValue)
            }


        })

        BTN_CANCEL.setOnClickListener {
            dismiss()
        }
        BTN_APPLY.setOnClickListener {
            for (i in listdata.manufacture_list) {
                if (i.isselect) {
                    EvisionLog.D("## SS-", i.manufacture_name)
                }
            }
            listdata.price_filter[0].selectmax = rangeSeekBar.selectedMaxValue.toString()
            listdata.price_filter[0].selectmin = rangeSeekBar.selectedMinValue.toString()
            EvisionLog.D("## EXACT-", Gson().toJson(listdata))
            manui.onItemSelect(Gson().toJson(listdata))
            dismiss()
        }




    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FilterBottomSheetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FilterBottomSheetFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
