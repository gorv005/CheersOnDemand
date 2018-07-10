package com.cheersondemand.intractor.notifications;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.notification.NotificationResponse;
import com.google.gson.Gson;

import retrofit2.Response;

/**
 * Created by AB on 7/31/2017.
 */

public class NotificationViewIntractorImpl implements INotificationViewIntractor {


    @Override
    public void getNotificationList(String token, String userId, String page, String perPage,final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getNotificationList(new ResponseResolver<NotificationResponse>() {
                @Override
                public void onSuccess(NotificationResponse r, Response response) {
                    listener.onSuccessNotificationList(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        NotificationResponse response= gson.fromJson(msg,NotificationResponse.class);
                        listener.onSuccessNotificationList(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,userId,page,perPage);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteNotification(String token, String userId, String notification_id, final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().deleteNotification(new ResponseResolver<NotificationResponse>() {
                @Override
                public void onSuccess(NotificationResponse r, Response response) {
                    listener.onDeleteNotificationList(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        NotificationResponse response= gson.fromJson(msg,NotificationResponse.class);
                        listener.onDeleteNotificationList(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,userId,notification_id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void clearAllNotification(String token, String userId, final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().clearAllNotification(new ResponseResolver<NotificationResponse>() {
                @Override
                public void onSuccess(NotificationResponse r, Response response) {
                    listener.onClearAllNotificationList(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        NotificationResponse response= gson.fromJson(msg,NotificationResponse.class);
                        listener.onClearAllNotificationList(response);

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
}
