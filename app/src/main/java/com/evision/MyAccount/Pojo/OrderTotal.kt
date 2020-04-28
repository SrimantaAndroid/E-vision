package com.evision.MyAccount.Pojo

data class OrderTotal(
        val currency: String,
        val delivery_cost: String,
        val discount: String,
        val grand_total: String,
        val subtotal: String,
        val tax: String,
        val tax_name: String
)