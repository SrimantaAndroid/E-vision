package com.evision.CartManage.Pojo

data class CartTotal(
        val currency: String,
        val grand_total: Double,
        val subtotal: Double,
        val tax: String,
        val tax_name: String
)