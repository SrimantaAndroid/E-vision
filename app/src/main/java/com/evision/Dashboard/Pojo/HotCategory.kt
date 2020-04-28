package com.evision.Dashboard.Pojo

data class HotCategory(
        val cat_id: String,
        val cat_name: String,
        val product_list: List<Product>
)