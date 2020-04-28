package com.evision.ProductList.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Productlist {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("product_list")
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