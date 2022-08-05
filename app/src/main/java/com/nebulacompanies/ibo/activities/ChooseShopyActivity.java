package com.nebulacompanies.ibo.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.Interface.OnItemSelect;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.ecom.MainActivity;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.ChooseShoppyModel;
import com.nebulacompanies.ibo.model.MyShoppyModel;
import com.nebulacompanies.ibo.model.TermsConditionModel;
import com.nebulacompanies.ibo.shoppy.R;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.ecom.utils.Utils.actionFinish;

public class ChooseShopyActivity extends AppCompatActivity implements View.OnClickListener, OnItemSelect {
    //RecyclerView rvShoppy;
    private MyTextViewEcom textTnc;
    // MyBoldTextViewEcom txtHeader;
    // private Button btnNext;
    Utils utils;
    APIInterface mAPIInterface;
    MaterialProgressBar mProgressDialog;
    Dialog dialog;
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorContent, txtRetry, txtErrorTitle;
    List<ChooseShoppyModel.Datum> mdataList = new ArrayList<>();
    List<TermsConditionModel.TermsConditionData> termsConditionData = new ArrayList<>();
    //ChooseShoppyAdapter mAdapter;
    int selPos = -1;
    Session session;
    ImageView logout;
    ImageView imgShop;
    MyTextViewEcom txtUsername;
    // ScrollView scrollView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_shopy);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        findViews();
        initTermsDialog();
        // callAPI();
    }

    TextView txtTerms, txtGotit;

    private void initTermsDialog() {
        dialog = new Dialog(ChooseShopyActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        txtGotit = dialog.findViewById(R.id.text_okay);
        txtTerms = dialog.findViewById(R.id.terms);
        txtGotit.setOnClickListener(v -> dialog.dismiss());
    }

    private void callAPI() {
        callMyShoppy();
        callTermsCondition();
    }

    private void callShoppyList() {
        session.setShoppyid(0);
        session.setShoppyAmt(0);
        session.setShoppyName("");
        session.setTempShoppyid(0);
        session.setRepurchaseamt(0);
        if (Utils.isNetworkAvailable(ChooseShopyActivity.this)) {
            mProgressDialog.setVisibility(View.VISIBLE);

            Call<ChooseShoppyModel> wsCallingEvents = mAPIInterface.getShoppyList();
            wsCallingEvents.enqueue(new Callback<ChooseShoppyModel>() {
                @Override
                public void onResponse(@NotNull Call<ChooseShoppyModel> call, @NotNull Response<ChooseShoppyModel> response) {
                    mdataList.clear();

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            ChooseShoppyModel.Response orderListModelEcom = response.body().getResponse();
                            if (orderListModelEcom.getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                mdataList.addAll(orderListModelEcom.getData());
                               /* final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ChooseShopyActivity.this);
                                rvShoppy.setLayoutManager(mLayoutManager);
                                rvShoppy.setItemAnimator(new DefaultItemAnimator());
                                mAdapter = new ChooseShoppyAdapter(ChooseShopyActivity.this, mdataList, ChooseShopyActivity.this::onItemSelect);
                                rvShoppy.setAdapter(mAdapter);*/
                            }
                            if (mdataList.size() > 0) {
                                selPos = mdataList.get(0).getId();
                                imgShop.setVisibility(View.VISIBLE);
                                textTnc.setVisibility(View.VISIBLE);
                                llEmpty.setVisibility(View.GONE);

                                Glide.with(ChooseShopyActivity.this)
                                        .load(mdataList.get(0).getImagePath())
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .placeholder(R.drawable.placeholder)
                                        .into(imgShop);

                            } else {
                                noRecordsFound();
                            }
                        } else {
                            noRecordsFound();
                        }
                    } else {
                        noRecordsFound();
                    }
                    mProgressDialog.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ChooseShoppyModel> call, Throwable t) {
                    noRecordsFound();
                    mProgressDialog.setVisibility(View.GONE);
                }
            });
        } else {
            noInternetAvailable();
        }
    }

    private void callMyShoppy() {
        if (Utils.isNetworkAvailable(ChooseShopyActivity.this)) {
            mProgressDialog.setVisibility(View.VISIBLE);

            Call<MyShoppyModel> wsCallingEvents = mAPIInterface.getMyShoppy(session.getShoppyUserID());
            wsCallingEvents.enqueue(new Callback<MyShoppyModel>() {
                @Override
                public void onResponse(Call<MyShoppyModel> call, @NotNull Response<MyShoppyModel> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            MyShoppyModel.Response orderListModelEcom = response.body().getResponse();
                            if (orderListModelEcom.getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                int shoppyid = orderListModelEcom.getData().getShoppyPackageId();
                                if (shoppyid == 0) {
                                    callShoppyList();
                                } else {
                                    session.setShoppyid(shoppyid);
                                    session.setShoppyAmt(orderListModelEcom.getData().getMinAmount());
                                    session.setShoppyName(orderListModelEcom.getData().getName());
                                    session.setTempShoppyid(shoppyid);
                                    session.setRepurchaseamt(orderListModelEcom.getData().getRepurchase());
                                    openHomeScreen();
                                }
                            } else {
                                callShoppyList();
                            }
                        } else {
                            noRecordsFound();
                        }
                    } else {
                        noRecordsFound();
                    }
                    mProgressDialog.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<MyShoppyModel> call, Throwable t) {
                    noRecordsFound();
                    mProgressDialog.setVisibility(View.GONE);
                }
            });
        } else {
            noInternetAvailable();
        }
    }

    private void callTermsCondition() {
        if (Utils.isNetworkAvailable(ChooseShopyActivity.this)) {
            mProgressDialog.setVisibility(View.VISIBLE);

            Call<TermsConditionModel> wsCallingEvents = mAPIInterface.getTermsCondition();
            wsCallingEvents.enqueue(new Callback<TermsConditionModel>() {
                @Override
                public void onResponse(Call<TermsConditionModel> call, @NotNull Response<TermsConditionModel> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            TermsConditionModel.Response termCondition = response.body().getResponse();
                            termsConditionData = termCondition.getData();
                            if (termCondition.getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                              /*  dialog = new Dialog(ChooseShopyActivity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.dialog_info);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                TextView txtGotit = dialog.findViewById(R.id.text_okay);
                                TextView txtTerms = dialog.findViewById(R.id.terms);*/
                                txtTerms.setText(termsConditionData.get(0).getContent());
                                // txtGotit.setOnClickListener(v -> dialog.dismiss());
                            }

                        } else {
                            noRecordsFound();
                        }
                    } else {
                        noRecordsFound();
                    }
                    mProgressDialog.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<TermsConditionModel> call, Throwable t) {
                    noRecordsFound();
                    mProgressDialog.setVisibility(View.GONE);
                }
            });
        } else {
            noInternetAvailable();
        }
    }

    private void openHomeScreen() {
        Intent dash;
        dash = new Intent(ChooseShopyActivity.this, ShoppyHomeScreen.class);
        startActivity(dash);
        finish();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }

    private void findViews() {
        utils = new Utils();
        session = new Session(getApplicationContext());
        mAPIInterface = APIClient.getShoppyClient(getApplicationContext()).create(APIInterface.class);
        registerReceiver(mMessageReceiver, new IntentFilter(actionFinish));


        imgShop = findViewById(R.id.imgshoppy);
        // rvShoppy = findViewById(R.id.rv_shoppy);
        textTnc = findViewById(R.id.text_tnc);
       /* txtHeader = findViewById(R.id.text_chooseshoppy);
        scrollView = findViewById(R.id.scrolllay);

        btnNext = findViewById(R.id.nextButton);*/
        mProgressDialog = findViewById(R.id.progresbar);
        llEmpty = findViewById(R.id.llEmpty);
        imgError = findViewById(R.id.imgError);
        txtErrorTitle = findViewById(R.id.txtErrorTitle);
        txtErrorContent = findViewById(R.id.txtErrorSubTitle);
        txtRetry = findViewById(R.id.txtRetry);
        logout = findViewById(R.id.imgLogout);
        txtUsername = findViewById(R.id.text_name);

        txtRetry.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.GONE);
        //  scrollView.setVisibility(View.GONE);
        textTnc.setVisibility(View.GONE);

        textTnc.setOnClickListener(this);
        imgShop.setOnClickListener(this);
        // btnNext.setOnClickListener(this);
        logout.setOnClickListener(this);
        txtRetry.setOnClickListener(this);

        txtUsername.setText("Hi, " + session.getUserName());
    }

    @Override
    public void onClick(View v) {
        if (v == imgShop) {
            imgShop.setEnabled(false);
            openDashboard();
        } else if (v == textTnc) {
            dialog.show();
        } else if (v == logout) {
            utils.getLogout(this, session);
        } else if (v == txtRetry) {
            callAPI();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgShop.setEnabled(true);
        callAPI();
    }

    //Error View
    void noRecordsFound() {
        selPos = -1;
        txtErrorTitle.setText("");
        txtErrorContent.setText("No Record Found.");
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        imgShop.setVisibility(View.GONE);
        textTnc.setVisibility(View.GONE);
    }

    private void noInternetAvailable() {
        selPos = -1;
        mProgressDialog.setVisibility(View.GONE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setText(R.string.error_content);
        llEmpty.setVisibility(View.VISIBLE);
        imgShop.setVisibility(View.GONE);
        textTnc.setVisibility(View.GONE);
    }

    private void openDashboard() {
        if (selPos != -1) {
            session.setShoppyid(0);
            session.setTempShoppyid(mdataList.get(0).getId());
            session.setShoppyAmt(mdataList.get(0).getMinAmount());
            session.setShoppyName(mdataList.get(0).getName());
            session.setRepurchaseamt(mdataList.get(0).getRepurchase());

            Intent i = new Intent(ChooseShopyActivity.this, MainActivity.class);
            startActivity(i);
            imgShop.setEnabled(true);
        }
    }

    @Override
    public void onItemSelect(Integer parentposition) {
        selPos = parentposition;
        openDashboard();
    }
}