package com.cheersondemand.serviceconnection;

import android.content.Context;

/**
 * Created by AB on 7/8/2017.
 */

public interface CompleteListener {

    public void done(String response);
    Context getApplicationsContext();
}
