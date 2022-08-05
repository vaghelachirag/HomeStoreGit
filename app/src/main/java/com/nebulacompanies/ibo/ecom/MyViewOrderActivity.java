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
import com.nebulacompanies.ibo.shoppy.BuildConfig;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.shoppy.R;
import com.nebulacompanies.ibo.ecom.adapter.MyViewOrderAdapter;
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

public class MyViewOrderActivity extends AppCompatActivity implements OrderListCallback, OnItemClickTrack {

    ImageView imageView;
    MyButtonEcom btnAddToCart, btnOrderTrack;
    RecyclerView rvMyViewOrder;
    private ArrayList<OrderListModel.OrderDetailsListModel> orderDetailsListModels = new ArrayList<>();
    private MyViewOrderAdapter myViewOrderAdapter;
    private ArrayList<TrackListModelEcom> trackListModelEcoms = new ArrayList<>();

    RelativeLayout mRootLayout;
    LinearLayout lyshippingdetails, laywallet;
    APIInterface mAPIInterfaceShoppy;
    Intent viewOrderData;
    Session session;
    String m_deviceId, uniqueID, orderNumber, orderShippingAddress, orderBillingAddress,
            orderBillingAddressUser, orderShippingAddressUser, status, statusDate,
            orderShippingTranId, mobileNo;
    Integer isPickupPoint;
    int ordergrandTotalPrice, orderShippingCharge, orderSubTotal, ordertotal, homestoredisc, homestoreperc;
    //ArrayList<MyTotalCountCartData> myTotalCountCartData = new ArrayList<>();
    private ArrayList<TrackListModelEcom> trackListModels = new ArrayList<>();
    MyTextViewEcom tvOrderId, tvwalletvalue, tvOrderShippingAddress, tvShippingMobileNo, tvOrderBillingAddress, tvShippingAddressOne, tvbillingAddressOne,
            tvbillingShipping, tvBillingSubTotal, tvBillingGrandTotal, tvBillingTranID, tvpickuptitle,
            tvShippingProviderName, tvShippingNumber, tvCreateTicket, tvHomestoredisc,
            tvBillingfulltotal;

