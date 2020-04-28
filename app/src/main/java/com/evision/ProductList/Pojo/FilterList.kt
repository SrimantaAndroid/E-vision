package com.evision.ProductList.Pojo

data class FilterList(
        val manufacture_list: List<Manufacture>,
        val price_filter: List<Price>,
        val status: Int
)