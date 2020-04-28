package com.evision.Dashboard

import com.evision.Dashboard.Pojo.Dasboard

class DasboardContract{
    interface View{
        fun preparepage(Hotlist: Dasboard)
    }

    interface Presenter{
            fun call_preparation()
    }
    interface Intractor{
            fun call_http_dashboard(viee:View)
    }
}