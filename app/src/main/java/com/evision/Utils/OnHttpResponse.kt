package com.evision.Utils

interface OnHttpResponse {
    fun onSuccess(response:String)
    fun onError(error:String)
    fun onStart()
}