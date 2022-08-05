package com.nebulacompanies.ibo.ecom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Interface.QuntityClick;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.activities.LoginActivity;
import com.nebulacompanies.ibo.adapter.BottommSheetDialog;
import com.nebulacompanies.ibo.ecom.adapter.RackAdapter;
import com.nebulacompanies.ibo.ecom.adapter.SkuAdapter;
import com.nebulacompanies.ibo.ecom.model.AddToCartModel;
import com.nebulacompanies.ibo.ecom.model.CartListModelEcom;
import com.nebulacompanies.ibo.ecom.model.CartListModelReviewEcom;
import com.nebulacompanies.ibo.ecom.model.CartRECKListModelEcom;
import com.nebulacompanies.ibo.ecom.model.FreeProductListModel;
import com.nebulacompanies.ibo.ecom.model.FreeProductsModel;
import com.nebulacompanies.ibo.ecom.model.MyCart;
import com.nebulacompanies.ibo.ecom.model.MyCartData;
import com.nebulacompanies.ibo.ecom.model.MyCartReview;
import com.nebulacompanies.ibo.ecom.utils.CartAdapterCallback;
import com.nebulacompanies.ibo.ecom.utils.CartAdapterPayCallback;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.ProductDecsDetailsDialogFragment;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.AddVariantModel;
import com.nebulacompanies.ibo.model.ChooseShoppyModel;
import com.nebulacompanies.ibo.model.DeleteShoppyCartModel;
import com.nebulacompanies.ibo.model.MyShoppyModel;
import com.nebulacompanies.ibo.model.ProductQuantity;
import com.nebulacompanies.ibo.shoppy.R;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialogCart;
import com.nebulacompanies.ibo.util.AppUtils;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.util.UtilsVersion;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;
import com.nebulacompanies.ibo.view.NebulaEditText;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.ecom.utils.Utils.actionFail;
import static com.nebulacompanies.ibo.ecom.utils.Utils.actionUservalid;
import static com.nebulacompanies.ibo.util.Config.quantityTshirt;
import static com.nebulacompanies.ibo.util.Config.variantidTshirt;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;

