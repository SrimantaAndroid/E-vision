package com.evision.Dashboard


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.evision.Dashboard.Adapter.AdapterBrandViewPager
import com.evision.Dashboard.Adapter.AdapterHomeBrand
import com.evision.Dashboard.Adapter.AdapterHotCategory
import com.evision.Dashboard.Adapter.AdapterTopBannerViewPager
import com.evision.Dashboard.Pojo.Dasboard

import com.evision.R
import com.evision.Search.SearchFragment
import com.evision.Utils.AppDialog
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import com.pixelcan.inkpageindicator.InkPageIndicator
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.util.*
import android.os.Handler
import android.widget.TextView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DashboardFragment : Fragment(),DasboardContract.View {
    lateinit var RecV:RecyclerView
  //  lateinit var rec_homebrands:RecyclerView
    override fun preparepage(Hotlist: Dasboard) {
        dialogloader.dismiss()
        val adapter = AdapterTopBannerViewPager(this.activity!!, Hotlist.top_banner)
        viewpager.adapter = adapter
        val inkPageIndicator = view!!.findViewById(R.id.indicator) as InkPageIndicator
        inkPageIndicator.setViewPager(viewpager)
        categoryadapter = AdapterHotCategory(Hotlist.hot_category, activity!!)
        RecV.adapter=categoryadapter

      //  val adapterHomeBrand=AdapterHomeBrand(Hotlist.home_brands,this.activity!!)
      //  val layoutmanager=LinearLayoutManager(this.activity!!,LinearLayoutManager.HORIZONTAL,false)
      //  rec_homebrands.layoutManager=layoutmanager!!
       // rec_homebrands.adapter=adapterHomeBrand
        if (Hotlist.home_brands.size>0) {
            tv_brand.visibility=View.VISIBLE
            val brandViewPager = AdapterBrandViewPager(this.activity!!, Hotlist.home_brands)
            ViewPager_brands.adapter = brandViewPager

            val indicator_band = view!!.findViewById<InkPageIndicator>(R.id.indicator_band)
            indicator_band.setViewPager(ViewPager_brands)
            val handler = Handler()
            val Update = Runnable {
                if (currentPage === Hotlist.home_brands.size - 1) {
                    currentPage = 0
                }
                ViewPager_brands.setCurrentItem(currentPage++, true)
            }

            timer = Timer() // This will create a new Thread
            timer!!.schedule(object : TimerTask() { // task to be scheduled
                override fun run() {
                    handler.post(Update)
                }
            }, DELAY_MS, PERIOD_MS)

        }
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
     lateinit var categoryadapter: AdapterHotCategory
    private lateinit var presenter:DashboardPresenterImple
    lateinit var viewpager: ViewPager
    lateinit var ViewPager_brands:ViewPager

    lateinit var dialogloader: AppDialog
    var currentPage = 0
    var timer: Timer? = null
    val DELAY_MS: Long = 500//delay in milliseconds before task is to be executed
    val PERIOD_MS: Long = 3000
    lateinit var tv_brand:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RecV=view.findViewById(R.id.RecV)
        dialogloader = AppDialog(activity!!)
        /************* TOP BANNER PART ************/
        viewpager = view.findViewById<ViewPager>(R.id.ViewPager)
        ViewPager_brands = view.findViewById<ViewPager>(R.id.ViewPager_brands)
        tv_brand=view.findViewById(R.id.tv_brand)

       // rec_homebrands=view.findViewById(R.id.rec_homebrands)

        /************** END TOP BANNER PART *********/

        RecV.layoutManager= LinearLayoutManager(activity)
        dialogloader.show()
        presenter= DashboardPresenterImple(this,DashboardIntractorImple())
        presenter.call_http_dashboard(this)

        SearchTXT.setOnClickListener {

            val sera= SearchFragment.newInstance("","")
            sera.show(activity!!.supportFragmentManager,"")
        }



    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                DashboardFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
