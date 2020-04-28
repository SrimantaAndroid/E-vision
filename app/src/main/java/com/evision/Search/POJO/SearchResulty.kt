package com.evision.Search.POJO

import com.evision.ProductList.Pojo.Product

data class SearchResulty (val search_data: List<Product>,
                          val status: Int)