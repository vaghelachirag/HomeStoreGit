package com.nebulacompanies.ibo.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.ecom.MainActivity;
import com.nebulacompanies.ibo.ecom.MyAddressAccountActivity;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.LoginValidate;
import com.nebulacompanies.ibo.shoppy.R;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.util.UtilsVersion;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppyHomeScreen extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layPurchaseHistory, layEcom, layShare, layRate, layAddress, layFb, layYoutube, layProfile, layHomeStore;
    LinearLayout layInventory, laysellprod, laysleshistory, laynebula;
    Button logout;
    Session session;
    LinearLayout layContact;
    APIInterface mAPIInterfaceProfile;

    MyTextViewEcom tvProfileName, tvProfileEmail, tvProfileMobile, tvProfileRank, tvKyc, txtpackname;
    //ImageView imgProfileClose;
    //MyTextViewEcom tvProfileName, tvProfileEmail, tvProfileMobile, tvProfileRank,tvKyc;
    //ImageView imgProfileClose,imgKycStatus;
    Button btnOk;
    TextView txtName, tvVersionName;
    Utils utils = new Utils();
    boolean isKyc;
    LoginValidate loginValidate;
    Dialog dialog;
    MyTextViewEcom txtAbout, txtReturn, txtShipping, txtPrivacy, txtContact;

    String about="https://shop.nebulacare.in/Home/About";
    String returnpolicy="https://shop.nebulacare.in/Home/ReturnPolicy";
    String shipping="https://shop.nebulacare.in/Home/ShippingPolicy";
    String privacy="https://shop.nebulacare.in/Home/PrivacyPolicy";
    String contactus="https://shop.nebulacare.in/Home/Contact";
    UtilsVersion utilsVersion = new UtilsVersion();

    @SuppressLint("NonConstantResourceId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppy_home);
        Utils.darkenStatusBar(this, R.color.colorNotification);

        initUI();
        initDialog();
        setDefData();
        getUserDetail(false);
        utilsVersion.checkVersion(this);
    }

    private void initDialog() {
        dialog = new Dialog(ShoppyHomeScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_profile);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        // This is line that does all the magic
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);

        tvProfileName = (MyTextViewEcom) dialog.findViewById(R.id.tv_my_profile_name);
        tvProfileEmail = (MyTextViewEcom) dialog.findViewById(R.id.tv_my_profile_email);
        tvProfileMobile = (MyTextViewEcom) dialog.findViewById(R.id.tv_my_profile_mobile);
        tvProfileRank = (MyTextViewEcom) dialog.findViewById(R.id.tv_my_rank);
        //  imgProfileClose = (ImageView) dialog.findViewById(R.id.img_profile_close);
        btnOk = (Button) dialog.findViewById(R.id.okButton);
        btnOk.setOnClickListener(v -> {
            dialog.dismiss();
            enableAllButtons();
        });
        // imgProfileClose.setOnClickListener(v -> dialog.dismiss());

    }


    private void initUI() {

        session = new Session(this);
        mAPIInterfaceProfile = APIClient.getClient(this).create(APIInterface.class);

        layEcom = findViewById(R.id.lay_shopecom);
        layPurchaseHistory = findViewById(R.id.lay_purchase_history);
        layShare = findViewById(R.id.lay_share);
        layRate = findViewById(R.id.lay_rate);
        layContact = findViewById(R.id.lay_contact);
        layFb = findViewById(R.id.lay_fb);
        layInventory = findViewById(R.id.lay_inventory);
        laysellprod = findViewById(R.id.laysellproduct);
        laysleshistory = findViewById(R.id.laysaleshistory);
        layYoutube = findViewById(R.id.lay_youtube);
        laynebula = findViewById(R.id.laynebulapro);
        logout = findViewById(R.id.btn_logout);
        layProfile = findViewById(R.id.lay_profile);
        layAddress = findViewById(R.id.lay_address);
        layHomeStore = findViewById(R.id.ly_homeStore);
        txtName = findViewById(R.id.textNameid);
        tvVersionName = findViewById(R.id.tv_version);

        txtAbout= findViewById(R.id.open_aboutus);
        txtReturn= findViewById(R.id.open_return);
        txtShipping= findViewById(R.id.open_shipping);
        txtPrivacy= findViewById(R.id.open_privacy);
        txtContact= findViewById(R.id.open_contact);


        txtAbout.setPaintFlags(txtAbout.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        txtReturn.setPaintFlags(txtReturn.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        txtShipping.setPaintFlags(txtShipping.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        txtPrivacy.setPaintFlags(txtPrivacy.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        txtContact.setPaintFlags(txtContact.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        tvKyc = findViewById(R.id.tv_KYC);

        txtAbout.setOnClickListener(this);
        txtReturn.setOnClickListener(this);
        txtShipping.setOnClickListener(this);
        txtPrivacy.setOnClickListener(this);
        txtContact.setOnClickListener(this);
        layInventory.setOnClickListener(this);
        laysellprod.setOnClickListener(this);
        laysleshistory.setOnClickListener(this);
        layProfile.setOnClickListener(this);
        layAddress.setOnClickListener(this);
        layEcom.setOnClickListener(this);
        layPurchaseHistory.setOnClickListener(this);
        layShare.setOnClickListener(this);
        layRate.setOnClickListener(this);
        layContact.setOnClickListener(this);
        layFb.setOnClickListener(this);
        layYoutube.setOnClickListener(this);
        logout.setOnClickListener(this);
        layHomeStore.setOnClickListener(this);
        laynebula.setOnClickListener(this);
    }

    private void setDefData() {
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        assert info != null;
        String versionName = info.versionName;
        //int versioncode = info.versionCode;
        tvVersionName.setText("APP VERSION: " + versionName);
        txtpackname = findViewById(R.id.tvpackagename);
//Talha Khan,10357 (Star Platinum)
        txtName.setText(session.getUserName() + ", " + session.getLoginID());
        // txtpackname.setText(session.getShoppyName());
    }

    boolean callFinish = false;

    @Override
    protected void onResume() {
        super.onResume();
        callFinish = false;
        enableAllButtons();

    }

    private void enableAllButtons() {
        layProfile.setEnabled(true);
        layAddress.setEnabled(true);
        layEcom.setEnabled(true);
        layPurchaseHistory.setEnabled(true);
        layShare.setEnabled(true);
        layRate.setEnabled(true);
        layContact.setEnabled(true);
        layFb.setEnabled(true);
        layYoutube.setEnabled(true);
        logout.setEnabled(true);
        layInventory.setEnabled(true);
        laysellprod.setEnabled(true);
        laysleshistory.setEnabled(true);
        layHomeStore.setEnabled(true);
        laynebula.setEnabled(true);
    }


    @Override
    public void onClick(View v) {
        v.setEnabled(false);

        if (v == layProfile) {
            if (dataLoad)
                dialog.show();
            else {
                getUserDetail(true);
            }

        } else if (v == layAddress) {
            Intent intent = new Intent(this, MyAddressAccountActivity.class);
            startActivity(intent);
        } else if (v == layHomeStore) {
            showComingSoon();
            /*Intent intent = new Intent(this, HomeStoreDetailsActivity.class);
            startActivity(intent);*/
        } else if (v == laysleshistory) {
            //  showComingSoon();
            Intent dash = new Intent(ShoppyHomeScreen.this, SalesHistoryActivity.class);
            startActivity(dash);
        } else if (v == laysellprod) {
            // showComingSoon();
            Intent dash = new Intent(ShoppyHomeScreen.this, SellProductsActivity.class);
            startActivity(dash);
        } else if (v == layInventory) {
            Intent dash = new Intent(ShoppyHomeScreen.this, MyInventoryActivity.class);
            startActivity(dash);
        } else if (v == layEcom) {
            Intent dash = new Intent(ShoppyHomeScreen.this, MainActivity.class);
            startActivity(dash);
        } else if (v == layPurchaseHistory) {
            // layPurchaseHistory.setEnabled(false);
            Intent intent = new Intent(this, OrderListActivity.class);
            startActivity(intent);
        } else if (v == layShare) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Nebula");
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "http://play.google.com/store/apps/details?id=" + getPackageName();
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            enableAllButtons();
        } else if (v == layRate) {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            }
            enableAllButtons();
        } else if (v == layContact) {
            Uri uri = Uri.parse("https://www.instagram.com/nebulacare.in/");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        uri));
            }
            enableAllButtons();
        } else if (v == layFb) {

            Uri uri = Uri.parse("https://www.facebook.com/nebulacare.in");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.facebook.katana");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        uri));
            }
            enableAllButtons();
        } else if (v == layYoutube) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/c/nebulacompanies?sub_confirmation=1"));
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }

            enableAllButtons();
        } else if (v == logout) {
            utils.getLogout(this, session);
        } else if (v == laynebula) {
            String appPackageName ="com.nebulacompanies.ibo";
           if(!openApp(appPackageName)){
               startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
           }
        }
        else if(v==txtAbout){
            openweburl(about);
            v.setEnabled(true);

        }
        else if(v==txtReturn){openweburl(returnpolicy);
        v.setEnabled(true);
        }
        else if(v==txtShipping){openweburl(shipping);
        v.setEnabled(true);
        }
        else if(v==txtPrivacy){openweburl(privacy);
        v.setEnabled(true);
        }
        else if(v==txtContact){openweburl(contactus);
        v.setEnabled(true);
        }
    }

    private void openweburl(String link) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

    public  boolean openApp( String packageName) {
        PackageManager manager = getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {
                return false;
                //throw new ActivityNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(i);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void showComingSoon() {
        Toast.makeText(this, "Coming soon!", Toast.LENGTH_SHORT).show();
        enableAllButtons();
    }

    boolean dataLoad = false;

    public void getUserDetail(boolean showdialog) {
        if (Utils.isNetworkAvailable(this)) {

            Call<LoginValidate> wsCallingToken = mAPIInterfaceProfile.loginValidate(session.getIboKeyId());
            wsCallingToken.enqueue(new Callback<LoginValidate>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NotNull Call<LoginValidate> call, @NotNull Response<LoginValidate> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            loginValidate = response.body();
                            LoginValidate.Data mdata = loginValidate.getData();

                            boolean expiredUser = mdata.isIsBlocked() || mdata.isIsExpired() || mdata.isIsSuspended();
                            if (expiredUser) {
                                String msg = "your account has been " + (mdata.isIsBlocked() ? "blocked" : (mdata.isIsExpired() ? "expired" : "suspended"));
                                utils.showErrorDialog(ShoppyHomeScreen.this, false, "Authentication Error", msg, true, session);
                            } else {
                                dataLoad = true;
                                isKyc = mdata.isIsKYC();
                                tvProfileName.setText(mdata.getFirstName() + " " + (TextUtils.isEmpty(mdata.getLastName()) ? "" : mdata.getLastName()));
                                tvProfileEmail.setText(mdata.getEmail());
                                tvProfileMobile.setText(mdata.getMobile());
                                tvProfileRank.setText(mdata.getRank());

                                tvKyc.setVisibility(isKyc ? View.VISIBLE : View.INVISIBLE);

                                //Talha Khan,10357 (Star Platinum)
                                txtName.setText(session.getUserName() + ", " + session.getLoginID() + " (" + mdata.getRank() + ")");
                                if (showdialog)
                                    dialog.show();
                            }
                        } else {

                        }
                    } else {

                    }
                    enableAllButtons();
                }

                @Override
                public void onFailure(@NotNull Call<LoginValidate> call, @NotNull Throwable t) {
                    enableAllButtons();
                    Log.e(getClass().getSimpleName(), "ERROR : " + t.getMessage());
                    Log.e("Token Get 4", "Token Get 4: " + t.getMessage());
                }
            });
        } else {
            enableAllButtons();
            utils.dialogueNoInternet(this);
        }
    }
}