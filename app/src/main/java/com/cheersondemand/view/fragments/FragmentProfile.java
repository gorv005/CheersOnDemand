package com.cheersondemand.view.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.GuestUserCreateResponse;
import com.cheersondemand.model.authentication.AuthenticationResponse;
import com.cheersondemand.model.logout.LogoutRequest;
import com.cheersondemand.model.logout.LogoutResponse;
import com.cheersondemand.model.order.CreateOrderResponse;
import com.cheersondemand.model.order.addtocart.AddToCartResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.cheersondemand.model.profile.ProfileUpdateRequest;
import com.cheersondemand.model.wishlist.WishListDataResponse;
import com.cheersondemand.model.wishlist.WishListResponse;
import com.cheersondemand.presenter.home.order.IOrderViewPresenterPresenter;
import com.cheersondemand.presenter.home.order.OrderViewPresenterImpl;
import com.cheersondemand.presenter.profile.IProfileViewPresenter;
import com.cheersondemand.presenter.profile.ProfileViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.ImageLoader.ImageLoader;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.MainActivity;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment implements View.OnClickListener, IProfileViewPresenter.IProfileView, IOrderViewPresenterPresenter.IOrderView {


    @BindView(R.id.imgProfile)
    CircularImageView imgProfile;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.btnEdit)
    Button btnEdit;
    @BindView(R.id.llProfileView)
    LinearLayout llProfileView;
    @BindView(R.id.profile)
    LinearLayout profile;
    @BindView(R.id.tvNumberWishListItems)
    TextView tvNumberWishListItems;
    @BindView(R.id.llWishList)
    RelativeLayout llWishList;
    @BindView(R.id.llOrders)
    RelativeLayout llOrders;
    @BindView(R.id.llSavedAddress)
    RelativeLayout llSavedAddress;
    @BindView(R.id.llPaymentInfo)
    RelativeLayout llPaymentInfo;
    @BindView(R.id.llConnectedAcc)
    RelativeLayout llConnectedAcc;
    @BindView(R.id.llChangePassword)
    RelativeLayout llChangePassword;
    @BindView(R.id.llHelp)
    RelativeLayout llHelp;
    @BindView(R.id.switch_button)
    SwitchButton switchButton;
    @BindView(R.id.llNotification)
    RelativeLayout llNotification;
    @BindView(R.id.llLogout)
    RelativeLayout llLogout;
    Unbinder unbinder;
    boolean isLogin;
    AuthenticationResponse authenticationResponse;
    @BindView(R.id.viewChangePassword)
    View viewChangePassword;
    ImageLoader imageLoader;
    IProfileViewPresenter iProfileViewPresenter;
    WishListDataResponse productListResponse;
    IOrderViewPresenterPresenter iOrderViewPresenterPresenter;
    @BindView(R.id.llBecomePartner)
    RelativeLayout llBecomePartner;
    Util util;
    @BindView(R.id.rlView)
    RelativeLayout rlView;
    boolean isNotification;
    @BindView(R.id.viewConnectedAccount)
    View viewConnectedAccount;

    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new Util();
        imageLoader = new ImageLoader(getActivity());
        iProfileViewPresenter = new ProfileViewPresenterImpl(this, getActivity());
        iOrderViewPresenterPresenter = new OrderViewPresenterImpl(this, getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();

        if (isLogin) {
            authenticationResponse = SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER);
            tvEmail.setText(authenticationResponse.getData().getUser().getEmail());
            tvName.setText(authenticationResponse.getData().getUser().getName());
            if(SharedPreference.getInstance(getActivity()).getBoolean(C.IS_SOCAIL)) {
                llChangePassword.setVisibility(View.GONE);
                viewChangePassword.setVisibility(View.GONE);
            }
            else {
                llChangePassword.setVisibility(View.VISIBLE);
                viewChangePassword.setVisibility(View.VISIBLE);
            }
            llConnectedAcc.setVisibility(View.GONE);
            viewConnectedAccount.setVisibility(View.GONE);

            if (authenticationResponse.getData().getUser().getProfilePicture() != null) {
               imageLoader.DisplayImage(authenticationResponse.getData().getUser().getProfilePicture(), imgProfile);
               // Util.setImage(getActivity(),authenticationResponse.getData().getUser().getProfilePicture(),imgProfile);
            }
            else {
                //imageLoader.DisplayImage("", imgProfile);

          //      imgProfile.setImageResource(R.drawable.missing);
                Util.setImage(getActivity(),authenticationResponse.getData().getUser().getProfilePicture(),imgProfile);

            }

        } else {
            btnEdit.setVisibility(View.GONE);
            tvEmail.setText(getString(R.string.view_orders));
            tvName.setText(getString(R.string.sign_in));
            llConnectedAcc.setVisibility(View.GONE);
            viewConnectedAccount.setVisibility(View.GONE);
            llChangePassword.setVisibility(View.GONE);
            viewChangePassword.setVisibility(View.GONE);
            llLogout.setVisibility(View.GONE);
           // imageLoader.DisplayImage("", imgProfile);
            imgProfile.setImageResource(R.drawable.default_placeholder);
        }

        getWishList();


    }


    void getWishList() {
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();
            iOrderViewPresenterPresenter.getWishList(false, "", id, Util.id(getActivity()));
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iOrderViewPresenterPresenter.getWishList(true, token, id, Util.id(getActivity()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Context darkTheme = new ContextThemeWrapper(getActivity(),
                R.style.ActivityTheme);
        inflater = (LayoutInflater)
                darkTheme.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreference.getInstance(getActivity()).setBoolean(C.IS_FROM_PAYMENT, false);
        isLogin = SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN);

        btnEdit.setOnClickListener(this);
        llProfileView.setOnClickListener(this);
        llLogout.setOnClickListener(this);
        llChangePassword.setOnClickListener(this);
        llWishList.setOnClickListener(this);
        llSavedAddress.setOnClickListener(this);
        llPaymentInfo.setOnClickListener(this);
        llHelp.setOnClickListener(this);
        llBecomePartner.setOnClickListener(this);
        llOrders.setOnClickListener(this);
        llConnectedAcc.setOnClickListener(this);
        llNotification.setOnClickListener(this);
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_NOTIFICATION)) {
            switchButton.setChecked(true);
            isNotification = true;
        } else {
            switchButton.setChecked(false);
            isNotification = false;
        }


        if (isLogin) {
            switchButton.setEnabled(true);
        } else {
            switchButton.setEnabled(false);
        }
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (!isLogin) {
                    dialogSignIn();
                } else {
                    isNotification = isChecked;
                    updateProfile((isChecked));
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        Bundle bundle = new Bundle();

        switch (view.getId()) {
            case R.id.llProfileView:
                // move to login screen if not login
                if (!isLogin) {
                   /* intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_AUTHNITICATION);
                    bundle.putBoolean(C.IS_LOGIN_SCREEN, true);

                    intent.putExtra(C.BUNDLE, bundle);
                    startActivity(intent);*/
                    gotoLogin();
                }
                break;
            case R.id.btnEdit:

                Intent intent1 = new Intent(getActivity(), ActivityContainer.class);
                Bundle bundle1 = new Bundle();
                intent1.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_UPDATE_PROFILE);
                intent1.putExtra(C.BUNDLE, bundle1);
                startActivity(intent1);
                break;
            case R.id.llLogout:
                dialog();
                break;

            case R.id.llChangePassword:
                if (isLogin) {
                    Intent intent2 = new Intent(getActivity(), ActivityContainer.class);
                    intent2.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_CHANGE_PASSWORD);
                    startActivity(intent2);
                }
                break;
            case R.id.llWishList:

                Intent intent2 = new Intent(getActivity(), ActivityContainer.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable(C.PRODUCT_LIST, productListResponse);
                bundle.putInt(C.SOURCE, C.FRAGMENT_PROFILE_HOME);
                intent2.putExtra(C.BUNDLE, bundle2);
                intent2.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_WISHLIST);
                startActivity(intent2);

                break;
            case R.id.llSavedAddress:
                if (isLogin) {
                    Intent intent3 = new Intent(getActivity(), ActivityContainer.class);
                    Bundle bundle5 = new Bundle();
                    bundle5.putInt(C.ADDRESS_ID, 0);
                    bundle5.putString(C.ADDRESS_NAME, "");
                    intent3.putExtra(C.BUNDLE, bundle5);
                    bundle.putBoolean(C.IS_LOCATION_CHANGED, false);

                    intent3.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_ADDRESS_LIST);
                    startActivity(intent3);
                } else {
                    dialogSignIn();
                }
                break;
            case R.id.llNotification:
                if (!isLogin) {
                    dialogSignIn();

                }
                break;
            case R.id.llPaymentInfo:
                if (isLogin) {
                    Intent intent7 = new Intent(getActivity(), ActivityContainer.class);
                    intent7.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_CARD_LIST);
                    startActivity(intent7);
                } else {
                    dialogSignIn();
                }
                break;
            case R.id.llConnectedAcc:
                if (isLogin) {
                    Intent intent7 = new Intent(getActivity(), ActivityContainer.class);
                    intent7.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_CARD_LIST);
                    startActivity(intent7);
                } else {
                    dialogSignIn();
                }
                break;
            case R.id.llHelp:
                Intent intent4 = new Intent(getActivity(), ActivityContainer.class);

                intent4.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_HELP_CENTER);
                startActivity(intent4);
                break;
            case R.id.llBecomePartner:
                Intent intent5 = new Intent(getActivity(), ActivityContainer.class);
                intent5.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_BECOME_PARTNER);
                startActivity(intent5);
                break;
            case R.id.llOrders:
                if (isLogin) {
                    Intent intent6 = new Intent(getActivity(), ActivityContainer.class);
                    intent6.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_ORDER_LIST);
                    startActivity(intent6);
                } else {
                    dialogSignIn();
                }
                break;
        }
    }

    @Override
    public void getResponseSuccess(LogoutResponse response) {
        try {
        if (response.getSuccess()) {
            SharedPreference.getInstance(getActivity()).setBoolean(C.IS_LOGIN, false);
            SharedPreference.getInstance(getActivity()).clearData();
            gotoLoginLogout();
        }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    void gotoLogin() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(C.IS_LOGIN_SCREEN, true);
        bundle.putBoolean(C.IS_FROM_HOME, true);
        bundle.putInt(C.SOURCE, C.FRAGMENT_PROFILE_HOME);
        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_AUTHNITICATION);
       // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(C.BUNDLE, bundle);
        getActivity().startActivity(intent);
    }
    void gotoLoginLogout() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(C.IS_LOGIN_SCREEN, true);
        bundle.putBoolean(C.IS_FROM_HOME, false);
        bundle.putInt(C.SOURCE, C.FRAGMENT_PRODUCTS_HOME);
        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_AUTHNITICATION);
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(C.BUNDLE, bundle);
        getActivity().startActivity(intent);
    }
    void updateProfile(boolean isNotified) {
        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest();
        profileUpdateRequest.setIsNotificationEnabled(isNotified);

        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iProfileViewPresenter.updateProfile(token, id, profileUpdateRequest);

    }

    @Override
    public void onSuccessUpdateProfile(GuestUserCreateResponse Response) {
        try {
        if (Response.getSuccess()) {
            SharedPreference.getInstance(getActivity()).setBoolean(C.IS_NOTIFICATION, isNotification);
            util.setSnackbarMessage(getActivity(), getString(R.string.profile_updated), rlView, false);

        } else {
            util.setSnackbarMessage(getActivity(), Response.getMessage(), rlView, true);

        }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void getCreateOrderSuccess(CreateOrderResponse response) {

    }

    @Override
    public void getAddToCartSucess(AddToCartResponse response) {

    }

    @Override
    public void getUpdateCartSuccess(UpdateCartResponse response) {

    }

    @Override
    public void getRemoveItemFromCartSuccess(UpdateCartResponse response) {

    }

    @Override
    public void getCartListSuccess(UpdateCartResponse response) {

    }

    @Override
    public void addTowishListSuccess(WishListResponse response) {

    }

    @Override
    public void removeFromWishListSuccess(WishListResponse response) {

    }

    @Override
    public void getWishListSuccess(WishListDataResponse response) {
        try {
            if (response.getSuccess()) {
                productListResponse = response;
                if (response.getData() != null && response.getData().size() > 0) {
                    tvNumberWishListItems.setVisibility(View.VISIBLE);
                    tvNumberWishListItems.setText("" + response.getData().size());
                } else {
                    tvNumberWishListItems.setVisibility(View.GONE);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getMinimumAmountResponse(WishListResponse response) {

    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void showProgress() {
        util.showDialog(C.MSG, getActivity());

    }

    @Override
    public void hideProgress() {
        util.hideDialog();

    }


    void dialogSignIn() {
        final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.dialog); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//to set the message
        TextView title = (TextView) dialog.findViewById(R.id.tvmessagedialogtitle);

        TextView message = (TextView) dialog.findViewById(R.id.tvmessagedialogtext);
        title.setText(getString(R.string.sign_in));
        message.setText(getString(R.string.you_need_to_sign_in));
//add some action to the buttons
        Button yes = (Button) dialog.findViewById(R.id.bmessageDialogYes);
        yes.setText(getString(R.string.sign_in));
        yes.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();

                gotoLogin();
            }
        });

        Button no = (Button) dialog.findViewById(R.id.bmessageDialogNo);
        no.setText(getString(R.string.cancel));
        no.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void dialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.dialog); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//to set the message
        TextView title = (TextView) dialog.findViewById(R.id.tvmessagedialogtitle);

        TextView message = (TextView) dialog.findViewById(R.id.tvmessagedialogtext);
        title.setText(getString(R.string.logout));
        message.setText(getString(R.string.are_you_sure_logout));
//add some action to the buttons
        Button yes = (Button) dialog.findViewById(R.id.bmessageDialogYes);
        yes.setText(getString(R.string.logout));
        yes.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();

                LogoutRequest logoutRequest = new LogoutRequest();
                logoutRequest.setToken(SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken());
                logoutRequest.setDeviceToken(SharedPreference.getInstance(getActivity()).getString(C.DEVICE_TOKEN));

                iProfileViewPresenter.logout(logoutRequest);
            }
        });

        Button no = (Button) dialog.findViewById(R.id.bmessageDialogNo);
        no.setText(getString(R.string.cancel));
        no.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
