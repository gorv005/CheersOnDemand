package com.cheersondemand.presenter.address;

import android.content.Context;

import com.cheersondemand.intractor.address.AddressViewIntractorImpl;
import com.cheersondemand.intractor.address.IAddressViewIntractor;
import com.cheersondemand.model.address.AddDeliveryAddressRequest;
import com.cheersondemand.model.address.AddressAddResponse;
import com.cheersondemand.model.address.AddressRequest;
import com.cheersondemand.model.address.AddressResponse;
import com.cheersondemand.model.address.RemoveAddressRequest;


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
    public void onRemoveAddressSucess(AddressAddResponse Response) {
        if (mView != null) {
            mView.hideProgress();
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
    public void onAddDeliveryAddressSuccess(AddressAddResponse Response) {
        if (mView != null) {
            mView.hideProgress();
            mView.onAddDeliveryAddressSuccess(Response);
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
    public void AddAddress(boolean isAuth,String token, String userId, AddressRequest addressRequest) {
        if (mView != null) {
            mView.showProgress();
            iAddressViewIntractor.AddAddress(isAuth,token,userId,addressRequest, this);
        }
    }

    @Override
    public void EditAddAddress(boolean isAuth,String token, String userId, String id, AddressRequest addressRequest) {
        if (mView != null) {
            mView.showProgress();
            iAddressViewIntractor.EditAddAddress(isAuth,token,userId,id,addressRequest, this);
        }
    }

    @Override
    public void getAddresses(boolean isAuth,String token,String userId, String uuid) {
        if (mView != null) {
            mView.showProgress();

            iAddressViewIntractor.getAddresses(isAuth,token,userId,uuid, this);
        }
    }

    @Override
    public void RemoveAddAddress(boolean isAuth,String token, String userId, String id,RemoveAddressRequest removeAddressRequest) {
        if (mView != null) {
            mView.showProgress();
            iAddressViewIntractor.RemoveAddAddress(isAuth,token,userId,id,removeAddressRequest,this);
        }
    }

    @Override
    public void addDeliveryAddress(String token, String userId, String cardId, AddDeliveryAddressRequest addDeliveryAddressRequest) {
        if (mView != null) {
            mView.showProgress();
            iAddressViewIntractor.addDeliveryAddress(token,userId,cardId,addDeliveryAddressRequest, this);
        }
    }

    @Override
    public void addDeliveryAddress(String token, String userId, String cardId, AddressRequest addressRequest) {
        if (mView != null) {
            mView.showProgress();
            iAddressViewIntractor.addDeliveryAddress(token,userId,cardId,addressRequest, this);
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
