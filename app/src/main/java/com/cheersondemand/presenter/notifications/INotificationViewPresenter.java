package com.cheersondemand.presenter.notifications;

import com.cheersondemand.model.notification.NotificationResponse;

/**
 * Created by AB on 5/30/2018.
 */

public interface INotificationViewPresenter {

    public void getNotificationList(String token, String userId, String page, String perPage);
    public void deleteNotification(String token, String userId, String notification_id);
    public void clearAllNotification(String token, String userId);

    void onDestroy();

    interface INotificationView {

        void onSuccessNotificationList(NotificationResponse Response);
        void onDeleteNotificationList(NotificationResponse Response);
        void onClearAllNotificationList(NotificationResponse Response);
        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}
