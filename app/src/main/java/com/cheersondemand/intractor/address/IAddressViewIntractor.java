package com.cheersondemand.intractor.address;

import android.content.Context;

import com.cheersondemand.model.address.AddDeliveryAddressRequest;
import com.cheersondemand.model.address.AddressAddResponse;
import com.cheersondemand.model.address.AddressRequest;
import com.cheersondemand.model.address.AddressResponse;

/**
 * Created by AB on 7/31/2017.
 */

public interface IAddressViewIntractor {
    interface OnFinishedListener {
        void onRemoveAddressSucess(AddressAddResponse Response);
        void onAddAddressSucess(AddressAddResponse Response);
        void onEditAddressSucess(AddressAddResponse Response);
        void onAddressListSucess(AddressResponse Response);
        void onAddDeliveryAddressSuccess(AddressAddResponse Response);

        void onError(String response);
        Context getAPPContext();
    }
    public void AddAddress(String token, String userId, AddressRequest addressRequest, OnFinishedListener listener);
    public void EditAddAddress(String token, String userId,String id, AddressRequest addressRequest, OnFinishedListener listener);
    public void getAddresses(boolean isAuth,String token,String userId, String uuid, OnFinishedListener listener);
    public void RemoveAddAddress(String token, String userId,String id,OnFinishedListener listener);

    public void addDeliveryAddress(String token, String userId, String cardId, AddDeliveryAddressRequest addDeliveryAddressRequest,OnFinishedListener listener);
    public void addDeliveryAddress(String token, String userId,String cardId,AddressRequest addressRequest,OnFinishedListener listener);


}
