package com.evision.CartManage.Pojo

data class OrderReview(
        var currency: String,
        var decripcion: String,
        var model: String,
        var price: String,
        var qty: String,
        var total: String,
        var coupon_text:String
)