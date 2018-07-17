package com.cheersondemand.intractor.address;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.address.AddDeliveryAddressRequest;
import com.cheersondemand.model.address.AddressAddResponse;
import com.cheersondemand.model.address.AddressRequest;
import com.cheersondemand.model.address.AddressResponse;
import com.google.gson.Gson;

import retrofit2.Response;

/**
 * Created by AB on 7/31/2017.
 */

public class AddressViewIntractorImpl implements IAddressViewIntractor {


    @Override
    public void AddAddress(String token, String userId, AddressRequest addressRequest, final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().AddAddress(new ResponseResolver<AddressAddResponse>() {
                @Override
                public void onSuccess(AddressAddResponse r, Response response) {
                    listener.onAddAddressSucess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        AddressAddResponse response= gson.fromJson(msg,AddressAddResponse.class);
                        listener.onAddAddressSucess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,userId,addressRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void EditAddAddress(String token, String userId, String id, AddressRequest addressRequest, final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().EditAddress(new ResponseResolver<AddressAddResponse>() {
                @Override
                public void onSuccess(AddressAddResponse r, Response response) {
                    listener.onEditAddressSucess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        AddressAddResponse response= gson.fromJson(msg,AddressAddResponse.class);
                        listener.onEditAddressSucess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,userId,id,addressRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getAddresses(String token, String userId, final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getAddressList(new ResponseResolver<AddressResponse>() {
                @Override
                public void onSuccess(AddressResponse r, Response response) {
                    listener.onAddressListSucess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        AddressResponse response= gson.fromJson(msg,AddressResponse.class);
                        listener.onAddressListSucess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,userId);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void RemoveAddAddress(String token, String userId, String id,final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().removeAddress(new ResponseResolver<AddressAddResponse>() {
                @Override
                public void onSuccess(AddressAddResponse r, Response response) {
                    listener.onRemoveAddressSucess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        AddressAddResponse response= gson.fromJson(msg,AddressAddResponse.class);
                        listener.onRemoveAddressSucess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,userId,id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void addDeliveryAddress(String token, String userId, String cardId, AddDeliveryAddressRequest addDeliveryAddressRequest, final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().addDeliveryAddress(new ResponseResolver<AddressAddResponse>() {
                @Override
                public void onSuccess(AddressAddResponse r, Response response) {
                    listener.onAddDeliveryAddressSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        AddressAddResponse response= gson.fromJson(msg,AddressAddResponse.class);
                        listener.onAddDeliveryAddressSuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,userId,cardId,addDeliveryAddressRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void addDeliveryAddress(String token, String userId, String cardId, AddressRequest addressRequest,final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().addDeliveryAddress(new ResponseResolver<AddressAddResponse>() {
                @Override
                public void onSuccess(AddressAddResponse r, Response response) {
                    listener.onAddDeliveryAddressSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        AddressAddResponse response= gson.fromJson(msg,AddressAddResponse.class);
                        listener.onAddDeliveryAddressSuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,userId,cardId,addressRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
