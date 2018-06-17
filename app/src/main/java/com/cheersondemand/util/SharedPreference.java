package com.cheersondemand.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cheersondemand.model.AuthenticationResponse;
import com.cheersondemand.model.GuestUserCreateResponse;
import com.cheersondemand.model.store.StoreList;
import com.google.gson.Gson;

/**
 * Created by gaurav.garg on 08-09-2016.
 */
public class SharedPreference {

    private Context context;
    private static SharedPreference savePreferenceAndData;

    public static SharedPreference getInstance(Context context)
    {
        if(savePreferenceAndData==null)
        {
            savePreferenceAndData = new SharedPreference(context);
        }
        return savePreferenceAndData;
    }


    public SharedPreference(Context context)
    {
        this.context = context;

    }
    public void setString(String key, String data)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(key, data).apply();
    }

    public void setBoolean(String key, boolean status)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean(key, status).apply();
    }
    public boolean getBoolean(String key)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return  prefs.getBoolean(key, false);
    }
    public String getString(String key)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return  prefs.getString(key, null);
    }

    public void clearData()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().clear().commit();
    }


    public AuthenticationResponse getUser(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String data = prefs.getString(key, null);
        Gson gson = new Gson();
        return gson.fromJson(data, AuthenticationResponse.class);

    }

    public void setUser(String key, AuthenticationResponse adminUser) {
        Gson gson = new Gson();
        String json = gson.toJson(adminUser);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(key, json).apply();

    }
    public void seGuestUser(String key, GuestUserCreateResponse guestUserCreateResponse) {
        Gson gson = new Gson();
        String json = gson.toJson(guestUserCreateResponse);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(key, json).apply();

    }
    public GuestUserCreateResponse geGuestUser(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String data = prefs.getString(key, null);
        Gson gson = new Gson();
        return gson.fromJson(data, GuestUserCreateResponse.class);

    }
    public void setStore(String key, StoreList storeList) {
        Gson gson = new Gson();
        String json = gson.toJson(storeList);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(key, json).apply();

    }
    public StoreList getStore(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String data = prefs.getString(key, null);
        Gson gson = new Gson();
        return gson.fromJson(data, StoreList.class);

    }
}
