package com.cheersondemand.view.fragments;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cheersondemand.R;
import com.cheersondemand.model.GuestUserCreateResponse;
import com.cheersondemand.model.UserResponse;
import com.cheersondemand.model.authentication.AuthenticationResponse;
import com.cheersondemand.model.logout.LogoutResponse;
import com.cheersondemand.model.profile.ProfileUpdateRequest;
import com.cheersondemand.presenter.profile.IProfileViewPresenter;
import com.cheersondemand.presenter.profile.ProfileViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.ImageLoader.ImageLoader;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_CANCELED;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUpdateProfile extends Fragment implements View.OnClickListener, IProfileViewPresenter.IProfileView {


    @BindView(R.id.imgProfile)
    CircularImageView imgProfile;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPhoneNo)
    EditText etPhoneNo;
    @BindView(R.id.btnSaveProfile)
    Button btnSaveProfile;
    Unbinder unbinder;
    AuthenticationResponse authenticationResponse;
    @BindView(R.id.rlView)
    RelativeLayout rlView;
    @BindView(R.id.ivCamera)
    ImageView ivCamera;
    Util util;
    IProfileViewPresenter iProfileViewPresenter;
    boolean isRemoved = false;
    ImageLoader imageLoader;
    private Uri fileUri = null;
    private int GALLERY = 1, CAMERA = 2;
    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

           /* if (source.toString().equals("-")){
                return "";
            }*/
            return source.toString();
        }
    };


    public FragmentUpdateProfile() {
        // Required empty public constructor
    }

    public static Bitmap rotateImageIfRequired(Bitmap img, String selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    public static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iProfileViewPresenter = new ProfileViewPresenterImpl(this, getActivity());
        util = new Util();
        imageLoader = new ImageLoader(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgProfile.setOnClickListener(this);
        btnSaveProfile.setOnClickListener(this);
        authenticationResponse = SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER);
        initFields();

        setDetail(authenticationResponse);

    }

    void setDetail(AuthenticationResponse authenticationResponse) {
        etName.setText(authenticationResponse.getData().getUser().getName());
        etEmail.setText(authenticationResponse.getData().getUser().getEmail());
        etPhoneNo.setText(authenticationResponse.getData().getUser().getPhoneNumber());
        etName.clearFocus();
        etEmail.clearFocus();
        etPhoneNo.clearFocus();
        if (authenticationResponse.getData().getUser().getProfilePicture() != null) {
            imageLoader.DisplayImage(authenticationResponse.getData().getUser().getProfilePicture(), imgProfile);
            //  Util.setImage(getActivity(),authenticationResponse.getData().getUser().getProfilePicture(),imgProfile);
            ivCamera.setVisibility(View.VISIBLE);

        } else {
            //  Util.setImage(getActivity(),"",imgProfile);
            //  imageLoader.DisplayImage("", imgProfile);
            ivCamera.setVisibility(View.VISIBLE);
            //imgProfile.setImageResource(R.drawable.missing);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText(R.string.edit_profile);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    void initFields() {
        //   etPhoneNo.setFilters(new InputFilter[]{filter});

        btnSaveProfile.setEnabled(false);
        etName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {


                validationFields();


            }
        });
        etPhoneNo.addTextChangedListener(new AutoAddTextWatcher(etPhoneNo,
                "-",
                3, 6));
