package frameworks.baselogin;

import android.content.Context;


import com.universe.villiger.base.model.bean.UserInfo;

import frameworks.basemvp.AppBaseFragment;

/**
 * Created by sandeep.g9 on 7/27/2017.
 */

public abstract class BaseLoginFragment<T extends ILoginMVP.ILoginPresenter> extends AppBaseFragment<T> implements ILoginMVP.ILoginView {
    LoginListener loginListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginListener = (LoginListener) context;
    }

    @Override
    public void sendLoginSuccessToActivity(UserInfo user) {
        loginListener.onLoginSuccess(user);
    }

    public void sendLoginFailToActivity() {
        loginListener.onLoginFail();
    }

    public static interface LoginListener {
        public void onLoginSuccess(UserInfo user);

        public void onLoginFail();
    }
}