    MyBoldTextViewEcom tvshippingtitle, tvshippingProvider, tvShippingNo;
    ToolTipsManager mToolTipsManager;
    String pdf = "";
    MaterialProgressBar mProgressDialog1;
    String getFacility, invoicecode, valueCallBack;
    Utils utils = new Utils();
    int ewallet = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view_orders);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        session = new Session(this);
        viewOrderData = getIntent();
        fetchIntent();
        initUI();
        getTrackOrder(orderNumber);
        getInvoice(orderNumber);
        setDataUI();

    }

    private void fetchIntent() {
        if (viewOrderData != null) {
            String listdata = viewOrderData.getStringExtra("listdata");

            OrderListModel listdataModel = new Gson().fromJson(listdata,
                    new TypeToken<OrderListModel>() {
                    }.getType());
            orderDetailsListModels = listdataModel.getOrderDetails();
            orderNumber = listdataModel.getOrderNumber();

            ordergrandTotalPrice = listdataModel.getOrderShippingGrandTotal();
            orderShippingCharge = listdataModel.getOrderShippingShippingCharges();
            orderSubTotal = listdataModel.getOrderShippingSubTotal();
            ordertotal = listdataModel.getIboTotalprice();
            ewallet = listdataModel.geteWalletAmount();
            homestoredisc = listdataModel.getHomeStorediscount();
            homestoreperc = listdataModel.getHomeStorediscountPercentage();
            orderShippingAddress = listdataModel.getOrderShippingAddresses();
            orderBillingAddress = listdataModel.getOrderBillingAddresses();
            mobileNo = listdataModel.getMobileNo();
            orderBillingAddressUser = listdataModel.getOrderShippingBillingAddressUser();
            orderShippingAddressUser = listdataModel.getOrderShippingShippingAddressUser();
            orderShippingTranId = listdataModel.getOrderShippingTransactionId();
            isPickupPoint = listdataModel.getIsPickupPoint();
            status = listdataModel.getStatus();
            statusDate = listdataModel.getStatusUpdatedOn();
        }
    }

    private void setDataUI() {
        if (isPickupPoint == 0) {
            tvshippingtitle.setText("Shipping Detail");
            tvOrderBillingAddress.setText(orderBillingAddress);
            tvShippingMobileNo.setVisibility(View.VISIBLE);
            tvShippingMobileNo.setText("Mobile: +91" + mobileNo);
            lyshippingdetails.setVisibility(View.VISIBLE);
            mRootLayout.setVisibility(View.GONE);
        } else {
            tvshippingtitle.setText("Pickup Location");
            lyshippingdetails.setVisibility(View.GONE);
            mRootLayout.setVisibility(View.VISIBLE);
            //tvOrderBillingAddress.setText( orderBillingAddress );
            try {
                String currentString = orderBillingAddress;
                String[] separated = currentString.split("\\(Ph.:");
                String address = separated[0];
                String mobile = separated[1];
                tvOrderBillingAddress.setText(address);
                tvShippingMobileNo.setVisibility(View.VISIBLE);
                mobile = mobile.replaceAll("\\)", "");
                tvShippingMobileNo.setText("Mobile: " + mobile);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        tvOrderShippingAddress.setText(orderShippingAddress);
        tvShippingAddressOne.setText(orderShippingAddressUser);
        tvbillingAddressOne.setText(orderBillingAddressUser);

        tvBillingfulltotal.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvBillingfulltotal.setText("" + ordertotal);
        tvHomestoredisc.setText(homestoredisc + " (" + homestoreperc + "%)");
        tvBillingSubTotal.setText("" + orderSubTotal);
        tvbillingShipping.setText("" + orderShippingCharge);
        tvBillingGrandTotal.setText("" + ordergrandTotalPrice);


        tvBillingTranID.setText(orderShippingTranId);

        m_deviceId = android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

        ToolTip.Builder builder = new ToolTip.Builder(this, imageView, mRootLayout, "Tip message", ToolTip.POSITION_RIGHT_TO);

        Log.d("MDEVICEIDuniqueIDOrder", "MDEVICEIDuniqueIDOrder: " + uniqueID);
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
        tvOrderId.setOnClickListener(view -> {
            if (valueCallBack.equalsIgnoreCase("Dispatched") ||
                    valueCallBack.equalsIgnoreCase("Delivered")) {
                getInvoicePDF();
            } else {
                Toast.makeText(getApplicationContext(), "Your order is not yet dispatched.", Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMyViewOrder.setLayoutManager(mLayoutManager);
        rvMyViewOrder.setItemAnimator(new DefaultItemAnimator());
        myViewOrderAdapter = new MyViewOrderAdapter(MyViewOrderActivity.this,
                orderDetailsListModels,
                MyViewOrderActivity.this, orderNumber, status, statusDate,
                MyViewOrderActivity.this,
                isPickupPoint != 0);
        //productsAdapter = new ProductsAdapter(getActivity(),productArrayList);
        rvMyViewOrder.setAdapter(myViewOrderAdapter);
        btnOrderTrack.setVisibility(isPickupPoint != 0 ? View.GONE : View.VISIBLE);
        tvCreateTicket.setOnClickListener(v -> {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                Intent intent = new Intent(MyViewOrderActivity.this, CustomerSupport.class);
                intent.putExtra("showorder", 1);
                intent.putExtra("ticketid", orderNumber);
                startActivity(intent);
            } else {
                utils.dialogueNoInternet(this);
            }
        });
        btnOrderTrack.setOnClickListener(view -> {
            if (Utils.isNetworkAvailable(MyViewOrderActivity.this)) {
                mProgressDialog1.setVisibility(View.VISIBLE);
                Call<TrackListModelEcom> wsCallingEvents = mAPIInterfaceShoppy.getTrackEcom(orderNumber);
                wsCallingEvents.enqueue(new Callback<TrackListModelEcom>() {
                    @Override
                    public void onResponse(Call<TrackListModelEcom> call, Response<TrackListModelEcom> response) {
                        if (mProgressDialog1 != null) {
                            mProgressDialog1.setVisibility(View.VISIBLE);
                        }
                        if (response.isSuccessful()) {
                            trackListModels.clear();
                            if (response.code() == 200) {
                                assert response.body() != null;
                                if (response.body().getResponse().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                                    trackUrl = response.body().getResponse().getData().getTrackingData().getTrackUrl();

                                    if (mProgressDialog1 != null) {
                                        mProgressDialog1.setVisibility(View.GONE);
                                    }
                                    if (!TextUtils.isEmpty(trackUrl)) {

                                        Intent myWebLink = new Intent(Intent.ACTION_VIEW);
                                        myWebLink.setData(Uri.parse(trackUrl));
                                        startActivity(myWebLink);
                                    } else {
                                        if (mProgressDialog1 != null) {
                                            mProgressDialog1.setVisibility(View.GONE);
                                        }
                                        Toast.makeText(MyViewOrderActivity.this, "Tracking not available", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    if (mProgressDialog1 != null) {
                                        mProgressDialog1.setVisibility(View.GONE);
                                    }
                                }
                            } else {
                                if (mProgressDialog1 != null) {
                                    mProgressDialog1.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            if (mProgressDialog1 != null) {
                                mProgressDialog1.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TrackListModelEcom> call, Throwable t) {
                        Log.d("TrackOrderERROR", "TrackOrderERROR: " + t);
                        if (mProgressDialog1 != null) {
                            mProgressDialog1.setVisibility(View.GONE);
                        }
                    }
                });
            } else {
                utils.dialogueNoInternet(MyViewOrderActivity.this);
            }
        });
        if (Utils.showWallet && ewallet > 0) {
            tvwalletvalue.setText("" + ewallet);
            laywallet.setVisibility(View.VISIBLE);
        } else {
            laywallet.setVisibility(View.GONE);
        }
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
            myActionMenuItem.setVisible(orderDetailsListModels.size() > 2);
        }catch (Exception e){e.printStackTrace();}
        searchView.setQueryHint("Search for Product");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);// Do not iconify the widget; expand it by default

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // This is your adapter that will be filtered
                myViewOrderAdapter.getFilter().filter(newText.toString());
                //  Toast.makeText(getApplicationContext(),"textChanged :"+newText,Toast.LENGTH_LONG).show();
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                // **Here you can get the value "query" which is entered in the search box.**
                myViewOrderAdapter.getFilter().filter(query.toString());
                // Toast.makeText(getApplicationContext(), "searchvalue :" + query, Toast.LENGTH_LONG).show();
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }
    private void initUI() {

        // String orderId = orderNumber;
        mToolTipsManager = new ToolTipsManager();
        Toolbar toolbar = findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setContentInsetStartWithNavigation(0);
        toolbar.setNavigationOnClickListener(v -> {
            // perform whatever you want on back arrow click
            onBackPressed();
        });

        mAPIInterfaceShoppy = APIClient.getShoppyClient(this).create(APIInterface.class);

        btnAddToCart = findViewById(R.id.btn_add_to_cart);


        rvMyViewOrder = findViewById(R.id.recyclerview_order_summary);
        rvMyViewOrder.setHasFixedSize(true);

        tvOrderId = findViewById(R.id.tv_order_id);
        tvshippingtitle = findViewById(R.id.tv_payment_shipping_title);
        tvCreateTicket = findViewById(R.id.tv_ticket);
        lyshippingdetails = findViewById(R.id.ly_shipping_details);
        tvShippingProviderName = findViewById(R.id.tv_shipping_provider_name);
        tvshippingProvider = findViewById(R.id.tv_shipping_provider);
        tvShippingNo = findViewById(R.id.tv_shipping_no);
        tvShippingNumber = findViewById(R.id.tv_shipping_number);
        tvwalletvalue = findViewById(R.id.tv_wallet_value);
        laywallet = findViewById(R.id.laywallet);
        tvOrderBillingAddress = findViewById(R.id.tv_shipping_address_two);
        tvOrderShippingAddress = findViewById(R.id.tv_billing_address_two);
        tvShippingMobileNo = findViewById(R.id.tv_shipping_address_mobile);
        tvShippingAddressOne = findViewById(R.id.tv_shipping_address_one);
        tvbillingAddressOne = findViewById(R.id.tv_billing_address_one);
        tvbillingShipping = findViewById(R.id.tv_order_shiping_price);
        tvBillingGrandTotal = findViewById(R.id.tv_order_grand_total_value);
        tvBillingSubTotal = findViewById(R.id.tv_order_sub_total_value);
        tvBillingfulltotal = findViewById(R.id.tv_order_total_value);
        tvHomestoredisc = findViewById(R.id.tv_homestore_discount);
        tvBillingTranID = findViewById(R.id.tv_tran_value);
        mProgressDialog1 = findViewById(R.id.progresbar);
        imageView = findViewById(R.id.img_info);
        mRootLayout = findViewById(R.id.root_layout);
        btnOrderTrack = findViewById(R.id.btn_track);


        imageView.setOnClickListener(view -> ViewTooltip
                .on(imageView)
                //.customView(customView)
                .position(ViewTooltip.Position.TOP)
                .arrowSourceMargin(0)
                .arrowTargetMargin(0)
                .text("Once we have the stock available at this pickup location, we shall call you to collect your order.")
                .textColor(Color.BLACK)
                .clickToHide(true)
                .padding(10, 0, 10, 0)
                .autoHide(true, 2800)
                .clickToHide(true)
                .color(createPaint())
                .border(Color.BLACK, 4)
                .animation(new ViewTooltip.FadeTooltipAnimation(500))
                .onDisplay(view12 -> Log.d("ViewTooltip", "onDisplay"))
                .onHide(view1 -> Log.d("ViewTooltip", "onHide"))
                .show());

        tvOrderId.setText(orderNumber);
    }

    String trackUrl;

    //api shipping details
    private void getTrackOrder(String orderId) {
        Call<TrackListModelEcom> wsCallingEvents = mAPIInterfaceShoppy.getTrackEcom(orderId);
        wsCallingEvents.enqueue(new Callback<TrackListModelEcom>() {
            @Override
            public void onResponse(Call<TrackListModelEcom> call, Response<TrackListModelEcom> response) {

                if (response.isSuccessful()) {
                    trackListModels.clear();
                    if (response.code() == 200) {
                        Log.d("TrackOrder", "TrackOrder: " + response);
                        if (response.body().getResponse().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                            Log.d("TrackOrder", "TrackOrder: " + new Gson().toJson(response.body()));

                            String shippingProviderName = response.body().getResponse().getData().getTrackingData().getShippingProvider();
                            if (!shippingProviderName.isEmpty()) {
                                tvShippingProviderName.setText(" " + shippingProviderName);
                                tvShippingNumber.setText(" " + response.body().getResponse().getData().getTrackingData().getAwbNo());

                            } else {
                                lyshippingdetails.setVisibility(View.GONE);
                            }

                            //joiningdate.setText(idDetails.get(0).getJoiningDate());
                        } else if (response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TrackListModelEcom> call, Throwable t) {
                Log.d("TrackOrderERROR", "TrackOrderERROR: " + t);
            }
        });
    }

    private Paint createPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(new LinearGradient(0, 0, 0, 600, WHITE, WHITE, Shader.TileMode.REPEAT));
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // getMyCartListTotalCount(m_deviceId, session.getShoppyUserID());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onMethodCallbackMain() {
        onBackPressed();
    }


    public void pdf() {
        File dir = Environment.getExternalStorageDirectory();
        File assist = new File(pdf);
        try {
            InputStream fis = new FileInputStream(assist);

            long length = assist.length();
            if (length > Integer.MAX_VALUE) {
                Log.e("MainActivity", "cannnottt   readddd");
            }
            byte[] bytes = new byte[(int) length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = fis.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            File data = new File(dir, "mydemo.pdf");
            OutputStream op = new FileOutputStream(data);
            op.write(bytes);
        } catch (Exception ex) {
            Log.e("MainActivity", "" + ex.getMessage());
        }
    }


    private void getInvoice(String orderNumber) {

        // mProgressDialog1.setVisibility(View.VISIBLE);
        Call<TrackOrderModel> wsCallingBanersList = mAPIInterfaceShoppy.getInvoice(orderNumber);
        Log.d("OnresponseStart", "OnresponseStart: ");
        wsCallingBanersList.enqueue(new Callback<TrackOrderModel>() {
            @Override
            public void onResponse(Call<TrackOrderModel> call, Response<TrackOrderModel> response) {
                Log.d("OnresponseIF", "OnresponseIF: " + response);
               /* if (mProgressDialog1!=null){
                    mProgressDialog1.setVisibility(View.VISIBLE);
                }*/
                if (response.isSuccessful()) {
                    trackListModelEcoms.clear();
                    if (response.code() == 200) {
                        Log.d("Onresponse", "Onresponse: " + response);
                        if (response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                        } else if (response.body().getResponse().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                            getFacility = response.body().getResponse().getData().getFacility();
                            invoicecode = response.body().getResponse().getData().getInvoicecode();

                            SharedPreferences.Editor editor = getSharedPreferences(PrefUtils.prefFacility, MODE_PRIVATE).edit();
                            editor.putString("getFacility", getFacility);
                            editor.apply();

                            Log.e("getFacility: ", "getFacility: " + getFacility + " " + invoicecode);
                        } else if (response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {

                        }
                    }
                } else {
                    Log.d("onResponse", "onResponse: " + response);
                }
            }

            @Override
            public void onFailure(Call<TrackOrderModel> call, Throwable t) {
                Log.d("OnresponseFail", "OnresponseFail: " + t);
                if (mProgressDialog1 != null) {
                    mProgressDialog1.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getInvoicePDF() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            APIInterface mAPIInterface = APIClient.getClientUniCommmerce(MyViewOrderActivity.this).create(APIInterface.class);
            mProgressDialog1.setVisibility(View.VISIBLE);
            //Call<ResponseBody> wsCallingRegistation = mAPIInterface.getPDF("AMDHYD2021INS0017" );
            Call<ResponseBody> wsCallingRegistation = mAPIInterface.getPDF(invoicecode);
            //if (!invoicecode.equals( "" )) {
            wsCallingRegistation.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (mProgressDialog1 != null) {
                        mProgressDialog1.setVisibility(View.INVISIBLE);
                    }

                    try {
                        InputStream mypdfbytes = response.body().byteStream();
                        Log.d("pdf:", "success: " + mypdfbytes);
                        // Save PDF file in Folder Name: "Nebula"
                        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                        File folder = new File(extStorageDirectory, "Nebula");
                        folder.mkdir();
                        InputStream is = response.body().byteStream();
                        FileOutputStream fos = new FileOutputStream(
                                new File(folder, "Invoice.pdf"));

                        int read = 0;
                        byte[] buffer = new byte[32768];
                        while ((read = is.read(buffer)) > 0) {
                            fos.write(buffer, 0, read);
                        }
                        fos.close();
                        is.close();
                        Log.d("pdf:", "SuccessPDF: " + fos);

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        File file = new File(Environment.getExternalStorageDirectory() + "/Nebula/" + "Invoice.pdf");
                        Uri data = FileProvider.getUriForFile(MyViewOrderActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                        intent.setDataAndType(data, "application/pdf");
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent);

                    } catch (Exception e) {
                        Toast.makeText(MyViewOrderActivity.this, "Invoice is not generated yet. ", Toast.LENGTH_LONG).show();
                        Log.d("pdf:", "error: " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    mProgressDialog1.setVisibility(View.INVISIBLE);
                    Log.d("pdf:", "error: " + t);
                }
            });
        } else {
            utils.dialogueNoInternet(this);
            // Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(String value) {
        valueCallBack = value;
        Log.e("Order Track Status", "Order Track Status: " + valueCallBack);
    }
}