/*
        etPhoneNo.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {


                validationFields();


            }
        });
*/
    }

    void validationFields() {
        if (etName.getText().length() > 2 && etName.length() < 31) {

            if (etPhoneNo.getText().length() > 0 && etPhoneNo.length() == 12) {


                btnSaveProfile.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_enable));
                btnSaveProfile.setEnabled(true);

            } else {

                btnSaveProfile.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
                btnSaveProfile.setEnabled(false);

            }
        } else {

            btnSaveProfile.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_disable));
            btnSaveProfile.setEnabled(false);

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgProfile:
                openPopUpForImageChange();
                break;
            case R.id.btnSaveProfile:
                updateProfile();
                break;
        }
    }

   /* private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getActivity(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }*/

    void updateProfile() {
        try {
            ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest();
            profileUpdateRequest.setName(etName.getText().toString());
            profileUpdateRequest.setPhoneNumber(etPhoneNo.getText().toString());

            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            if (fileUri != null) {
                //   File file = new File(getRealPathFromURI(fileUri));
                File file = Util.getCompressed(getActivity(), getRealPathFromURI(fileUri));
                // File file = new File(fileUri.getPath());
                RequestBody isDeleted = RequestBody.create(MediaType.parse("text/plain"), "" + 0);

                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), etName.getText().toString());
                RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), etPhoneNo.getText().toString());
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

                //  RequestBody requestFile = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(fileUri)), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("profile_picture", "image.jpg", requestFile);


                iProfileViewPresenter.updateProfile(token, id, body, name, phone, isDeleted);
            } else if (fileUri == null && isRemoved) {
                File file = new File(Environment.getExternalStorageDirectory() + "");
                RequestBody isDeleted = RequestBody.create(MediaType.parse("text/plain"), "" + 1);
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), etName.getText().toString());
                RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), etPhoneNo.getText().toString());
                //   RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

                // MultipartBody.Part body = MultipartBody.Part.createFormData("profile_picture", "image.jpg", requestFile);
                iProfileViewPresenter.updateProfile(token, id, null, name, phone, isDeleted);
            } else {
                iProfileViewPresenter.updateProfile(token, id, profileUpdateRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void openPopUpForImageChange() {
        if (isCameraPermissionGranted()) {
            showPictureDialog();
        } else {
            requestPermissionForCamera();
        }
    }

    /*   public String getRealPathFromURI(Uri uri) {
           Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
           cursor.moveToFirst();
           int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
           return cursor.getString(idx);
       }*/
    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    /*
        private String getRealPathFromURI(Uri contentUri) {
            Cursor cursor = null;
            try {
                String[] proj = { MediaStore.Images.Media.DATA };
                cursor = getActivity().getContentResolver().query(contentUri,  proj, null, null, null);

                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            } catch (Exception e) {
                Log.e("DEBUG", "getRealPathFromURI Exception : " + e.toString());
                return "";
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    */
    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems;

        if (authenticationResponse.getData().getUser().getProfilePicture() == null || isRemoved) {
            pictureDialogItems = new String[]{
                    "Select photo from gallery",
                    "Capture photo from camera"};
        } else {
            pictureDialogItems = new String[]{
                    "Select photo from gallery",
                    "Capture photo from camera",
                    "Remove"};
        }
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                            case 2:
                                isRemoved = true;
                                imgProfile.setImageResource(R.drawable.missing);
                                ivCamera.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                fileUri = contentURI;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    //   String path = saveImage(bitmap);
                    bitmap = rotateImageIfRequired(bitmap, getRealPathFromURI(fileUri));

                    bitmap = Util.scaleDown(bitmap, 500, true);
                    imgProfile.setImageBitmap(bitmap);
                    isRemoved = false;
                    ivCamera.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }

        } else if (requestCode == CAMERA) {
            try {

                BitmapFactory.Options options = new BitmapFactory.Options();

                // downsizing image as it throws OutOfMemory Exception for larger
                // images
                options.inSampleSize = 1;

                Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                        options);

                  bitmap = rotateImageIfRequired(bitmap, fileUri.getPath());
                imgProfile.setImageBitmap(bitmap);
                isRemoved = false;
                ivCamera.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
 /*   private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("DEBUG",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("DEBUG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("DEBUG", "Error accessing file: " + e.getMessage());
        }
    }

    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getActivity().getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }*/
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //  fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            File file = createImageFile();
            boolean isDirectoryCreated = file.getParentFile().mkdirs();
            Log.e("DEBUG", "openCamera: isDirectoryCreated: " + isDirectoryCreated);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri tempFileUri = FileProvider.getUriForFile(getActivity().getApplicationContext(),
                        "com.cheersondemand", // As defined in Manifest
                        file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, tempFileUri);
            } else {
                Uri tempFileUri = Uri.fromFile(file);
                fileUri = tempFileUri;
                intent.putExtra(MediaStore.EXTRA_OUTPUT, tempFileUri);
            }
            // intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
                Date());
        File file = new File(C.IMAGE_PATH, "IMG_" + timeStamp +
                ".jpg");
        fileUri = Uri.fromFile(file);
        return file;
    }

    public boolean isCameraPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {


                // ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }


    }

    private void requestPermissionForCamera() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
            //     ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Utils.PERMISSION_REQUEST_CODE);
            // getDailogConfirm("Please allow camera permission in App Settings for additional functionality.", "4");
            //  Toast.makeText(getActivity(),"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 2);
        }
    }

    @Override
    public void getResponseSuccess(LogoutResponse response) {

    }

    @Override
    public void onSuccessUpdateProfile(GuestUserCreateResponse Response) {
        try {
            if (Response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), getString(R.string.profile_updated), rlView, false);

                Gson gson = new Gson();
                String json = gson.toJson(Response.getData());
                UserResponse response = gson.fromJson(json, UserResponse.class);
                authenticationResponse.getData().setUser(response);
                SharedPreference.getInstance(getActivity()).setUser(C.AUTH_USER, authenticationResponse);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 10 seconds
                        getActivity().finish();
                    }
                }, 2000);
            } else {
                util.setSnackbarMessage(getActivity(), Response.getMessage(), rlView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(getActivity(), response, rlView, true);

    }

    @Override
    public void showProgress() {
        util.showDialog(C.MSG, getActivity());

    }

    @Override
    public void hideProgress() {
        util.hideDialog();

    }

    public class AutoAddTextWatcher implements TextWatcher {
        private CharSequence mBeforeTextChanged;
        private TextWatcher mTextWatcher;
        private int[] mArray_pos;
        private EditText mEditText;
        private String mAppentText;

        public AutoAddTextWatcher(EditText editText, String appendText, int... position) {
            this.mEditText = editText;
            this.mAppentText = appendText;
            this.mArray_pos = position.clone();
        }

        public AutoAddTextWatcher(EditText editText, String appendText, TextWatcher textWatcher, int... position) {
            this(editText, appendText, position);
            this.mTextWatcher = textWatcher;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            mBeforeTextChanged = s.toString();

            if (mTextWatcher != null)
                mTextWatcher.beforeTextChanged(s, start, count, after);

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            for (int i = 0; i < mArray_pos.length; i++) {
                if (((mBeforeTextChanged.length() - mAppentText.length() * i) == (mArray_pos[i] - 1) &&
                        (s.length() - mAppentText.length() * i) == mArray_pos[i])) {
                    mEditText.append(mAppentText);

                    break;
                }

                if (((mBeforeTextChanged.length() - mAppentText.length() * i) == mArray_pos[i] &&
                        (s.length() - mAppentText.length() * i) == (mArray_pos[i] + 1))) {
                    int idx_start = mArray_pos[i] + mAppentText.length() * i;
                    int idx_end = Math.min(idx_start + mAppentText.length(), s.length());

                    String sub = mEditText.getText().toString().substring(idx_start, idx_end);

                    if (!sub.equals(mAppentText)) {
                        mEditText.getText().insert(s.length() - 1, mAppentText);
                    }

                    break;
                }

                if (mAppentText.length() > 1 &&
                        (mBeforeTextChanged.length() - mAppentText.length() * i) == (mArray_pos[i] + mAppentText.length()) &&
                        (s.length() - mAppentText.length() * i) == (mArray_pos[i] + mAppentText.length() - 1)) {
                    int idx_start = mArray_pos[i] + mAppentText.length() * i;
                    int idx_end = Math.min(idx_start + mAppentText.length(), s.length());

                    mEditText.getText().delete(idx_start, idx_end);

                    break;
                }

            }

            if (mTextWatcher != null)
                mTextWatcher.onTextChanged(s, start, before, count);

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mTextWatcher != null) {
                mTextWatcher.afterTextChanged(s);
            }
            validationFields();


        }

    }

}
