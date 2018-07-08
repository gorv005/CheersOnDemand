package com.cheersondemand.presenter.address;

import android.content.Context;

import com.cheersondemand.intractor.address.AddressViewIntractorImpl;
import com.cheersondemand.intractor.address.IAddressViewIntractor;
import com.cheersondemand.model.address.AddressAddResponse;
import com.cheersondemand.model.address.AddressRequest;
import com.cheersondemand.model.address.AddressResponse;


public class AddressViewPresenterImpl implements IAddressViewPresenter, IAddressViewIntractor.OnFinishedListener {

    IAddressView mView;
    Context context;
    IAddressViewIntractor iAddressViewIntractor;

    public AddressViewPresenterImpl(IAddressView mView, Context context) {
        this.mView = mView;
        this.context = context;
        iAddressViewIntractor = new AddressViewIntractorImpl();

    }

    @Override
    public void onRemoveAddressSucess(AddressResponse Response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.onRemoveAddressSuccess(Response);
        }
    }

    @Override
    public void onAddAddressSucess(AddressAddResponse Response) {
        if (mView != null) {
            mView.hideProgress();
            mView.onAddAddressSuccess(Response);
        }
    }

    @Override
    public void onEditAddressSucess(AddressAddResponse Response) {
        if (mView != null) {
            mView.hideProgress();
            mView.onEditAddressSuccess(Response);
        }
    }

    @Override
    public void onAddressListSucess(AddressResponse Response) {
        if (mView != null) {
            mView.hideProgress();
            mView.onAddressListSuccess(Response);
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
    public void AddAddress(String token, String userId, AddressRequest addressRequest) {
        if (mView != null) {
            mView.showProgress();
            iAddressViewIntractor.AddAddress(token,userId,addressRequest, this);
        }
    }

    @Override
    public void EditAddAddress(String token, String userId, String id, AddressRequest addressRequest) {
        if (mView != null) {
            mView.showProgress();
            iAddressViewIntractor.EditAddAddress(token,userId,id,addressRequest, this);
        }
    }

    @Override
    public void getAddresses(String token, String userId) {
        if (mView != null) {
            mView.showProgress();

            iAddressViewIntractor.getAddresses(token,userId, this);
        }
    }

    @Override
    public void RemoveAddAddress(String token, String userId, String id) {
        if (mView != null) {
            mView.showProgress();
            iAddressViewIntractor.RemoveAddAddress(token,userId,id, this);
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
