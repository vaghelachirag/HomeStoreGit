package com.nebulacompanies.ibo.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.adapter.MyInventoryAdapter;
import com.nebulacompanies.ibo.ecom.model.OrderInventoryModel;
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

public class MyInventoryActivity extends AppCompatActivity {

    // MyTextViewEcom txtTitle;
    //ImageView imgBack;
    //RelativeLayout laytoolbar;
    //Error View
    private RelativeLayout llEmpty;
    private ImageView imgError;
    private MyTextViewEcom txtErrorContent, txtRetry;
    MyBoldTextViewEcom txtErrorTitle;
    RecyclerView recList;
    MaterialProgressBar mProgressDialog;
    String userId, iboId;
    Session session;
    private final ArrayList<OrderInventoryModel.Datum> inventoryList = new ArrayList<>();
    private MyInventoryAdapter myInventoryAdapter;
    APIInterface mAPIInterface;
    LinearLayout layData;
    MyTextViewEcom textProducts, textPrice, textPV, textNV, textBV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_inventory);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        mAPIInterface = APIClient.getShoppyClient(getApplicationContext()).create(APIInterface.class);
        initUI();
        getInventoryList();
    }

    MenuItem myActionMenuItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        myActionMenuItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        try {
            LinearLayout searchFrameLL = (LinearLayout) searchView.findViewById(R.id.search_edit_frame);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(-25, 0, 8, 0); //params.setMargins(left, top, right, bottom)
            // params.setMarginStart(0);  //(or just use individual like this
            searchFrameLL.setLayoutParams(params);
        }catch (Exception e){e.printStackTrace();}
        try {
            myActionMenuItem.setVisible(inventoryList.size() > 2);
        }catch (Exception e){e.printStackTrace();}
        searchView.setQueryHint("Search for Product");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);// Do not iconify the widget; expand it by default

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // This is your adapter that will be filtered
                myInventoryAdapter.getFilter().filter(newText.toString());
                //  Toast.makeText(getApplicationContext(),"textChanged :"+newText,Toast.LENGTH_LONG).show();
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                // **Here you can get the value "query" which is entered in the search box.**
                myInventoryAdapter.getFilter().filter(query.toString());
                // Toast.makeText(getApplicationContext(), "searchvalue :" + query, Toast.LENGTH_LONG).show();
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }

    private void initUI() {
        session = new Session(this);
        userId = session.getShoppyUserID();
        iboId = session.getIboKeyId();

        Toolbar toolbar = findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setContentInsetStartWithNavigation(0);
        toolbar.setNavigationOnClickListener(v -> {
            // perform whatever you want on back arrow click
            onBackPressed();
        });
       /* Toolbar toolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);*/
/*
        laytoolbar = findViewById(R.id.toolbarlay);
        txtTitle = findViewById(R.id.toolbar_title1);
        imgBack = findViewById(R.id.img_back);
        laytoolbar.setVisibility(View.VISIBLE);
        txtTitle.setText("My Inventory");

        imgBack.setOnClickListener(v -> onBackPressed());*/
        recList = findViewById(R.id.recycler_view);

        // Error View
        llEmpty = (RelativeLayout) findViewById(R.id.llEmpty);
        imgError = (ImageView) findViewById(R.id.imgError);
        txtErrorTitle = (MyBoldTextViewEcom) findViewById(R.id.txtErrorTitle);
        txtErrorContent = (MyTextViewEcom) findViewById(R.id.txt_error_content);
        txtRetry = (MyTextViewEcom) findViewById(R.id.txtRetry);

        textProducts = findViewById(R.id.tv_total_products);
        textPrice = findViewById(R.id.tv_mycart_retail_price);
        textBV = findViewById(R.id.tv_bv_value);
        textNV = findViewById(R.id.tv_nv_price);
        textPV = findViewById(R.id.tv_pv_price);

        mProgressDialog = findViewById(R.id.progresbar);
        layData = findViewById(R.id.laydata);
        txtRetry.setOnClickListener(v -> getInventoryList());

        recList = findViewById(R.id.recycler_view);
        noRecordsFound();
    }

    private void getInventoryList() {
        try {
            myActionMenuItem.setVisible(false);
        }catch (Exception e){e.printStackTrace();}

        if (Utils.isNetworkAvailable(MyInventoryActivity.this)) {

            mProgressDialog.setVisibility(View.VISIBLE);
            Call<OrderInventoryModel> wsCallingEvents = mAPIInterface.getInventoryList(userId, iboId);
            wsCallingEvents.enqueue(new Callback<OrderInventoryModel>() {
                @Override
                public void onResponse(Call<OrderInventoryModel> call, Response<OrderInventoryModel> response) {

                    inventoryList.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            OrderInventoryModel inventoryModel = response.body();
                            if (inventoryModel.getResponse().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                llEmpty.setVisibility(View.GONE);
                                inventoryList.addAll(inventoryModel.getResponse().getData());
                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyInventoryActivity.this);
                                recList.setLayoutManager(mLayoutManager);
                                recList.setItemAnimator(new DefaultItemAnimator());
                                myInventoryAdapter = new MyInventoryAdapter(MyInventoryActivity.this, inventoryList);
                                recList.setAdapter(myInventoryAdapter);

                                Log.e("ORDERListAPI", "ORDERListAPI: " + new Gson().toJson(response.body()));
                            }
                            if (inventoryList.size() > 0) {
                                llEmpty.setVisibility(View.GONE);
                                layData.setVisibility(View.VISIBLE);

                                int qnty = 0;
                                int total = 0;
                                int pv = 0;
                                int nv = 0;
                                int bv = 0;
                                for (int i = 0; i < inventoryList.size(); i++) {
                                    OrderInventoryModel.Datum mdata = inventoryList.get(i);
                                    qnty = qnty + mdata.getQuantity();
                                    total = (int) (total + (mdata.getHomeStorePrice() * mdata.getQuantity()));
                                    pv = (int) (pv + (mdata.getPv() * mdata.getQuantity()));
                                    nv = (int) (nv + (mdata.getNv() * mdata.getQuantity()));
                                    if (i == 0)
                                        bv = (int) mdata.getBv();
                                }
                                textBV.setText(bv + "%");
                                textNV.setText(String.valueOf(nv));
                                textPV.setText(String.valueOf(pv));
                                textPrice.setText(String.valueOf(total));
                                textProducts.setText(String.valueOf(qnty));
                                try {
                                    myActionMenuItem.setVisible(inventoryList.size() > 2);
                                }catch (Exception e){e.printStackTrace();}

                            } else {
                                noRecordsFound();
                            }
                        } else {
                            noRecordsFound();
                        }
                    } else
                        noRecordsFound();
                    mProgressDialog.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<OrderInventoryModel> call, Throwable t) {
                    mProgressDialog.setVisibility(View.GONE);
                    noRecordsFound();
                }
            });
        } else {
            noInternetAvailable();
        }
    }

    void noRecordsFound() {
        txtErrorTitle.setText("You have not any inventory yet.");
        txtErrorContent.setText("Looks like you have no inventory in your inventory list.");
        imgError.setImageResource(R.drawable.ic_shopping_bag);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        layData.setVisibility(View.GONE);
    }

    private void noInternetAvailable() {
        layData.setVisibility(View.GONE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setText(R.string.error_content);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
    }
}