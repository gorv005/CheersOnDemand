package com.cheersondemand.intractor.home.cart;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.authentication.GenRequest;
import com.cheersondemand.model.order.CreateOrderResponse;
import com.cheersondemand.model.order.addtocart.AddToCartRequest;
import com.cheersondemand.model.order.addtocart.AddToCartResponse;
import com.cheersondemand.model.order.addtocart.CartHasItemResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartRequest;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.cheersondemand.model.wishlist.WishListDataResponse;
import com.cheersondemand.model.wishlist.WishListRequest;
import com.cheersondemand.model.wishlist.WishListResponse;
import com.google.gson.Gson;

import retrofit2.Response;

/**
 * Created by AB on 7/31/2017.
 */

public class OrderViewIntractorImpl implements IOrderViewIntractor {

    @Override
    public void createOrder(String user_id, GenRequest uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().createOrder(new ResponseResolver<CreateOrderResponse>() {
                @Override
                public void onSuccess(CreateOrderResponse createOrderResponse, Response response) {
                    listener.onSuccess(createOrderResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {

                    if (error == null || error.getError() == null) {
                        try {


                            Gson gson = new Gson();
                            CreateOrderResponse createOrderResponse = gson.fromJson(msg, CreateOrderResponse.class);

                            listener.onSuccess(createOrderResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }

            }, user_id, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createOrder(String token, String user_id, GenRequest uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().createOrder(new ResponseResolver<CreateOrderResponse>() {
                @Override
                public void onSuccess(CreateOrderResponse createOrderResponse, Response response) {
                    listener.onSuccess(createOrderResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            CreateOrderResponse createOrderResponse = gson.fromJson(msg, CreateOrderResponse.class);

                            listener.onSuccess(createOrderResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }

                }
            }, token, user_id, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToCart(String user_id, String order_id, AddToCartRequest addToCartRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().addToCart(new ResponseResolver<AddToCartResponse>() {
                @Override
                public void onSuccess(AddToCartResponse addToCartResponse, Response response) {
                    listener.onSuccessAddToCart(addToCartResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            AddToCartResponse addToCartResponse = gson.fromJson(msg, AddToCartResponse.class);

                            listener.onSuccessAddToCart(addToCartResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, user_id, order_id, addToCartRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToCart(String token, String user_id, String order_id, AddToCartRequest addToCartRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().addToCart(new ResponseResolver<AddToCartResponse>() {
                @Override
                public void onSuccess(AddToCartResponse addToCartResponse, Response response) {
                    listener.onSuccessAddToCart(addToCartResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            AddToCartResponse addToCartResponse = gson.fromJson(msg, AddToCartResponse.class);

                            listener.onSuccessAddToCart(addToCartResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, token, user_id, order_id, addToCartRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCart(String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().updateCart(new ResponseResolver<UpdateCartResponse>() {
                @Override
                public void onSuccess(UpdateCartResponse updateProductQuantityResponse, Response response) {
                    listener.onSuccessUpdateCart(updateProductQuantityResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            UpdateCartResponse response = gson.fromJson(msg, UpdateCartResponse.class);

                            listener.onSuccessUpdateCart(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, user_id, order_id, updateProductQuantityRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCart(String token, String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().updateCart(new ResponseResolver<UpdateCartResponse>() {
                @Override
                public void onSuccess(UpdateCartResponse updateProductQuantityResponse, Response response) {
                    listener.onSuccessUpdateCart(updateProductQuantityResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            UpdateCartResponse response = gson.fromJson(msg, UpdateCartResponse.class);

                            listener.onSuccessUpdateCart(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, token, user_id, order_id, updateProductQuantityRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeItemFromCart(String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().removeItemFromCart(new ResponseResolver<UpdateCartResponse>() {
                @Override
                public void onSuccess(UpdateCartResponse updateProductQuantityResponse, Response response) {
                    listener.onSuccessRemoveCart(updateProductQuantityResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            UpdateCartResponse response = gson.fromJson(msg, UpdateCartResponse.class);

                            listener.onSuccessRemoveCart(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, user_id, order_id, updateProductQuantityRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeItemFromCart(String token, String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().removeItemFromCart(new ResponseResolver<UpdateCartResponse>() {
                @Override
                public void onSuccess(UpdateCartResponse updateProductQuantityResponse, Response response) {
                    listener.onSuccessRemoveCart(updateProductQuantityResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            UpdateCartResponse response = gson.fromJson(msg, UpdateCartResponse.class);

                            listener.onSuccessRemoveCart(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, token, user_id, order_id, updateProductQuantityRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCartList(String user_id, String order_id, String uuid, boolean isFromPayment, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getCartList(new ResponseResolver<UpdateCartResponse>() {
                @Override
                public void onSuccess(UpdateCartResponse updateProductQuantityResponse, Response response) {
                    listener.onSuccessCartList(updateProductQuantityResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            UpdateCartResponse response = gson.fromJson(msg, UpdateCartResponse.class);

                            listener.onSuccessCartList(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, user_id, order_id, uuid, isFromPayment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCartList(String token, String user_id, String order_id, String uuid, boolean isFromPayment, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getCartList(new ResponseResolver<UpdateCartResponse>() {
                @Override
                public void onSuccess(UpdateCartResponse updateProductQuantityResponse, Response response) {
                    listener.onSuccessCartList(updateProductQuantityResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            UpdateCartResponse response = gson.fromJson(msg, UpdateCartResponse.class);

                            listener.onSuccessCartList(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, token, user_id, order_id, uuid, isFromPayment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToWishList(String user_id, WishListRequest wishListRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().addToWishList(new ResponseResolver<WishListResponse>() {
                @Override
                public void onSuccess(WishListResponse wishListResponse, Response response) {
                    listener.onSuccessAddToWishList(wishListResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            WishListResponse response = gson.fromJson(msg, WishListResponse.class);

                            listener.onSuccessAddToWishList(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, user_id, wishListRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToWishList(String token, String user_id, WishListRequest wishListRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().addToWishList(new ResponseResolver<WishListResponse>() {
                @Override
                public void onSuccess(WishListResponse wishListResponse, Response response) {
                    listener.onSuccessAddToWishList(wishListResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            WishListResponse response = gson.fromJson(msg, WishListResponse.class);

                            listener.onSuccessAddToWishList(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, token, user_id, wishListRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFromWishList(String user_id, WishListRequest wishListRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().removeFromWishList(new ResponseResolver<WishListResponse>() {
                @Override
                public void onSuccess(WishListResponse wishListResponse, Response response) {
                    listener.onSuccessRemoveFromWishList(wishListResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            WishListResponse response = gson.fromJson(msg, WishListResponse.class);

                            listener.onSuccessAddToWishList(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, user_id, wishListRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFromWishList(String token, String user_id, WishListRequest wishListRequest, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().removeFromWishList(new ResponseResolver<WishListResponse>() {
                @Override
                public void onSuccess(WishListResponse wishListResponse, Response response) {
                    listener.onSuccessRemoveFromWishList(wishListResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            WishListResponse response = gson.fromJson(msg, WishListResponse.class);

                            listener.onSuccessAddToWishList(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, token, user_id, wishListRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getWishList(boolean isAuthUser, String token, String UserId, String uuid, final onCartResponseFinishListner listener) {
        try {

            WebServicesWrapper.getInstance().getWishList(new ResponseResolver<WishListDataResponse>() {
                @Override
                public void onSuccess(WishListDataResponse r, Response response) {
                    listener.onSuccessWishList(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            WishListDataResponse response = gson.fromJson(msg, WishListDataResponse.class);
                            listener.onSuccessWishList(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, isAuthUser, token, UserId, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCartHasItem(boolean isAuthUser, String token, String UserId, GenRequest genRequest, final onCartResponseFinishListner listener) {
        try {

            WebServicesWrapper.getInstance().getCartHasItem(new ResponseResolver<CartHasItemResponse>() {
                @Override
                public void onSuccess(CartHasItemResponse cartHasItemResponse, Response response) {
                    listener.onSuccess(cartHasItemResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            CartHasItemResponse response = gson.fromJson(msg, CartHasItemResponse.class);

                            listener.onSuccess(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, isAuthUser, token, UserId, genRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMinimumOrderAmount(boolean isAuthUser, String token, String user_id, String order_id, String uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getMinimumOrderAmount(new ResponseResolver<WishListResponse>() {
                @Override
                public void onSuccess(WishListResponse updateProductQuantityResponse, Response response) {
                    listener.onSuccessMinimumOrderAmount(updateProductQuantityResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            WishListResponse response = gson.fromJson(msg, WishListResponse.class);

                            listener.onSuccessMinimumOrderAmount(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            },isAuthUser, token, user_id, order_id, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
