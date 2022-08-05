package com.nebulacompanies.ibo.ecom;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.ecom.fragment.HomeFragment;
import com.nebulacompanies.ibo.ecom.model.CartListTotalCountModelEcom;
import com.nebulacompanies.ibo.ecom.model.MyTotalCountCartData;
import com.nebulacompanies.ibo.ecom.model.ProfileModelEcom;
import com.nebulacompanies.ibo.ecom.utils.CallBackListener;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.shoppy.R;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.util.UtilsVersion;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;

public class MainActivity extends AppCompatActivity implements
        CounterCallback,
        CallBackListener {

    APIInterface mAPIInterface, mAPIInterfaceShoppy;
    MaterialProgressBar mProgressDialog;
    public RelativeLayout lnCart, rlSearchView;

    MyTextViewEcom txtErrorContent;
    ImageView imgSearch, imgFav, imgCart, imgSearchClose, imgMainBack;
    MyTextViewEcom tvToolbarTitle, tvCartBadge, tvPvStatus, txtRetry;
    NebulaEditTextEcom editSearch;
    LinearLayout lnLocation;
    FrameLayout frameLayoutContent;
    UtilsVersion utilsVersion = new UtilsVersion();
    ArrayList<MyTotalCountCartData> myTotalCountCartData = new ArrayList<>();
    String m_deviceId, uniqueID, userName;
    Session session;
    public static String Dialogdismis = "dialogdismis";
    com.nebulacompanies.ibo.util.Session sessionNormal;
    Utils utils;
    IntentFilter filterLogin;

    @SuppressLint({"ClickableViewAccessibility", "HardwareIds"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ecom);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Utils.darkenStatusBar(this, R.color.colorNotification);

        initPref();
        initUI();
        initMenu();
        initData();
        initOnclick();
        callAPI();
    }

    private void initPref() {
        mAPIInterface = APIClient.getClient(MainActivity.this).create(APIInterface.class);
        mAPIInterfaceShoppy = APIClient.getShoppyClient(MainActivity.this).create(APIInterface.class);
        session = new Session(this);
        sessionNormal = new com.nebulacompanies.ibo.util.Session(this);

       // utilsVersion.checkVersion(this);
        utils = new Utils();
        m_deviceId = android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

        if (m_deviceId == null || m_deviceId.equals("")) {
            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();
                SharedPreferences preferences = this.getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefDeviceid, randomID);
                editor.apply();
                m_deviceId = randomID;
            } else {
                m_deviceId = uniqueID;
            }
        }

    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        frameLayoutContent = findViewById(R.id.frameLayoutContent);
        lnLocation = findViewById(R.id.ln_location);
        mProgressDialog = findViewById(R.id.progresbar);

        txtErrorContent = findViewById(R.id.txt_error_content);
        txtRetry = findViewById(R.id.txtRetry);
        tvPvStatus = findViewById(R.id.tv_pv_status);

        filterLogin = new IntentFilter(utils.actionLogin);
        registerReceiver(myReceiver, filterLogin);

        lnCart = toolbar.findViewById(R.id.toolbar_settings);
        rlSearchView = toolbar.findViewById(R.id.rl_search_view);
        imgSearch = toolbar.findViewById(R.id.img_search);
        imgFav = toolbar.findViewById(R.id.img_my_fav);
        imgCart = toolbar.findViewById(R.id.img_my_cart);
        imgMainBack = toolbar.findViewById(R.id.img_main_back);
        tvCartBadge = toolbar.findViewById(R.id.cart_badge);
        imgSearchClose = toolbar.findViewById(R.id.img_search_close);
        editSearch = toolbar.findViewById(R.id.editMobileNo);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initOnclick() {

        editSearch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                searchIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(searchIntent);
                overridePendingTransition(R.anim.fade_in_new, R.anim.fade_out_new);
                return true;
            }
            return false;
        });

        imgCart.setOnClickListener(view -> {
            Intent login = new Intent(MainActivity.this, MyCartActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
        });

        imgMainBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void initData() {
        lnLocation.setVisibility(View.GONE);
    }

    private void initMenu() {
        openFragment(HomeFragment.newInstance("", ""));
    }

    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(utils.actionLogin)) {
                setSessionData();
            }
        }
    };

    public void setSessionData() {
        userName = sessionNormal.getUserName();
        Log.d("username", "username:" + userName);
        if (userName == null || userName.equals("")) {
            getMyProfile();
        } else {
            tvToolbarTitle.setText("Hi, " + userName);
        }
    }

    public void callAPI() {
        setSessionData();
        getTokenUniCommerce();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(myReceiver);
    }

    void getTokenUniCommerce() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            Call<JsonObject> wsCallingUniCommLogin = mAPIInterface.getUniCommToken("it@nebulacompanies.com", "Nebula7890", "password", "my-trusted-client");
            wsCallingUniCommLogin.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try {
                                trimCache(MainActivity.this);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            try {
                                String responseString = response.body().toString();
                                Log.i("INFO response", "response: " + responseString);
                                Log.i("TokenError", "TokenError: " + responseString);
                                JSONObject jsonObject = new JSONObject(responseString);

                                String tokenUniCommerce = jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_TOKEN_TYPE_UNICOMMERCE) + " "
                                        + jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_ACCESS_TOKEN_UNICOMMERCE);

                                session.setAccessTokenUnicommerce(tokenUniCommerce);
                                Log.d("tokenUniCommerce: ", "tokenUniCommerce: " + tokenUniCommerce);
                                android.util.Log.e("tokenUniCommerce", "tokenUniCommerce: " + new Gson().toJson(response.body()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                        }
                    } else {
                        Log.e("tokenUniCommerce", "tokenUniCommerce: " + new Gson().toJson(response.body()));
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    // mProgressDialog.dismiss();
                    Log.i("tokenUniCommerce", "tokenUniCommerce: " + t);
                }
            });
        } else {
            //  AppUtils.displayNetworkErrorMessage(this);
        }
    }


    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }


    //Bottom Sheet Code.
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutContent, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void getMyCartListTotalCount(String deviceId, String userID) {
        Call<CartListTotalCountModelEcom> wsCallingEvents = mAPIInterfaceShoppy.getMyCartTotalCountListEcom(deviceId, userID);
        wsCallingEvents.enqueue(new Callback<CartListTotalCountModelEcom>() {
            @Override
            public void onResponse(Call<CartListTotalCountModelEcom> call, Response<CartListTotalCountModelEcom> response) {
                myTotalCountCartData.clear();
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.d("CartAPI", "CartAPI: " + response);
                        if (response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                            tvCartBadge.setText("0");
                        } else if (response.body().getResponse().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                            Log.d("CartAPIIn", "CartAPIIn: " + response.body().getResponse().getData());
                            String count = String.valueOf(response.body().getResponse().getData().getSumOfQty());
                            tvCartBadge.setText(count);
                            // final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CartListTotalCountModelEcom> call, @NotNull Throwable t) {
                Log.d("CartAPI", "CartAPI: " + t);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        utils.checkExpireUser(mAPIInterface, this, session);
        utilsVersion.checkVersion(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getMyCartListTotalCount(m_deviceId, session.getShoppyUserID());
    }

    @Override
    public void onMethodCounterCallback() {
        Log.d("counter API Final", "counter API Final");

    }

    @Override
    public void onCallBack() {
        getMyCartListTotalCount(m_deviceId, session.getShoppyUserID());
    }

    private void getMyProfile() {
        Call<ProfileModelEcom> wsCallingEvents = mAPIInterface.getMyProfileEcom();
        wsCallingEvents.enqueue(new Callback<ProfileModelEcom>() {
            @Override
            public void onResponse(Call<ProfileModelEcom> call, Response<ProfileModelEcom> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        //  Log.d("PROFILEAPI", "PROFILEAPI: " + response);
                        if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                            //   Log.d("PROFILEAPIResponse", "PROFILEAPIResponse: " + new Gson().toJson(response.body()));
                            String firstName = response.body().getData().getFirstName();
                          /*  String lastName = response.body().getData().getLastName();
                            String email = response.body().getData().getEmail();
                            String gender = response.body().getData().getGender();
                            String mobile = response.body().getData().getMobile();
                            Log.d("firstName", "firstName: " + firstName);*/
                            userName = "Hi, " + firstName;
                            tvToolbarTitle.setText(userName);

                            sessionNormal.setUserName(firstName);
                           /* SharedPreferences preferences = getSharedPreferences(PrefUtils.prefUsername, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(PrefUtils.prefUsername, userName);
                            editor.apply();*/

                        } /*else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                        }*/

                    }
                } else if (response.code() == 401) {
                    Log.d("AddressListGETINData401", "AddressListGETINData: " + new Gson().toJson(response.body()));

                    /*Intent dash = new Intent(getActivity(), LoginActivityEcom.class);
                    startActivity(dash);*/

                }
            }

            @Override
            public void onFailure(Call<ProfileModelEcom> call, Throwable t) {
                Log.d("PROFILEAPIINERROR", "PROFILEAPIINERROR: " + t);
            }
        });
    }

   /* private void noInternetAvailable() {
        //rvSearch.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setVisibility(View.VISIBLE);
        txtErrorContent.setText(R.string.error_content);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.GONE);
        txtRetry.setVisibility(View.VISIBLE);
    }*/

}
