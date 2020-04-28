package com.evision.MyAccount


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.evision.R
import com.evision.Utils.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ProfileFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val image=view.findViewById<ImageView>(R.id.IMG_user)
        image.visibility = View.GONE
//        Glide.with(activity!!).load("https://pbs.twimg.com/profile_images/1074551495142584320/pIjknua7_400x400.jpg").apply(RequestOptions().circleCrop()).into(image)

        BTNEdit.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.acc_container, EditFragment.newInstance("","")).commit()
        }
        loader = AppDialog(activity!!)
        val params = HashMap<String, Any>()
        params.put("customer_id", ShareData(activity!!).getUser()!!.customerId)

        onHTTP().POSTCALL(URL.VIEWPROFILE, params, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                loader.dismiss()
                if (JSONObject(response).optInt("status") == 200) {
                    val obj = JSONObject(response).optJSONArray("profile_view").optJSONObject(0)
                    Name.setText(obj.optString("customer_name"))
                    Email.setText(obj.optString("email"))
                    Phone.setText(obj.optString("telephone"))
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


        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ProfileFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
