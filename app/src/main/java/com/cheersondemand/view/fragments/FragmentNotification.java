package com.cheersondemand.view.fragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.GuestUserCreateResponse;
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
    RelativeLayout LLView;
    int totalPages;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    boolean loading = true;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    long page = 1, perPage = 10;
    int posItem;
    boolean isLoadMore=false;
    public FragmentNotification() {
        // Required empty public constructor
    }

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

        getNotificationList("" + page);
    }


    void getNotificationList(String page) {
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
        iNotificationViewPresenter.getNotificationList(token, id, page, "" + perPage);
    }

    public void deleteNotification(int pos) {
        posItem = pos;
        String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

        String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();

        iNotificationViewPresenter.deleteNotification(token, id, "" + notifications.get(pos).getId());
    }

    public void clearAllNotification() {

       dialog();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnContinueShopping.setOnClickListener(this);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvNotifications.setLayoutManager(layoutManager);
        rvNotifications.setHasFixedSize(true);
        rvNotifications.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                    if (loading && page <= totalPages) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            progressbar.setVisibility(View.VISIBLE);
                            isLoadMore=true;
                            getNotificationList("" + page++);
                        }
                    }
                }
            }
        });
        ActivityContainer.tvClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllNotification();
            }
        });
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
        try {
            isLoadMore=false;
            if (Response.getSuccess()) {
                progressbar.setVisibility(View.GONE);
                if (notifications != null && notifications.size() > 0 && page != 1) {
                    notifications.addAll(Response.getData());
                    adapterNotification.notifyDataSetChanged();
                } else {
                    if (Response.getData() != null && Response.getData().size() > 0) {
                        llNoProductInCount.setVisibility(View.GONE);
                        ActivityContainer.tvClearAll.setVisibility(View.VISIBLE);

                        if (Response.getMeta() != null && Response.getMeta().getPagination() != null) {
                            totalPages = Response.getMeta().getPagination().getTotalPages();
                        }
                        notifications = Response.getData();
                        adapterNotification = new AdapterNotification(notifications, getActivity());
                        rvNotifications.setAdapter(adapterNotification);
                    } else {
                        // util.setSnackbarMessage(getActivity(), Response.getMessage(), LLView, true);
                        llNoProductInCount.setVisibility(View.VISIBLE);
                    }
                }
                loading = true;

            } else {
                util.setSnackbarMessage(getActivity(), Response.getMessage(), LLView, true);
                llNoProductInCount.setVisibility(View.VISIBLE);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDeleteNotificationList(GuestUserCreateResponse Response) {
        try {
            if (Response.getSuccess()) {
                notifications.remove(posItem);
                adapterNotification.notifyDataSetChanged();
                util.setSnackbarMessage(getActivity(), Response.getMessage(), LLView, false);
                if (notifications != null && notifications.size() == 0) {
                    ActivityContainer.tvClearAll.setVisibility(View.GONE);
                }
            } else {
                util.setSnackbarMessage(getActivity(), Response.getMessage(), LLView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClearAllNotificationList(GuestUserCreateResponse Response) {
        try {
            if (Response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), Response.getMessage(), LLView, false);
                notifications.clear();
                adapterNotification.notifyDataSetChanged();
                ActivityContainer.tvClearAll.setVisibility(View.GONE);

            } else {
                util.setSnackbarMessage(getActivity(), Response.getMessage(), LLView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(getActivity(), response, LLView, true);

    }

    @Override
    public void showProgress() {
        if(!isLoadMore) {
            util.showDialog(C.MSG, getActivity());
        }
    }

    @Override
    public void hideProgress() {
        if(!isLoadMore) {
            util.hideDialog();
        }
    }

    void dialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.dialog); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//to set the message
        TextView title = (TextView) dialog.findViewById(R.id.tvmessagedialogtitle);

        TextView message = (TextView) dialog.findViewById(R.id.tvmessagedialogtext);
        title.setText(getString(R.string.app_name));
        message.setText(getString(R.string.are_you_sure_delete_notification));
//add some action to the buttons
        Button yes = (Button) dialog.findViewById(R.id.bmessageDialogYes);
        yes.setText(getString(R.string.yes));
        yes.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();
                String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

                String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();

                iNotificationViewPresenter.clearAllNotification(token, id);

            }
        });

        Button no = (Button) dialog.findViewById(R.id.bmessageDialogNo);
        no.setText(getString(R.string.No));
        no.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
