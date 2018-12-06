package com.cheersondemand.view;

import android.app.ActivityManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.cheersondemand.R;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.StoreProducts;
import com.cheersondemand.view.fragments.FragmentAuthentication;
import com.cheersondemand.view.fragments.FragmentSplash;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.List;

public class    MainActivity extends AppCompatActivity {
    private Bundle bundle;
    private int fragmentAction;
    private Fragment fragment;
    boolean isNotification=false,isFirstTime=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  Fabric.with(this, new Crashlytics());
        StoreProducts.getInstance().clear();

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);
        try {
            isNotification=false;
            isFirstTime=true;
            SharedPreference.getInstance(this).setBoolean(C.IS_SHOW_FROM_MAIL,false);
            Intent extras = getIntent();
            Log.e("extras",""+extras);
            if (SharedPreference.getInstance(this).getBoolean(C.IS_LOGIN)) {
                if (extras != null) {
                    boolean isNoti = extras.getBooleanExtra(C.IS_NOTIFICATION, false);
                    Log.e("IS nOTI", "" + isNoti);
                    if (isNoti) {
                        isNotification = true;
                        isFirstTime = false;
                        Intent intent = new Intent(this, ActivityContainer.class);
                     /*   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(C.IS_NOTIFICATION, true);
                        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_ORDER_DETAIL);
                        bundle.putString(C.ORDER_ID, extras.getStringExtra(C.ORDER_ID));
                        intent.putExtra(C.BUNDLE, bundle);
                        startActivity(intent);
                    } else {
                        // Intent intent = getIntent();
                        Uri data = extras.getData();
                        //   Log.e("DEBUG","DATA="+data.toString());
                        if (data != null && data.toString() != null && data.toString().contains("resetPassword")) {
                            String d = data.toString().substring(data.toString().lastIndexOf('/') + 1);
                            Intent i = new Intent(this, ActivityContainer.class);
                            Bundle bundle = new Bundle();
                            i.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_RESET_PASSWORD);
                            bundle.putString(C.TOKEN, d);
                            i.putExtra(C.BUNDLE, bundle);
                            startActivity(i);
                        } else if (data != null && data.toString() != null && data.toString().contains("myProfile")) {

                            if(isAnyActivityInStack()){
                                gotoHome(C.FRAGMENT_PROFILE_HOME);
                            }
                            else {
                                SharedPreference.getInstance(this).setBoolean(C.IS_SHOW_FROM_MAIL,true);
                                SharedPreference.getInstance(this).setString(C.SOURCE,""+C.FRAGMENT_PROFILE_HOME);
                                gotoSplash();
                            }

                        }
                        else if (data != null && data.toString() != null && data.toString().contains("shopNow")) {

                            if(isAnyActivityInStack()){
                                gotoHome(C.FRAGMENT_PRODUCTS_HOME);
                            }
                            else {
                                SharedPreference.getInstance(this).setBoolean(C.IS_SHOW_FROM_MAIL,true);
                                SharedPreference.getInstance(this).setString(C.SOURCE,""+C.FRAGMENT_PRODUCTS_HOME);
                                gotoSplash();
                            }

                        }
                        else if (data != null && data.toString() != null && data.toString().contains("myOrders")) {

                            isNotification = true;
                            isFirstTime = false;
                            Intent intent = new Intent(this, ActivityContainer.class);
                     /*   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
                            Bundle bundle = new Bundle();
                            intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_ORDER_LIST);
                            intent.putExtra(C.BUNDLE, bundle);
                            startActivity(intent);

                        }
                        else {
                            gotoSplash();
                        }
                    }
                } else {
                    gotoSplash();
                }
            }
            else {

                Uri data = extras.getData();
                //   Log.e("DEBUG","DATA="+data.toString());
                if (data != null && data.toString() != null && data.toString().contains("resetPassword")) {
                    String d = data.toString().substring(data.toString().lastIndexOf('/') + 1);
                    Intent i = new Intent(this, ActivityContainer.class);
                    Bundle bundle = new Bundle();
                    i.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_RESET_PASSWORD);
                    bundle.putString(C.TOKEN, d);
                    i.putExtra(C.BUNDLE, bundle);
                    startActivity(i);
                }else {
                    gotoSplash();
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
            gotoSplash();
        }


    }

    public void gotoHome(int source){
        Intent intent = new Intent(this, ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle=new Bundle();
        bundle.putInt(C.FRAGMENT_ACTION,source);
        intent.putExtra(C.BUNDLE,bundle);
        startActivity(intent);
    }
    void  gotoSplash(){

        bundle = getIntent().getBundleExtra(C.BUNDLE);
        try {
            if (bundle != null && bundle.getInt(C.SOURCE) == C.FRAGMENT_CART) {
            } else {
                SharedPreference.getInstance(this).setString(C.ORDER_ID, null);
            }
        }
        catch (Exception e){
            SharedPreference.getInstance(this).setString(C.ORDER_ID, null);

        }
        fragmentAction = getIntent().getIntExtra(C.FRAGMENT_ACTION, C.FRAGMENT_SPLASH);
        getToken();
        fragmnetLoader(fragmentAction, bundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(isNotification && isFirstTime){
            if(isAnyActivityInStack()) {
                finish();
            }
            else {
                gotoSplash();
            }
        }
        else {
            isFirstTime=true;
        }
        //Log.d("Firebase", "token "+ FirebaseInstanceId.getInstance().getToken());

    }


    boolean isAnyActivityInStack(){
        try {
            ActivityManager mngr = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

            List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

            if (taskList.get(0).numActivities > 1) {
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }

    boolean isTopActivityHome(){
        try {
            ActivityManager mngr = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

            List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

            if (taskList.get(0).numActivities > 1) {
                if(taskList.get(0).topActivity.getClassName().contains("ActivityHome")
                        || taskList.get(0).topActivity.getClassName().contains("ActivitySearchProducts")||
                        taskList.get(0).topActivity.getClassName().contains("ActivityContainer"))
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }
    void  getToken(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( MainActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String updatedToken = instanceIdResult.getToken();
                Log.e("Updated Token",updatedToken);
                SharedPreference.getInstance(MainActivity.this).setString(C.DEVICE_TOKEN,updatedToken);

            }
        });
    }
    public void fragmnetLoader(int fragmentType, Bundle bundle) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (fragmentType) {
                case C.FRAGMENT_SPLASH:
                    fragment = new FragmentSplash();
                    fragmentTransaction.replace(R.id.container, fragment);
                    fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_SPLASH);
                    break;
                case C.FRAGMENT_AUTHNITICATION:
                    fragment = new FragmentAuthentication();
                    fragmentTransaction.replace(R.id.container, fragment);
                    //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_LOGIN);
                    break;


            }
            fragment.setArguments(bundle);
            fragmentTransaction.commit();
            getSupportFragmentManager().executePendingTransactions();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        getSupportFragmentManager().executePendingTransactions();
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {

//            Log.e("CountPop", getSupportFragmentManager().getBackStackEntryCount() + "");

            int fragmentCount = getSupportFragmentManager().getBackStackEntryCount();
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(fragmentCount - 2);
            String fragmentTag = backEntry.getName();

            getSupportFragmentManager().popBackStack();

            getSupportFragmentManager().executePendingTransactions();

        } else {
            finish();
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            fragment=getVisibleFragment();
            if (fragment instanceof FragmentAuthentication  ) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

}
