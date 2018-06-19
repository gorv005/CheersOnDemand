package com.cheersondemand.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cheersondemand.R;
import com.cheersondemand.model.store.StoreList;
import com.cheersondemand.model.store.StoreListResponse;
import com.cheersondemand.model.store.UpdateStore;
import com.cheersondemand.model.store.UpdateStoreResponse;
import com.cheersondemand.presenter.store.IStoreViewPresenter;
import com.cheersondemand.presenter.store.StoreViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityHome;
import com.cheersondemand.view.adapter.store.AdapterStore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentStoreSelection extends Fragment implements IStoreViewPresenter.IStoreView, View.OnClickListener {


    @BindView(R.id.etStoreName)
    EditText etStoreName;
    @BindView(R.id.lvStoreList)
    ListView lvStoreList;
    Unbinder unbinder;
    AdapterStore adapterStore;
    @BindView(R.id.LLView)
    RelativeLayout LLView;
    Util util;
    IStoreViewPresenter iStoreViewPresenter;
    @BindView(R.id.rlRecentSearch)
    LinearLayout rlRecentSearch;
    @BindView(R.id.btnBack)
    RelativeLayout btnBack;
    @BindView(R.id.btnSubmit)
    RelativeLayout btnSubmit;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    int from;
    public FragmentStoreSelection() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new Util();
        iStoreViewPresenter = new StoreViewPresenterImpl(this, getActivity());
       from= getArguments().getInt(C.FROM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_selection, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvStoreList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        imgBack.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        if(SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iStoreViewPresenter.getStoreList(Util.id(getActivity()));
        }
        else {

            String token="bearer "+SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();

            iStoreViewPresenter.getStoreList(token,Util.id(getActivity()));

        }
        etStoreName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //  String text = etStoreName.getText().toString().toLowerCase(Locale.getDefault());
                adapterStore.getFilter().filter(etStoreName.getText().toString());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getStoreListSuccess(StoreListResponse response) {
        adapterStore = new AdapterStore(getActivity(), response.getData());
        lvStoreList.setAdapter(adapterStore);
    }

    @Override
    public void updateStoreSuccess(UpdateStoreResponse response) {
        if(response.getSuccess()) {
            if (from == C.SEARCH) {
                gotoHome();
            } else if (from == C.HOME) {
                getActivity().finish();
            }
        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);

        }
    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(getActivity(), response, LLView, true);

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().finish();
                break;
            case R.id.imgBack:
                getActivity().finish();
                break;
            case R.id.btnSubmit:
                StoreList storeList = adapterStore.getSelectedItem();
                if (storeList != null) {
                    SharedPreference.getInstance(getActivity()).setStore(C.SELECTED_STORE, storeList);

                    UpdateStore updateStore = new UpdateStore();
                    updateStore.setWarehouseId(storeList.getId());
                    updateStore.setUuid(Util.id(getActivity()));
                    if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {

                        iStoreViewPresenter.updateStore("" + SharedPreference.getInstance(getActivity()).
                                geGuestUser(C.GUEST_USER).getId(), updateStore);
                    }
                    else {
                        String token="bearer "+SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
                        String id=""+SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

                        iStoreViewPresenter.updateStore(token,id, updateStore);
                    }
                }
                else {
                    util.setSnackbarMessage(getActivity(), getString(R.string.plz_select_store), LLView, true);
                }

                break;

        }
    }

    public void gotoHome() {
        Intent intent = new Intent(getActivity(), ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getActivity().startActivity(intent);
    }
}