package com.evision.CartManage.Pojo

data class OrderTotal(
        var coupon_code: String,
        var currency: String,
        var delivery: String,
        var delivery_type: String,
        var discount_amount: String,
        var grand_total: Double,
        var subtotal: Double,
        var tax: String,
        var tax_name: String
)