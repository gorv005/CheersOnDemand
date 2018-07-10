package com.cheersondemand.intractor.notifications;

import android.content.Context;

import com.cheersondemand.model.notification.NotificationResponse;

/**
 * Created by AB on 7/31/2017.
 */

public interface INotificationViewIntractor {
    interface OnFinishedListener {
        void onSuccessNotificationList(NotificationResponse Response);
        void onDeleteNotificationList(NotificationResponse Response);
        void onClearAllNotificationList(NotificationResponse Response);

        void onError(String response);
        Context getAPPContext();
    }
    public void getNotificationList(String token, String userId, String page, String perPage, OnFinishedListener listener);
    public void deleteNotification(String token, String userId, String notification_id, OnFinishedListener listener);
    public void clearAllNotification(String token, String userId, OnFinishedListener listener);



}
