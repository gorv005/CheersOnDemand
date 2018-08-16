package com.cheersondemand.presenter.home.order;

import android.content.Context;

import com.cheersondemand.intractor.home.cart.IOrderViewIntractor;
import com.cheersondemand.intractor.home.cart.OrderViewIntractorImpl;
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
            mView.hideProgress();
            mView.getCreateOrderSuccess(response);
        }
    }

    @Override
    public void onSuccessAddToCart(AddToCartResponse response) {
        if (mView != null) {
            mView.hideProgress();
            mView.getAddToCartSucess(response);
        }
    }

    @Override
    public void onSuccessUpdateCart(UpdateCartResponse response) {
        if (mView != null) {
            mView.hideProgress();
            mView.getUpdateCartSuccess(response);
        }
    }

    @Override
    public void onSuccessRemoveCart(UpdateCartResponse response) {
        if (mView != null) {
            mView.hideProgress();
            mView.getRemoveItemFromCartSuccess(response);
        }
    }

    @Override
    public void onSuccessCartList(UpdateCartResponse response) {
        if (mView != null) {
            mView.hideProgress();
            mView.getCartListSuccess(response);
        }
    }

    @Override
    public void onSuccessAddToWishList(WishListResponse response) {
        if (mView != null) {
            mView.hideProgress();
            mView.addTowishListSuccess(response);
        }
    }

    @Override
    public void onSuccessRemoveFromWishList(WishListResponse response) {
        if (mView != null) {
            mView.hideProgress();
            mView.removeFromWishListSuccess(response);
        }
    }

    @Override
    public void onSuccess(CartHasItemResponse response) {
        if (hasItemView != null) {
            hasItemView.hideProgress();
            hasItemView.getCartHasItemSuccess(response);
        }
    }

    @Override
    public void onSuccessWishList(WishListDataResponse response) {
        if (mView != null) {
            mView.hideProgress();
            mView.getWishListSuccess(response);
        }
    }

    @Override
    public void onError(String response) {
        if (mView != null) {
            mView.hideProgress();
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
            mView.showProgress();

            iOrderViewIntractor.createOrder(user_id,uuid, this);
        }
    }

    @Override
    public void createOrder(String token, String user_id, GenRequest uuid) {
        if (mView != null) {
            mView.showProgress();

            iOrderViewIntractor.createOrder(token,user_id,uuid, this);
        }
    }

    @Override
    public void addToCart(String user_id, String order_id, AddToCartRequest addToCartRequest) {
        if (mView != null) {
            mView.showProgress();

            iOrderViewIntractor.addToCart(user_id,order_id,addToCartRequest, this);
        }
    }

    @Override
    public void addToCart(String token, String user_id, String order_id, AddToCartRequest addToCartRequest) {
        if (mView != null) {
            mView.showProgress();

            iOrderViewIntractor.addToCart(token,user_id,order_id,addToCartRequest, this);
        }
    }

    @Override
    public void updateCart(String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest) {
        if (mView != null) {
            mView.showProgress();

            iOrderViewIntractor.updateCart(user_id,order_id,updateProductQuantityRequest, this);
        }
    }

    @Override
    public void updateCart(String token, String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest) {
        if (mView != null) {
            mView.showProgress();

            iOrderViewIntractor.updateCart(token,user_id,order_id,updateProductQuantityRequest, this);
        }
    }

    @Override
    public void removeItemFromCart(String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest) {
        if (mView != null) {
            mView.showProgress();

            iOrderViewIntractor.removeItemFromCart(user_id,order_id,updateProductQuantityRequest, this);
        }
    }

    @Override
    public void removeItemFromCart(String token, String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest) {
        if (mView != null) {
            mView.showProgress();

            iOrderViewIntractor.removeItemFromCart(token,user_id,order_id,updateProductQuantityRequest, this);
        }
    }

    @Override
    public void getCartList(String user_id, String order_id, String uuid,boolean isFromPayment) {
        if (mView != null) {
            mView.showProgress();
            iOrderViewIntractor.getCartList(user_id,order_id,uuid,isFromPayment, this);
        }
    }

    @Override
    public void getCartList(String token, String user_id, String order_id, String uuid,boolean isFromPayment) {
        if (mView != null) {
            mView.showProgress();
            iOrderViewIntractor.getCartList(token,user_id,order_id,uuid, isFromPayment,this);
        }
    }

    @Override
    public void addToWishList(String user_id, WishListRequest wishListRequest) {
        if (mView != null) {
            mView.showProgress();

            iOrderViewIntractor.addToWishList(user_id,wishListRequest, this);
        }
    }

    @Override
    public void addToWishList(String token, String user_id, WishListRequest wishListRequest) {
        if (mView != null) {
            mView.showProgress();

            iOrderViewIntractor.addToWishList(token,user_id,wishListRequest, this);
        }
    }

    @Override
    public void removeFromWishList(String user_id, WishListRequest wishListRequest) {
        if (mView != null) {
            mView.showProgress();

            iOrderViewIntractor.removeFromWishList(user_id,wishListRequest, this);
        }
    }

    @Override
    public void removeFromWishList(String token, String user_id, WishListRequest wishListRequest) {
        if (mView != null) {
            mView.showProgress();

            iOrderViewIntractor.removeFromWishList(token,user_id,wishListRequest, this);
        }
    }

    @Override
    public void getCartHasItem(boolean isAuth, String token, String user_id, GenRequest genRequest) {
        if (hasItemView != null) {
            mView.showProgress();

            iOrderViewIntractor.getCartHasItem(isAuth,token,user_id,genRequest, this);
        }
    }

    @Override
    public void getWishList(boolean isAuthUser, String token, String UserId, String uuid) {
        if (mView != null) {
            mView.showProgress();

            iOrderViewIntractor.getWishList(isAuthUser,token,UserId,uuid, this);
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
