package com.evision.MyAccount.Pojo

data class OrderDetail(
        val billing_shipping_address: String,
        val increment_id: String,
        val order_date: String,
        val payment_method: String,
        val payment_status: String,
        val pickup_address: String,
        val transaction_id: String
)