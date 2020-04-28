package com.evision.ProductList.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ManufacturarList {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("manufacture_products")
    @Expose
    private List<Product> productList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

}