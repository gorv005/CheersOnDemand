package com.cheersondemand.view.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheersondemand.R;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.ActivitySearchLocation;
import com.cheersondemand.view.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSplash extends Fragment {

    Handler handler;
    public static final int RequestPermissionCode = 7;
    @BindView(R.id.ivSplash)
    ImageView ivSplash;
    Unbinder unbinder;
    @BindView(R.id.tvCheers)
    TextView tvCheers;
    @BindView(R.id.tvStarted)
    TextView tvStarted;

    public FragmentSplash() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Thread timer = new Thread() {
            public void run() {
                try {
              startFadeInAnimation();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (Util.isNetworkConnectivity(getActivity())) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (CheckingPermissionIsEnabledOrNot()) {
                            if (SharedPreference.getNoti(getActivity(),C.IS_NOTIFICATION_PERMISSION_ASK)) {
                                try {
                                    SharedPreference.getInstance(getActivity()).setStore(C.SELECTED_STORE,null);
                                    SharedPreference.getInstance(getActivity()).setString(C.LOCATION_SELECTED,null);
                                    SharedPreference.getInstance(getActivity()).setLocation(C.SELECTED_LOCATION, null);

                                    if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN)) {
                                         if(SharedPreference.getInstance(getActivity()).getBoolean(C.IS_ANY_ADDRESS_ADDED)){
                                             gotoLocationAndStoreList();
                                         }
                                         else {
                                             /*Intent intent = new Intent(getActivity(), ActivitySearchLocation.class);
                                             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                             intent.putExtra(C.FROM, C.SEARCH);
                                             startActivity(intent)*/;

                                             ((MainActivity) getActivity()).fragmnetLoader(C.FRAGMENT_ADDRESS_PICKUP_DELIVERY_SELECTION, null);

                                         }
                                   /* Intent intent = new Intent(getActivity(), ActivityHome.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    getActivity().startActivity(intent);*/
                                    } else {
                                      // gotoAuthniticationScreen();
                                        ((MainActivity) getActivity()).fragmnetLoader(C.FRAGMENT_ADDRESS_PICKUP_DELIVERY_SELECTION, null);

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                    enableNotification();
                            }
                        }else {
                            RequestPermission();
                        }
                    }
                }, C.SPLASH_LOADER_TIME);
            } else {
                Toast.makeText(getActivity(), "Please connect to internet", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    void gotoAuthniticationScreen(){
        Bundle bundle = new Bundle();
        bundle.putBoolean(C.IS_LOGIN_SCREEN, false);
        bundle.putBoolean(C.IS_FROM_HOME, false);
        ((MainActivity) getActivity()).fragmnetLoader(C.FRAGMENT_AUTHNITICATION, bundle);
    }
    void gotoLocationAndStoreList() {
        Intent intent = new Intent(getActivity(), ActivityContainer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle = new Bundle();
        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_STORE_LOCATION_LIST);
        bundle.putInt(C.FROM, C.HOME);
        bundle.putBoolean(C.IS_CROSS_SHOW, false);
        intent.putExtra(C.BUNDLE, bundle);
        startActivity(intent);

    }
    public void startFadeInAnimation() {

        ivSplash.setVisibility(View.INVISIBLE);
        tvCheers.setVisibility(View.INVISIBLE);
        tvStarted.setVisibility(View.INVISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        ivSplash.setVisibility(View.VISIBLE);
        ivSplash.startAnimation(animation);

        final Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        final   Animation animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvCheers.startAnimation(animation1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            tvCheers.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvStarted.startAnimation(animation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tvStarted.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
       /* Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        tvCheers.setAnimation(animation1);
        animation1.start();
        Animation animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        tvStarted.setAnimation(animation2);
        animation2.start();*/
    }

    private void RequestPermission() {
        try {
            // Creating String Array with Permissions.
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {
                            WRITE_EXTERNAL_STORAGE,
                            ACCESS_FINE_LOCATION,
                            ACCESS_COARSE_LOCATION

                    }, RequestPermissionCode);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean ExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean locationAccess = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean CourseAccess = grantResults[2] == PackageManager.PERMISSION_GRANTED;


                    if (ExternalStorage && locationAccess && CourseAccess) {

                        Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    public boolean CheckingPermissionIsEnabledOrNot() {
        try {
            int FirstPermissionResult = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE);
            int SecondPermissionResult = ContextCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION);
            int ThirdPermissionResult = ContextCompat.checkSelfPermission(getActivity(), ACCESS_COARSE_LOCATION);

       /* return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED;*/
            if (FirstPermissionResult == PackageManager.PERMISSION_GRANTED && SecondPermissionResult == PackageManager.PERMISSION_GRANTED
                    && ThirdPermissionResult == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else if (FirstPermissionResult == PackageManager.PERMISSION_GRANTED && SecondPermissionResult == PackageManager.PERMISSION_DENIED
                    && ThirdPermissionResult == PackageManager.PERMISSION_DENIED) {
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    void enableNotification() {
        final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme); //this is a reference to the style above
        dialog.setContentView(R.layout.view_notification_access); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(false);
      //  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//to set the message
        Button btnGiveAccess = (Button) dialog.findViewById(R.id.btnGiveAccess);

        TextView tvNoThanks = (TextView) dialog.findViewById(R.id.tvNoThanks);

        btnGiveAccess.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SharedPreference.SaveNoti(getActivity(),C.IS_NOTIFICATION_PERMISSION_ASK,true);

                SharedPreference.getInstance(getActivity()).setBoolean(C.IS_NOTIFICATION_ENABLE,true);
                dialog.dismiss();
                onResume();
            }
        });


        tvNoThanks.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                SharedPreference.SaveNoti(getActivity(),C.IS_NOTIFICATION_PERMISSION_ASK,true);

                SharedPreference.getInstance(getActivity()).setBoolean(C.IS_NOTIFICATION_ENABLE,false);

                dialog.dismiss();
                onResume();

            }
        });

        dialog.show();
    }

}
