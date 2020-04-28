package com.evision.Dashboard

import com.evision.Dashboard.Pojo.Dasboard
import com.evision.Utils.EvisionLog
import com.evision.Utils.OnHttpResponse
import com.evision.Utils.URL
import com.evision.Utils.onHTTP
import com.google.gson.Gson

class DashboardIntractorImple:DasboardContract.Intractor {
    override fun call_http_dashboard(viee: DasboardContract.View) {
        onHTTP().GETCALL(URL.GETDASHBOARD, object : OnHttpResponse {
            override fun onSuccess(response: String) {
                EvisionLog.D("##MSG-", response)
                val dasb = Gson().fromJson(response, Dasboard::class.java)
                viee.preparepage(dasb)
            }

            override fun onError(error: String) {

            }

            override fun onStart() {
            }

        })
    }


}