package com.evision.CartManage.Pojo

data class CartResponse(
        val cart_count: Int,
        val cart_items: List<CartItem>,
        val cart_totals: List<CartTotal>,
        val customer_id: String,
        val status: Int,
        val message: String
)