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
import android.widget.Button;
import android.widget.EditText;
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
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.ActivityHome;
import com.cheersondemand.view.adapter.store.AdapterStore;

import java.util.List;

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
    RelativeLayout imgBack;
    int from;
    List<StoreList> storeList;
    StoreList store;
    @BindView(R.id.btnChangeLocation)
    Button btnChangeLocation;
    @BindView(R.id.llNoStore)
    LinearLayout llNoStore;
    @BindView(R.id.rlStoreView)
    RelativeLayout rlStoreView;

    public FragmentStoreSelection() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new Util();
        iStoreViewPresenter = new StoreViewPresenterImpl(this, getActivity());
        from = getArguments().getInt(C.FROM);
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
        btnChangeLocation.setOnClickListener(this);
        rlStoreView.setVisibility(View.GONE);
        llNoStore.setVisibility(View.GONE);
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iStoreViewPresenter.getStoreList(Util.id(getActivity()));
        } else {

            String token = "bearer " + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();

            iStoreViewPresenter.getStoreList(token, Util.id(getActivity()));

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
        try {
            if (response.getSuccess()) {
                storeList = response.getData();
                if (storeList != null && storeList.size() > 0) {
                    rlStoreView.setVisibility(View.VISIBLE);
                    ((ActivityContainer) getActivity()).hideToolBar();
                    StoreList storeList1 = SharedPreference.getInstance(getActivity()).getStore(C.SELECTED_STORE);
                    adapterStore = new AdapterStore(from, getActivity(), storeList, storeList1);
                    lvStoreList.setAdapter(adapterStore);
                } else {
                    ((ActivityContainer) getActivity()).hideToolBar();
                    ActivityContainer.tvTitle.setText(getString(R.string.coming_soon_));
                    llNoStore.setVisibility(View.VISIBLE);
                }
            } else {
                // util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);
                llNoStore.setVisibility(View.VISIBLE);
                ActivityContainer.tvTitle.setText(getString(R.string.coming_soon_));
                ((ActivityContainer) getActivity()).hideToolBar();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStoreSuccess(UpdateStoreResponse response) {
        try {
            if (response.getSuccess()) {
                if (response.getData() != null && response.getData().getIsQuantityUpdated()) {
                    SharedPreference.getInstance(getActivity()).setBoolean(C.IS_QUANTITY_UPDATED, response.getData().getIsQuantityUpdated());
                }
                else {
                    SharedPreference.getInstance(getActivity()).setBoolean(C.IS_QUANTITY_UPDATED, false);

                }
                SharedPreference.getInstance(getActivity()).setStore(C.SELECTED_STORE, store);
                if (from == C.SEARCH) {
                    showHome();
                } else if (from == C.HOME) {
                    showHome();
                }
                else if (from == C.FRAGMENT_PRODUCT_LISTING) {
                    getActivity().onBackPressed();
                }
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);

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
        util.showDialog(C.MSG, getActivity());

    }

    @Override
    public void hideProgress() {
        util.hideDialog();

    }

    void updateStore() {
        store = adapterStore.getSelectedItem();
        if (store != null && storeList != null) {

            UpdateStore updateStore = new UpdateStore();
            updateStore.setUuid(Util.id(getActivity()));
            updateStore.setWarehouseId(store.getId());
            if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {

                iStoreViewPresenter.updateStore("" + SharedPreference.getInstance(getActivity()).
                        geGuestUser(C.GUEST_USER).getId(), updateStore);
            } else {
                String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
                String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

                iStoreViewPresenter.updateStore(token, id, updateStore);
            }
        } else {
            util.setSnackbarMessage(getActivity(), getString(R.string.plz_select_store), LLView, true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                if (from == C.SEARCH) {

                    getActivity().finish();
                } else {
                    updateStore();
                }
                break;
            case R.id.imgBack:
                if (from == C.SEARCH) {

                    getActivity().finish();
                } else {
                    updateStore();
                }
                break;
            case R.id.btnSubmit:

                updateStore();
                break;
            case R.id.btnChangeLocation:
                getActivity().onBackPressed();
                break;

        }
    }

    public void gotoHome() {
        try {
            Intent intent = new Intent(getActivity(), ActivityHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            getActivity().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void  showHome(){
        if(SharedPreference.getInstance(getActivity()).getBoolean(C.IS_SHOW_FROM_MAIL)){
            String s=SharedPreference.getInstance(getActivity()).getString(C.SOURCE);
            if(s!=null){
                gotoSource(Integer.parseInt(s));
            }
            else {
                gotoHome();
            }
        }
        else {
            gotoHome();
        }
    }
    public void gotoSource(int source){
        SharedPreference.getInstance(getActivity()).setBoolean(C.IS_SHOW_FROM_MAIL,false);
        Intent intent = new Intent(getActivity(), ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle=new Bundle();
        bundle.putInt(C.FRAGMENT_ACTION,source);
        intent.putExtra(C.BUNDLE,bundle);
        startActivity(intent);
    }
}
