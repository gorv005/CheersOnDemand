package frameworks.appsession;

import android.content.Context;

import com.universe.villiger.base.model.bean.UserInfo;
import frameworks.dbhandler.PrefManager;

import frameworks.retrofit.GsonFactory;


/**
 * Created by Sandeep on 06/12/2016.
 */

public class AppUserManager {
    private static final String KEY_SESSION_ID = "USER";
    private static final String INTENT_USER_SYNC_COMPLETED = "USER_SYNC_COMPLETED";
    private Context mContext;

    AppUserManager(Context context) {
        mContext = context;
    }

    public void saveUser(UserInfo user) {
        PrefManager mPrefManager;
        mPrefManager = new PrefManager(mContext);
        mPrefManager.putString(KEY_SESSION_ID, GsonFactory.getGson().toJson(user));

    }

    public UserInfo getUser() {
        PrefManager mPrefManager;
        mPrefManager = new PrefManager(mContext);
        return GsonFactory.getGson().fromJson(mPrefManager.getString(KEY_SESSION_ID), UserInfo.class);
    }

    public void clearUser() {
        PrefManager mPrefManager;
        mPrefManager = new PrefManager(mContext);
        mPrefManager.putString(KEY_SESSION_ID, null);
    }

}
