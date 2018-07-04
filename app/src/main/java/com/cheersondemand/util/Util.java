package com.cheersondemand.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheersondemand.R;
import com.cheersondemand.model.SideMenuItem;
import com.cheersondemand.model.productList.Sort;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by GAURAV on 5/23/2018.
 */

public class Util {
    Snackbar snackbar;

    public static boolean isNetworkConnectivity(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void setSnackbarMessage(Activity activity, String status, View view, boolean isError) {


        snackbar = Snackbar
                .make(view, status, Snackbar.LENGTH_LONG)
                .setAction("X", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(ContextCompat.getColor(activity, R.color.cross_color));
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        if(isError) {
            textView.setTextColor(ContextCompat.getColor(activity, R.color.error_color));
        }
        else {
            textView.setTextColor(ContextCompat.getColor(activity, R.color.cross_color));

        }
        sbView.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));

        snackbar.show();


    }

    public static List<SideMenuItem> getSideFilterList(){
      List<SideMenuItem> sideMenuItems = new ArrayList<SideMenuItem>();
        sideMenuItems.add(new SideMenuItem(R.string.price_range, 0, C.TAG_FRAGMENT_PRICE_RANGE));
        sideMenuItems.add(new SideMenuItem(R.string.brand, 0, C.TAG_FRAGMENT_BRAND));
        sideMenuItems.add(new SideMenuItem(R.string.category, 0, C.TAG_FRAGMENT_CATEGORY));
        return sideMenuItems;
    }


    public static List<Sort> getSortList(){
        List<Sort> sorts = new ArrayList<Sort>();
        sorts.add(new Sort(1,R.string.low_to_high, R.drawable.group_23, R.drawable.right));
        sorts.add(new Sort(2,R.string.high_to_low, R.drawable.group_23, R.drawable.right));
        sorts.add(new Sort(3,R.string.new_product, R.drawable.new_sticker, R.drawable.right));
        sorts.add(new Sort(4,R.string.popularity, R.drawable.fire_1_copy, R.drawable.right));

        return sorts;
    }
    public static boolean isValidMail(String email) {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public static boolean isValidPhone(CharSequence phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        }
    }
    private static String uniqueID = null;
    public synchronized static String id(Context context) {
        if (uniqueID == null) {

            uniqueID = SharedPreference.getInstance(context).getString(C.PREF_UNIQUE_ID);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreference.getInstance(context).setString(C.PREF_UNIQUE_ID, uniqueID);
            }
        }
        return uniqueID;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + 10 + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static void setImage(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
               // .placeholder(R.drawable.placeholder)
    //.error(R.drawable.imagenotfound)
                .into(imageView);
    }

    public static String get2Decimal(Double d){
          DecimalFormat df2 = new DecimalFormat(".##");
          return df2.format(d);

    }
}
