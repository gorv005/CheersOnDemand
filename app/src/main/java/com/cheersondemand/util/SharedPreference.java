package com.cheersondemand.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cheersondemand.model.authentication.AuthenticationResponse;
import com.cheersondemand.model.authentication.GuestUser;
import com.cheersondemand.model.card.CardList;
import com.cheersondemand.model.location.SelectedLocation;
import com.cheersondemand.model.store.StoreList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by AB.garg on 08-09-2016.
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
    public void seGuestUser(String key, GuestUser guestUserCreateResponse) {
        Gson gson = new Gson();
        String json = gson.toJson(guestUserCreateResponse);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(key, json).apply();

    }
    public GuestUser geGuestUser(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String data = prefs.getString(key, null);
        Gson gson = new Gson();
        return gson.fromJson(data, GuestUser.class);

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
    public void setCard(String key, List<CardList> storeList) {
        Gson gson = new Gson();
        String json = gson.toJson(storeList);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(key, json).apply();

    }
    void clearCardList(){

    }
    public void setLocation(String key, List<SelectedLocation> storeList) {
        Gson gson = new Gson();
        String json = gson.toJson(storeList);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(key, json).apply();

    }

    public void addRecentSearch(String key,SelectedLocation location) {
        boolean isFavAlready=false;
        SelectedLocation location1=null;
        List<SelectedLocation> recentLocations = getRecentLocations(key);
        if (recentLocations == null) {
            recentLocations = new ArrayList<SelectedLocation>();
            recentLocations.add(location);
            setLocation(key, recentLocations);
        }
        else {
            for (int i = 0; i < recentLocations.size(); i++) {
                location1=recentLocations.get(i);
                if (location.getName().equalsIgnoreCase(location1.getName())) {
                    isFavAlready = true;
                    break;
                }
            }
            if(isFavAlready){
                removeRecentLocation(key,location1);
            }
            else {
                recentLocations.add(location);
                setLocation(key, recentLocations);
            }
        }

    }
    public void removeRecentLocation(String key, SelectedLocation selectedLocation) {
   List<SelectedLocation> selectedLocationList = getRecentLocations(key);
        if (selectedLocationList != null) {
            for(int i=0;i<selectedLocationList.size();i++){
                if (selectedLocation.getName().trim().equalsIgnoreCase(selectedLocationList.get(i).getName().trim())) {

                    selectedLocationList.remove(selectedLocationList.remove(i));
                    setLocation(key, selectedLocationList);

                }
            }
            addRecentSearch(key, selectedLocation);
        }
    }

    public void removeCard(String key, CardList selectedLocation) {
        List<CardList> selectedLocationList = getCard(key);
        if (selectedLocationList != null) {
            for(int i=0;i<selectedLocationList.size();i++){
                if (selectedLocation.getCardNumber().trim().equalsIgnoreCase(selectedLocationList.get(i).getCardNumber().trim())) {

                    selectedLocationList.remove(selectedLocationList.remove(i));
                    setCard(key, selectedLocationList);

                }
            }
            addCard(key, selectedLocation);
        }
    }
    public List<SelectedLocation> getRecentLocations(String key) {
        List<SelectedLocation> recentSearches;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);


        if (prefs.contains(key)) {
            String jsonFavorites = prefs.getString(key, null);
            Gson gson = new Gson();
            SelectedLocation[] recent = gson.fromJson(jsonFavorites,
                    SelectedLocation[].class);

            recentSearches = Arrays.asList(recent);
            recentSearches = new ArrayList<SelectedLocation>(recentSearches);
        } else
            return null;

        return (List<SelectedLocation>) recentSearches;
    }
   /* public SelectedLocation getLocation(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String data = prefs.getString(key, null);
        Gson gson = new Gson();
        return gson.fromJson(data, SelectedLocation.class);

    }*/
   public List<CardList> getCard(String key) {
       List<CardList> recentSearches;

       SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);


       if (prefs.contains(key)) {
           String jsonFavorites = prefs.getString(key, null);
           if(jsonFavorites!=null) {
               Gson gson = new Gson();
               CardList[] recent = gson.fromJson(jsonFavorites,
                       CardList[].class);
                if(recent!=null) {
                    recentSearches = Arrays.asList(recent);
                    recentSearches = new ArrayList<CardList>(recentSearches);
                }
                else {
                    return null;
                }
           }
           else {
               return null;
           }
       } else
           return null;

       return (List<CardList>) recentSearches;
   }
    public void addCard(String key,CardList cardList) {
        boolean isFavAlready=false;
        CardList location1=null;
        List<CardList> recentLocations = getCard(key);
        if (recentLocations == null) {
            recentLocations = new ArrayList<CardList>();
            recentLocations.add(cardList);
            setCard(key, recentLocations);
        }
        else {
            for (int i = 0; i < recentLocations.size(); i++) {
                location1=recentLocations.get(i);
                if (cardList.getCardNumber().equalsIgnoreCase(location1.getCardNumber())) {
                    isFavAlready = true;
                    break;
                }
            }
            if(isFavAlready){
                removeCard(key,location1);
            }
            else {
                recentLocations.add(cardList);
                setCard(key, recentLocations);
            }
        }

    }
}
