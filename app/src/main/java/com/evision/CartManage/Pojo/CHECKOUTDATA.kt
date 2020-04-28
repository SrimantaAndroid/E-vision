package com.evision.CartManage.Pojo

data class CHECKOUTDATA(
        var order_review: List<OrderReview>,
        var order_totals: List<OrderTotal>,
        var status: Int
)