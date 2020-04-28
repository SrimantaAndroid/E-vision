package com.evision.CartManage

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebView
import com.google.android.gms.maps.MapView



class CustomMapView(p0: Context?, attr: AttributeSet) : WebView(p0,attr) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev)
    }


}