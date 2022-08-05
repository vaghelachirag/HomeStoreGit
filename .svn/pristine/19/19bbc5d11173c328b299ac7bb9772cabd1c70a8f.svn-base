package com.nebulacompanies.ibo.ecom;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.florent37.viewtooltip.ViewTooltip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.ecom.adapter.MyViewOrderAdapter;
import com.nebulacompanies.ibo.ecom.adapter.ViewSaleHistoryAdapter;
import com.nebulacompanies.ibo.ecom.model.CartListTotalCountModelEcom;
import com.nebulacompanies.ibo.ecom.model.MyTotalCountCartData;
import com.nebulacompanies.ibo.ecom.model.OrderListModel;
import com.nebulacompanies.ibo.ecom.model.TrackListModelEcom;
import com.nebulacompanies.ibo.ecom.model.TrackOrderModel;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.OrderListCallback;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.OrderHistoryModel;
import com.nebulacompanies.ibo.shoppy.BuildConfig;
import com.nebulacompanies.ibo.shoppy.R;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.tomergoldst.tooltips.ToolTip;
import com.tomergoldst.tooltips.ToolTipsManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.WHITE;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;

public class ViewSaleHistoryActivity extends AppCompatActivity {


    RecyclerView recList;
    MaterialProgressBar mProgressDialog;
    Session session;
    String userId, iboId;
    ViewSaleHistoryAdapter viewSaleHistoryAdapter;
    List<OrderHistoryModel.SalesDetail> orderDetailsListModels;
    Intent viewOrderData;
    MyTextViewEcom txtID;
    MyTextViewEcom txtProducts,total;
    MyTextViewEcom txtUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sale_history);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        session = new Session(this);

        Toolbar toolbar = findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setContentInsetStartWithNavigation(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                onBackPressed();
            }
        });
        initUI();

    }
    MenuItem myActionMenuItem;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

         myActionMenuItem = menu.findItem(R.id.action_search);
        try {
            myActionMenuItem.setVisible(orderDetailsListModels.size() > 2);
        }catch (Exception e){e.printStackTrace();}
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setQueryHint("Search for Product");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);// Do not iconify the widget; expand it by default
        try {
            LinearLayout searchFrameLL = (LinearLayout) searchView.findViewById(R.id.search_edit_frame);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(-25, 0, 8, 0); //params.setMargins(left, top, right, bottom)
            // params.setMarginStart(0);  //(or just use individual like this
            searchFrameLL.setLayoutParams(params);
        }catch (Exception e){e.printStackTrace();}
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // This is your adapter that will be filtered
                viewSaleHistoryAdapter.getFilter().filter(newText.toString());
                //  Toast.makeText(getApplicationContext(),"textChanged :"+newText,Toast.LENGTH_LONG).show();
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                // **Here you can get the value "query" which is entered in the search box.**
                viewSaleHistoryAdapter.getFilter().filter(query.toString());
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

        recList = findViewById(R.id.recycler_view);

        txtID = findViewById(R.id.tv_order_id);
        txtProducts= findViewById(R.id.tv_order_grand_total_value);
        total= findViewById(R.id.tv_order_shiping_price);
        txtUser= findViewById(R.id.tv_shipping_address_one);

        recList = findViewById(R.id.recyclerview_view_sale);


        viewOrderData = getIntent();
        String listdata = viewOrderData.getStringExtra("listdata");
      //  String orderlistdata = viewOrderData.getStringExtra("orderlistdata");

        OrderHistoryModel.Datum listdataModel = new Gson().fromJson(listdata, new TypeToken<OrderHistoryModel.Datum>() {
        }.getType());
        orderDetailsListModels = listdataModel.getSalesDetails();
        txtID.setText(listdataModel.getOrderNumber());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recList.setLayoutManager(mLayoutManager);
        recList.setItemAnimator(new DefaultItemAnimator());
        viewSaleHistoryAdapter = new ViewSaleHistoryAdapter(ViewSaleHistoryActivity.this, (ArrayList<OrderHistoryModel.SalesDetail>) orderDetailsListModels);
        recList.setAdapter(viewSaleHistoryAdapter);

        int count=0;
        int grandtotal=0;
        for (int i = 0; i < orderDetailsListModels.size(); i++) {
            count=count+orderDetailsListModels.get(i).getQuantity();
            int totalp= (int) orderDetailsListModels.get(i).getTotal();
            grandtotal=grandtotal+totalp;
        }
try {
    myActionMenuItem.setVisible(orderDetailsListModels.size() > 2);
}catch (Exception e){e.printStackTrace();}
        String cusName=listdataModel.getCustomerName();
        String iboid=listdataModel.getIboid();
        String uname=TextUtils.isEmpty(cusName)?(iboid+" ("+listdataModel.getIboName()+")"):
                (listdataModel.getPhoneNumber()+" ("+cusName+")");


        txtUser.setText(""+uname);
        txtProducts.setText(""+count);
        total.setText(""+grandtotal);
    }


}