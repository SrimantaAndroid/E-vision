package com.evision.MyAccount.Pojo

data class OrderDetails(
        val order_details: List<OrderDetail>,
        val order_items: List<OrderItem>,
        val order_totals: List<OrderTotal>,
        val status: Int
)