public class MyCartActivity extends AppCompatActivity implements
        CartAdapterCallback, CartAdapterPayCallback,
        ProductDecsDetailsDialogFragment.ItemClickListener, QuntityClick {

    MaterialProgressBar mProgressDialog;
    APIInterface mAPIInterface, mAPIInterfaceShoppy;
    Typeface typeface;
    Session session;
    String addressNameSave;
    Toolbar toolbar;
    private RelativeLayout llEmpty;
    ImageView imgBackArrow, imgFav, imgError, imgClose, imgCloseReck, imgCloseSku;
    Spinner spinner;
    MyButtonEcom btnAddToCart, btnProceed, btnConfirm, btnConfirmReck, btnConfirmSku;
    ConstraintLayout constraintLayoutPlaceOrder;
    LinearLayout lnLocation;
    NebulaEditTextEcom editcartqnty;
    private MyTextViewEcom txtErrorContent, txtRetry, tvLocation;
    MyBoldTextViewEcom txtErrorTitle;
    MyTextViewEcom tvTotalAmount, tvToolbarTitle;
    RecyclerView rvFreeProducts, rvFreeProductsRec, rvCartItem, rvFreeProductsSku;
    AlertDialog dialogFreeProducts, dialogCart, dialogReck, dialogSku;
    //CheckBox cbFreeLookUp;
    ArrayList<MyCart> cartModels = new ArrayList<>();
    ArrayList<AddVariantModel.Datum> freeProductModels = new ArrayList<>();
    ArrayList<CartRECKListModelEcom.Datum> reckProductModels = new ArrayList<>();
    ArrayList<AddVariantModel.Datum> skuProductModels = new ArrayList<>();
    ArrayList<MyCartReview> cartModelsMyCartReviews = new ArrayList<>();
    private SkuAdapter freeProductsListAdapter;
    UtilsVersion utilsVersion = new UtilsVersion();
    Utils utils = new Utils();
    SharedPreferences citySave, sharedPreferencesAddressType;
    int subtotal;
    double totalPV;
    Intent cartNotification;
    String deviceId, uniqueID;
    int cityId, backID, selectCategory, promoid;
    Integer pvResult, grandTotal;
    ProgressDialog mLoadProgressDialog;
    boolean isAddressType, isServicable, isHomeStore;
    boolean checkAddress = false;
    RackAdapter reckAdapter;
    String msgInfo;
    int productidSelected = 0;
    int productidQty = 0;
    CartListModelEcom responseData;
    Snackbar snackbar = null;
    BottommSheetDialog bottomSheet;
    int grandtotal = 0;
    ArrayList<Integer> mycartProductId = new ArrayList<>();
    ArrayList<Integer> mycartMandatoryProductId = new ArrayList<>();
    ArrayList<Integer> mycartMainProductId = new ArrayList<>();
    String[] cartqnty = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    ArrayList<Integer> reckidList = new ArrayList<>();
    ArrayList<Integer> tshirtList = new ArrayList<>();
    int reckID = -1;
    int xReckID = -1;
    int xReckMainId = -1;
    int skuID = -1;
    int xSkuID = -1;
    int xSkuMainId = -1;
    boolean keyboardshowing = false;
    private final int TYPE_FOOTER = 1;
    private final int TYPE_ITEM = 2;
    //String[] qtySize = {"select", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10+"};
    ArrayList<String> qtyList = new ArrayList<>();
    int xPos = 0;
    HashMap<Integer, ProductQuantity> productQuantities = new HashMap<>();
    HashMap<Integer, ProductQuantity> productQuantitiesFinal = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);
        initData();
        initFullDialog();
        initUI();
        initSharedPreference();
        initDialogFreeProducts();
        initReckDialogFreeProducts();
        initSkuProducts();
        initDialogCarrt();
        displayTotalPrice(0);
        initOnClick();
        setDataUI();
        if (session.getShoppyid() == 0) {
            callRackFreeAPI();
        }

    }

    private void initData() {
        session = new Session(this);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/ember.ttf");
        mAPIInterface = APIClient.getClient(MyCartActivity.this).create(APIInterface.class);
        mAPIInterfaceShoppy = APIClient.getShoppyClient(MyCartActivity.this).create(APIInterface.class);
        cartNotification = getIntent();
        if (cartNotification != null) {
            backID = cartNotification.getIntExtra("session_back", 0);
        }
    }

    public void initFullDialog() {
        mLoadProgressDialog = new ProgressDialog(MyCartActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
        mLoadProgressDialog.setCancelable(false);
        mLoadProgressDialog.setMessage("Loading...");
    }

    public void showFullDialog() {
        if (mLoadProgressDialog != null && !mLoadProgressDialog.isShowing()) {
            showLog("full dialog==", "SHOW===");
            mLoadProgressDialog.show();
        }
    }

    public void hideFullDialog() {
        if (mLoadProgressDialog != null && mLoadProgressDialog.isShowing()) {
            showLog("full dialog==", "HIDE===");
            mLoadProgressDialog.dismiss();
        }
    }

    @SuppressLint("HardwareIds")
    void initSharedPreference() {
        deviceId = android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

        citySave = getSharedPreferences(PrefUtils.prefCityid, 0);
        cityId = citySave.getInt(PrefUtils.prefCityid, 0); //0 is the default value

        //showLog("MDEVICEID cart uniqueID", "MDEVICEIDcart uniqueID: " + uniqueID);
        if (deviceId == null || deviceId.equals("")) {
            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = deviceSharedPreferences.edit();
                editor.putString(PrefUtils.prefDeviceid, randomID);
                editor.apply();
                deviceId = randomID;
            } else {
                deviceId = uniqueID;
            }
        }
        //showLog("MDEVICEID Cart", "MDEVICEID cart: " + deviceId);
    }

    void initUI() {
        llEmpty = findViewById(R.id.llEmpty);
        imgError = findViewById(R.id.imgError);
        txtErrorTitle = findViewById(R.id.txtErrorTitle);
        txtErrorContent = findViewById(R.id.txt_error_content);
        txtRetry = findViewById(R.id.txtRetry);

        //getting the toolbar
        mProgressDialog = findViewById(R.id.progresbar);
        toolbar = findViewById(R.id.toolbar_search);
        imgBackArrow = findViewById(R.id.img_back);
        imgFav = findViewById(R.id.img_my_fav);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        btnProceed = findViewById(R.id.proceed);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        rvCartItem = findViewById(R.id.rv_myCartList);
        rvCartItem.setNestedScrollingEnabled(false);
        tvTotalAmount = findViewById(R.id.tv_payable_amount);
        constraintLayoutPlaceOrder = findViewById(R.id.cn_place_order);
        // cbFreeLookUp = findViewById(R.id.chk_free_look_up);
        tvTotalAmount = findViewById(R.id.tv_payable_amount);

       /*
        imgInfo = findViewById(R.id.image_info);
        tvMycartItemPrice = findViewById(R.id.tv_mycart_item_price);
        tvShipingPrice = findViewById(R.id.tv_shiping_price);
        tvGrandTotal = findViewById(R.id.tv_grand_total_value);
        cardViewDetails = findViewById(R.id.card_view_details);
        lnCartMRP = findViewById(R.id.ln_cart_mrp);
        lnCartRetail = findViewById(R.id.ln_cart_retail);
        viewMRP = findViewById(R.id.view_mrp);
        tvMRP = findViewById(R.id.tv_mycart_mrp_price);
        tvMycartRetailPrice = findViewById(R.id.tv_mycart_retail_price);*/

        rvCartItem.setLayoutManager(new LinearLayoutManager(this));
        rvCartItem.setItemAnimator(null);

        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText("My Cart");
        lnLocation = toolbar.findViewById(R.id.ln_location);
        tvLocation = toolbar.findViewById(R.id.tv_location);

        IntentFilter filter = new IntentFilter();
        filter.addAction(actionUservalid);
        filter.addAction(actionFail);
        registerReceiver(myReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(actionUservalid)) {
                openOrderSummary();
            } else if (intent.getAction().equalsIgnoreCase(actionFail)) {
                Toast.makeText(MyCartActivity.this, "There is an error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void showLocationAdd() {
        ProductDecsDetailsDialogFragment addPhotoBottomDialogFragment =
                ProductDecsDetailsDialogFragment.newInstance();

        addPhotoBottomDialogFragment.show(getSupportFragmentManager(), ProductDecsDetailsDialogFragment.TAG);


    }

    @SuppressLint("SetTextI18n")
    void initOnClick() {
        /*imgInfo.setOnClickListener(v -> {
            if (!keyboardshowing) {
                SweetAlertDialog loading = new SweetAlertDialog(MyCartActivity.this, SweetAlertDialog.NORMAL_TYPE);
                loading.setCancelable(true);
                loading.setConfirmText("OK");
                loading.setOnShowListener(dialog -> {
                    SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                    MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
                    MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
                    MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                    textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                    textContent.setTypeface(typeface);
                    textTitle.setTypeface(typeface);
                    btnConfirm.setTypeface(typeface);
                    alertDialog.setCancelable(false);

                    textTitle.setText("Info");
                    textContent.setText(msgInfo);
                    btnConfirm.setText("OK");
                    textContent.setGravity(Gravity.NO_GRAVITY);
                    btnConfirm.setOnClickListener(v1 -> loading.dismiss());
                });

                loading.show();
            }
        });*/
        imgBackArrow.setOnClickListener(view -> onBackPressed());

        imgFav.setOnClickListener(view -> {
            Intent login = new Intent(MyCartActivity.this, MyWishListActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
        });

        txtRetry.setOnClickListener(v -> getMyCartList(true, false, false));

        tvLocation.setOnClickListener(view -> {
            if (!keyboardshowing) {
                checkAddress = false;
                showLocationAdd();
            }
        });
        btnProceed.setOnClickListener(view -> {
            if (!keyboardshowing) {
                btnProceed.setEnabled(false);
                btnProceed.setClickable(false);
                if (session.getLogin()) {
                    callMinAmtAPI();

                } else {
                    hideprogress();
                    utils.openLoginDialog(MyCartActivity.this, utils.gotoMyCart);
                }
            }
        });
    }

    private void callMinAmtAPI() {
        if (Utils.isNetworkAvailable(MyCartActivity.this)) {
            showFullDialog();

            Call<ChooseShoppyModel> wsCallingEvents = mAPIInterfaceShoppy.getShoppyList();
            wsCallingEvents.enqueue(new Callback<ChooseShoppyModel>() {
                @Override
                public void onResponse(Call<ChooseShoppyModel> call, @NotNull Response<ChooseShoppyModel> response) {
                    List<ChooseShoppyModel.Datum> mdataList = new ArrayList<>();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            ChooseShoppyModel.Response orderListModelEcom = response.body().getResponse();
                            if (orderListModelEcom.getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                mdataList.addAll(orderListModelEcom.getData());
                                int shoppytempid = session.getTempShoppyid();
                                boolean match=false;
                                for (int i = 0; i < mdataList.size(); i++) {
                                    if(shoppytempid==mdataList.get(i).getId()){
                                        match=true;

                                        session.setShoppyAmt((int) mdataList.get(i).getMinAmount());
                                        session.setShoppyName(mdataList.get(i).getName());
                                        session.setRepurchaseamt(mdataList.get(i).getRepurchase());
                                    }
                                }
                                if(match)
                                    doProceed();
                                else {
                                    Toast.makeText(MyCartActivity.this, "Please Try again", Toast.LENGTH_SHORT).show();
                                    btnProceed.setEnabled(true);
                                    btnProceed.setClickable(true);
                                }
                            } else {
                                Toast.makeText(MyCartActivity.this, "Please Try again", Toast.LENGTH_SHORT).show();
                                btnProceed.setEnabled(true);
                                btnProceed.setClickable(true);
                            }
                        } else {
                            btnProceed.setEnabled(true);
                            btnProceed.setClickable(true);
                            // utils.doLogout(getApplicationContext(),session);
                            Toast.makeText(MyCartActivity.this, "Please Try again", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        btnProceed.setEnabled(true);
                        btnProceed.setClickable(true);
                        // utils.doLogout(getApplicationContext(),session);
                        Toast.makeText(MyCartActivity.this, "Please Try again", Toast.LENGTH_SHORT).show();

                    }
                    // mProgressDialog.setVisibility(View.GONE);
                    // setenablebutton(true);
                    hideFullDialog();
                }

                @Override
                public void onFailure(Call<ChooseShoppyModel> call, Throwable t) {
                    btnProceed.setEnabled(true);
                    btnProceed.setClickable(true);
                    Toast.makeText(MyCartActivity.this, "Please Try again", Toast.LENGTH_SHORT).show();
                    hideFullDialog();
                       /* utils.doLogout(getApplicationContext(),session);
 hideFullDialog();
                        mProgressDialog.setVisibility(View.GONE);
                        setenablebutton(true);*/
                }
            });
        } else {
            //  setenablebutton(true);
            AppUtils.displayNetworkErrorMessage(this);
            hideFullDialog();
            btnProceed.setEnabled(true);
            btnProceed.setClickable(true);
        }
    }

    private void doProceed() {
        int minAmt;
        if (session.getShoppyid() == 0)
            minAmt = session.getShoppyamt();
        else
            minAmt = session.getRepurchaseAmt();
        showLog("minAmt","subtotal "+subtotal+" : "+minAmt);
        if (subtotal >= minAmt) {
            if (session.getShoppyid() == 0) {
                if (xReckID != -1 && xSkuID != -1) {
                    checkAddress = true;
                    showLocationAdd();
                    hideprogress();
                } else {
                    Toast.makeText(this, "Please add compulsory products.", Toast.LENGTH_SHORT).show();
                }
            } else {
                checkAddress = true;
                showLocationAdd();
                hideprogress();
            }
        } else {
            btnProceed.setEnabled(true);
            // dialog here
            SweetAlertDialog loading = new SweetAlertDialog(MyCartActivity.this, SweetAlertDialog.WARNING_TYPE);
            loading.setCancelable(true);
            loading.setConfirmText("OK");
            loading.setOnShowListener(dialog -> {
                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
                MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
                MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                // btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textContent.setTypeface(typeface);
                textTitle.setTypeface(typeface);
                btnConfirm.setTypeface(typeface);
                alertDialog.setCancelable(false);
                //textTitle.setText(response.body().getMessage());
                //int remain = minAmt - grandtotal;
                textTitle.setText("Insufficient cart value");
                String msg = "Please fulfil your minimum purchase for this order.\nYour minimum purchase amount for this order is  Rs " + minAmt + ".";
                textContent.setText(msg);
                btnConfirm.setText("OK");
                // textContent.setText("Pin you have entered is Invalid.");
                textContent.setGravity(Gravity.NO_GRAVITY);
                btnConfirm.setOnClickListener(v -> loading.dismiss());
            });
            loading.show();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (session.getShoppyid() != 0) {
            callResumeAPI();
        }
        setDataUI();
    }

    private void callResumeAPI() {
        utilsVersion.checkVersion(this);
        utils.checkExpireUser(mAPIInterface, this, session);
        getMyCartList(true, false, false);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == utils.gotoMyCart) {
            if (resultCode == RESULT_OK) {
                //getMyCartList(true, false);
                //set broadcast here for product list/banner screen
                setDataUI();
                Intent mIntent = new Intent(Utils.actionLogin);
                sendBroadcast(mIntent);
            }
        }
    }

    private void setDataUI() {
        if (session.getLogin()) { //!showLocation &&
            try {
                lnLocation.setVisibility(View.VISIBLE);
                sharedPreferencesAddressType = getSharedPreferences(PrefUtils.prefAddresstype, 0);
                isAddressType = sharedPreferencesAddressType.getBoolean(PrefUtils.prefAddresstype, false);

                SharedPreferences SharedPreferencesLocationName = getSharedPreferences(PrefUtils.prefLocation, 0);
                addressNameSave = SharedPreferencesLocationName.getString(PrefUtils.prefLocation, null);

                if (!TextUtils.isEmpty(addressNameSave)) {
                    showLog("Location Empty", "Location Empty " + addressNameSave);
                    if (isAddressType) {
                        tvLocation.setText("Pickup from " + addressNameSave);
                    } else {
                        tvLocation.setText("Deliver to " + addressNameSave);
                    }
                    showLog("Update Value", "Update Value" + isAddressType);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getMyCartList(boolean calculate, boolean compare, boolean callReview) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            showFullDialog();
            showprogress();
            Call<CartListModelEcom> wsCallingEvents;
            // if (session.getLogin())
            wsCallingEvents = mAPIInterfaceShoppy.getMyCartListEcom(deviceId, session.getShoppyUserID(), cityId, session.getTempShoppyid());
            //else
            //  wsCallingEvents = mAPIInterface.getMyCartListEcomWoLogin(deviceId, 0);
            wsCallingEvents.enqueue(new Callback<CartListModelEcom>() {
                @Override
                public void onResponse(Call<CartListModelEcom> call, Response<CartListModelEcom> response) {
                    showLog("CartResponse", "CartResponse11: " + response.body());
                    hideprogress();
                    cartModels.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            showLog("CartAPI", "CartAPI: " + response);
                            showLog("CartResponse", "CartResponse200: " + response.body());
                            if (compare) {
                                CartListModelEcom responseDataNew = response.body();
                                String mydataNew = new Gson().toJson(responseDataNew, new TypeToken<CartListModelEcom>() {
                                }.getType());
                                String mydataOld = new Gson().toJson(responseData, new TypeToken<CartListModelEcom>() {
                                }.getType());
                                if (mydataNew.equals(mydataOld)) {
                                    checkAddress = true;
                                    showLocationAdd();
                                    hideprogress();
                                    hideFullDialog();
                                } else {
                                    Toast.makeText(MyCartActivity.this, "Cart has been updated. Kindly place your order again.", Toast.LENGTH_SHORT).show();
                                    setDataCart(response.body(), calculate);
                                }
                                responseData = response.body();
                            } else {
                                responseData = response.body();
                                setDataCart(response.body(), calculate);
                            }
                        } else {
                            hideFullDialog();
                            setErrorDataCart();
                        }
                    } else {
                        hideFullDialog();
                        setErrorDataCart();
                    }
                    if (callReview) {
                        getMyCartListReview(String.valueOf(cityId));
                    }
                }

                @Override
                public void onFailure(Call<CartListModelEcom> call, Throwable t) {
                    hideFullDialog();
                    hideprogress();
                    setErrorDataCart();
                }
            });
        } else {
            hideFullDialog();
            noInternetAvailable();
        }
    }

    public void hideprogress() {
        if (mProgressDialog != null)
            mProgressDialog.setVisibility(View.INVISIBLE);


        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btnProceed.setEnabled(true);
                btnProceed.setClickable(true);
            }
        }, 1000);
    }

    public void showprogress() {
        if (mProgressDialog != null)
            mProgressDialog.setVisibility(View.VISIBLE);
        btnProceed.setEnabled(false);
    }

    private void setErrorDataCart() {
        showLog("Error11", "setErrorDataCart");
        if (snackbar != null && snackbar.isShown())
            snackbar.dismiss();
        // cardViewDetails.setVisibility(View.GONE);
        //  cbFreeLookUp.setVisibility(View.GONE);
        constraintLayoutPlaceOrder.setVisibility(View.GONE);
        noRecordsFound();
    }

    SweetAlertDialog loadingInfo;

    @SuppressLint("SetTextI18n")
    private void setDataCart(CartListModelEcom body, boolean calculateFree) {
        //  new Thread(() -> runOnUiThread(() -> {
        MyCartData data;
        showFullDialog();
        msgInfo = "";
        //your code or your request that you want to run on uiThread
        if (body.getResponse().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
            noRecordsFound();
            //  cbFreeLookUp.setVisibility(View.GONE);
            constraintLayoutPlaceOrder.setVisibility(View.GONE);
        } else if (body.getResponse().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
            productQuantities.clear();
            productQuantitiesFinal.clear();
            data = body.getResponse().getData();
            body.getResponse().getData();
            for (int i = 0; i < data.getCart().size(); i++) {
                if (data.getCart().get(i).getCartQuantity() > 0)
                    cartModels.add(data.getCart().get(i));
            }
            if (session.getShoppyid() == 0) {
                mycartProductId.clear();
                mycartMandatoryProductId.clear();
                mycartMainProductId.clear();
                for (int i = 0; i < cartModels.size(); i++) {
                    mycartProductId.add(cartModels.get(i).getProductId());
                    if (cartModels.get(i).isMandatory()) {
                        mycartMandatoryProductId.add(cartModels.get(i).getProductId());
                        mycartMainProductId.add(cartModels.get(i).getId());
                    }
                }
            }

            for (int i = 0; i < cartModels.size(); i++) {
                int productid = cartModels.get(i).getProductId();
                int qty = cartModels.get(i).getCartQuantity();
                int minQty = cartModels.get(i).getPurchaseQuantity();
                minQty = minQty == 0 ? 1 : minQty;
                ProductQuantity productQuantity;
                if (productQuantities.containsKey(productid)) {
                    productQuantity = productQuantities.get(productid);
                    int xQty = productQuantity.getQty();
                    int totalQty = qty + xQty;
                    productQuantity.setMinQty(minQty);
                    productQuantity.setQty(totalQty);
                    if (!cartModels.get(i).isMandatory()) {
                        int spnvalue = minQty - xQty;
                        cartModels.get(i).setSpinner(spnvalue == 0 ? 1 : spnvalue);
                    } else {
                        cartModels.get(i).setSpinner(qty == 0 ? 1 : qty);
                    }
                } else {
                    productQuantity = new ProductQuantity();
                    productQuantity.setMinQty(minQty);
                    productQuantity.setQty(qty);
                    cartModels.get(i).setSpinner(minQty);
                }
                productQuantities.put(productid, productQuantity);
            }

            for (Integer key : productQuantities.keySet()) {
                ProductQuantity value = productQuantities.get(key);
                showLog("setDataCart", "value: " + key + " :: " + value.getQty() + " / " + value.getMinQty());

                int myQty = value.getQty();
                int minQty = value.getMinQty();

                if (myQty < minQty) {
                    productQuantitiesFinal.put(key, value);
                }

            }
            showLog("setDataCart", "CartAPIIn: " + data.getCart().size());

            // showLog("setDataCart", "productQuantities: " +productQuantities);

            grandtotal = data.getGrandTotal();
            tvTotalAmount.setText("₹ " + grandtotal);
           /* tvMycartItemPrice.setText("" + data.getSubTotal());
            tvShipingPrice.setText("" + data.getShippingCharge());
            tvGrandTotal.setText("" + grandtotal);
            tvMRP.setText("" + data.getCalculatedMRP());
            tvMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvMycartRetailPrice.setText((data.getHomeStoreProfit()) + " (" + data.getHomeStoreProfitPercent() + "%) ");
*/
            msgInfo = data.getShippingChargeInfo();
            pvResult = data.getTotalPV();
            grandTotal = data.getGrandTotal();
            subtotal = data.getSubTotal();
            totalPV = data.getTotalPV();
            String hs = (data.getHomeStoreProfit()) + " (" + data.getHomeStoreProfitPercent() + "%) ";
            loadingInfo = new SweetAlertDialog(MyCartActivity.this, SweetAlertDialog.NORMAL_TYPE);
            loadingInfo.setCancelable(true);
            loadingInfo.setConfirmText("OK");
            loadingInfo.setOnShowListener(dialog -> {
                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
                MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
                MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                textContent.setTypeface(typeface);
                textTitle.setTypeface(typeface);
                btnConfirm.setTypeface(typeface);
                alertDialog.setCancelable(false);

                textTitle.setText("Info");
                textContent.setText(msgInfo);
                btnConfirm.setText("OK");
                textContent.setGravity(Gravity.NO_GRAVITY);
                btnConfirm.setOnClickListener(v1 -> loadingInfo.dismiss());
            });

            MyCartProductsAdapter myCartProductsAdapter = new MyCartProductsAdapter(MyCartActivity.this,
                    cartModels, subtotal, data.getShippingCharge(), grandtotal, data.getCalculatedMRP(), hs, msgInfo);

            try {
                rvCartItem.scrollToPosition(xPos - 1);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    rvCartItem.scrollToPosition(xPos + 1);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            rvCartItem.setAdapter(myCartProductsAdapter);
               /* String pvslabnew = data.getPvSlab();
                String totalslabnew = data.getPriceSlab();
                String prodid = "0";
                calculateFree=false;*/
              /*  if (calculateFree) {
                    boolean isfreeproduct = false;
                    callFree = true;
                    if (firsttime) {
                        firsttime = false;
                        for (int i = 0; i < cartModels.size(); i++) {
                            boolean ispromo = cartModels.get(i).isIsPromo();
                            boolean issilver = cartModels.get(i).isIsRankRewardSilver();
                            boolean isbronze = cartModels.get(i).isIsRankRewardBronze();
                            if (ispromo && (!issilver && !isbronze)) {
                                callFree = false;
                                prodid = String.valueOf(cartModels.get(i).getProductId());
                                isfreeproduct = cartModels.get(i).isIsFree();
                            }
                        }
                        if (!callFree) {
                            pvslab = pvslabnew;
                            totalslab = totalslabnew;
                            if (!isfreeproduct)
                                getMyFreeProductList(Integer.parseInt(prodid));
                        } else {
                            showLog("FIRST SLAB++", pvslab + " : " + pvslabnew + " , " + totalslab + " : " + totalslabnew);
                            if (!(pvslab.equals(pvslabnew)) || !(totalslab.equals(totalslabnew))) {
                                pvslab = pvslabnew;
                                totalslab = totalslabnew;
                                getMyFreeProductList(-1);
                            }
                        }
                    } else {
                        // check slab
                        showLog("SLAB++", pvslab + " : " + pvslabnew + " , " + totalslab + " : " + totalslabnew);
                        if (!(pvslab.equals(pvslabnew)) || !(totalslab.equals(totalslabnew))) {
                            pvslab = pvslabnew;
                            totalslab = totalslabnew;
                            getMyFreeProductList(-1);
                        }
                    }
                }*/
              /*  showLog("setDataCart", "totalCartAmountGET " + grandTotal);
                showLog("setDataCart ", "SKU Cart: " + cartModels.toString());*/
          /*  isShowRetail = body.getResponse().getData().isShowRetail();

            if (!isShowRetail) {
                lnCartMRP.setVisibility(View.GONE);
                lnCartRetail.setVisibility(View.GONE);
                viewMRP.setVisibility(View.GONE);
            } else {
                lnCartMRP.setVisibility(View.VISIBLE);
                lnCartRetail.setVisibility(View.VISIBLE);
                viewMRP.setVisibility(View.VISIBLE);
            }*/
           /*if (pvResult < Const.PVValue && mrpisAssociate) {
                tvPvStatus.setVisibility(View.VISIBLE);
                tvPvStatus.setText("PV value in your cart: " + pvResult + ". Add items worth " + Const.PVValue + " PV to buy the items at discounted Nebula IBO Price.");

            }else*/
           /*     RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) nestedScrollView
                        .getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 100);
                nestedScrollView.setLayoutParams(layoutParams);
                tvPvStatus.setVisibility(View.GONE);*/
/*
            myHandler = new Handler();
            myRunnable = () -> {
                // if (pvResult != null) {
                if (pvResult < Const.PVValue && mrpisAssociate) {
                    alertLessOne();
                    // paymentFail();
                } else if (pvResult > Const.PVValue && pvResult < Const.PVValueMax && mrpisAssociate) {
                    alertLessTwo();
                    // paymentFail();
                } else if (pvResult >= Const.PVValueMax && mrpisAssociate) {
                    alertLessThree();
                    // paymentFail();
                }
                //}
            };
            myHandler.postDelayed(myRunnable, 1500);*/
            // showLog("setDataCart", "pvResult: " + pvResult);
            //Log.e("setDataCart", "CartListAPI: " + new Gson().toJson(body));
        }/* else {
                setErrorDataCart();
            }*/
        if (cartModels.size() > 0) {
            llEmpty.setVisibility(View.GONE);
            rvCartItem.setVisibility(View.VISIBLE);
            // cardViewDetails.setVisibility(View.VISIBLE);
            lnLocation.setVisibility(View.VISIBLE);
            constraintLayoutPlaceOrder.setVisibility(View.VISIBLE);
        } else {
            setErrorDataCart();
        }
        // setSnackBar(data);
        if (session.getShoppyid() == 0 && mycartProductId.size() > 0) {
            xReckID = -1;
            xSkuID = -1;
            checkForTshirtSku();
            checkForReck();
            hideFullDialog();
            showCompulsoryDialog();
        } else {
            hideFullDialog();
            callAutoAddProducts();
           /* for (Object key : productQuantitiesFinal.keySet()) {
                ProductQuantity value = productQuantitiesFinal.get(key);
                showLog("productQuantitiesFinal", "productQuantitiesFinal value: " + key + " :: " + value.getQty() + " / " + value.getMinQty());
            }*/
        }
        // })).start();
    }

    private void callAutoAddProducts() {
        showLog("productQuantitiesFinal", "Call force add API value: " + productQuantitiesFinal.size());
        if (productQuantitiesFinal.size() > 0)
            callForceAdd(0);
    }

    private void callForceAdd(int index) {
        if (productQuantitiesFinal.size() > 0 && index < productQuantitiesFinal.size()) {
            Map.Entry<Integer, ProductQuantity> keys = (new ArrayList<>(productQuantitiesFinal.entrySet())).get(index);
            //keys.getKey()
            ProductQuantity value = keys.getValue();
            int availQty = value.getQty();
            int minQty = value.getMinQty();
            showLog("productQuantitiesFinal", "productQuantitiesFinal value: " + keys.getKey() + " :: " + value.getQty() + " / " + value.getMinQty());
            int addQty = minQty - availQty;

            addProductToCart(addQty, keys.getKey(), index);
        } else {
            showLog("productQuantitiesFinal", "productQuantitiesFinal value: all set");

            onMethodCallbackMain();
        }
    }

    private void showCompulsoryDialog() {
        showLog("productQuantitiesFinal", "showCompulsoryDialog :: " + xReckID + " :: " + xSkuID);
        if (xReckID != -1 && xSkuID != -1) {
            callAutoAddProducts();
        } else {
            if (xReckID == -1) {
                showReckDialogNow();
            } else {
                showTshirtDialog();
            }
        }
    }

    private void checkForTshirtSku() {
        if (mycartMandatoryProductId.size() > 0) {
            for (int i = 0; i < tshirtList.size(); i++) {
                int pid = tshirtList.get(i);
                if (mycartMandatoryProductId.contains(pid)) {
                    xSkuID = pid;
                    xSkuMainId = mycartMainProductId.get(mycartMandatoryProductId.indexOf(pid));
                }
            }
        }
        if (xSkuID == -1) {
            for (int i = 0; i < tshirtList.size(); i++) {
                if (mycartProductId.contains(tshirtList.get(i))) {
                    xSkuID = tshirtList.get(i);
                }
            }
        }
    }

    private void checkForReck() {
        // check is it in list?
        if (mycartMandatoryProductId.size() > 0) {
            for (int i = 0; i < reckidList.size(); i++) {
                int pid = reckidList.get(i);
                if (mycartMandatoryProductId.contains(pid)) {
                    xReckID = pid;
                    xReckMainId = mycartMainProductId.get(mycartMandatoryProductId.indexOf(pid));
                }
            }
        }
        if (xReckID == -1) {
            for (int i = 0; i < reckidList.size(); i++) {
                if (mycartProductId.contains(reckidList.get(i))) {
                    xReckID = reckidList.get(i);
                }
            }
        }
    }

    public void showTshirtDialog() {
        if (xSkuID == -1)
            showSkuDialogNow();
    }

    private void callRackFreeAPI() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            reckProductModels.clear();
            Call<CartRECKListModelEcom> wsCallingEvents = mAPIInterfaceShoppy.getMyRackCartListEcom();
            wsCallingEvents.enqueue(new Callback<CartRECKListModelEcom>() {
                @Override
                public void onResponse(Call<CartRECKListModelEcom> call, Response<CartRECKListModelEcom> response) {
                    if (mLoadProgressDialog != null && mLoadProgressDialog.isShowing()) {
                        hideFullDialog();
                    }
                    if (response.isSuccessful()) {
                        if (response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                            //initReckDialogFreeProducts();
                            reckProductModels.addAll(response.body().getResponse().getData());
                            if (reckProductModels.size() > 0) {
                                for (int i = 0; i < reckProductModels.size(); i++) {
                                    reckidList.add(reckProductModels.get(i).getProductId());
                                }
                            }
                            callVariantsAPI();
                        } else
                            callResumeAPI();
                    }
                }

                @Override
                public void onFailure(Call<CartRECKListModelEcom> call, Throwable t) {
                }
            });
        } else {
            noInternetAvailable();
        }
    }

    private void setSnackBar(MyCartData data) {
        if (data != null && data.isShowOfferMessage()) {
            snackbar = Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_INDEFINITE);
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
            View snackView = getLayoutInflater().inflate(R.layout.snack_offer, null);
            MyTextViewEcom textEcom = snackView.findViewById(R.id.snack_message2);
            MyBoldTextViewEcom textEcomMain = snackView.findViewById(R.id.snack_message);
            MyBoldTextViewEcom textAdd = snackView.findViewById(R.id.textadditems);
            ImageView imageCancel = snackView.findViewById(R.id.imgcancel);

            imageCancel.setOnClickListener(v -> snackbar.dismiss());
            textEcomMain.setText(data.getMainOfferMessage());
            textEcom.setText(data.getSecondaryOfferMessage());
            textAdd.setOnClickListener(v -> callOffer());
            textEcom.setOnClickListener(v -> callOffer());
            textEcomMain.setOnClickListener(v -> callOffer());
            layout.setPadding(0, 0, 0, 0);
            layout.addView(snackView, 0);
            snackbar.show();
        } else {
            if (snackbar != null && snackbar.isShown())
                snackbar.dismiss();
        }
    }

    private void callOffer() {
        Intent intent = new Intent(MyCartActivity.this, MyCategoryActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onMethodCallbackMain() {
        showLog("onMethodCallbackMain", "cartModels: " + cartModels.size());
        if (cartModels.size() > 0) {
            constraintLayoutPlaceOrder.setVisibility(View.VISIBLE);
            // cardViewDetails.setVisibility(View.VISIBLE);
        } else {
            //   cbFreeLookUp.setVisibility(View.GONE);
            constraintLayoutPlaceOrder.setVisibility(View.GONE);
            noRecordsFound();
        }
        getMyCartList(true, false, false);
    }

    private void displayTotalPrice(double price) {
        tvTotalAmount.setText("₹ " + price);
        showLog("Total Delete Price", "Total Delete Price: " + price);
    }

    @Override
    public void onMethodPayCallbackMain(double d) {
    }

    void noRecordsFound() {
        txtErrorTitle.setText("Cart is Empty");
        txtErrorContent.setText("Looks like you have no items in your shopping cart.");
        imgError.setImageResource(R.drawable.ic_shopping_cart);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        rvCartItem.setVisibility(View.GONE);
        lnLocation.setVisibility(View.GONE);
    }

    private void getMyFreeProductList(int selId) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Call<FreeProductListModel> wsCallingEvents = mAPIInterface.getMyFreeProduct(session.getIboKeyId(), subtotal, totalPV);
            wsCallingEvents.enqueue(new Callback<FreeProductListModel>() {
                @Override
                public void onResponse(Call<FreeProductListModel> call, Response<FreeProductListModel> response) {
                    showLog("CartResponse", "CartResponse11: " + response.body());
                    freeProductModels.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                promoid = selId;
                                freeProductModels.addAll(response.body().getData());
                                for (int i = 0; i < freeProductModels.size(); i++) {
                                    int pId = freeProductModels.get(i).getProductId();
                                    skuProductModels.get(i).setIsselected(pId == promoid);
                                }
                                freeProductsListAdapter = new SkuAdapter(MyCartActivity.this, freeProductModels);
                                rvFreeProducts.setAdapter(freeProductsListAdapter);
                                dialogFreeProducts.show();
                            }
                           /* if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                //noRecordsFound();
                                // Toast.makeText(MyCartActivity.this, "No Free Product!", Toast.LENGTH_SHORT).show();

                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                freeProductModels.addAll(response.body().getData());
                                freeProductsListAdapter = new SkuAdapter(MyCartActivity.this, freeProductModels);
                                rvFreeProducts.setAdapter(freeProductsListAdapter);
                                promoid = selId;
                                freeProductModels.get(0).setIsselected(true);
                                if (selId != -1) {
                                    boolean selExist = false;
                                    for (int i = 0; i < freeProductModels.size(); i++) {
                                        int prod = freeProductModels.get(i).getProductId();
                                        if (prod == selId) {
                                            selExist = true;
                                            promoid = freeProductModels.get(i).getPromoId();
                                            freeProductModels.get(i).setIsselected(true);
                                            showLog("Promo==", selId + " : " + prod + " : " + promoid + " : " + i);
                                        }
                                    }
                                    if (freeProductModels.size() == 1 && selExist) {
                                    } else {
                                        promoid = -1;
                                        dialogFreeProducts.show();
                                    }
                                } else
                                    dialogFreeProducts.show();*/
                           /* } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            }*/
                        }
                    }
                }

                @Override
                public void onFailure(Call<FreeProductListModel> call, Throwable t) {
                }
            });
        } else {
            // noInternetConnectionMessage();
            utils.dialogueNoInternet(this);
        }
    }

    private void submitFreeProducts() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            showFullDialog();
            Call<FreeProductsModel> wsCallingEvents = mAPIInterface.submitProducts(session.getIboKeyId(), deviceId, promoid);
            wsCallingEvents.enqueue(new Callback<FreeProductsModel>() {
                @Override
                public void onResponse(Call<FreeProductsModel> call, Response<FreeProductsModel> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            FreeProductsModel mData = response.body();
                            int statuscode = mData.getStatusCode();
                            if (statuscode == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                Toast.makeText(MyCartActivity.this, "Free product added successfully", Toast.LENGTH_SHORT).show();
                                getMyCartList(false, false, false);
                            } else
                                hideFullDialog();
                        } else
                            hideFullDialog();
                    } else
                        hideFullDialog();
                }

                @Override
                public void onFailure(Call<FreeProductsModel> call, Throwable t) {
                    hideFullDialog();
                }
            });
        } else {
            hideFullDialog();
            utils.dialogueNoInternet(this);
        }
    }
