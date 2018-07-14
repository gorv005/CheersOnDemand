package com.cheersondemand.frameworks.retrofit;import com.cheersondemand.model.BrandResponse;import com.cheersondemand.model.CategoriesResponse;import com.cheersondemand.model.GuestUserCreateResponse;import com.cheersondemand.model.ProductsWithCategoryResponse;import com.cheersondemand.model.SubCategoryResponse;import com.cheersondemand.model.address.AddressAddResponse;import com.cheersondemand.model.address.AddressRequest;import com.cheersondemand.model.address.AddressResponse;import com.cheersondemand.model.authentication.AuthenticationResponse;import com.cheersondemand.model.authentication.GenRequest;import com.cheersondemand.model.authentication.LoginRequest;import com.cheersondemand.model.authentication.SignUpRequest;import com.cheersondemand.model.authentication.SocialLoginRequest;import com.cheersondemand.model.card.AddCardRequest;import com.cheersondemand.model.card.CardAddResponse;import com.cheersondemand.model.card.CardListResponse;import com.cheersondemand.model.card.DeleteCardRequest;import com.cheersondemand.model.changepassword.PasswordRequest;import com.cheersondemand.model.changepassword.PasswordResponse;import com.cheersondemand.model.coupon.ApplyCouponRequest;import com.cheersondemand.model.coupon.CouponInfoResponse;import com.cheersondemand.model.coupon.CouponListResponse;import com.cheersondemand.model.coupon.CouponRequest;import com.cheersondemand.model.location.RecentLocationResponse;import com.cheersondemand.model.location.SaveLocation;import com.cheersondemand.model.location.SaveLocationResponse;import com.cheersondemand.model.logout.LogoutRequest;import com.cheersondemand.model.logout.LogoutResponse;import com.cheersondemand.model.notification.NotificationResponse;import com.cheersondemand.model.order.CreateOrderResponse;import com.cheersondemand.model.order.addtocart.AddToCartRequest;import com.cheersondemand.model.order.addtocart.AddToCartResponse;import com.cheersondemand.model.order.addtocart.CartHasItemResponse;import com.cheersondemand.model.order.orderdetail.OrderListResponse;import com.cheersondemand.model.order.updatecart.UpdateCartRequest;import com.cheersondemand.model.order.updatecart.UpdateCartResponse;import com.cheersondemand.model.productList.ProductListResponse;import com.cheersondemand.model.productdescription.SimilarProductsResponse;import com.cheersondemand.model.profile.ProfileUpdateRequest;import com.cheersondemand.model.search.SearchProductResponse;import com.cheersondemand.model.search.SearchResponse;import com.cheersondemand.model.search.SearchResultsResponse;import com.cheersondemand.model.store.AddStore;import com.cheersondemand.model.store.StoreListResponse;import com.cheersondemand.model.store.UpdateStore;import com.cheersondemand.model.store.UpdateStoreResponse;import com.cheersondemand.model.wishlist.WishListDataResponse;import com.cheersondemand.model.wishlist.WishListRequest;import com.cheersondemand.model.wishlist.WishListResponse;import com.google.gson.Gson;import java.util.List;import okhttp3.MultipartBody;import okhttp3.OkHttpClient;import okhttp3.RequestBody;import okhttp3.logging.HttpLoggingInterceptor;import retrofit2.Call;import retrofit2.Retrofit;import retrofit2.converter.gson.GsonConverterFactory;/** * Created by abhishekkumar on 2/7/18. */public class WebServicesWrapper {    private final static String BASE_URL = "http://ror.anasource.com:8090";    private static WebServicesWrapper wrapper;    protected WebServices webServices;    private Gson gson;    private WebServicesWrapper(String baseUrl) {        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();        webServices = new Retrofit.Builder()                .addConverterFactory(GsonConverterFactory.create())                .baseUrl(baseUrl)                .client(client)                .build().create(WebServices.class);        gson = new Gson();    }    public static WebServicesWrapper getInstance() {        if (wrapper == null)            wrapper = new WebServicesWrapper(BASE_URL);        return wrapper;    }    public Call<AuthenticationResponse> signUp(ResponseResolver<AuthenticationResponse> apiResponseResponseResolve, SignUpRequest signUpRequestr) {        Call<AuthenticationResponse> apiResponseResponseCall = webServices.signUpUsingEmail(signUpRequestr);        apiResponseResponseCall.enqueue(apiResponseResponseResolve);        return apiResponseResponseCall;    }    public Call<AuthenticationResponse> loginUsingEmail(ResponseResolver<AuthenticationResponse> apiResponseResponseResolve, LoginRequest loginRequest) {        Call<AuthenticationResponse> apiResponseResponseCall = webServices.loginUsingEmail(loginRequest);        apiResponseResponseCall.enqueue(apiResponseResponseResolve);        return apiResponseResponseCall;    }    public Call<AuthenticationResponse> loginUsingSocial(ResponseResolver<AuthenticationResponse> apiResponseResponseResolve, SocialLoginRequest loginRequest) {        Call<AuthenticationResponse> apiResponseResponseCall = webServices.loginUsingSocial(loginRequest);        apiResponseResponseCall.enqueue(apiResponseResponseResolve);        return apiResponseResponseCall;    }    public Call<PasswordResponse> changePassword(ResponseResolver<PasswordResponse> apiResponseResponseResolve,String token,String userId ,PasswordRequest passwordRequest ) {        Call<PasswordResponse> apiResponseResponseCall = webServices.changePassword(token,userId,passwordRequest);        apiResponseResponseCall.enqueue(apiResponseResponseResolve);        return apiResponseResponseCall;    }    public Call<PasswordResponse> forgotPassword(ResponseResolver<PasswordResponse> apiResponseResponseResolve,PasswordRequest passwordRequest ) {        Call<PasswordResponse> apiResponseResponseCall = webServices.ForgotPassword(passwordRequest);        apiResponseResponseCall.enqueue(apiResponseResponseResolve);        return apiResponseResponseCall;    }    public Call<BrandResponse> getCategories(ResponseResolver<BrandResponse> apiResponseResponseResolve, String uuid) {        Call<BrandResponse> categoriesResponseCall = webServices.getBrands(uuid);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }        public Call<CategoriesResponse> getCategories(ResponseResolver<CategoriesResponse> apiResponseResponseResolve,boolean isAuth,String token, String uuid) {        Call<CategoriesResponse> categoriesResponseCall;        if(isAuth) {            categoriesResponseCall = webServices.getCategories(token,uuid );        }        else {            categoriesResponseCall = webServices.getCategories(uuid);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<SubCategoryResponse> getSubCategories(ResponseResolver<SubCategoryResponse> apiResponseResponseResolve, boolean isAuth, String token, List<Integer> id, String uuid) {        Call<SubCategoryResponse> categoriesResponseCall;        if(isAuth) {            categoriesResponseCall = webServices.getSubCategories(token,id,uuid );        }        else {            categoriesResponseCall = webServices.getSubCategories(id,uuid);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    //LANDING SCREEN    public Call<ProductsWithCategoryResponse> getProductsWithCategories(ResponseResolver<ProductsWithCategoryResponse> apiResponseResponseResolve, String uuid) {        Call<ProductsWithCategoryResponse> categoriesResponseCall = webServices.getProductsWithCategories(uuid);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<ProductsWithCategoryResponse> getProductsWithCategories(ResponseResolver<ProductsWithCategoryResponse> apiResponseResponseResolve, String token, String uuid) {        Call<ProductsWithCategoryResponse> categoriesResponseCall = webServices.getProductsWithCategories(token, uuid);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<BrandResponse> getBrands(ResponseResolver<BrandResponse> apiResponseResponseResolve, boolean isAuth,String auth, String uuid) {        Call<BrandResponse> categoriesResponseCall;        if(isAuth){            categoriesResponseCall = webServices.getBrands(auth, uuid);        }        else {            categoriesResponseCall = webServices.getBrands(uuid);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<GuestUserCreateResponse> createGuestUser(ResponseResolver<GuestUserCreateResponse> apiResponseResponseResolve, GenRequest uuid) {        Call<GuestUserCreateResponse> categoriesResponseCall = webServices.createGuestUser(uuid);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<LogoutResponse> logout(ResponseResolver<LogoutResponse> apiResponseResponseResolve, LogoutRequest request) {        Call<LogoutResponse> categoriesResponseCall = webServices.Logout(request);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    //STORE LIST    public Call<StoreListResponse> getStoreList(ResponseResolver<StoreListResponse> apiResponseResponseResolve, String uuid) {        Call<StoreListResponse> categoriesResponseCall = webServices.getStoreList(uuid);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<StoreListResponse> getStoreList(ResponseResolver<StoreListResponse> apiResponseResponseResolve, String token, String uuid) {        Call<StoreListResponse> categoriesResponseCall = webServices.getStoreList(token, uuid);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<UpdateStoreResponse> updateStore(ResponseResolver<UpdateStoreResponse> apiResponseResponseResolve,                                                 UpdateStore updateStore, String id) {        Call<UpdateStoreResponse> categoriesResponseCall = webServices.updateStore(id, updateStore);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<UpdateStoreResponse> updateStore(ResponseResolver<UpdateStoreResponse> apiResponseResponseResolve,                                                 String token, UpdateStore updateStore, String id) {        Call<UpdateStoreResponse> categoriesResponseCall = webServices.updateStore(token, id, updateStore);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    //SAVE LOCATION    public Call<SaveLocationResponse> saveLocation(ResponseResolver<SaveLocationResponse> apiResponseResponseResolve, SaveLocation saveLocation, String id) {        Call<SaveLocationResponse> categoriesResponseCall = webServices.saveLocation(id, saveLocation);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<SaveLocationResponse> saveLocation(ResponseResolver<SaveLocationResponse> apiResponseResponseResolve, String token, SaveLocation saveLocation, String id) {        Call<SaveLocationResponse> categoriesResponseCall = webServices.saveLocation(token, id, saveLocation);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    //SAVE LOCATION    public Call<SimilarProductsResponse> getSimilarProducts(ResponseResolver<SimilarProductsResponse> apiResponseResponseResolve, String productID, String uuid, String page, String per_page) {        Call<SimilarProductsResponse> categoriesResponseCall = webServices.getSimilarProducts(productID, uuid, page, per_page);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<SimilarProductsResponse> getSimilarProducts(ResponseResolver<SimilarProductsResponse> apiResponseResponseResolve, String token, String productID, String uuid, String page, String per_page) {        Call<SimilarProductsResponse> categoriesResponseCall = webServices.getSimilarProductsAuth(token, productID, page, per_page);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }//Create Order    public Call<CreateOrderResponse> createOrder(ResponseResolver<CreateOrderResponse> apiResponseResponseResolve, String userId, GenRequest uuid) {        Call<CreateOrderResponse> categoriesResponseCall = webServices.createOrder(userId, uuid);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<CreateOrderResponse> createOrder(ResponseResolver<CreateOrderResponse> apiResponseResponseResolve, String token, String userId, GenRequest uuid) {        Call<CreateOrderResponse> categoriesResponseCall = webServices.createOrder(token, userId, uuid);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    //Add to cart    public Call<AddToCartResponse> addToCart(ResponseResolver<AddToCartResponse> apiResponseResponseResolve, String userId, String order_id, AddToCartRequest addToCartRequest) {        Call<AddToCartResponse> categoriesResponseCall = webServices.addToCart(userId, order_id, addToCartRequest);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<AddToCartResponse> addToCart(ResponseResolver<AddToCartResponse> apiResponseResponseResolve, String token, String userId, String order_id, AddToCartRequest addToCartRequest) {        Call<AddToCartResponse> categoriesResponseCall = webServices.addToCart(token, userId, order_id, addToCartRequest);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    //Update cart    public Call<UpdateCartResponse> updateCart(ResponseResolver<UpdateCartResponse> apiResponseResponseResolve, String userId, String order_id, UpdateCartRequest updateProductQuantityRequest) {        Call<UpdateCartResponse> categoriesResponseCall = webServices.updateCart(userId, order_id, updateProductQuantityRequest);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<UpdateCartResponse> updateCart(ResponseResolver<UpdateCartResponse> apiResponseResponseResolve, String token, String userId, String order_id, UpdateCartRequest updateProductQuantityRequest) {        Call<UpdateCartResponse> categoriesResponseCall = webServices.updateCart(token, userId, order_id, updateProductQuantityRequest);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    //Remove Item From cart    public Call<UpdateCartResponse> removeItemFromCart(ResponseResolver<UpdateCartResponse> apiResponseResponseResolve, String userId, String order_id, UpdateCartRequest updateProductQuantityRequest) {        Call<UpdateCartResponse> categoriesResponseCall = webServices.removeItemFromCart(userId, order_id, updateProductQuantityRequest);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<UpdateCartResponse> removeItemFromCart(ResponseResolver<UpdateCartResponse> apiResponseResponseResolve, String token, String userId, String order_id, UpdateCartRequest updateProductQuantityRequest) {        Call<UpdateCartResponse> categoriesResponseCall = webServices.removeItemFromCart(token, userId, order_id, updateProductQuantityRequest);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    //CART LIST    public Call<UpdateCartResponse> getCartList(ResponseResolver<UpdateCartResponse> apiResponseResponseResolve, String userId, String order_id, String uuid) {        Call<UpdateCartResponse> categoriesResponseCall = webServices.getCartList(userId, order_id, uuid);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<UpdateCartResponse> getCartList(ResponseResolver<UpdateCartResponse> apiResponseResponseResolve, String token, String userId, String order_id, String uuid) {        Call<UpdateCartResponse> categoriesResponseCall = webServices.getCartList(token, userId, order_id, uuid);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    //WISH LIST    public Call<WishListResponse> addToWishList(ResponseResolver<WishListResponse> apiResponseResponseResolve, String userId, WishListRequest wishListRequest) {        Call<WishListResponse> categoriesResponseCall = webServices.addToWishList(userId, wishListRequest);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<WishListResponse> addToWishList(ResponseResolver<WishListResponse> apiResponseResponseResolve, String token, String userId, WishListRequest wishListRequest) {        Call<WishListResponse> categoriesResponseCall = webServices.addToWishList(token, userId, wishListRequest);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<WishListResponse> removeFromWishList(ResponseResolver<WishListResponse> apiResponseResponseResolve, String userId, WishListRequest wishListRequest) {        Call<WishListResponse> categoriesResponseCall = webServices.removeFromWishList(userId, wishListRequest);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<WishListResponse> removeFromWishList(ResponseResolver<WishListResponse> apiResponseResponseResolve, String token, String userId, WishListRequest wishListRequest) {        Call<WishListResponse> categoriesResponseCall = webServices.removeFromWishList(token, userId, wishListRequest);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<WishListDataResponse> getWishList(ResponseResolver<WishListDataResponse> apiResponseResponseResolve, boolean isAuth, String token, String userId, String uuid) {        Call<WishListDataResponse> categoriesResponseCall;        if(isAuth) {            categoriesResponseCall = webServices.getWishList(token, userId, uuid);        }        else {            categoriesResponseCall = webServices.getWishList(userId, uuid);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    //Coupons    public Call<UpdateCartResponse> applyCoupon(ResponseResolver<UpdateCartResponse> apiResponseResponseResolve, boolean isAuthUser, String token, ApplyCouponRequest applyCouponRequest) {        Call<UpdateCartResponse> categoriesResponseCall;        if (isAuthUser) {            categoriesResponseCall = webServices.applyCoupon(token, applyCouponRequest);        } else {            categoriesResponseCall = webServices.applyCoupon(applyCouponRequest);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }   /* public Call<UpdateCartResponse> applyCoupon(ResponseResolver<UpdateCartResponse> apiResponseResponseResolve, String token, String uuid,String code, String cart_value,String cart_id) {        Call<UpdateCartResponse> categoriesResponseCall = webServices.applyCoupon(token,uuid,code,cart_value,cart_id);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }*/    public Call<CouponListResponse> getListOfCoupons(ResponseResolver<CouponListResponse> apiResponseResponseResolve,boolean isAuthUser, String token, String uuid, String cart_id) {        Call<CouponListResponse> categoriesResponseCall;        if(isAuthUser) {           categoriesResponseCall = webServices.getListOfCoupon(token, uuid, cart_id);        }        else {            categoriesResponseCall = webServices.getListOfCoupon(uuid, cart_id);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    } /*   public Call<CouponListResponse> getListOfCoupons(ResponseResolver<CouponListResponse> apiResponseResponseResolve, String uuid, String cart_id) {        Call<CouponListResponse> categoriesResponseCall = webServices.getListOfCoupon(uuid, cart_id);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }*/    public Call<CouponInfoResponse> getCouponDetail(ResponseResolver<CouponInfoResponse> apiResponseResponseResolve,boolean isAuthUser, String token, String coupon_id, String uuid) {        Call<CouponInfoResponse> categoriesResponseCall;        if(isAuthUser) {           categoriesResponseCall = webServices.getCouponDetail(token, coupon_id, uuid);        }        else {          categoriesResponseCall = webServices.getCouponDetail(coupon_id, uuid);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }  /*  public Call<CouponInfoResponse> getCouponDetail(ResponseResolver<CouponInfoResponse> apiResponseResponseResolve, String coupon_id, String uuid) {        Call<CouponInfoResponse> categoriesResponseCall = webServices.getCouponDetail(coupon_id, uuid);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }*/    public Call<UpdateCartResponse> removeCoupon(ResponseResolver<UpdateCartResponse> apiResponseResponseResolve,boolean isAuthUser, String token, CouponRequest couponRequest) {        Call<UpdateCartResponse> categoriesResponseCall;        if(isAuthUser) {             categoriesResponseCall = webServices.removeCoupon(token, couponRequest);        }        else {             categoriesResponseCall = webServices.removeCoupon(couponRequest);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    } /*   public Call<UpdateCartResponse> removeCoupon(ResponseResolver<UpdateCartResponse> apiResponseResponseResolve, CouponRequest couponRequest) {        Call<UpdateCartResponse> categoriesResponseCall = webServices.removeCoupon(couponRequest);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }*/    public Call<CartHasItemResponse> getCartHasItem(ResponseResolver<CartHasItemResponse> apiResponseResponseResolve, boolean isAuthUser, String token,String userId, GenRequest genRequest) {        Call<CartHasItemResponse> categoriesResponseCall;        if(isAuthUser) {            categoriesResponseCall = webServices.getCartHasItem(token, userId,genRequest);        }        else {            categoriesResponseCall = webServices.getCartHasItem(userId,genRequest);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<ProductListResponse> getProductList(ResponseResolver<ProductListResponse> apiResponseResponseResolve, boolean isAuthUser, String token, String uuid,String pageNo,String perPage) {        Call<ProductListResponse> categoriesResponseCall;        if(isAuthUser) {            categoriesResponseCall = webServices.getProductList(token, uuid,pageNo,perPage);        }        else {            categoriesResponseCall = webServices.getProductList( uuid,pageNo,perPage);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<ProductListResponse> getProductList(ResponseResolver<ProductListResponse> apiResponseResponseResolve, boolean isAuthUser, String token, String uuid,String pageNo,                                                    String perPage,String category_id,String from, String to,String orderBy,String orderField) {        Call<ProductListResponse> categoriesResponseCall;        if(isAuthUser) {            categoriesResponseCall = webServices.getProductList(token, uuid,category_id,from,to,orderBy,orderField,pageNo,perPage);        }        else {            categoriesResponseCall = webServices.getProductList(uuid,category_id,from,to,orderBy,orderField,pageNo,perPage);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<ProductListResponse> getAllProducts(ResponseResolver<ProductListResponse> apiResponseResponseResolve, boolean isAuthUser, String token, String uuid,String pageNo,                                                    String perPage,String from, String to,String orderBy,String orderField) {        Call<ProductListResponse> categoriesResponseCall;        if(isAuthUser) {            categoriesResponseCall = webServices.getAllProducts(token, uuid,from,to,orderBy,orderField,pageNo,perPage);        }        else {            categoriesResponseCall = webServices.getAllProducts(uuid,from,to,orderBy,orderField,pageNo,perPage);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<ProductListResponse> getAllProductsFilter(ResponseResolver<ProductListResponse> apiResponseResponseResolve, boolean isAuthUser, String token, List<Integer> category_id, String uuid, String pageNo,                                                          String perPage, String from, String to, String orderBy, String orderField) {        Call<ProductListResponse> categoriesResponseCall;        if(isAuthUser) {            categoriesResponseCall = webServices.getAllProducts(token,category_id, uuid,from,to,orderBy,orderField,pageNo,perPage);        }        else {            categoriesResponseCall = webServices.getAllProducts(category_id,uuid,from,to,orderBy,orderField,pageNo,perPage);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<ProductListResponse> getAllProductsFilter(ResponseResolver<ProductListResponse> apiResponseResponseResolve, boolean isAuthUser, String token, List<Integer> category_id, List<Integer> brand_id,List<Integer> sub_cat_id,String uuid, String pageNo,                                                          String perPage, String from, String to, String orderBy, String orderField) {        Call<ProductListResponse> categoriesResponseCall;        if(isAuthUser) {            categoriesResponseCall = webServices.getAllProducts(token,category_id,brand_id,sub_cat_id, uuid,from,to,orderBy,orderField,pageNo,perPage);        }        else {            categoriesResponseCall = webServices.getAllProducts(category_id,brand_id,sub_cat_id,uuid,from,to,orderBy,orderField,pageNo,perPage);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<AddressAddResponse> AddAddress(ResponseResolver<AddressAddResponse> apiResponseResponseResolve, String token, String userId, AddressRequest addressRequest) {        Call<AddressAddResponse> categoriesResponseCall = webServices.AddAddress(token, userId, addressRequest);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<AddressAddResponse> EditAddress(ResponseResolver<AddressAddResponse> apiResponseResponseResolve, String token, String userId,String id, AddressRequest addressRequest) {        Call<AddressAddResponse> categoriesResponseCall = webServices.EditAddress(token, userId,id, addressRequest);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<AddressResponse> getAddressList(ResponseResolver<AddressResponse> apiResponseResponseResolve, String token, String userId) {        Call<AddressResponse> categoriesResponseCall = webServices.getAddresses(token, userId);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<AddressAddResponse> removeAddress(ResponseResolver<AddressAddResponse> apiResponseResponseResolve, String token, String userId,String id) {        Call<AddressAddResponse> categoriesResponseCall = webServices.removeAddress(token, userId,id);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<RecentLocationResponse> getRecentLocations(ResponseResolver<RecentLocationResponse> apiResponseResponseResolve, boolean isAuthUser, String token, String uuid, String user_id) {        Call<RecentLocationResponse> categoriesResponseCall;        if(isAuthUser) {            categoriesResponseCall = webServices.getRecentLocation(token,user_id,uuid);        }        else {            categoriesResponseCall = webServices.getRecentLocation(user_id,uuid);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<SearchResponse> getRecentSearches(ResponseResolver<SearchResponse> apiResponseResponseResolve, boolean isAuthUser, String token, String uuid) {        Call<SearchResponse> categoriesResponseCall;        if(isAuthUser) {            categoriesResponseCall = webServices.getRecentSearches(token,uuid);        }        else {            categoriesResponseCall = webServices.getRecentSearches(uuid);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<SearchResultsResponse> getSearchResults(ResponseResolver<SearchResultsResponse> apiResponseResponseResolve, boolean isAuthUser, String token, String uuid, String query) {        Call<SearchResultsResponse> categoriesResponseCall;        if(isAuthUser) {            categoriesResponseCall = webServices.getSearchResults(token,uuid,query);        }        else {            categoriesResponseCall = webServices.getSearchResults(uuid,query);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<SearchProductResponse> getFetchRecordOfSearch(ResponseResolver<SearchProductResponse> apiResponseResponseResolve, boolean isAuthUser, String token, String uuid, String query, String class_name, String class_id, String results_count) {        Call<SearchProductResponse> categoriesResponseCall;        if(isAuthUser) {            categoriesResponseCall = webServices.getFetchRecordOfSearch(token,uuid,class_name,class_id,query,results_count);        }        else {            categoriesResponseCall = webServices.getFetchRecordOfSearch(uuid,class_name,class_id,query,results_count);        }        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<NotificationResponse> getNotificationList(ResponseResolver<NotificationResponse> apiResponseResponseResolve, String token, String userId, String page, String perPage) {        Call<NotificationResponse> categoriesResponseCall = webServices.getNotificationList(token, userId,page,perPage);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<GuestUserCreateResponse> deleteNotification(ResponseResolver<GuestUserCreateResponse> apiResponseResponseResolve, String token, String userId, String notification_id) {        Call<GuestUserCreateResponse> categoriesResponseCall = webServices.deleteNotification(token, userId,notification_id);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<GuestUserCreateResponse> clearAllNotification(ResponseResolver<GuestUserCreateResponse> apiResponseResponseResolve, String token, String userId) {        Call<GuestUserCreateResponse> categoriesResponseCall = webServices.clearAllNotification(token, userId);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<UpdateStoreResponse> addStore(ResponseResolver<UpdateStoreResponse> apiResponseResponseResolve, AddStore addStore) {        Call<UpdateStoreResponse> categoriesResponseCall = webServices.addBecomePartner(addStore);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<GuestUserCreateResponse> updateProfile(ResponseResolver<GuestUserCreateResponse> apiResponseResponseResolve, String token, String userId, MultipartBody.Part part, RequestBody name,RequestBody phone) {        Call<GuestUserCreateResponse> categoriesResponseCall = webServices.updateProfile(token,userId,part,name,phone);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<GuestUserCreateResponse> updateProfile(ResponseResolver<GuestUserCreateResponse> apiResponseResponseResolve, String token, String userId, ProfileUpdateRequest profileUpdateRequest) {        Call<GuestUserCreateResponse> categoriesResponseCall = webServices.updateProfile(token,userId,profileUpdateRequest);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<CardListResponse> getCardList(ResponseResolver<CardListResponse> apiResponseResponseResolve, String token, String userId) {        Call<CardListResponse> categoriesResponseCall = webServices.getCardList(token,userId);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<CardAddResponse> addCard(ResponseResolver<CardAddResponse> apiResponseResponseResolve, String token, String userId, AddCardRequest addCardRequest) {        Call<CardAddResponse> categoriesResponseCall = webServices.addCard(token,userId,addCardRequest);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<CardAddResponse> deleteCard(ResponseResolver<CardAddResponse> apiResponseResponseResolve, String token, String userId, DeleteCardRequest deleteCardRequest) {        Call<CardAddResponse> categoriesResponseCall = webServices.deleteCard(token,userId,deleteCardRequest);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }    public Call<OrderListResponse> getOrderList(ResponseResolver<OrderListResponse> apiResponseResponseResolve, String token, String userId) {        Call<OrderListResponse> categoriesResponseCall = webServices.getOrderList(token,userId);        categoriesResponseCall.enqueue(apiResponseResponseResolve);        return categoriesResponseCall;    }}