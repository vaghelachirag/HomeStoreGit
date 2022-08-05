package com.nebulacompanies.ibo.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.ecom.adapter.OrderListAdapter;
import com.nebulacompanies.ibo.ecom.model.OrderListModel;
import com.nebulacompanies.ibo.ecom.model.OrderListModelEcom;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.shoppy.R;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;

import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListActivity extends AppCompatActivity {
    MyTextViewEcom txtTitle;
    ImageView imgBack;
    RelativeLayout laytoolbar;
    private RelativeLayout llEmpty;
    private ImageView imgError;
    private MyTextViewEcom txtErrorContent, txtRetry;
    MyBoldTextViewEcom txtErrorTitle;
    RecyclerView recList;
    MaterialProgressBar mProgressDialog;
    String userId;
    Session session;
    ArrayList<OrderListModel> orderListModel = new ArrayList<>();
    private OrderListAdapter myOrderDetailsActivity;
    APIInterface mAPIInterfaceTrackOrder;
    LinearLayout layData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        session = new Session(this);
        mAPIInterfaceTrackOrder = APIClient.getShoppyClient(getApplicationContext()).create(APIInterface.class);
        initUI();
        getOrderList();
    }

    private void initUI() {
        laytoolbar = findViewById(R.id.toolbarlay);
        txtTitle = findViewById(R.id.toolbar_title1);
        imgBack = findViewById(R.id.img_back);
        recList = findViewById(R.id.recycler_view);
        laytoolbar.setVisibility(View.VISIBLE);
        txtTitle.setText("Purchase History");
        userId = session.getShoppyUserID();
        layData = findViewById(R.id.laydata);
        imgBack.setOnClickListener(v -> onBackPressed());

        // Error View
        llEmpty =  findViewById(R.id.llEmpty);
        imgError =  findViewById(R.id.imgError);
        txtErrorTitle =  findViewById(R.id.txtErrorTitle);
        txtErrorContent =  findViewById(R.id.txt_error_content);
        txtRetry =  findViewById(R.id.txtRetry);
        mProgressDialog = findViewById(R.id.progresbar);
        txtRetry.setOnClickListener(v -> getOrderList());

    }


    private void getOrderList() {
        if (Utils.isNetworkAvailable(OrderListActivity.this)) {

            mProgressDialog.setVisibility(View.VISIBLE);
            Call<OrderListModelEcom> wsCallingEvents = mAPIInterfaceTrackOrder.getOrderListEcom(userId);
            wsCallingEvents.enqueue(new Callback<OrderListModelEcom>() {
                @Override
                public void onResponse(Call<OrderListModelEcom> call, Response<OrderListModelEcom> response) {
                    mProgressDialog.setVisibility(View.GONE);
                    orderListModel.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            OrderListModelEcom orderListModelEcom = response.body();
                            if (orderListModelEcom.getResponse().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                llEmpty.setVisibility(View.GONE);
                                orderListModel.addAll(orderListModelEcom.getResponse().getData());
                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderListActivity.this);
                                recList.setLayoutManager(mLayoutManager);
                                recList.setItemAnimator(new DefaultItemAnimator());
                                myOrderDetailsActivity = new OrderListAdapter(OrderListActivity.this, orderListModel);
                                recList.setAdapter(myOrderDetailsActivity);
                                Log.e("ORDERListAPI", "ORDERListAPI: " + new Gson().toJson(response.body()));
                            }
                        }
                    }

                    if (orderListModel.size() > 0) {
                        llEmpty.setVisibility(View.GONE);
                        layData.setVisibility(View.VISIBLE);
                    } else {
                        noRecordsFound();
                    }
                }

                @Override
                public void onFailure(Call<OrderListModelEcom> call, Throwable t) {

                    mProgressDialog.setVisibility(View.GONE);
                    Log.e("ORDERListAPIError", "ORDERListAPI: " + t);
                    noRecordsFound();

                }
            });
        } else {
            noInternetAvailable();
        }
    }

    //Error View
    void noRecordsFound() {
        txtErrorTitle.setText("You have not placed any orders yet.");
        txtErrorContent.setText("Looks like you have no orders in your order list.");
        imgError.setImageResource(R.drawable.ic_shopping_bag);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        layData.setVisibility(View.GONE);
    }

    private void noInternetAvailable() {
        layData.setVisibility(View.GONE);
        mProgressDialog.setVisibility(View.GONE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setText(R.string.error_content);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
    }
}
