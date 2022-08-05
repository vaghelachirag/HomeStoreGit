package com.nebulacompanies.ibo.ecom;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.shoppy.R;
import com.nebulacompanies.ibo.ecom.adapter.MyAddressAccountAdapter;
import com.nebulacompanies.ibo.ecom.model.AddressData;
import com.nebulacompanies.ibo.ecom.model.AddressModel;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;

import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class MyAddressAccountActivity extends AppCompatActivity {

    MaterialProgressBar mProgressDialog;
    APIInterface mAPIInterface, mAPIInterfaceShoppy;

    Toolbar toolbar;
    ImageView imgBackArrow, imgSearch, imgSearchClose;
    NebulaEditTextEcom editSearch;
    RelativeLayout rlSearchView;
    MyTextViewEcom tvToolbarTitle, tvMyAccountAddresses;
    MyBoldTextViewEcom txtYourAddress;
    Session session;
    private RecyclerView recyclerView;
    private ArrayList<AddressModel> addressDataList = new ArrayList<>();
    private MyAddressAccountAdapter myAddressAdapter;

    String deviceId, addressMobile, addressEditCallBack, addressAddCallBack, userId;
    MyBoldTextViewEcom textPersonal;

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_account);

        //  AddressDataPrepare();
        session = new Session(this);
        userId = session.getShoppyUserID();
        deviceId = android.provider.Settings.Secure.getString(
                this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        // mAPIInterface = APIClient.getClient(MyAddressAccountActivity.this).create(APIInterface.class);
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        mAPIInterfaceShoppy = APIClient.getShoppyClient(this).create(APIInterface.class);

        findById();
        setAction();
        getAddressList();
        setLayoutVisibility(0);
      /*  TelephonyManager TelephonyMgr = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId();*/

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setAction() {

        String value = editSearch.getText().toString();

        imgSearchClose.setOnClickListener(view -> {
            if (value != null) {
                editSearch.getText().clear();
            }
        });

        imgSearch.setOnClickListener(view -> rlSearchView.setVisibility(View.VISIBLE));

        imgBackArrow.setOnClickListener(view -> onBackPressed());

        tvMyAccountAddresses.setOnClickListener(v -> {
            Intent intent = new Intent(MyAddressAccountActivity.this, AddAddressAcountNewActivity.class);
            startActivity(intent);
        });
    }

    private void findById() {

        recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        //getting the toolbar
        toolbar = findViewById(R.id.toolbar_search);
        mProgressDialog = findViewById(R.id.progresbar);
        imgBackArrow = findViewById(R.id.img_back);
        tvMyAccountAddresses = findViewById(R.id.tv_my_account_addresses);
        textPersonal = findViewById(R.id.text_label);
        txtYourAddress = findViewById(R.id.txt_YourAddress);

        imgSearch = toolbar.findViewById(R.id.img_search);
        rlSearchView = toolbar.findViewById(R.id.rl_search_view);
        imgSearchClose = toolbar.findViewById(R.id.img_search_close);
        editSearch = toolbar.findViewById(R.id.edt_search_search);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText("My Address");
    }

    private void setLayoutVisibility(int int_Visibility) {

        if (int_Visibility == 0) {
            txtYourAddress.setVisibility(View.VISIBLE);
            tvMyAccountAddresses.setVisibility(View.GONE);
            textPersonal.setVisibility(View.GONE);
        }
        else {
            txtYourAddress.setVisibility(View.VISIBLE);
            tvMyAccountAddresses.setVisibility(View.VISIBLE);
            textPersonal.setVisibility(View.GONE);
        }
    }


    public void getAddressList() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
           /* mProgressDialog = new ProgressDialog(MyAddressAccountActivity.this, R.style.MyTheme);
            mProgressDialog.show();

            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog_ecom);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if(keyCode==KeyEvent.KEYCODE_BACK && !event.isCanceled()) {
                        if(mProgressDialog.isShowing()) {
                            //your logic here for back button pressed event
                            mProgressDialog.dismiss();
                        }
                        return true;
                    }
                    return false;
                }
            });*/

            mProgressDialog.setVisibility(View.VISIBLE);

            Call<AddressData> wsCallingEvents = mAPIInterfaceShoppy.getAddressListEcom(userId);
            wsCallingEvents.enqueue(new Callback<AddressData>() {
                @Override
                public void onResponse(Call<AddressData> call, Response<AddressData> response) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }
                    addressDataList.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {

                            Log.d("AddressListGETOUT", "AddressListGETOUT: " + response.body().getResponse().getMessage());
                            if (response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                // noRecordsFound();
                            } else if (response.body().getResponse().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                addressDataList.clear();
                                addressDataList.addAll(response.body().getResponse().getData());
                                Log.d("AddressListGETIN", "AddressListGETIN: " + response.body().getResponse().getMessage());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyAddressAccountActivity.this);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                myAddressAdapter = new MyAddressAccountAdapter(addressDataList, MyAddressAccountActivity.this);
                                // myCartProductsAdapter = new MyCartProductsAdapter(MyCartActivity.this,cartModels);
                                recyclerView.setAdapter(myAddressAdapter);
                                Log.d("AddressListGETINData", "AddressListGETINData: " + response.body().getResponse().getData());

                            } else if (response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                            }
                            if (response.body().getResponse().getData().size() > 0) {
                                // llEmpty.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                setLayoutVisibility(0);
                            } else {
                                //llEmpty.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                                setLayoutVisibility(1);
                            }
                        }
                    } else if (response.code() == 401) {
                        Log.d("AddressListGETINData401", "AddressListGETINData: " + new Gson().toJson(response.body()));

                       /* Intent addressAccountIntent = new Intent(MyAddressAccountActivity.this, LoginActivityEcom.class);
                        addressAccountIntent.putExtra("ADDRESSACCOUNTCALL", 1);
                        startActivity(addressAccountIntent);*/
                        setLayoutVisibility(1);
                    }
                }

                @Override
                public void onFailure(Call<AddressData> call, Throwable t) {
                    mProgressDialog.setVisibility(View.GONE);
                    Log.d("AddressListGETError", "AddressListGETError: " + t);

                }
            });
        } else {
            // noInternetConnection();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent editAddressCallBack = getIntent();
        if (editAddressCallBack != null && addressEditCallBack != null) {
            addressMobile = editAddressCallBack.getStringExtra("editAddressCallBackAccount");
            getAddressList();

        } else if (editAddressCallBack != null && addressAddCallBack != null) {
            addressMobile = editAddressCallBack.getStringExtra("addAddressCallBackAccount");
            getAddressList();
        }

        //   tvMyAccountAddresses.setVisibility(addressDataList.size()>0?View.GONE:View.VISIBLE);
    }
}