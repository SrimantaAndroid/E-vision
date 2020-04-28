package com.evision.ProductList.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("product_ink")
    @Expose
    private String productInk;
    @SerializedName("modelo")
    @Expose
    private String modelo;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("special_price")
    @Expose
    private String specialPrice;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("allow_purchase")
    @Expose
    private String allowPurchase;
    @SerializedName("short_description")
    @Expose
    private String short_description;
    @SerializedName("addtocart_option")
    @Expose
    private Integer addtocartOption;

    @SerializedName("new_arrival")
    @Expose
    private Integer new_arrival;


    public Integer getNew_arrival() {
        return new_arrival;
    }


    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public void setNew_arrival(Integer new_arrival) {
        this.new_arrival = new_arrival;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductInk() {
        return productInk;
    }

    public void setProductInk(String productInk) {
        this.productInk = productInk;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(String specialPrice) {
        this.specialPrice = specialPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAllowPurchase() {
        return allowPurchase;
    }

    public void setAllowPurchase(String allowPurchase) {
        this.allowPurchase = allowPurchase;
    }

    public Integer getAddtocartOption() {
        return addtocartOption;
    }

    public void setAddtocartOption(Integer addtocartOption) {
        this.addtocartOption = addtocartOption;
    }

}