package com.cheersondemand.serviceconnection;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cheersondemand.util.C;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by AB on 7/8/2017.
 */

public class ServiceConnection {

    public ServiceConnection() {
    }



    public void makeJsonObjectRequest(final String method, String jsonBody, String msg, final CompleteListener completeListener) {

        try {
            Log.e("DEBUG","REQUEST="+jsonBody);
            JSONObject obj = null;
            try {
                obj = new JSONObject(jsonBody);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String REGISTER_URL= C.BASE_URL+method;

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    REGISTER_URL,obj,new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    Log.e(TAG, response.toString());
                    completeListener.done(response.toString());

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error: " + error.getMessage());

                }

            });

            // Adding request to request queue
            // AppController.getInstance().addToRequestQueue(jsonObjReq);
            RequestQueue requestQueue = Volley.newRequestQueue(completeListener.getApplicationsContext());
            requestQueue.add(jsonObjReq);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
