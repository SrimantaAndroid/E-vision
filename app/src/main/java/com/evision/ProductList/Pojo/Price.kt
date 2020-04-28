package com.evision.ProductList.Pojo

data class Price(
        val currency: String,
        val max_price: String,
        val min_price: String,
        var selectmin: String = min_price,
        var selectmax: String = max_price
)