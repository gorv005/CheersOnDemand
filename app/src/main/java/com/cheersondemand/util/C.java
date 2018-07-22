package com.cheersondemand.util;

import android.os.Environment;

public interface C {
    long SPLASH_LOADER_TIME = 5000;
    String BUNDLE ="bundle" ;
    String FRAGMENT_ACTION ="action" ;
    int FRAGMENT_SPLASH =1 ;
    String TAG_FRAGMENT_SPLASH ="TAG_FRAGMENT_SPLASH" ;
    String TAG_FRAGMENT_LOGIN ="TAG_FRAGMENT_LOGIN" ;
    int FRAGMENT_AUTHNITICATION = 2;
    String TAG_FRAGMENT_PRODUCT_LISTING ="TAG_FRAGMENT_PRODUCT_LISTING" ;

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
    String PRODUCT ="product" ;
    int FRAGMENT_PRODUCT_DESC = 10;
    String bearer="bearer ";
    String ORDER_ID ="order_id" ;
    int FRAGMENT_COUPON_LIST = 11;
    String CART_VALUE ="cart_value" ;
    String COUPON_ID ="coupon_id" ;
    String COUPON_NAME ="coupon_name" ;
    String CART_HAS_ITEM ="cart_has_item" ;
    int FRAGMENT_CATEGORIES = 12;
    int FRAGMENT_PRODUCT_LISTING = 13;

    String CAT_ID = "cat_id";
    String SOURCE = "source";
    String TAG_FRAGMENT_PRICE_RANGE ="TAG_FRAGMENT_PRICE_RANGE" ;
    String TAG_FRAGMENT_CATEGORY = "TAG_FRAGMENT_CATEGORY";
    String TAG_FRAGMENT_BRAND = "TAG_FRAGMENT_BRAND";
    int FRAGMENT_PRICE_RANGE = 14;
    int FRAGMENT_BRANDS =15 ;
    int FRAGMENT_CATEGORIES_FILTER =16 ;
    String BRANDS_LIST ="brand_list" ;
    String CATEGORY_LIST ="cat_list" ;
    int REQUEST_CODE =122 ;
    String TAG_FRAGMENT_CART = "TAG_FRAGMENT_CART";
    int FRAGMENT_FORGOT_PASSWORD = 17;
    int FRAGMENT_WISHLIST =18 ;
    String PRODUCT_LIST = "product_list";
    String IS_EDIT ="is_edit" ;
    String ADDRESS = "address";
    int FRAGMENT_ADDRESS_LIST = 19;
    int FRAGMENT_ADD_ADDRESS = 20;
    String TAG_FRAGMENT_ADD_ADDRESS = "TAG_FRAGMENT_ADD_ADDRESS";
    String SUB_CATEGORY_LIST = "sub_cat_list";
    String TAG_FRAGMENT_SUB_CAT = "TAG_FRAGMENT_SUB_CAT";
    int FRAGMENT_CATEGORIES_HOME=21;
    String SUB_CAT_ID = "subCatId";
    String MSG ="" ;
    String MIN_RANGE ="minRange" ;
   String MAX_RANGE = "maxRange";
    String HELP_ITEM ="help_center" ;
    int FRAGMENT_HELP_CENTER = 22;
    int FRAGMENT_HELP_CENTER_PAGES =23 ;
    String TAG_FRAGMENT_HELP_CENTER_PAGES ="TAG_FRAGMENT_HELP_CENTER_PAGES" ;

    public static final String IMAGE_PATH = Environment
            .getExternalStorageDirectory().getPath() + "/COD";
    int FRAGMENT_BECOME_PARTNER =24 ;
    String IS_NOTIFICATION ="is_notification" ;
    int FRAGMENT_ADD_CARD =25 ;
    String TAG_FRAGMENT_FRAGMENT_ADD_CARD ="TAG_FRAGMENT_FRAGMENT_ADD_CARD" ;
    int FRAGMENT_CARD_LIST = 26;
    String STRIPE_APP_KEY = "pk_test_ISZW9qDBCzC06UHb2asnW3tz";
    String placed="placed";
    int FRAGMENT_ORDER_LIST = 27;
    String cancelled="cancelled";
    String delivered="delivered";
    String confirmed="confirmed";
    String ORDER = "order";
    int FRAGMENT_ORDER_DETAIL =28 ;
    String TAG_FRAGMENT_ORDER_DETAIL = "TAG_FRAGMENT_ORDER_DETAIL";
    String IS_REORDER = "is_reorder";
    int FRAGMENT_SELECT_ADDRESS =29 ;
    String CART_ID ="cardId" ;
    String IS_FROM_CHECKOUT ="is_from_checkout" ;

    public static final String CARD_NUMBER_FORMAT = "XXXX XXXX XXXX XXXX";
    public static final String CARD_NUMBER_FORMAT_AMEX = "XXXX XXXXXX XXXXX";
    public static final char CHAR_X = 'X';

    int FRAGMENT_PAYMENT_CONFIRMATION = 30;
    String TAG_FRAGMENT_SELECT_ADDRESS = "TAG_FRAGMENT_SELECT_ADDRESS";
    String CART_DATA ="cart_data" ;
    String ADDRESS_ID = "address_id";
    String PAYMENT_RESULT ="payment_result" ;
    int FRAGMENT_PAYMENT_RESULT = 31;
    String VISA = "Visa";
    String MasterCard="MasterCard";
    String Discover="Discover";
    String AMEX = "American Express";
    String CARD_DATA = "cardData";
    String PAYMENT_SUCCESS = "paymentSuccess";
    String PAYMENT_FAILED = "paymentFailed";
    String ORDER_CANCEL ="orderCancel";
}
