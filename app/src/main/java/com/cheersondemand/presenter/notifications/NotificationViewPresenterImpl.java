package com.cheersondemand.presenter.notifications;

import android.content.Context;

import com.cheersondemand.intractor.notifications.INotificationViewIntractor;
import com.cheersondemand.intractor.notifications.NotificationViewIntractorImpl;
import com.cheersondemand.model.GuestUserCreateResponse;
import com.cheersondemand.model.notification.NotificationResponse;


public class NotificationViewPresenterImpl implements INotificationViewPresenter, INotificationViewIntractor.OnFinishedListener {

    INotificationView mView;
    Context context;
    INotificationViewIntractor iNotificationViewIntractor;

    public NotificationViewPresenterImpl(INotificationView mView, Context context) {
        this.mView = mView;
        this.context = context;
        iNotificationViewIntractor = new NotificationViewIntractorImpl();

    }

    @Override
    public void onSuccessNotificationList(NotificationResponse Response) {
        if (mView != null) {
            mView.hideProgress();
            mView.onSuccessNotificationList(Response);
        }
    }

    @Override
    public void onDeleteNotificationList(GuestUserCreateResponse Response) {
        if (mView != null) {
            mView.hideProgress();
            mView.onDeleteNotificationList(Response);
        }
    }

    @Override
    public void onClearAllNotificationList(GuestUserCreateResponse Response) {
        if (mView != null) {
            mView.hideProgress();
            mView.onClearAllNotificationList(Response);
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
    public void getNotificationList(String token, String userId, String page, String perPage) {
        if (mView != null) {
            mView.showProgress();
            iNotificationViewIntractor.getNotificationList(token,userId,page,perPage, this);
        }
    }

    @Override
    public void deleteNotification(String token, String userId, String notification_id) {
        if (mView != null) {
            mView.showProgress();

            iNotificationViewIntractor.deleteNotification(token,userId,notification_id, this);
        }
    }

    @Override
    public void clearAllNotification(String token, String userId) {
        if (mView != null) {
            mView.showProgress();

            iNotificationViewIntractor.clearAllNotification(token,userId, this);
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
