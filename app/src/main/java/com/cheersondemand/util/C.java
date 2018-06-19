package com.cheersondemand.util;

public interface C {
    long SPLASH_LOADER_TIME = 5000;
    String BUNDLE ="bundle" ;
    String FRAGMENT_ACTION ="action" ;
    int FRAGMENT_SPLASH =1 ;
    String TAG_FRAGMENT_SPLASH ="TAG_FRAGMENT_SPLASH" ;
    String TAG_FRAGMENT_LOGIN ="TAG_FRAGMENT_LOGIN" ;
    int FRAGMENT_AUTHNITICATION = 2;

    int FRAGMENT_PRODUCTS_HOME =3 ;
    String TAG_FRAGMENT_PRODUCTS_HOME = "TAG_FRAGMENT_PRODUCTS_HOME";
    String BASE_URL = "http://ror.anasource.com:8090/cheers_on_demand/api/v1/";
    String AUTH_USER = "user";
    String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    int FRAGMENT_PROFILE_HOME =4 ;
    String IS_LOGIN = "is_login";
    String IS_LOGIN_SCREEN ="is_login_screen" ;
    int FRAGMENT_UPDATE_PROFILE =5 ;
    int FRAGMENT_NOTIFICATIONS = 7;

    int FRAGMENT_CART =6 ;
    String GUEST_USER ="Guest_user" ;
    String IS_LOGIN_GUEST = "is_guest_user";
    int FRAGMENT_CHANGE_PASSWORD = 8;
    String GUEST_USER_ALLREADY_CREATED ="Guest user already created" ;
    int REQUEST_ADDRESS =01 ;
    int FRAGMENT_STORE_LIST =9 ;
    String SELECTED_STORE = "selected_store";
    int HOME = 1;
    int SEARCH =2 ;
    String FROM ="from" ;
    String LOCATION_SELECTED ="selected_loc" ;
    String DEVICE_TOKEN ="device_token" ;
    String IS_AUTH_USER ="is_auth_user" ;
}
