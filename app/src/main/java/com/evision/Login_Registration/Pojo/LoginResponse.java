package com.evision.Login_Registration.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

@SerializedName("cart_count")
@Expose
private Integer cartCount;
@SerializedName("customer_id")
@Expose
private String customerId;
@SerializedName("email")
@Expose
private String email;
@SerializedName("first_name")
@Expose
private String firstName;
@SerializedName("last_name")
@Expose
private String lastName;
@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private Integer status;

public Integer getCartCount() {
return cartCount;
}

public void setCartCount(Integer cartCount) {
this.cartCount = cartCount;
}

public String getCustomerId() {
return customerId;
}

public void setCustomerId(String customerId) {
this.customerId = customerId;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getFirstName() {
return firstName;
}

public void setFirstName(String firstName) {
this.firstName = firstName;
}

public String getLastName() {
return lastName;
}

public void setLastName(String lastName) {
this.lastName = lastName;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

}