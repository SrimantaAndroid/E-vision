package com.evision.Search


import android.os.Bundle


import com.evision.R
import android.R.attr.gravity
import android.R.attr.keyLabel
import android.content.Intent
import android.os.Handler

import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evision.ProductList.Adapter.AdapterProduct
import com.evision.ProductList.Pojo.Product
import com.evision.ProductList.ProductListActivity
import com.evision.Search.Adapter.AdapterProductSearch
import com.evision.Search.POJO.SearchResulty
import com.evision.Utils.AppDialog
import com.evision.Utils.EvisionLog
import com.evision.Utils.OnHttpResponse
import com.evision.Utils.onHTTP
import com.google.gson.Gson
import kotlinx.android.synthetic.main.content_check_out_address.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.json.JSONObject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SearchFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var adapter: AdapterProductSearch
    lateinit var loader: AppDialog
    var prod_list =ArrayList<Product>()
    lateinit var Rec_listP:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme);
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
             getDialog().getWindow().setGravity(Gravity.TOP)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Rec_listP=view.findViewById(R.id.Rec_listP)
        Rec_listP.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        adapter = AdapterProductSearch(activity!!,prod_list!!)
        Rec_listP.adapter = adapter

        loader=AppDialog(activity!!)
        VIEWMORE.visibility=View.GONE
        IMG_BACK.setOnClickListener {
            dialog.dismiss()
        }
        rlback.setOnClickListener {
            dialog.dismiss()
        }

        EDX_key.addTextChangedListener(object :TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (!EDX_key.text.toString().equals("")) {
                    callsearchApi()
                   // EDX_key.isEnabled = false
                    Handler().postDelayed({
                       // EDX_key.isEnabled = true
                        EDX_key.requestFocus(EDX_key.length())
                    }, 1500)
                }else{
                    prod_list.clear()
                    adapter.notify(prod_list)
                    VIEWMORE.visibility=View.GONE
                    TXT_EMTY.visibility = View.VISIBLE
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })

        EDX_key.setOnEditorActionListener(object :TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId== EditorInfo.IME_ACTION_SEARCH)
                {
                    IMG_SEARCH.performClick()
                    return  true
                }
                return false
            }

        })
        IMG_SEARCH.setOnClickListener {
            callsearchApi()
        }

    }

    fun test(vale: String) {
        EvisionLog.D("## STRING-", vale)
    }

    fun test(vale: Int) {
        EvisionLog.D("## INTEGET-", "" + vale)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window.setLayout(width, height)
            dialog.setCanceledOnTouchOutside(false)
        }
    }
    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SearchFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    fun  callsearchApi(){
        if (!EDX_key.text.toString().equals("")) {
            val params = HashMap<String, Any>()
            params.put("keyword", EDX_key.text.toString())
            EvisionLog.D("## PARAMS-", Gson().toJson(params))
            onHTTP().POSTCALL(com.evision.Utils.URL.SEARCH, params, object : OnHttpResponse {
                override fun onSuccess(response: String) {

//                arrlist=ArrayList()
                    EvisionLog.D("## DATA-", response)

                    val listdata = Gson().fromJson(response, SearchResulty::class.java)
//                arrlist .addAll(listdata.productList)
                    if (listdata.status == 200) {
                       // EDX_key.isEnabled=true
                        prod_list.clear()
                        prod_list= listdata.search_data as ArrayList<Product>;
                        TXT_EMTY.visibility = View.GONE
                        if (listdata.search_data.size > 7) {
                            VIEWMORE.visibility = View.VISIBLE
                            MORETXT.setTextColor(activity!!.resources.getColor(R.color.white))
                            VIEWMORE.setBackgroundColor(activity!!.resources.getColor(R.color.colorPrimary))
                            MORETXT.setText("View more " + (listdata.search_data.size - 7) + " products")
                        } else {
                            VIEWMORE.visibility = View.GONE
                        }
                         // adapter = AdapterProductSearch(activity!!, prod_list)
                        //  Rec_listP.adapter = adapter
                          adapter.notify(prod_list)
                    } else {
                        prod_list.clear()
                        adapter.notify(prod_list)
                        //Rec_listP.adapter = null
                        VIEWMORE.visibility=View.GONE
                        TXT_EMTY.setText(JSONObject(response).optString("message"))
                        TXT_EMTY.visibility = View.VISIBLE
                    }
                   // loader.dismiss()
                }

                override fun onError(error: String) {
                   // EDX_key.isEnabled=true
                  //  loader.dismiss()

                }

                override fun onStart() {
                   // loader.show()
                }

            })


            test("ddd")
            test(11)
            VIEWMORE.setOnClickListener {
                startActivity(Intent(activity, ProductListActivity::class.java).putExtra("pid", "BYSEARCH").putExtra("cname", EDX_key.text.toString()))

            }
        }else{
            Toast.makeText(activity!!,activity!!.resources.getString(R.string.searchtext), Toast.LENGTH_SHORT).show()
            EDX_key.requestFocus()
        }
    }
}
