package com.evision.Dashboard

class DashboardPresenterImple(private val dashview:DasboardContract.View,private val intractor:DasboardContract.Intractor) :DasboardContract.Presenter,DasboardContract.Intractor {
    override fun call_http_dashboard(viee: DasboardContract.View) {
        intractor.call_http_dashboard(dashview)
    }
    override fun call_preparation() {

    }
}