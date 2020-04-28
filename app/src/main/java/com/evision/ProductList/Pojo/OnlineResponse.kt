package com.evision.ProductList.Pojo

data class OnlineResponse(val code: Int,
                          val status: String,
                          val online_products_all: List<Product>)