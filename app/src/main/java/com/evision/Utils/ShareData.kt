package com.evision.Utils

import android.content.Context
import android.content.SharedPreferences
import com.evision.Login_Registration.Pojo.LoginResponse
import com.google.gson.Gson

class ShareData (mContext: Context){
    val mContext=mContext
    var prefs: SharedPreferences? =mContext.getSharedPreferences("com.evision",0)
    fun SetUserData(data:String){
        val editor=prefs!!.edit()
        editor.putString("USER",data)
        editor.apply()
    }

    fun SetUserData(data: LoginResponse){
        val editor=prefs!!.edit()
        EvisionLog.D("## USER DATA-",Gson().toJson(data))
        editor.putString("USER",Gson().toJson(data))
        editor.apply()
    }

    fun getUser():LoginResponse?{
        var sata = prefs!!.getString("USER", "")
        return Gson().fromJson(sata,LoginResponse::class.java)
    }

    fun Logout()
    {
        val editor=prefs!!.edit()
        editor.clear()
        editor.commit()
    }

}