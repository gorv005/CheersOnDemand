package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cheersondemand.R;
import com.cheersondemand.model.notification.NotificationResponse;
import com.cheersondemand.model.notification.Notifications;
import com.cheersondemand.presenter.notifications.INotificationViewPresenter;
import com.cheersondemand.presenter.notifications.NotificationViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.notification.AdapterNotification;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNotification extends Fragment implements View.OnClickListener, INotificationViewPresenter.INotificationView {


    @BindView(R.id.btnContinueShopping)
    Button btnContinueShopping;
    Unbinder unbinder;
    @BindView(R.id.llNoProductInCount)
    LinearLayout llNoProductInCount;
    @BindView(R.id.rvNotifications)
    RecyclerView rvNotifications;
    Util util;
    List<Notifications> notifications;
    INotificationViewPresenter iNotificationViewPresenter;
    AdapterNotification adapterNotification;
    LinearLayoutManager layoutManager;
    @BindView(R.id.LLView)
    LinearLayout LLView;

    public FragmentNotification() {
        // Required empty public constructor
    }

    long page = 1, perPage = 10;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new Util();
        iNotificationViewPresenter = new NotificationViewPresenterImpl(this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText(R.string.notification);
        getNotificationList("" + page, "" + perPage);
    }


    void getNotificationList(String page, String perPage) {
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iNotificationViewPresenter.getNotificationList(token, id, page, perPage);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnContinueShopping.setOnClickListener(this);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvNotifications.setLayoutManager(layoutManager);
        rvNotifications.setHasFixedSize(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContinueShopping:
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onSuccessNotificationList(NotificationResponse Response) {

        if (Response.getSuccess()) {
            notifications = Response.getData();
            if(notifications!=null && notifications.size()>0) {
                llNoProductInCount.setVisibility(View.GONE);

                adapterNotification = new AdapterNotification(notifications, getActivity());
                rvNotifications.setAdapter(adapterNotification);
            }
            else {
               // util.setSnackbarMessage(getActivity(), Response.getMessage(), LLView, true);
                llNoProductInCount.setVisibility(View.VISIBLE);
            }
        }
        else {
            util.setSnackbarMessage(getActivity(), Response.getMessage(), LLView, true);
            llNoProductInCount.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onDeleteNotificationList(NotificationResponse Response) {
        if (Response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), Response.getMessage(), LLView, false);
            
        }
    }

    @Override
    public void onClearAllNotificationList(NotificationResponse Response) {
        if (Response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), Response.getMessage(), LLView, false);
            notifications.clear();
            adapterNotification.notifyDataSetChanged();

        }
    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