/*
    private void alertLessOne() {
        SweetAlertDialogCart loading = new SweetAlertDialogCart(MyCartActivity.this, SweetAlertDialogCart.WARNING_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setCancelText("Cancel");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            TextView textTitle = (TextView) alertDialog.findViewById(R.id.title_text);
            TextView textContent = (TextView) alertDialog.findViewById(R.id.content_text);
            Button btnConfirm = (Button) alertDialog.findViewById(R.id.confirm_button);
            Button btnCancel = (Button) alertDialog.findViewById(R.id.cancel_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            //textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            // btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            btnCancel.setTypeface(typeface);
            textTitle.setText("PV: " + pvResult);
            btnConfirm.setBackgroundResource(R.drawable.blue_button_background);
            btnConfirm.setText("Shop More");
            btnCancel.setText("Ignore");
            textContent.setText("Your total PV for this order is " + pvResult + ". No NV is generated on this order. Add products worth total " + Const.PVValue + " PV on this order to avail guaranteed 100 NV and you will also get these product at a discounted Nebula IBO Price.");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> {

                Intent i = new Intent(MyCartActivity.this, MainActivity.class);
                startActivity(i);


                loading.dismiss();

            });
            btnCancel.setOnClickListener(view -> dialog.dismiss());
        });
    }

    private void alertLessTwo() {
        SweetAlertDialogCart loading = new SweetAlertDialogCart(MyCartActivity.this, SweetAlertDialogCart.WARNING_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("Shop More");
        loading.setCancelText("Ignore");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            TextView textTitle = (TextView) alertDialog.findViewById(R.id.title_text);
            TextView textContent = (TextView) alertDialog.findViewById(R.id.content_text);
            Button btnConfirm = (Button) alertDialog.findViewById(R.id.confirm_button);
            Button btnCancel = (Button) alertDialog.findViewById(R.id.cancel_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            //textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            // btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            btnCancel.setTypeface(typeface);
            textTitle.setText("PV: " + pvResult);
            btnConfirm.setBackgroundResource(R.drawable.blue_button_background);
            btnConfirm.setText("Shop More");
            btnCancel.setText("Ignore");
            textContent.setText("Your total PV for this order is " + pvResult + ". Purchase products worth " + Const.PVValueMax + " PV to earn upto 1Lakh Community Growth Income every week.");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> {
                Intent i = new Intent(MyCartActivity.this, MainActivity.class);
                startActivity(i);

                loading.dismiss();

            });
            btnCancel.setOnClickListener(view -> dialog.dismiss());
        });
        try {
            loading.show();
        } catch (Exception e) {
            // WindowManager$BadTokenException will be caught and the app would not display
            // the 'Force Close' message
        }
    }

    private void alertLessThree() {
        SweetAlertDialogCart loading = new SweetAlertDialogCart(MyCartActivity.this, SweetAlertDialogCart.SUCCESS_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("Shop More");
        loading.setCancelText("Ignore");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            TextView textTitle = (TextView) alertDialog.findViewById(R.id.title_text);
            TextView textContent = (TextView) alertDialog.findViewById(R.id.content_text);
            Button btnConfirm = (Button) alertDialog.findViewById(R.id.confirm_button);
            Button btnCancel = (Button) alertDialog.findViewById(R.id.cancel_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            //textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            // btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            btnCancel.setTypeface(typeface);
            btnCancel.setVisibility(View.GONE);
            textTitle.setText("PV: " + pvResult);
            btnConfirm.setBackgroundResource(R.drawable.blue_button_background);
            btnConfirm.setText("OK");
            btnCancel.setText("Ignore");
            textContent.setText("Congratulation, you can now earn upto 1 Lakh Community Growth Income weekly.\n Kindly checkout and complete your payment.");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> loading.dismiss());
            btnCancel.setOnClickListener(view -> dialog.dismiss());
        });
        loading.show();
    }*/

    public void initDialogFreeProducts() {

        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_free_products, null);
        builder.setView(customLayout);
        imgClose = customLayout.findViewById(R.id.img_close);
        rvFreeProducts = customLayout.findViewById(R.id.rv_myFreeProductList);
        btnConfirm = customLayout.findViewById(R.id.btnConfirm);
        // rvFreeProducts.setHasFixedSize(true);
        // RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(MyCartActivity.this, LinearLayoutManager.VERTICAL, false);
        rvFreeProducts.setLayoutManager(new LinearLayoutManager(this));
        rvFreeProducts.setItemAnimator(null);

        dialogFreeProducts = builder.create();

        dialogFreeProducts.setCancelable(false);
        btnConfirm.setOnClickListener(v -> {
            for (int i = 0; i < freeProductModels.size(); i++) {
                AddVariantModel.Datum datum = freeProductModels.get(i);
                if (datum.isIsselected())
                    promoid = datum.getPromoId();
            }
            showLog("promoid", "promoid" + promoid);
            if (promoid != -1) {
                dialogFreeProducts.dismiss();
                submitFreeProducts();
            } else {
                Toast.makeText(MyCartActivity.this, "Please select free product.", Toast.LENGTH_SHORT).show();
            }
        });

        imgClose.setOnClickListener(v -> {
            if (promoid != -1) {
                dialogFreeProducts.dismiss();
            } else {
                Toast.makeText(MyCartActivity.this, "Please confirm your free product.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //int al
    public void initReckDialogFreeProducts() {

        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_free_products, null);
        builder.setView(customLayout);

        imgCloseReck = customLayout.findViewById(R.id.img_close);
        rvFreeProductsRec = customLayout.findViewById(R.id.rv_myFreeProductList);
        btnConfirmReck = customLayout.findViewById(R.id.btnConfirm);
        MyTextViewEcom txt = customLayout.findViewById(R.id.textid);
        txt.setText("Choose Product for Home Store");

        // rvFreeProductsRec.setHasFixedSize(true);
        // RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(MyCartActivity.this, LinearLayoutManager.VERTICAL, false);
        rvFreeProductsRec.setLayoutManager(new LinearLayoutManager(this));
        rvFreeProductsRec.setItemAnimator(null);
        imgCloseReck.setVisibility(View.GONE);
        dialogReck = builder.create();

        dialogReck.setCancelable(false);
        btnConfirmReck.setOnClickListener(v -> {
            btnConfirmReck.setEnabled(false);
            for (int i = 0; i < reckProductModels.size(); i++) {
                CartRECKListModelEcom.Datum datum = reckProductModels.get(i);
                if (datum.isIsselected()) {
                    reckID = datum.getProductId();
                    // reckPos = i;
                }

            }
            showLog("reckID", "reckID" + reckID + " : xReckID: " + xReckID);
            if (reckID != -1) {
                if (reckID != xReckID) {
                    if (xReckID != -1)
                        deleteReckFromCart(false);
                    else
                        addCompulsoryReck(true);
                } else {
                    dialogReck.dismiss();
                }

            } else {
                btnConfirmReck.setEnabled(true);
                Toast.makeText(MyCartActivity.this, "Please select product.", Toast.LENGTH_SHORT).show();
            }
        });
        dialogReck.setOnDismissListener(dialog -> showTshirtDialog());
    }


    private void initSkuProducts() {

        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_free_products, null);
        builder.setView(customLayout);

        imgCloseSku = customLayout.findViewById(R.id.img_close);
        rvFreeProductsSku = customLayout.findViewById(R.id.rv_myFreeProductList);
        btnConfirmSku = customLayout.findViewById(R.id.btnConfirm);
        MyTextViewEcom txt = customLayout.findViewById(R.id.textid);
        txt.setText("Choose Product for Home Store");

        // rvFreeProductsSku.setHasFixedSize(true);
        // RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(MyCartActivity.this, LinearLayoutManager.VERTICAL, false);
        rvFreeProductsSku.setLayoutManager(new LinearLayoutManager(this));
        rvFreeProductsSku.setItemAnimator(null);
        dialogSku = builder.create();
        imgCloseSku.setVisibility(View.GONE);
        dialogSku.setCancelable(false);
        /*imgCloseSku.setOnClickListener(v -> {
            dialogSku.dismiss();
        });*/
        btnConfirmSku.setOnClickListener(v -> {
            btnConfirmSku.setEnabled(false);
            for (int i = 0; i < skuProductModels.size(); i++) {
                AddVariantModel.Datum datum = skuProductModels.get(i);
                if (datum.isIsselected()) {
                    skuID = datum.getProductId();
                }
            }
            showLog("promoid", "skuID" + skuID + " : " + xSkuID);
            if (skuID != -1) {
                if (skuID != xSkuID) {
                    if (xSkuID != -1)
                        deleteReckFromCart(true);
                    else
                        addCompulsoryReck(false);

                } else {
                    dialogSku.dismiss();
                }
            } else {
                btnConfirmSku.setEnabled(true);
                Toast.makeText(MyCartActivity.this, "Please select product.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void initDialogCarrt() {

        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_cart_quantity, null);
        builder.setView(customLayout);

        imgClose = customLayout.findViewById(R.id.img_close);
        spinner = customLayout.findViewById(R.id.spinner);
        editcartqnty = customLayout.findViewById(R.id.edt_cart);
        btnConfirm = customLayout.findViewById(R.id.btnConfirm);
        ArrayAdapter dataAdapter = new ArrayAdapter(this, R.layout.spinner_ticket_item_ecom, cartqnty);
        dataAdapter.setDropDownViewResource(R.layout.spinner_ticket_item_ecom);
        spinner.setAdapter(dataAdapter);

        if (spinner.getSelectedItem() == null) {
            spinner.performClick();
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editcartqnty.setText(spinner.getSelectedItem().toString()); //this is taking the first value of the spinner by default.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialogCart = builder.create();

        dialogCart.setCancelable(true);
        btnConfirm.setOnClickListener(v -> {

        });

        imgClose.setOnClickListener(v -> {
            dialogCart.dismiss();
        });
    }


    public void setSelectedLocation(String addressType, Integer allCityID, String address,
                                    boolean isAddressType, String cityFacility,
                                    boolean isPincodeServicable, boolean isHomestoreAvail) {

        if (isAddressType) {
            isServicable = true;
            isHomeStore = true;
            tvLocation.setText("Pickup from " + address);
            showLog("Update Value", "Update Value" + isAddressType);
        } else {
            tvLocation.setText("Deliver to " + address);
            isServicable = isPincodeServicable;
            isHomeStore = isHomestoreAvail;
            showLog("picode", "" + isPincodeServicable);
            showLog("Update Value", "Update Value" + isAddressType + "" + isPincodeServicable);
        }

        addressNameSave = address;

        allCityID = allCityID;
        addressType = addressType;
        cityFacility = cityFacility;
        SharedPreferences preferences = getSharedPreferences(PrefUtils.prefLocation, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PrefUtils.prefLocation, addressNameSave);
        editor.apply();
        showLog("Location Empty SAVE", "Location Empty SAVE" + addressNameSave);

        SharedPreferences preferencesAddressType = getSharedPreferences(PrefUtils.prefAddresstype, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorpreferencesAddressType = preferencesAddressType.edit();
        editorpreferencesAddressType.putBoolean(PrefUtils.prefAddresstype, isAddressType);
        editorpreferencesAddressType.apply();

        SharedPreferences preferencesAddressCityID = getSharedPreferences(PrefUtils.prefPrefcityid, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorpreferencesAddressCityID = preferencesAddressCityID.edit();
        editorpreferencesAddressCityID.putInt(PrefUtils.prefPrefcityid, allCityID);
        editorpreferencesAddressCityID.apply();

        showLog("Location ID", "Location ID" + allCityID);

        SharedPreferences sharedPreferencesAddressType = getSharedPreferences(PrefUtils.prefAddresstypevalue, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorAddressType = sharedPreferencesAddressType.edit();
        editorAddressType.putString(PrefUtils.prefAddresstypevalue, addressType);
        editorAddressType.apply();
        showLog("Location Type", "Location Type" + addressType);


        SharedPreferences sharedPreferencesFacility = getSharedPreferences(PrefUtils.prefFacility, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorFacility = sharedPreferencesFacility.edit();
        editorFacility.putString(PrefUtils.prefFacility, cityFacility);
        editorFacility.apply();
        showLog("Facility", "Facility: " + cityFacility);
        // checkAddress = true;
        citySave = getSharedPreferences(PrefUtils.prefCityid, 0);
        cityId = citySave.getInt(PrefUtils.prefCityid, 0);
        showLog("cityId", "cityId: " + cityId);

        //if(!edit)
        getMyCartList(false, false, true);

    }

    @Override
    public void onBackPressed() {
        showLog("Location Empty SAVE", "Location Empty SAVE Back" + addressNameSave);

        hideprogress();
        if (selectCategory == 10) {
           /* Intent cartReview = new Intent(MyCartActivity.this, MyCategoryActivity.class);
            cartReview.putExtra("SELECTEDVALUECATEGORYBACK", 11);
            startActivity(cartReview);*/
            Intent intent = new Intent();
            setResult(utils.productShare, intent);
            finish();

        } else if (backID == 1) {
            Intent cartReview = new Intent(MyCartActivity.this, MainActivity.class);
            startActivity(cartReview);

        } else {
            // super.onBackPressed();
            Intent intent = new Intent();
            setResult(utils.productShare, intent);
            finish();
        }
      /*  if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
        }*/

        SharedPreferences FirstRunPV = getSharedPreferences(PrefUtils.prefIsfirstpv, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = FirstRunPV.edit();
        edit.clear();
        edit.apply();
    }

    @Override
    public void onItemClick(String item) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @Override
    public void onUpdateList() {
        onMethodCallbackMain();
    }

    @Override
    public void onFullDialogchange(boolean fullDialog) {
        if (fullDialog)
            showFullDialog();
        else
            hideFullDialog();

    }

    public void showLog(Object title, Object message) {
        Log.d(String.valueOf(title), String.valueOf(message));
    }

    ArrayAdapter<CharSequence> langAdapter;

    public class MyCartProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context context;
        ArrayList<MyCart> myCartsModel;
        double pvResultAdapter;
        int subtotal;
        int shippingCharge;
        int grandtotal;
        int calculatedMRP;
        String hs;
        String msgInfo;

        public class FooterViewHolder extends RecyclerView.ViewHolder {
            //TextView headerTitle;
            ImageView imgInfo;
            MyTextViewEcom tvMycartItemPrice, tvShipingPrice, tvGrandTotal, tvMRP, tvMycartRetailPrice;

            public FooterViewHolder(View view) {
                super(view);
                imgInfo = view.findViewById(R.id.image_info);
                tvMycartItemPrice = view.findViewById(R.id.tv_mycart_item_price);
                tvShipingPrice = view.findViewById(R.id.tv_shiping_price);
                tvGrandTotal = view.findViewById(R.id.tv_grand_total_value);
              /*  lnCartMRP =  view.findViewById(R.id.ln_cart_mrp);
                lnCartRetail = view. findViewById(R.id.ln_cart_retail);*/
                tvMRP = view.findViewById(R.id.tv_mycart_mrp_price);
                tvMycartRetailPrice = view.findViewById(R.id.tv_mycart_retail_price);
            }
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            //@BindView(R.id.tv_mycart_name)
            MyTextViewEcom tvMyCartName;

            // @BindView(R.id.tv_mycart_item_price)
            MyTextViewEcom tvMyCartItemPrice;

            // @BindView(R.id.product_count)
            MyTextViewEcom tvProductCount;

            //  @BindView(R.id.tv_mycart_tablet)
            MyTextViewEcom tvMyCartTablet;

            // @BindView(R.id.thumbnail)
            ImageView thumbnail;

            // @BindView(R.id.img_my_cart_delete)
            ImageView imgDelete;

            // @BindView(R.id.btn_remove_item)
            MyButtonEcom btnRemoveItem;

            // @BindView(R.id.btn_add_item)
            MyButtonEcom btnAddItem;

            /*@BindView(R.id.coursesspinner)
            Spinner spnr;*/

            // @BindView(R.id.card_view)
            CardView cardView;

            // @BindView(R.id.tv_pv_value)
            MyTextViewEcom tvCartPV;

            // @BindView(R.id.tv_nv_value)
            MyTextViewEcom tvCartNV;

            // @BindView(R.id.tv_bv_value)
            MyTextViewEcom tvCartBV;

            // @BindView(R.id.tv_original_price_value)
            MyTextViewEcom tvMRPPrice;

            // @BindView(R.id.txtreward)
            MyTextViewEcom txtReward;

            // @BindView(R.id.tv_price_discount)
            MyTextViewEcom tvMRP;

            // @BindView(R.id.product_qty)
            MyTextViewEcom prodQty;

            // @BindView(R.id.lv_free)
            LinearLayout layfree;

            //  @BindView(R.id.ln_size)
            RelativeLayout lnSize;

            //  @BindView(R.id.rel_spinner)
            RelativeLayout relSpinner;


            //  @BindView(R.id.coursesspinner)
            Spinner spin;

            //  @BindView(R.id.edit_product_qty)
            NebulaEditText editQty;


            public MyViewHolder(View view) {
                super(view);
                // ButterKnife.bind(this, view);
                editQty = view.findViewById(R.id.edit_product_qty);
                spin = view.findViewById(R.id.coursesspinner);
                relSpinner = view.findViewById(R.id.rel_spinner);
                lnSize = view.findViewById(R.id.ln_size);
                layfree = view.findViewById(R.id.lv_free);
                prodQty = view.findViewById(R.id.product_qty);
                tvMRP = view.findViewById(R.id.tv_price_discount);
                txtReward = view.findViewById(R.id.txtreward);
                tvMRPPrice = view.findViewById(R.id.tv_original_price_value);
                tvCartBV = view.findViewById(R.id.tv_bv_value);
                tvCartNV = view.findViewById(R.id.tv_nv_value);
                tvCartPV = view.findViewById(R.id.tv_pv_value);
                cardView = view.findViewById(R.id.card_view);
                btnAddItem = view.findViewById(R.id.btn_add_item);
                btnRemoveItem = view.findViewById(R.id.btn_remove_item);
                imgDelete = view.findViewById(R.id.img_my_cart_delete);
                thumbnail = view.findViewById(R.id.thumbnail);
                tvMyCartTablet = view.findViewById(R.id.tv_mycart_tablet);
                tvProductCount = view.findViewById(R.id.product_count);
                tvMyCartItemPrice = view.findViewById(R.id.tv_mycart_item_price);
                tvMyCartName = view.findViewById(R.id.tv_mycart_name);

            }
        }

        @SuppressLint("HardwareIds")
        public MyCartProductsAdapter(Context context, ArrayList<MyCart> myCartsModel,
                                     int subtotal, int shippingCharge, int grandtotal, int calculatedMRP,
                                     String hs, String msgInfo) {
            keyboardshowing = false;
            this.context = context;
            this.myCartsModel = myCartsModel;
            this.subtotal = subtotal;
            this.shippingCharge = shippingCharge;
            this.grandtotal = grandtotal;
            this.calculatedMRP = calculatedMRP;
            this.hs = hs;
            this.msgInfo = msgInfo;
        }

        @Override
        public int getItemViewType(int position) {
           /* if (position == 0) {
                return TYPE_HEADER;
            } else*/
            showLog("pos==", "getItemViewType " + position + " == " + cartModels.size());

            if (position == cartModels.size()) {
                return TYPE_FOOTER;
            } else
                return TYPE_ITEM;
           /*
            if (position == myCartsModel.size()-1) {
                // This is where we'll add footer.
                return TYPE_FOOTER;
            }
            return super.getItemViewType(position);*/
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v;
            showLog("pos==", "viewType " + viewType);
            if (viewType == TYPE_FOOTER) {
                v = LayoutInflater.from(context).inflate(R.layout.activity_footer_cart, parent, false);
                FooterViewHolder vh = new FooterViewHolder(v);
                return vh;
            }

            v = LayoutInflater.from(context).inflate(R.layout.my_cart_list_item, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        boolean clickRel = false;
        String extra = " (Minimum order qty)";

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holderview, int mposition) {
            //getting the product of the specified position
            if (holderview instanceof FooterViewHolder) {
//////////////////////////////////////////////////////////////////////////////////////////////////////////
                //inflate your footer layout here
                final FooterViewHolder footerHolder = (FooterViewHolder) holderview;

                footerHolder.tvMycartItemPrice.setText("" + subtotal);
                footerHolder.tvShipingPrice.setText("" + shippingCharge);
                footerHolder.tvGrandTotal.setText("" + grandtotal);
                footerHolder.tvMRP.setText("" + calculatedMRP);
                footerHolder.tvMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                footerHolder.tvMycartRetailPrice.setText(hs);
                footerHolder.imgInfo.setOnClickListener(v -> {
                    if (!keyboardshowing) {
                        loadingInfo.show();
                    }
                });
            } else if (holderview instanceof MyViewHolder) {
                MyViewHolder holder = (MyViewHolder) holderview;

                MyCart myCart = myCartsModel.get(mposition);
                qtyList.clear();
                qtyList.add("Select");
                int minQty = myCart.isMandatory() ? myCart.getCartQuantity() : myCart.getSpinner();
                if (minQty <= 9) {
                    for (int i = minQty; i < 10; i++) {
                        String ex = "";
                        if (i == minQty)
                            ex = extra;
                        qtyList.add(i + ex);
                    }
                }

                if (qtyList.size() == 1) {
                    qtyList.add(minQty + extra);
                    qtyList.add(minQty + "+");
                } else
                    qtyList.add("10+");

                String[] namesArr = qtyList.toArray(new String[qtyList.size()]);
                langAdapter = new ArrayAdapter<>(context, R.layout.spinner_text, namesArr);
                langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);

                pvResultAdapter = myCart.getPv();
                //binding the data with the viewholder views

                holder.tvMyCartName.setText(myCart.getProductName());
                holder.tvProductCount.setText(String.valueOf(myCart.getCartQuantity()));
                holder.tvMyCartTablet.setText(String.valueOf(myCart.getVolWt()));

                holder.tvCartPV.setText("" + pvResultAdapter);
                holder.tvCartBV.setText("" + myCart.getBv() + "%");
                holder.tvCartNV.setText("" + myCart.getNv());
                holder.tvMyCartItemPrice.setText(String.valueOf(myCart.getHomeStorePrice()));
                holder.tvMRP.setText(String.valueOf(myCart.getMrpPrice()));
                holder.tvMRPPrice.setText(String.valueOf(myCart.getDistributorPrice()));
                holder.tvMRPPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tvMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                showLog("image == ", myCart.getMainImage());
                Glide.with(context)
                        .load(myCart.getMainImage()).fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(getRandomDrawbleColor())
                        .into(holder.thumbnail);

                holder.lnSize.setVisibility(View.GONE);
                holder.txtReward.setVisibility(View.GONE);
                holder.editQty.setVisibility(View.GONE);
                holder.prodQty.setVisibility(View.VISIBLE);
                holder.prodQty.setText(String.valueOf(myCart.getCartQuantity()));

                holder.relSpinner.setOnClickListener(v -> {
                    xPos = mposition;
                    showLog("perform==", "relSpinner click");

                    // InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (keyboardshowing) {
                        showLog("perform==", "Software Keyboard was shown");
                        // writeToLog("Software Keyboard was shown");
                    } else {
                        showLog("perform==", "Software Keyboard close");
                       /* if(qtyList.size()==2){

                        }
                        else {*/
                        holder.spin.setSelection(0, false);
                        clickRel = true;
                        holder.spin.performClick();
                        // }
                    }
                });
              /*  holder.spin.setEnabled(false);
                holder.spin.setClickable(false);*/
                holder.spin.setAdapter(langAdapter);

                // spinner.setOnItemSelectedListener(null);

          /*  spinner.post(() -> {
               // holder.spin.setSelection(0, false);
                spinner.post(() -> spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        showLog("perform==", "item select " + position);
                        productidSelected = myCart.getProductId();
                        productidQty = myCart.getCartQuantity();
                        if (position == qtySize.length - 1) {
                            showLog("perform==", position + " ==  " + (qtySize.length - 1));
                            keyboardshowing = true;
                            holder.editQty.setVisibility(View.VISIBLE);
                            holder.prodQty.setVisibility(View.GONE);
                            holder.editQty.setText("");
                            holder.editQty.requestFocus();
                            InputMethodManager inputMethodManager =
                                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.toggleSoftInputFromWindow(
                                    holder.editQty.getApplicationWindowToken(),
                                    InputMethodManager.SHOW_FORCED, 0);
                        } else {
                            showLog("perform==", "else===");
                            addToCart(position + 1);
                            keyboardshowing = false;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        showLog("perform==", "onNothingSelected ");
                    }
                }));
            });*/
                // holder.spin.setSelection(0, false);
                holder.spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int vposition, long id) {
                        showLog("perform==", "item select " + vposition + " :clickRel: " + clickRel + " :keyboardshowing " + keyboardshowing);

                        xPos = mposition;
                        //  if (clickRel) {
                        //    clickRel = false;
                        if (vposition > 0) {
                            productidSelected = myCart.getProductId();
                            productidQty = myCart.getCartQuantity();
                            if (vposition == namesArr.length - 1) {
                                showLog("perform==", vposition + " ==  " + (namesArr.length - 1));
                                keyboardshowing = true;
                                holder.editQty.setVisibility(View.VISIBLE);
                                holder.prodQty.setVisibility(View.GONE);
                                holder.editQty.setText("");
                                holder.editQty.requestFocus();
                                InputMethodManager inputMethodManager =
                                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.toggleSoftInputFromWindow(
                                        holder.editQty.getApplicationWindowToken(),
                                        InputMethodManager.SHOW_FORCED, 0);
                            } else {
                                showLog("perform==", "else===");
                                int position;
                                if (vposition == 1) {
                                    String pos = namesArr[vposition].replace(extra, "").trim();
                                    position = Integer.parseInt(pos);
                                } else
                                    position = Integer.parseInt(namesArr[vposition]);
                                // addToCart(position);
                                if (myCart.isMandatory()) {
                                    if (position > productidQty)
                                        addToCart(position, true);
                                } else
                                    addToCart(position, false);
                                keyboardshowing = false;
                            }
                        }
                        //  }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // showLog("perform==", "onNothingSelected ");
                        if (clickRel)
                            clickRel = false;
                        //  keyboardshowing = false;
                    }
                });


                holder.editQty.setKeyImeChangeListener((keyCode, event) -> {
                    showLog("perform==", "Back press");

                    if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
                        keyboardshowing = false;
                        //  clickRel=false;
                        // do something
                        holder.editQty.setVisibility(View.GONE);
                        holder.prodQty.setVisibility(View.VISIBLE);
                        holder.prodQty.setText(String.valueOf(productidQty));

                    }
                });
                holder.editQty.setOnEditorActionListener((v, actionId, event) -> {
                    showLog("perform==", actionId + " :: " + event);
                    if ((actionId == EditorInfo.IME_ACTION_DONE)) {
                        keyboardshowing = false;
                        // clickRel=false;
                        //do here your stuff
                        String qty = holder.editQty.getText().toString();
                        if (TextUtils.isEmpty(qty)) {
                            holder.editQty.setVisibility(View.GONE);
                            holder.prodQty.setVisibility(View.VISIBLE);
                            holder.prodQty.setText(String.valueOf(productidQty));
                        } else {
                            int q = Integer.parseInt(qty);
                            if (q > 0) {
                                showLog("perform==", q + " productidQty  :: " + productidQty + " :: " + minQty);
                                if (q == productidQty) {
                                    holder.editQty.setVisibility(View.GONE);
                                    holder.prodQty.setVisibility(View.VISIBLE);
                                    holder.prodQty.setText(String.valueOf(productidQty));
                                } else {
                                    if (myCart.isMandatory()) {
                                        if (q > productidQty)
                                            addToCart(q, true);
                                        else {
                                            holder.editQty.setVisibility(View.GONE);
                                            holder.prodQty.setVisibility(View.VISIBLE);
                                            holder.prodQty.setText(String.valueOf(productidQty));
                                        }
                                    } else {
                                        if (q >= minQty)
                                            addToCart(q, false);
                                        else {
                                            holder.editQty.setVisibility(View.GONE);
                                            holder.prodQty.setVisibility(View.VISIBLE);
                                            holder.prodQty.setText(String.valueOf(productidQty));
                                        }
                                    }
                                }
                            } else {
                                holder.editQty.setVisibility(View.GONE);
                                holder.prodQty.setVisibility(View.VISIBLE);
                                holder.prodQty.setText(String.valueOf(productidQty));
                            }
                        }
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        return true;
                    }
                    return false;
                });


                if (myCart.isIsPromo()) {
                    holder.btnAddItem.setVisibility(View.GONE);
                    // holder.btnRemoveItem.setVisibility(View.GONE);
                    holder.imgDelete.setVisibility(View.GONE);
                    holder.layfree.setVisibility(View.VISIBLE);

                    if (myCart.isIsRankRewardBronze() || myCart.isIsRankRewardSilver()) {
                        holder.lnSize.setVisibility(View.INVISIBLE);
                        holder.txtReward.setVisibility(View.VISIBLE);
                        if (myCart.isIsRankRewardBronze())
                            holder.txtReward.setText(myCart.getRankRewardBronzeText());
                        if (myCart.isIsRankRewardSilver())
                            holder.txtReward.setText(myCart.getRankRewardSilverText());
                    } /*else
                    holder.txtReward.setVisibility(View.GONE);*/
                    //  setsizegone(holder.lnSize,holder.txtReward);
                } else if (myCart.isMandatory()) {
                    holder.btnAddItem.setVisibility(View.GONE);
                    // holder.btnRemoveItem.setVisibility(View.GONE);
                    holder.imgDelete.setVisibility(View.GONE);
                    holder.layfree.setVisibility(View.GONE);
                    holder.txtReward.setVisibility(View.VISIBLE);
                    holder.txtReward.setText("Auto added for Home Store");
                    boolean showEdit = isItEditable(myCart.getProductId(), myCart.isMandatory());
                    holder.imgDelete.setImageResource(R.drawable.ic_baseline_edit_24);
                    holder.imgDelete.setVisibility(showEdit ? View.VISIBLE : View.GONE);
                } else {
                    //holder.btnAddItem.setVisibility(View.VISIBLE);
                    //holder.btnRemoveItem.setVisibility(View.VISIBLE);
                    holder.imgDelete.setVisibility(View.VISIBLE);
                    holder.imgDelete.setImageResource(R.drawable.ic_delete_black);
                    holder.layfree.setVisibility(View.GONE);
                }

                holder.btnAddItem.setOnClickListener(v -> {
                    productidSelected = myCart.getProductId();
                    productidQty = myCart.getCartQuantity();
                    xPos = mposition;
                    bottomSheet = new BottommSheetDialog(MyCartActivity.this, myCart.getProductName(), productidQty,
                            productidSelected, session.getShoppyUserID(), deviceId, mAPIInterfaceShoppy, MyCartActivity.this);
                    bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
                });

          /*  holder.btnRemoveItem.setOnClickListener(v -> {
                int curval = Integer.parseInt(holder.tvProductCount.getText().toString());
                showLog("curval", "curval " + curval);
                if (curval > 1) {
                   *//* txtHeader.setText(myCart.getProductName());
                    AddRemoveAdapter customAdapter = new AddRemoveAdapter(context, datalistRemove, myCart.getProductId(), false);
                    recyclerView.setAdapter(customAdapter);
                    dialogDetails.show();*//*
                    // curval--;
                    //addToCart(m_deviceId, session.getShoppyUserID(), myCart.getProductId(), 1, "minus");
                }
            });
          */

                holder.imgDelete.setOnClickListener(v -> {
                    xPos = mposition;
                    if (!keyboardshowing) {
                        boolean showEdit = isItEditable(myCart.getProductId(), myCart.isMandatory());
                        if (showEdit) {
                            boolean tshirt = myCart.getProductName().contains("shirt");
                            if (!tshirt) {
                                showReckDialogNow();
                            } else {
                                showSkuDialogNow();
                            }
                        } else {
                            if (myCart.isIsPromo()) {
                                if (myCart.isIsFree()) {
                                    deleteFromCart(myCart.getProductId(), myCart.getId(), true);
                                } else {
                                    if (freeProductModels.size() == 0)
                                        getMyFreeProductList(myCart.getProductId());
                                    else
                                        dialogFreeProducts.show();
                                }
                            } else
                                deleteFromCart(myCart.getProductId(), myCart.getId(), false);
                        }
                    }
                });
            }
        }

        private boolean isItEditable(int productId, boolean mandatory) {
            boolean editable = false;
            if (session.getShoppyid() == 0 && mandatory) {
                if (reckidList.size() > 0) {
                    editable = reckidList.contains(productId);
                }
                if (!editable)
                    if (tshirtList.size() > 0)
                        editable = tshirtList.contains(productId);
            }
            return editable;
        }

        @Override
        public int getItemCount() {
            // showLog("myCartsModel.size()", "myCartsModel.size():" + myCartsModel.size());
           /* if (myCartsModel == null) {
                return 0;
            }
            if (myCartsModel.size() == 0) {
                //Return 1 here to show nothing
                return 1;
            }
           */
            // Add extra view to show the footer view
            return myCartsModel.size() + 1;
        }

        private void deleteFromCart(Integer productId, Integer id, boolean isFreeproduct) {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                showFullDialog();
                // Call<DeleteCartModel> wsCallingRegistation;
                Call<DeleteShoppyCartModel> wsCallDelete;
                // if (session.getLogin()) {
                  /*  if (isFreeproduct) {
                        wsCallingRegistation = mAPIInterface.getDeleteFromCartModelCallV2(deviceId, userId, productId, id);
                        callDelete(wsCallingRegistation);
                    } else {*/
                wsCallDelete = mAPIInterfaceShoppy.getDeleteFromCartModelCall(deviceId, session.getShoppyUserID(), productId);
                wsCallDelete.enqueue(new Callback<DeleteShoppyCartModel>() {
                    @Override
                    public void onResponse(Call<DeleteShoppyCartModel> call, Response<DeleteShoppyCartModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                // Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                onMethodCallbackMain();
                            }
                        }
                        hideFullDialog();
                    }

                    @Override
                    public void onFailure(Call<DeleteShoppyCartModel> call, Throwable t) {
                        hideFullDialog();
                    }
                });
                //   }
              /*  } else {
                    wsCallingRegistation = mAPIInterface.getDeleteFromCartModelCallNologin(deviceId, productId);
                    callDelete(wsCallingRegistation);
                }*/
            } else {
                hideFullDialog();
                utils.dialogueNoInternet((Activity) context);
            }
        }

        private ColorDrawable[] vibrantLightColorList =
                {
                        new ColorDrawable(Color.parseColor("#9ACCCD")), new ColorDrawable(Color.parseColor("#8FD8A0")),
                        new ColorDrawable(Color.parseColor("#CBD890")), new ColorDrawable(Color.parseColor("#DACC8F")),
                        new ColorDrawable(Color.parseColor("#D9A790")), new ColorDrawable(Color.parseColor("#D18FD9")),
                        new ColorDrawable(Color.parseColor("#FF6772")), new ColorDrawable(Color.parseColor("#DDFB5C"))
                };

        public ColorDrawable getRandomDrawbleColor() {
            int idx = new Random().nextInt(vibrantLightColorList.length);
            return vibrantLightColorList[idx];
        }
    }

    private void showReckDialogNow() {
        if (!dialogReck.isShowing()) {
            for (int i = 0; i < reckProductModels.size(); i++) {
                int id = reckProductModels.get(i).getProductId();
                reckProductModels.get(i).setIsselected(id == xReckID);
            }
            reckAdapter = new RackAdapter(MyCartActivity.this, reckProductModels);
            rvFreeProductsRec.setAdapter(reckAdapter);
            btnConfirmReck.setEnabled(true);
            dialogReck.show();
        }
    }

    private void showSkuDialogNow() {
        if (!dialogSku.isShowing()) {
            for (int i = 0; i < skuProductModels.size(); i++) {
                int id = skuProductModels.get(i).getProductId();
                skuProductModels.get(i).setIsselected(id == xSkuID);
            }

            SkuAdapter skuProductsListAdapter = new SkuAdapter(MyCartActivity.this, skuProductModels);
            rvFreeProductsSku.setAdapter(skuProductsListAdapter);
            btnConfirmSku.setEnabled(true);
            dialogSku.show();
        }
    }

    private void getMyCartListReview(String cityID) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            showprogress();
            showFullDialog();
            APIInterface mAPIInterfaceShoppy = APIClient.getShoppyClient(MyCartActivity.this).create(APIInterface.class);
            Call<CartListModelReviewEcom> wsCallingEvents;
            //  if (showLocation)
            wsCallingEvents = mAPIInterfaceShoppy.getMyCartListReviewEcom(session.getShoppyUserID(), cityID);
            //  else
            //     wsCallingEvents = mAPIInterfaceCartReview.getMyCartListReviewEcom("0");
            wsCallingEvents.enqueue(new Callback<CartListModelReviewEcom>() {
                @Override
                public void onResponse(Call<CartListModelReviewEcom> call, Response<CartListModelReviewEcom> response) {
                    cartModelsMyCartReviews.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getResponse().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                cartModelsMyCartReviews.addAll(response.body().getResponse().getData().getOutOfStockProducts());
                                if (response.body().getResponse().getData().getCartCount() == 0) {
                                    if (checkAddress) {
                                        if (isServicable) {
                                            if (isHomeStore)
                                                utils.getExpireUser(mAPIInterface, MyCartActivity.this, session);
                                            else {
                                                hideFullDialog();
                                                showLog("isHomeStore", "" + isHomeStore);
                                                homestorenotAvailable();
                                            }
                                        } else {
                                            hideFullDialog();
                                            showLog("isPincode1", "" + isServicable);
                                            pincodenotAvailable();
                                        }
                                    } else
                                        hideFullDialog();
                                } else {
                                    hideFullDialog();
                                    Intent cartReview = new Intent(MyCartActivity.this, ProductAvaibilityActivity.class);
                                    startActivity(cartReview);
                                }
                            } else
                                hideFullDialog();
                        } else
                            hideFullDialog();
                    } else {
                        hideFullDialog();
                        utils.showErrorDialog(MyCartActivity.this, true, "Session Timeout", "Your Session has been expired. Please Login again.", true, session);
                    }
                    hideprogress();
                }

                @Override
                public void onFailure(Call<CartListModelReviewEcom> call, Throwable t) {
                    hideprogress();
                    hideFullDialog();
                    showLog("CartAPIReview5", "CartAPIReview5 " + t);
                }
            });
        } else {
            hideprogress();
            hideFullDialog();
            utils.dialogueNoInternet(this);
            // noInternetConnectionMessage();
        }
    }

    public void openOrderSummary() {
        Intent addressListIntent = new Intent(MyCartActivity.this, OrderSummaryActivity.class);
        addressListIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(addressListIntent);
        hideFullDialog();
    }

    private void homestorenotAvailable() {
        SweetAlertDialogCart loading = new SweetAlertDialogCart(MyCartActivity.this, SweetAlertDialogCart.WARNING_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            // ImageView imgCustom = (ImageView) alertDialog.findViewById( R.id.custom_image );
            MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            alertDialog.setCancelable(false);
            //textTitle.setText(response.body().getMessage());
           /*imgCustom.setVisibility( View.VISIBLE );
            imgCustom.setImageResource(R.drawable.success_pay);*/
            textTitle.setText("Home Store is not available on selected address");
            textContent.setText("Please select another address.");
            btnConfirm.setText("OK");
            // textContent.setText("Pin you have entered is Invalid.");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> {
                loading.dismiss();
                btnProceed.performClick();
            });
        });
        loading.show();
    }

    private void pincodenotAvailable() {
        SweetAlertDialogCart loading = new SweetAlertDialogCart(MyCartActivity.this, SweetAlertDialogCart.WARNING_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            // ImageView imgCustom = (ImageView) alertDialog.findViewById( R.id.custom_image );
            MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            alertDialog.setCancelable(false);
            //textTitle.setText(response.body().getMessage());
           /*imgCustom.setVisibility( View.VISIBLE );
            imgCustom.setImageResource(R.drawable.success_pay);*/
            textTitle.setText("This pincode is not serviceable");
            textContent.setText("Please select another address.");
            btnConfirm.setText("OK");
            // textContent.setText("Pin you have entered is Invalid.");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> {
                loading.dismiss();
                btnProceed.performClick();
            });
        });
        loading.show();
    }

    private void noInternetAvailable() {
        if (snackbar != null && snackbar.isShown())
            snackbar.dismiss();
        // cardViewDetails.setVisibility(View.GONE);
        // cbFreeLookUp.setVisibility(View.GONE);
        constraintLayoutPlaceOrder.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setText(R.string.error_content);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        rvCartItem.setVisibility(View.GONE);
        lnLocation.setVisibility(View.VISIBLE);
    }

    private void addCompulsoryReck(boolean reck) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            showFullDialog();
            Call<AddToCartModel> wsCallingRegistation;
            wsCallingRegistation = mAPIInterfaceShoppy.getAddToCartModelCall(deviceId, session.getShoppyUserID(),
                    reck ? reckID : skuID, 1, "plus", true);

            wsCallingRegistation.enqueue(new Callback<AddToCartModel>() {
                @Override
                public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                            if (reck) {
                                dialogReck.dismiss();
                                xReckID = reckID;
                            } else {
                                dialogSku.dismiss();
                                xSkuID = skuID;
                            }
                            onMethodCallbackMain();
                        } else {
                            setEnableButtons(reck);
                        }
                    } else {
                        setEnableButtons(reck);
                    }
                    hideFullDialog();
                }

                @Override
                public void onFailure(Call<AddToCartModel> call, Throwable t) {
                    hideFullDialog();
                    setEnableButtons(reck);
                }
            });
        } else {
            setEnableButtons(reck);
            hideFullDialog();
            utils.dialogueNoInternet((Activity) this);
        }
    }

    private void setEnableButtons(boolean reck) {
        if (reck) {
            btnConfirmReck.setEnabled(true);
        } else {
            btnConfirmSku.setEnabled(true);
        }
    }

    private void callVariantsAPI() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            showFullDialog();
            skuProductModels.clear();
            Call<AddVariantModel> wsCallingRegistation = mAPIInterfaceShoppy.getAddvariantCall(quantityTshirt, variantidTshirt);
            tshirtList.clear();
            wsCallingRegistation.enqueue(new Callback<AddVariantModel>() {
                @Override
                public void onResponse(Call<AddVariantModel> call, Response<AddVariantModel> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            AddVariantModel mData = response.body();
                            int statuscode = mData.getResponse().getStatusCode();
                            if (statuscode == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                skuProductModels.addAll(response.body().getResponse().getData());
                                for (int i = 0; i < skuProductModels.size(); i++) {
                                    int pId = skuProductModels.get(i).getProductId();
                                    tshirtList.add(pId);
                                }
                                callResumeAPI();
                            }
                        }
                    }
                    hideFullDialog();
                }

                @Override
                public void onFailure(Call<AddVariantModel> call, Throwable t) {
                    hideFullDialog();
                }
            });
        } else {
            hideFullDialog();
            utils.dialogueNoInternet(MyCartActivity.this);
        }
    }

    private void deleteReckFromCart(boolean tshirt) {

        if (Utils.isNetworkAvailable(getApplicationContext())) {
            showFullDialog();
            Call<DeleteShoppyCartModel> wsCallDelete;
            wsCallDelete = mAPIInterfaceShoppy.
                    getDeleteRecFromCartModelCall(deviceId, session.getShoppyUserID(),
                            tshirt ? xSkuID : xReckID, tshirt ? xSkuMainId : xReckMainId);
            wsCallDelete.enqueue(new Callback<DeleteShoppyCartModel>() {
                @Override
                public void onResponse(Call<DeleteShoppyCartModel> call, Response<DeleteShoppyCartModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                            addCompulsoryReck(!tshirt);
                        }
                    }
                    btnConfirmReck.setEnabled(true);
                    btnConfirmSku.setEnabled(true);
                    hideFullDialog();
                }

                @Override
                public void onFailure(Call<DeleteShoppyCartModel> call, Throwable t) {
                    btnConfirmReck.setEnabled(true);
                    btnConfirmSku.setEnabled(true);
                    hideFullDialog();
                }
            });
        } else {
            btnConfirmReck.setEnabled(true);
            btnConfirmSku.setEnabled(true);
            hideFullDialog();
            utils.dialogueNoInternet(MyCartActivity.this);
        }
    }

    public void addToCart(int selQty, boolean ismandatory) {
        if (productidQty == selQty) {
            showLog("perform==", " Same= " + productidQty + " == " + selQty);
        } else {
           /* boolean callAPI = true;
            int minQtty = 0;*/
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                showFullDialog();
                Call<AddToCartModel> wsCallingRegistation;
                boolean add = productidQty < selQty;
                showLog("perform==", add + " = " + productidQty + " < " + selQty);
                int quantity;
                if (add) {
                    quantity = selQty - productidQty;
                    showLog("perform==", quantity + " = " + selQty + " - " + productidQty);
                } else {
                    quantity = productidQty - selQty;
                    showLog("perform==", quantity + " = " + productidQty + " - " + selQty);
                   /* if (!ismandatory) {
                        ProductQuantity productQuantity = productQuantities.get(productidSelected);
                        minQtty = productQuantity.getMinQty();
                        if (selQty < minQtty)
                            callAPI = false;
                    }*/
                }
                // if (callAPI) {
                wsCallingRegistation = mAPIInterfaceShoppy.getAddToCartModelCall(deviceId, session.getShoppyUserID(),
                        productidSelected, quantity, add ? "plus" : "minus", false);
                showLog("quantityOUT: ", "quantityOUT: " + quantity);
                wsCallingRegistation.enqueue(new Callback<AddToCartModel>() {
                    @Override
                    public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                showLog("quantityINBEfore: ", "quantityINBEfore: " + quantity);
                                onMethodCallbackMain();
                                showLog("quantityINAFTER: ", "quantityINAFTER: " + quantity);
                            }
                        }
                        hideFullDialog();
                    }

                    @Override
                    public void onFailure(Call<AddToCartModel> call, Throwable t) {
                        Toast.makeText(MyCartActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                        hideFullDialog();
                    }
                });
              /*} else {
                    Toast.makeText(MyCartActivity.this, "Minimum quantity of poduct is " + minQtty, Toast.LENGTH_SHORT).show();
                    showLog("quantityOUT: ", "Minimum quantityOUT: " + quantity);
                    hideFullDialog();
                }*/
            } else {
                hideFullDialog();
                utils.dialogueNoInternet(MyCartActivity.this);
            }
        }
    }

    public void addProductToCart(int selQty, int pID, int index) {

        if (Utils.isNetworkAvailable(getApplicationContext())) {
            showFullDialog();
            Call<AddToCartModel> wsCallingRegistation;

            wsCallingRegistation = mAPIInterfaceShoppy.getAddToCartModelCall(deviceId, session.getShoppyUserID(),
                    pID, selQty, "plus", false);
            showLog("quantityOUT: ", "quantityOUT: " + selQty);
            wsCallingRegistation.enqueue(new Callback<AddToCartModel>() {
                @Override
                public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                            //productQuantitiesFinal.remove(index);
                            callForceAdd(index + 1);
                            // showLog("quantityINBEfore: ", "quantityINBEfore: " + quantity);
                            // onMethodCallbackMain();
                            //showLog("quantityINAFTER: ", "quantityINAFTER: " + quantity);
                        } else
                            hideFullDialog();
                    } else
                        hideFullDialog();
                }

                @Override
                public void onFailure(Call<AddToCartModel> call, Throwable t) {
                    Toast.makeText(MyCartActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                    hideFullDialog();
                }
            });
        } else {
            hideFullDialog();
            utils.dialogueNoInternet(MyCartActivity.this);
        }
    }
}