package com.cheersondemand.presenter.address;

import com.cheersondemand.model.address.AddressAddResponse;
import com.cheersondemand.model.address.AddressRequest;
import com.cheersondemand.model.address.AddressResponse;

/**
 * Created by AB on 5/30/2018.
 */

public interface IAddressViewPresenter {

    public void AddAddress(String token, String userId, AddressRequest addressRequest);

    public void EditAddAddress(String token, String userId, String id, AddressRequest addressRequest);

    public void getAddresses(String token, String userId);

    public void RemoveAddAddress(String token, String userId, String id);

    void onDestroy();

    interface IAddressView {
        void onRemoveAddressSuccess(AddressAddResponse Response);

        void onAddAddressSuccess(AddressAddResponse Response);

        void onEditAddressSuccess(AddressAddResponse Response);

        void onAddressListSuccess(AddressResponse Response);
        public void getResponseError(String response);

        void showProgress();

        void hideProgress();
    }

}
