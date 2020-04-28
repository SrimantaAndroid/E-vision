package com.evision.Utils

import android.os.AsyncTask
import okhttp3.MediaType
import okhttp3.OkHttpClient
import android.os.AsyncTask.execute
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import android.os.AsyncTask.execute
import com.bumptech.glide.load.engine.Resource
import com.evision.R
import org.json.JSONObject


class onHTTP {
    val JSON = MediaType.parse("application/json; charset=utf-8")
    var client = OkHttpClient()


    fun GETCALL(url:String,onHttpResponse: OnHttpResponse){
        object : AsyncTask<Any, Any, Any>() {
            override fun onPostExecute(result: Any?) {
                super.onPostExecute(result)
                if(result==null)
                    onHttpResponse.onError("Something went wrong please try again.")
                else
                    onHttpResponse.onSuccess(result as String)
            }

            override fun onPreExecute() {
                super.onPreExecute()
                onHttpResponse.onStart()
            }

            override fun doInBackground(vararg objects: Any): Any? {
                try {
                    val res=GET(url)
                   return res
                }catch (e:Exception){
                    return null
                }
            }
        }.execute()

    }


    fun POSTCALL(url:String,params:Map<String,Any>,onHttpResponse: OnHttpResponse){
        object : AsyncTask<Any, Any, Any>() {
            override fun onPostExecute(result: Any?) {
                super.onPostExecute(result)
                if(result==null)
                    onHttpResponse.onError("Something went wrong please try again.")
                else
                    onHttpResponse.onSuccess(result as String)
            }

            override fun onPreExecute() {
                super.onPreExecute()
                onHttpResponse.onStart()
            }

            override fun doInBackground(vararg objects: Any): Any? {
                try {
                    val paras=JSONObject(objects[0] as Map<String,Any>)
                    val res=post(url,paras.toString())
                    return res
                }catch (e:Exception){
                    return null
                }
            }
        }.execute(params)
    }









    @Throws(IOException::class)
    fun post(url: String, json: String): String {
        val body = RequestBody.create(JSON, json)
        val request = Request.Builder()
                .url(url)
                .post(body)
                .build()
        val response = client.newCall(request).execute()

        val stringResponse = response.body()!!.string()
        EvisionLog.E("response", "respose_::$stringResponse")
        EvisionLog.E(
                "response",
                "respose_ww_message::" + response.message()
        )
        EvisionLog.E(
                "response",
                "respose_ww_headers::" + response.headers()
        )
        EvisionLog.E(
                "response",
                "respose_ww_isRedirect::" + response.isRedirect
        )

        return stringResponse
    }

    @Throws(IOException::class)
    fun GET(url: String): String {
        val request = Request.Builder()
                .url(url)
                .build()

        val response = client.newCall(request).execute()
        return response.body()!!.string()
    }

}