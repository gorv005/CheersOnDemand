package com.cheersondemand.intractor.home.order;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.order.addtocart.AddToCartRequest;
import com.cheersondemand.model.order.addtocart.AddToCartResponse;
import com.cheersondemand.model.authentication.GenRequest;
import com.cheersondemand.model.order.CreateOrderResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartRequest;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.google.gson.Gson;

import retrofit2.Response;

/**
 * Created by GAURAV on 7/31/2017.
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

                        if(error==null ||error.getError()==null){

                            Gson gson=new Gson();
                            CreateOrderResponse createOrderResponse= gson.fromJson(msg,CreateOrderResponse.class);

                            listener.onSuccess(createOrderResponse);

                        }
                        else {
                            listener.onError(error.getError());
                        }
                }

            },user_id,uuid);
        }
        catch (Exception e){
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
                        if(error==null ||error.getError()==null){

                            Gson gson=new Gson();
                            CreateOrderResponse createOrderResponse= gson.fromJson(msg,CreateOrderResponse.class);

                            listener.onSuccess(createOrderResponse);

                        }
                        else {
                            listener.onError(error.getError());
                        }

                }
            },token,user_id,uuid);
        }
        catch (Exception e){
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
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        AddToCartResponse addToCartResponse= gson.fromJson(msg,AddToCartResponse.class);

                        listener.onSuccessAddToCart(addToCartResponse);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },user_id,order_id,addToCartRequest);
        }
        catch (Exception e){
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
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        AddToCartResponse addToCartResponse= gson.fromJson(msg,AddToCartResponse.class);

                        listener.onSuccessAddToCart(addToCartResponse);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,user_id,order_id,addToCartRequest);
        }
        catch (Exception e){
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
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        UpdateCartResponse response= gson.fromJson(msg,UpdateCartResponse.class);

                        listener.onSuccessUpdateCart(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },user_id,order_id,updateProductQuantityRequest);
        }
        catch (Exception e){
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
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        UpdateCartResponse response= gson.fromJson(msg,UpdateCartResponse.class);

                        listener.onSuccessUpdateCart(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,user_id,order_id,updateProductQuantityRequest);
        }
        catch (Exception e){
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
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        UpdateCartResponse response= gson.fromJson(msg,UpdateCartResponse.class);

                        listener.onSuccessRemoveCart(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },user_id,order_id,updateProductQuantityRequest);
        }
        catch (Exception e){
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
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        UpdateCartResponse response= gson.fromJson(msg,UpdateCartResponse.class);

                        listener.onSuccessRemoveCart(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,user_id,order_id,updateProductQuantityRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getCartList(String user_id, String order_id, String uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getCartList(new ResponseResolver<UpdateCartResponse>() {
                @Override
                public void onSuccess(UpdateCartResponse updateProductQuantityResponse, Response response) {
                    listener.onSuccessCartList(updateProductQuantityResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        UpdateCartResponse response= gson.fromJson(msg,UpdateCartResponse.class);

                        listener.onSuccessCartList(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },user_id,order_id,uuid);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getCartList(String token, String user_id, String order_id, String uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getCartList(new ResponseResolver<UpdateCartResponse>() {
                @Override
                public void onSuccess(UpdateCartResponse updateProductQuantityResponse, Response response) {
                    listener.onSuccessCartList(updateProductQuantityResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error!=null && error.getError()!=null) {
                        if(error.getError()==null){
                            listener.onError(error.getMessage());

                        }
                        else {
                            listener.onError(error.getError());
                        }                    }
                }
            },token,user_id,order_id,uuid);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
