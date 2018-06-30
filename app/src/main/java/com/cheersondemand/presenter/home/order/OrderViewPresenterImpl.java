package com.cheersondemand.presenter.home.order;

import android.content.Context;

import com.cheersondemand.intractor.home.order.IOrderViewIntractor;
import com.cheersondemand.intractor.home.order.OrderViewIntractorImpl;
import com.cheersondemand.model.order.addtocart.AddToCartRequest;
import com.cheersondemand.model.order.addtocart.AddToCartResponse;
import com.cheersondemand.model.authentication.GenRequest;
import com.cheersondemand.model.order.CreateOrderResponse;
import com.cheersondemand.model.order.addtocart.CartHasItemResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartRequest;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.cheersondemand.model.wishlist.WishListRequest;
import com.cheersondemand.model.wishlist.WishListResponse;


public class OrderViewPresenterImpl implements IOrderViewPresenterPresenter, IOrderViewIntractor.OnLoginFinishedListener,IOrderViewIntractor.onCartResponseFinishListner {

    IOrderView mView;
    ICartHasItem hasItemView;
    Context context;
    IOrderViewIntractor iOrderViewIntractor;

    public OrderViewPresenterImpl(IOrderView mView, Context context) {
        this.mView = mView;
        this.context = context;
        iOrderViewIntractor = new OrderViewIntractorImpl();

    }
    public OrderViewPresenterImpl(ICartHasItem mView, Context context) {
        this.hasItemView = mView;
        this.context = context;
        iOrderViewIntractor = new OrderViewIntractorImpl();

    }

    @Override
    public void onSuccess(CreateOrderResponse response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.getCreateOrderSuccess(response);
        }
    }

    @Override
    public void onSuccessAddToCart(AddToCartResponse response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.getAddToCartSucess(response);
        }
    }

    @Override
    public void onSuccessUpdateCart(UpdateCartResponse response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.getUpdateCartSuccess(response);
        }
    }

    @Override
    public void onSuccessRemoveCart(UpdateCartResponse response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.getRemoveItemFromCartSuccess(response);
        }
    }

    @Override
    public void onSuccessCartList(UpdateCartResponse response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.getCartListSuccess(response);
        }
    }

    @Override
    public void onSuccessAddToWishList(WishListResponse response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.addTowishListSuccess(response);
        }
    }

    @Override
    public void onSuccessRemoveFromWishList(WishListResponse response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.removeFromWishListSuccess(response);
        }
    }

    @Override
    public void onSuccess(CartHasItemResponse response) {
        if (hasItemView != null) {
            //mView.hideProgress();
            hasItemView.getCartHasItemSuccess(response);
        }
    }

    @Override
    public void onError(String response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.getResponseError(response);
        }
    }

    @Override
    public Context getAPPContext() {
        return context;
    }



    @Override
    public void createOrder(String user_id, GenRequest uuid) {
        if (mView != null) {

            iOrderViewIntractor.createOrder(user_id,uuid, this);
        }
    }

    @Override
    public void createOrder(String token, String user_id, GenRequest uuid) {
        if (mView != null) {

            iOrderViewIntractor.createOrder(token,user_id,uuid, this);
        }
    }

    @Override
    public void addToCart(String user_id, String order_id, AddToCartRequest addToCartRequest) {
        if (mView != null) {

            iOrderViewIntractor.addToCart(user_id,order_id,addToCartRequest, this);
        }
    }

    @Override
    public void addToCart(String token, String user_id, String order_id, AddToCartRequest addToCartRequest) {
        if (mView != null) {

            iOrderViewIntractor.addToCart(token,user_id,order_id,addToCartRequest, this);
        }
    }

    @Override
    public void updateCart(String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest) {
        if (mView != null) {

            iOrderViewIntractor.updateCart(user_id,order_id,updateProductQuantityRequest, this);
        }
    }

    @Override
    public void updateCart(String token, String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest) {
        if (mView != null) {

            iOrderViewIntractor.updateCart(token,user_id,order_id,updateProductQuantityRequest, this);
        }
    }

    @Override
    public void removeItemFromCart(String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest) {
        if (mView != null) {

            iOrderViewIntractor.removeItemFromCart(user_id,order_id,updateProductQuantityRequest, this);
        }
    }

    @Override
    public void removeItemFromCart(String token, String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest) {
        if (mView != null) {

            iOrderViewIntractor.removeItemFromCart(token,user_id,order_id,updateProductQuantityRequest, this);
        }
    }

    @Override
    public void getCartList(String user_id, String order_id, String uuid) {
        if (mView != null) {

            iOrderViewIntractor.getCartList(user_id,order_id,uuid, this);
        }
    }

    @Override
    public void getCartList(String token, String user_id, String order_id, String uuid) {
        if (mView != null) {

            iOrderViewIntractor.getCartList(token,user_id,order_id,uuid, this);
        }
    }

    @Override
    public void addToWishList(String user_id, WishListRequest wishListRequest) {
        if (mView != null) {

            iOrderViewIntractor.addToWishList(user_id,wishListRequest, this);
        }
    }

    @Override
    public void addToWishList(String token, String user_id, WishListRequest wishListRequest) {
        if (mView != null) {

            iOrderViewIntractor.addToWishList(token,user_id,wishListRequest, this);
        }
    }

    @Override
    public void removeFromWishList(String user_id, WishListRequest wishListRequest) {
        if (mView != null) {

            iOrderViewIntractor.removeFromWishList(user_id,wishListRequest, this);
        }
    }

    @Override
    public void removeFromWishList(String token, String user_id, WishListRequest wishListRequest) {
        if (mView != null) {

            iOrderViewIntractor.removeFromWishList(token,user_id,wishListRequest, this);
        }
    }

    @Override
    public void getCartHasItem(boolean isAuth, String token, String user_id, GenRequest genRequest) {
        if (mView != null) {

            iOrderViewIntractor.getCartHasItem(isAuth,token,user_id,genRequest, this);
        }
    }

    @Override
    public void onDestroy() {
        try {
            mView = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
