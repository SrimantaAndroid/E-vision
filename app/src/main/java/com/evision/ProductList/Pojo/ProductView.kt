package com.evision.ProductList.Pojo

data class ProductView(
        val addtocart_option: Int,
        val allow_purchase: String,
        val brand: String,
        val brand_link: String,
        val category_id: String,
        val category_link: String,
        val category_name: String,
        val currency: String,
        val descripcion: String,
        val extra_html_description: String,
        val modelo: String,
        val price: String,
        val product_id: String,
        val product_image: String,
        val product_ink: String,
        val product_name: String,
        val qty: Int,
        val short_description: String,
        val special_price: Int
)