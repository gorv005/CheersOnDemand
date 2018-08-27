package com.cheersondemand.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheersondemand.R;
import com.cheersondemand.model.SideMenuItem;
import com.cheersondemand.model.productList.Sort;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by AB on 5/23/2018.
 */

public class Util {
    private static final int JCB = 7;
    private static final int UNION =8 ;
    Snackbar snackbar;
    ProgressDialog progressDialog=null;
    public static final String VISA_PREFIX = "4";
    public static final String UNION_PREFIX = "62";

    public static final String MASTERCARD_PREFIX = "51,52,53,54,55,";
    public static final String DISCOVER_PREFIX = "6011";
    public static final String AMEX_PREFIX = "34,37,";
    public static final String DINERS_PREFIX = "30,36,38,";

    public static final int NONE = 0;

    public static final int VISA = 1;
    public static final int MASTERCARD = 2;
    public static final int DISCOVER = 3;
    public static final int AMEX = 4;
    public static final int PHONE =5;
    public static final int DINERS = 6;

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
        sideMenuItems.add(new SideMenuItem(R.string.sub_category, 0, C.TAG_FRAGMENT_SUB_CAT));

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
                //.placeholder(R.drawable.missing)
    .error(R.drawable.missing)
                .into(imageView);
    }

    public static String get2Decimal(Double d){
          DecimalFormat df2 = new DecimalFormat(".##");
          return df2.format(d);

    }


    public void showDialog(String msg, Context context){
        try {

            if (progressDialog == null || !progressDialog.isShowing()) {

                progressDialog = new ProgressDialog(context,R.style.customDialog);
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

               /* progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);*/
              /*  progressDialog.setContentView(R.layout.progress_dialog);
               TextView text = (TextView) progressDialog.findViewById(R.id.tvMsg);
               text.setText(msg);*/
                progressDialog.show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void hideDialog(){
        try {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public  static  boolean isKeyBoardVisible(Context context){
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) {
           return true;
        } else {
            return false;

        }
    }

    public  static Date getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 1);
        calendar.set(Calendar.MINUTE, 30);

       return calendar.getTime();

    }
    public  static Date getDefaultDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        calendar.add(Calendar.MINUTE, 30);
        calendar.add(Calendar.SECOND, 10);

        return calendar.getTime();

    }
    public static Date get1monthLaterDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
       return calendar.getTime();

    }
    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        catch (Exception e){

        }
    }
    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {

        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
/*
          Bitmap newBitmap=   Bitmap.createScaledBitmap(realImage,(int)(realImage.getWidth()*0.1), (int)(realImage.getHeight()*0.1), true);
*/
        return newBitmap;
    }

    public static int getCardTypeUsingBrandName(String cardType) {

        if (cardType.equalsIgnoreCase(C.VISA))
            return VISA;
       else if (cardType.equalsIgnoreCase(C.MasterCard))
            return MASTERCARD;
        else if (cardType.equalsIgnoreCase(C.AMEX))
            return AMEX;
      else   if (cardType.equalsIgnoreCase(C.Discover))
            return DISCOVER;
        else   if (cardType.equalsIgnoreCase(C.DINERS))
            return DINERS;
        else   if (cardType.equalsIgnoreCase(C.JCB))
            return JCB;
        else   if (cardType.equalsIgnoreCase(C.UNION))
            return UNION;
        return NONE;
    }
    public static String getCardTypeUsingBrandName(int cardType) {

        if (cardType==VISA)
            return C.VISA;
        else if (cardType==MASTERCARD)
            return C.MasterCard;
        else if (cardType==AMEX)
            return C.AMEX;
      else   if (cardType==DISCOVER)
            return C.Discover;
        else   if (cardType==DINERS)
            return C.DINERS;
        else   if (cardType==JCB)
            return C.JCB;
        else   if (cardType==UNION)
            return C.UNION;
        return "";
    }
    public static int getCardType(String cardNumber) {

        if (cardNumber.substring(0, 1).equals(VISA_PREFIX))
            return VISA;
        else if (MASTERCARD_PREFIX.contains(cardNumber.substring(0, 2) + ","))
            return MASTERCARD;
        else if (AMEX_PREFIX.contains(cardNumber.substring(0, 2) + ","))
            return AMEX;
        else if (cardNumber.substring(0, 4).equals(DISCOVER_PREFIX))
            return DISCOVER;
        else if (DINERS_PREFIX.contains(cardNumber.substring(0, 2) + ","))
            return DINERS;
        else if (Integer.parseInt(cardNumber.substring(0, 4))>=3528 &&Integer.parseInt(cardNumber.substring(0, 4))<=3589)
            return JCB;
       else if (cardNumber.substring(0, 2).equals(UNION_PREFIX))
            return UNION;
        return NONE;
    }


    public static void setCardType(int type,ImageView ivType,Context context)
    {
        switch(type)
        {
            case VISA:
                ivType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_visa_card));
                break;
            case MASTERCARD:
                ivType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_mastercard_card));
                break;
            case AMEX:
                ivType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_amex_card));
                break;
            case DISCOVER:
                ivType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_discover));
                break;
            case DINERS:
                ivType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_diners_club));
                break;
            case JCB:
                ivType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_jcb));
                break;
            case UNION:
                ivType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_union_pay));
                break;
            case NONE:
                ivType.setImageResource(android.R.color.transparent);
                break;

        }


    }

    public  static String getValueNumberFormat(String s){
      if(s!=null){
          if(s.length()==4){
          s=   s.substring(0, 1) + "," + s.substring(1, s.length());
          }
      }
      return s;
    }

    public  static String getNumberWithoutCommaFormat(String s){
        if(s!=null){
            if(s.length()==5){
                s=  s.replaceAll(",","");
            }
        }
        return s;
    }
    public static String handleCardNumber(String inputCardNumber, String seperator) {
        String unformattedText = inputCardNumber.replace(seperator, "");
        int cardType = getCardType(inputCardNumber);
        String format = (cardType == AMEX) ? C.CARD_NUMBER_FORMAT_AMEX : C.CARD_NUMBER_FORMAT;
        StringBuilder sbFormattedNumber = new StringBuilder();
        for(int iIdx = 0, jIdx = 0; (iIdx < format.length()) && (unformattedText.length() > jIdx); iIdx++) {
            if(format.charAt(iIdx) == C.CHAR_X)
                sbFormattedNumber.append(unformattedText.charAt(jIdx++));
            else
                sbFormattedNumber.append(format.charAt(iIdx));
        }

        return sbFormattedNumber.toString();
    }
    public static String handlePhoneNumber(String inputCardNumber, String seperator) {
        String unformattedText = inputCardNumber.replace(seperator, "");
        String format = C.PHONE_NUMBER_FORMAT;
        StringBuilder sbFormattedNumber = new StringBuilder();
        for(int iIdx = 0, jIdx = 0; (iIdx < format.length()) && (unformattedText.length() > jIdx); iIdx++) {
            if(format.charAt(iIdx) == C.CHAR_X)
                sbFormattedNumber.append(unformattedText.charAt(jIdx++));
            else
                sbFormattedNumber.append(format.charAt(iIdx));
        }

        return sbFormattedNumber.toString();
    }
    private final static AtomicInteger c = new AtomicInteger(0);
    public static int getID() {
        return c.incrementAndGet();
    }


    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyymmddhhmmss", Locale.getDefault());
    public static File getCompressed(Context context, String path) throws IOException {

        if(context == null)
            throw new NullPointerException("Context must not be null.");
        //getting device external cache directory, might not be available on some devices,
        // so our code fall back to internal storage cache directory, which is always available but in smaller quantity
        File cacheDir = context.getExternalCacheDir();
        if(cacheDir == null)
            //fall back
            cacheDir = context.getCacheDir();

        String rootDir = cacheDir.getAbsolutePath() + "/ImageCompressor";
        File root = new File(rootDir);

//Create ImageCompressor folder if it doesnt already exists.
        if(!root.exists())
            root.mkdirs();

//decode and resize the original bitmap from @param path.
        Bitmap bitmap = decodeImageFromFiles(path, /* your desired width*/300, /*your desired height*/ 300);

//create placeholder for the compressed image file
        File compressed = new File(root, SDF.format(new Date()) + ".jpg" /*Your desired format*/);

//convert the decoded bitmap to stream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

/*compress bitmap into byteArrayOutputStream
 Bitmap.compress(Format, Quality, OutputStream)

Where Quality ranges from 1–100.
 */
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);

/*
 Right now, we have our bitmap inside byteArrayOutputStream Object, all we need next is to write it to the compressed file we created earlier,
 java.io.FileOutputStream can help us do just That!

*/
        FileOutputStream fileOutputStream = new FileOutputStream(compressed);
        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.flush();

        fileOutputStream.close();

//File written, return to the caller. Done!
        return compressed;
    }

    public static Bitmap decodeImageFromFiles(String path, int width, int height) {
        BitmapFactory.Options scaleOptions = new BitmapFactory.Options();
        scaleOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, scaleOptions);
        int scale = 1;
        while (scaleOptions.outWidth / scale / 2 >= width
                && scaleOptions.outHeight / scale / 2 >= height) {
            scale *= 2;
        }
        // decode with the sample size
        BitmapFactory.Options outOptions = new BitmapFactory.Options();
        outOptions.inSampleSize = scale;
        return BitmapFactory.decodeFile(path, outOptions);
    }
}
