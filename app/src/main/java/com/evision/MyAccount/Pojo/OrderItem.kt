package com.evision.MyAccount.Pojo

data class OrderItem(
        val currency: String,
        val price: String,
        val product_id: String,
        val product_name: Any,
        val qty: String,
        val row_total: Double
)