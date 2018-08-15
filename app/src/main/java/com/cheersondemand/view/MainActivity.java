package com.cheersondemand.view;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  Fabric.with(this, new Crashlytics());
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);
        try {
            Intent intent = getIntent();
            Uri data = intent.getData();
            Log.e("DEBUG","DATA="+data.toString());
            if(data!=null && data.toString()!=null){
                String d=data.toString().substring(data.toString().lastIndexOf('/') + 1);
                Intent i = new Intent(this, ActivityContainer.class);
                Bundle bundle = new Bundle();
                i.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_RESET_PASSWORD);
                bundle.putString(C.TOKEN, d);
                i.putExtra(C.BUNDLE, bundle);
                startActivity(i);
            }
            else {
                gotoSplash();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            gotoSplash();
        }


    }


    void  gotoSplash(){
        SharedPreference.getInstance(this).setString(C.ORDER_ID,null);

        bundle = getIntent().getBundleExtra(C.BUNDLE);
        fragmentAction = getIntent().getIntExtra(C.FRAGMENT_ACTION, C.FRAGMENT_SPLASH);
        getToken();
        fragmnetLoader(fragmentAction, bundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Firebase", "token "+ FirebaseInstanceId.getInstance().getToken());

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
