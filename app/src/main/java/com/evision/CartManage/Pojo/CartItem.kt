package com.evision.CartManage.Pojo

data class CartItem(
        val currency: String,
        val image_path: String,
        val marca: String,
        val modelo: String,
        val name: String,
        val price: Double,
        val product_id: String,
        var qty: Int
)