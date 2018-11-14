package com.cheersondemand.presenter.address;

import com.cheersondemand.model.address.AddDeliveryAddressRequest;
import com.cheersondemand.model.address.AddressAddResponse;
import com.cheersondemand.model.address.AddressRequest;
import com.cheersondemand.model.address.AddressResponse;

/**
 * Created by AB on 5/30/2018.
 */

public interface IAddressViewPresenter {

    public void AddAddress(String token, String userId, AddressRequest addressRequest);

    public void EditAddAddress(String token, String userId, String id, AddressRequest addressRequest);

    public void getAddresses(boolean isAuth ,String token,String userId, String uuid);

    public void RemoveAddAddress(String token, String userId, String id);
    public void addDeliveryAddress(String token, String userId, String cardId, AddDeliveryAddressRequest addDeliveryAddressRequest);
    public void addDeliveryAddress(String token, String userId,String cardId,AddressRequest addressRequest);

    void onDestroy();

    interface IAddressView {
        void onRemoveAddressSuccess(AddressAddResponse Response);

        void onAddAddressSuccess(AddressAddResponse Response);

        void onEditAddressSuccess(AddressAddResponse Response);

        void onAddressListSuccess(AddressResponse Response);

        public void onAddDeliveryAddressSuccess(AddressAddResponse Response);

        public void getResponseError(String response);

        void showProgress();

        void hideProgress();
    }

}
