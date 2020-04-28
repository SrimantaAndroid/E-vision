package com.evision.CartManage.Pojo

import android.os.Parcel
import android.os.Parcelable

data class CustomerAddressBilling(
        var address: String,
        var address_1: String,
        var address_2: String,
        var city_id: String,
        var city_name: String,
        var country_id: String,
        var country_name: String,
        var fisrt_name: String,
        var last_name: String,
        var state_id: String,
        var state_name: String,
        var telephone: String,
          var  phone_country_code:String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeString(address_1)
        parcel.writeString(address_2)
        parcel.writeString(city_id)
        parcel.writeString(city_name)
        parcel.writeString(country_id)
        parcel.writeString(country_name)
        parcel.writeString(fisrt_name)
        parcel.writeString(last_name)
        parcel.writeString(state_id)
        parcel.writeString(state_name)
        parcel.writeString(telephone)
        parcel.writeString(phone_country_code)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CustomerAddressBilling> {
        override fun createFromParcel(parcel: Parcel): CustomerAddressBilling {
            return CustomerAddressBilling(parcel)
        }

        override fun newArray(size: Int): Array<CustomerAddressBilling?> {
            return arrayOfNulls(size)
        }
    }
}