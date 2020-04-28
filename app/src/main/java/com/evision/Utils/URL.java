package com.evision.Utils;

import org.jetbrains.annotations.NotNull;

public class URL {

    //public static String BASE = "https://indigitalsoft.in/evision/api/";
    public static String BASE = "https://www.evisionstore.com/api/";
    public static String GETCATEGORY=BASE+"home/category.php";
    public static String GETPRODUCTLIST=BASE+"product/list.php";
    public static String REGISTERNEWACC=BASE+"user/signup.php";
    public static String LOGIN_REQ=BASE+"user/login.php";
    public static String ADDtoCART=BASE+"user/account/addtocart.php";
    public static String GETCARTITEM=BASE+"product/cart.php";
    public static String GETDETAILS=BASE+"product/view.php";
    public static String DELTECART=BASE+"user/account/deletecart.php";
    public static String UPDATECART=BASE+"user/account/updatecart.php";
    public static final String GETFILTER=BASE+"product/manufacture.php";
    public static final String FILTERBYMANUFACTURER=BASE+"product/manufacture_pro.php";
    public static final String SEARCH=BASE+"product/search.php";
    public static final String GETDASHBOARD = BASE + "home/dashboard.php";
    @NotNull
    public static final String MINUSCART = BASE + "user/account/decreaseqty.php";
    public static final String VIEWPROFILE = BASE + "user/profile.php";
    public static final String EDITPROFILE = BASE + "user/profile_edit.php";
    @NotNull
    public static final String UPDATEPROFILE = BASE + "user/profileupdate.php";
    @NotNull
    public static final String GETADDRES = BASE + "user/address.php";
    public static final String CHECKOUTADDRESS = BASE + "checkout/getaddress.php";
    @NotNull
    public static final String GETADDRESEDIT = BASE + "user/addressedit.php";
    @NotNull
    public static final String GETSTATES = BASE + "user/getstate.php";

    @NotNull
    public static final String GETCOUNTRY = BASE + "checkout/getcountry.php";
    @NotNull
    public static final String GETCITYS = BASE + "user/getcity.php";
    public static final String UPDATEADDRESS = BASE + "user/adddressupdate.php";
    public static final String MYORDERLIST = BASE + "user/orderlist.php";
    public static final String ORDERDETAILS = BASE + "user/orderview.php";
    public static final String GETONLINESALELST = BASE + "product/online_products.php";
    public static final String POSTCONTACTUS = BASE + "contacts/contacts.php";
    public static final String FILTER = BASE + "product/filter.php";
    public static final String GETDELIVERY = BASE + "checkout/getdelivery.php";
    @NotNull
    public static final String UPDATECOUPN = BASE + "checkout/getcoupon.php";
    @NotNull
    public static final String GETREVIEW = BASE + "checkout/getorderreview.php";
    public static final String ORDERPALCEINSTORE = BASE + "checkout/placeorder.php";

    public static final String SEND_ADDRESS=BASE+"checkout/getmapaddress.php";
}
