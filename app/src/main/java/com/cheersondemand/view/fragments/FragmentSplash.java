package com.cheersondemand.view.fragments;


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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheersondemand.R;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityHome;
import com.cheersondemand.view.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
                            try {

                                if(SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN)){

                                   /* Intent intent = new Intent(getActivity(), ActivitySearchLocation.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.putExtra(C.FROM, C.SEARCH);
                                    startActivity(intent);*/
                                    Intent intent = new Intent(getActivity(), ActivityHome.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    getActivity().startActivity(intent);
                                }
                                else {
                                    Bundle bundle = new Bundle();
                                    bundle.putBoolean(C.IS_LOGIN_SCREEN, false);
                                    ((MainActivity) getActivity()).fragmnetLoader(C.FRAGMENT_AUTHNITICATION, bundle);
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        } else {
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

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(getActivity(), new String[]
                {
                        WRITE_EXTERNAL_STORAGE,

                }, RequestPermissionCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean ExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;


                    if (ExternalStorage) {

                        Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
