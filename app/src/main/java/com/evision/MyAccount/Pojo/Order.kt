package com.evision.MyAccount.Pojo

data class Order(
        val currency: String,
        val customer_name: String,
        val increment_id: String,
        val order_amount: String,
        val order_id: String,
        val order_time: String,
        val payment_status: String
)