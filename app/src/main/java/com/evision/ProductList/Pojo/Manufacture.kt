package com.evision.ProductList.Pojo

data class Manufacture(
        val category_id: String,
        val manufacture_name: String,
        val manufacture_url: String,
        var isselect: Boolean = false
)