package com.cheersondemand.frameworks.retrofit;import com.cheersondemand.model.CategoriesResponse;import com.cheersondemand.model.GuestUserCreateResponse;import com.cheersondemand.model.ProductsWithCategoryResponse;import com.cheersondemand.model.authentication.AuthenticationResponse;import com.cheersondemand.model.authentication.GenRequest;import com.cheersondemand.model.authentication.LoginRequest;import com.cheersondemand.model.authentication.SignUpRequest;import com.cheersondemand.model.authentication.SocialLoginRequest;import com.cheersondemand.model.coupon.CouponInfoResponse;import com.cheersondemand.model.coupon.CouponListResponse;import com.cheersondemand.model.coupon.CouponRequest;import com.cheersondemand.model.location.SaveLocation;import com.cheersondemand.model.location.SaveLocationResponse;import com.cheersondemand.model.logout.LogoutRequest;import com.cheersondemand.model.logout.LogoutResponse;import com.cheersondemand.model.order.CreateOrderResponse;import com.cheersondemand.model.order.addtocart.AddToCartRequest;import com.cheersondemand.model.order.addtocart.AddToCartResponse;import com.cheersondemand.model.order.updatecart.UpdateCartRequest;import com.cheersondemand.model.order.updatecart.UpdateCartResponse;import com.cheersondemand.model.productdescription.SimilarProductsResponse;import com.cheersondemand.model.store.StoreListResponse;import com.cheersondemand.model.store.UpdateStore;import com.cheersondemand.model.store.UpdateStoreResponse;import com.cheersondemand.model.wishlist.WishListRequest;import com.cheersondemand.model.wishlist.WishListResponse;import retrofit2.Call;import retrofit2.http.Body;import retrofit2.http.GET;import retrofit2.http.HTTP;import retrofit2.http.Header;import retrofit2.http.POST;import retrofit2.http.PUT;import retrofit2.http.Path;import retrofit2.http.Query;public interface WebServices {    @POST("/cheers_on_demand/api/v1/users")    Call<AuthenticationResponse> signUpUsingEmail(@Body SignUpRequest signUpRequest);    @POST("/cheers_on_demand/api/v1/oauth/token")    Call<AuthenticationResponse> loginUsingEmail(@Body LoginRequest loginRequest);    @POST("/cheers_on_demand/api/v1/oauth/token")    Call<AuthenticationResponse> loginUsingSocial(@Body SocialLoginRequest socialLoginRequest);    @POST("/cheers_on_demand/api/v1/categories")    Call<CategoriesResponse> getBrands(@Body GenRequest uuid);    @GET("/cheers_on_demand/api/v1/categories")    Call<CategoriesResponse> getGuestBrands(@Query("uuid") String uuid);    //LANDING SCREEN    @GET("/cheers_on_demand/api/v1/products")    Call<ProductsWithCategoryResponse> getProductsWithCategories(@Query("uuid") String uuid);    @GET("/cheers_on_demand/api/v1/products")    Call<ProductsWithCategoryResponse> getProductsWithCategories(@Header("Authorization") String token, @Query("uuid") String uuid);    @POST("/cheers_on_demand/api/v1/users/create_guest_user")    Call<GuestUserCreateResponse> createGuestUser(@Body GenRequest uuid);    @POST("/cheers_on_demand/api/v1/categories")    Call<CategoriesResponse> getBrands(@Header("Authorization") String token, @Body GenRequest uuid);    @POST("/cheers_on_demand/api/v1/oauth/revoke")    Call<LogoutResponse> Logout(@Body LogoutRequest logoutRequest);    //STORE    @GET("/cheers_on_demand/api/v1/stores/available_stores_list")    Call<StoreListResponse> getStoreList(@Query("uuid") String uuid);    @GET("/cheers_on_demand/api/v1/stores/available_stores_list")    Call<StoreListResponse> getStoreList(@Header("Authorization") String token, @Query("uuid") String uuid);    @PUT("/cheers_on_demand/api/v1/users/{user_id}/update_current_store")    Call<UpdateStoreResponse> updateStore(@Path("user_id") String user_id, @Body UpdateStore updateStore);    @PUT("/cheers_on_demand/api/v1/users/{user_id}/update_current_store")    Call<UpdateStoreResponse> updateStore(@Header("Authorization") String token, @Path("user_id") String user_id, @Body UpdateStore updateStore);    //SAVE LOCATION    @PUT("/cheers_on_demand/api/v1/users/{user_id}/save_user_location")    Call<SaveLocationResponse> saveLocation(@Path("user_id") String user_id, @Body SaveLocation saveLocation);    @PUT("/cheers_on_demand/api/v1/users/{user_id}/save_user_location")    Call<SaveLocationResponse> saveLocation(@Header("Authorization") String token, @Path("user_id") String user_id, @Body SaveLocation saveLocation);    //SIMILAR PRODUCT    @GET("/cheers_on_demand/api/v1/products/{product_id}")    Call<SimilarProductsResponse> getSimilarProducts(@Path("product_id") String product_id, @Query("uuid") String uuid, @Query("page") String page, @Query("per_page") String per_page);    @GET("/cheers_on_demand/api/v1/products/{product_id}")    Call<SimilarProductsResponse> getSimilarProductsAuth(@Header("Authorization") String token, @Path("product_id") String product_id, @Query("page") String page, @Query("per_page") String per_page);    //Create Order    @POST("/cheers_on_demand/api/v1/users/{user_id}/carts")    Call<CreateOrderResponse> createOrder(@Path("user_id") String user_id, @Body GenRequest uuid);    @POST("/cheers_on_demand/api/v1/users/{user_id}/carts")    Call<CreateOrderResponse> createOrder(@Header("Authorization") String token, @Path("user_id") String user_id, @Body GenRequest uuid);    //ADD To Cart    @POST("/cheers_on_demand/api/v1/users/{user_id}/carts/{order_id}/add_cart_item")    Call<AddToCartResponse> addToCart(@Path("user_id") String user_id, @Path("order_id") String order_id, @Body AddToCartRequest addToCartRequest);    @POST("/cheers_on_demand/api/v1/users/{user_id}/carts/{order_id}/add_cart_item")    Call<AddToCartResponse> addToCart(@Header("Authorization") String token, @Path("user_id") String user_id, @Path("order_id") String order_id, @Body AddToCartRequest addToCartRequest);    //Update Cart    @PUT("/cheers_on_demand/api/v1/users/{user_id}/carts/{order_id}/update_cart_item")    Call<UpdateCartResponse> updateCart(@Path("user_id") String user_id, @Path("order_id") String order_id, @Body UpdateCartRequest updateProductQuantityRequest);    @PUT("/cheers_on_demand/api/v1/users/{user_id}/carts/{order_id}/update_cart_item")    Call<UpdateCartResponse> updateCart(@Header("Authorization") String token, @Path("user_id") String user_id, @Path("order_id") String order_id, @Body UpdateCartRequest updateProductQuantityRequest);    //  @DELETE("/cheers_on_demand/api/v1/users/{user_id}/carts/{order_id}/remove_cart_item")     //DELETE PRODUCT    @HTTP(method = "DELETE", path = "/cheers_on_demand/api/v1/users/{user_id}/carts/{order_id}/remove_cart_item", hasBody = true)    Call<UpdateCartResponse> removeItemFromCart(@Path("user_id") String user_id, @Path("order_id") String order_id, @Body UpdateCartRequest updateProductQuantityRequest);    @HTTP(method = "DELETE", path = "/cheers_on_demand/api/v1/users/{user_id}/carts/{order_id}/remove_cart_item", hasBody = true)    Call<UpdateCartResponse> removeItemFromCart(@Header("Authorization") String token, @Path("user_id") String user_id, @Path("order_id") String order_id, @Body UpdateCartRequest updateProductQuantityRequest);    // CART LIST    @GET("/cheers_on_demand/api/v1/users/{user_id}/carts/{order_id}")    Call<UpdateCartResponse> getCartList( @Path("user_id") String user_id, @Path("order_id") String order_id,@Query("uuid") String uuid);    @GET("/cheers_on_demand/api/v1/users/{user_id}/carts/{order_id}")    Call<UpdateCartResponse> getCartList(@Header("Authorization") String token, @Path("user_id") String user_id, @Path("order_id") String order_id, @Query("uuid") String uuid);    // WishList    @POST("/cheers_on_demand/api/v1/users/{user_id}/wishlists")    Call<WishListResponse> addToWishList(@Path("user_id") String user_id, @Body WishListRequest wishListRequest);    @POST("/cheers_on_demand/api/v1/users/{user_id}/wishlists")    Call<WishListResponse> addToWishList(@Header("Authorization") String token,@Path("user_id") String user_id, @Body WishListRequest wishListRequest);    //DELETE PRODUCT    @HTTP(method = "DELETE", path = "/cheers_on_demand/api/v1/users/{user_id}/wishlists", hasBody = true)    Call<WishListResponse> removeFromWishList(@Path("user_id") String user_id, @Body WishListRequest wishListRequest);    @HTTP(method = "DELETE", path = "/cheers_on_demand/api/v1/users/{user_id}/wishlists", hasBody = true)    Call<WishListResponse> removeFromWishList(@Header("Authorization") String token,@Path("user_id") String user_id, @Body WishListRequest wishListRequest);    @GET("/cheers_on_demand/api/v1/users/{user_id}/wishlists")    Call<WishListResponse> getWishList(@Path("user_id") String user_id,@Query("uuid") String uuid);    @GET("/cheers_on_demand/api/v1/users/{user_id}/wishlists")    Call<WishListResponse> getWishList(@Header("Authorization") String token,@Path("user_id") String user_id, @Query("uuid") String uuid);    // COUPONS    @GET("/cheers_on_demand/api/v1/coupons/apply_coupon")    Call<UpdateCartResponse> applyCoupon( @Query("uuid") String uuid,@Query("code") String code,@Query("cart_value") String cart_value,@Query("cart_id") String cart_id);    @GET("/cheers_on_demand/api/v1/coupons/apply_coupon")    Call<UpdateCartResponse> applyCoupon(@Header("Authorization") String token, @Query("uuid") String uuid,@Query("code") String code,@Query("cart_value") String cart_value,@Query("cart_id") String cart_id);    @GET("/cheers_on_demand/api/v1/coupons")    Call<CouponListResponse> getListOfCoupon( @Query("uuid") String uuid,@Query("cart_id") String cart_id);    @GET("/cheers_on_demand/api/v1/coupons")    Call<CouponListResponse> getListOfCoupon(@Header("Authorization") String token, @Query("uuid") String uuid, @Query("cart_id") String cart_id);    @GET("/cheers_on_demand/api/v1/coupons/{coupon_id}")    Call<CouponInfoResponse> getCouponDetail(@Path("coupon_id") String coupon_id,@Query("uuid") String uuid);    @GET("/cheers_on_demand/api/v1/coupons/{coupon_id}")    Call<CouponInfoResponse> getCouponDetail(@Header("Authorization") String token, @Path("coupon_id") String coupon_id, @Query("uuid") String uuid);    @HTTP(method = "DELETE", path = "/cheers_on_demand/api/v1/coupons/remove_coupon", hasBody = true)    Call<UpdateCartResponse> removeCoupon(@Header("Authorization") String token, @Body CouponRequest couponRequest);    @HTTP(method = "DELETE", path = "/cheers_on_demand/api/v1/coupons/remove_coupon", hasBody = true)    Call<UpdateCartResponse> removeCoupon( @Body CouponRequest couponRequest);}