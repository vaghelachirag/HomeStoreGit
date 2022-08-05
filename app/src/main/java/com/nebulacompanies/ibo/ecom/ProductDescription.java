package com.nebulacompanies.ibo.ecom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.nebulacompanies.ibo.Interface.OnProductClick;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.activities.AddReviewActivity;
import com.nebulacompanies.ibo.shoppy.R;
import com.nebulacompanies.ibo.adapter.ProductReviewAdapter;
import com.nebulacompanies.ibo.adapter.SimilarProductAdapter;
import com.nebulacompanies.ibo.ecom.adapter.EBCAdapter;
import com.nebulacompanies.ibo.ecom.adapter.MockPagerBannerEcomAdapter;
import com.nebulacompanies.ibo.ecom.adapter.ProductVariantsAdapter;
import com.nebulacompanies.ibo.ecom.customBanner.CirclePageIndicator;
import com.nebulacompanies.ibo.ecom.customBanner.InfiniteViewPager;
import com.nebulacompanies.ibo.ecom.locationutils.PermissionUtils;
import com.nebulacompanies.ibo.ecom.model.AddToCartModel;
import com.nebulacompanies.ibo.ecom.model.BanersEcom;
import com.nebulacompanies.ibo.ecom.model.BanersListEcom;
import com.nebulacompanies.ibo.ecom.model.CartListModelEcom;
import com.nebulacompanies.ibo.ecom.model.CartListTotalCountModelEcom;
import com.nebulacompanies.ibo.ecom.model.EBCBannerModel;
import com.nebulacompanies.ibo.ecom.model.EBCDescriptionModel;
import com.nebulacompanies.ibo.ecom.model.ECBBannerDetailsModel;
import com.nebulacompanies.ibo.ecom.model.ECBDescriptionDestails;
import com.nebulacompanies.ibo.ecom.model.MyCart;
import com.nebulacompanies.ibo.ecom.model.MyTotalCountCartData;
import com.nebulacompanies.ibo.ecom.model.NewCategoryProductDetails;
import com.nebulacompanies.ibo.ecom.model.OtherProducts;
import com.nebulacompanies.ibo.ecom.model.ReviewsModel;
import com.nebulacompanies.ibo.ecom.model.SubCategoryProductList;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyItalicTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.ProductDecsDetailsDialogFragment;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialogCart;
import com.nebulacompanies.ibo.util.Const;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.util.UtilsVersion;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.nebulacompanies.ibo.ecom.utils.Utils.showReviews;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_FAIL;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;

public class ProductDescription extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener, OnProductClick {

    private static final String TAG = "ProductDescription";
    MaterialProgressBar mProgressDialog;
    private APIInterface mAPIInterface, mAPIInterfaceShoppy;
    Typeface typeface;
    PermissionUtils permissionUtils;
    Session session;
    Toolbar toolbar;
    NestedScrollView svProductDesc;
    //SwipeRefreshLayout mSwipeRefreshLayout;
    BottomSheetBehavior behavior;
    LinearLayout lnHome, lnAccount, lnTrackOrder, lnPincode, lnAvailability,
            lnProductDecs, lnLocation, layProductRate, layReadMore;
    private RelativeLayout llEmpty;
    ImageView imgMyAccount, imgHome, imgTrackOrder, imgBackArrow, imgMyCart, imgFav, imgSearch, imgSearchClose, imgPincodeBack, imgError, imgshare;
    MyButtonEcom btnAddToCart, btnAddItem, btnRemoveItem, btnBuyNow;
    public RelativeLayout lnCart, rlSearchView, rlBannerEcom;
    MyTextViewEcom tvDetectLocation, tvLocation, tvProductName, tvProductOfferPrice, tvProductIBO,tvProductMRP, tvProductDecs, tvProductReturn,
            tvProductWarranty, tvProductCounter, tvproductMrpTitle, tvEnterPincode;
    MyItalicTextViewEcom tvProductSaving;
    NebulaEditTextEcom editSearch;
    MyTextViewEcom tvPvStatus, tvSkuValue, tvHome, tvTrackOrder, tvAccount, tvToolbarTitle, tvCartBadge, tvProductShortDecs;
    InfiniteViewPager mViewPagerEcom;
    CirclePageIndicator mCircleIndicatorEcom;
    RecyclerView rvAddress, rvEBC, rvVariants, rvReviews;
    MyTextViewEcom tvPV, tvNV, tvBV, txtReviewPerson;
    RatingBar ratingBar;
    private MyTextViewEcom txtErrorContent, txtRetry;
    MyBoldTextViewEcom txtErrorTitle, txtRatings;

    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private final static int REVIEW_UPDATE = 3000;

    double latitude;
    double longitude;

    EBCAdapter ebcAdapter;
    public SliderLayout mDemoSliderProductDecs;
    ArrayList<MyTotalCountCartData> myTotalCountCartData = new ArrayList<>();
    private final ArrayList<BanersListEcom> banersListEcom = new ArrayList<>();
    MockPagerBannerEcomAdapter adapters;
    ProductVariantsAdapter productVariantsAdapter;

   // ArrayList<MyCart> cartModels = new ArrayList<>();
    public static ArrayList<ECBBannerDetailsModel> arrayListSiteProducts = new ArrayList<>();
    ProductReviewAdapter mpProductReviewAdapter;
    UtilsVersion utilsVersion = new UtilsVersion();
    Utils utils = new Utils();

    //String addressNameSave, addressType, cityFacility;
    //boolean isPermissionGranted, isAddressType, mrpResult, mrpisJoined30Days, isBottomSheetOpen = false;
    String productName, productDecs, productSaving, productClick, productWarranty, productReturn, productShortDecs;
    int addvalue, productQuantity, productMaxSaleQuantity, productID, ID, bannerID;
    String m_deviceId, uniqueID, productSKU;
    //Integer pvResult = 0;
    int  categoryid = 0;
   // SharedPreferences sharedPreferencesAddressType, spGetIsFirstPurchase, sharedPreferencesAddressTypeValue, sharedPreferencesFacility, citySave;
    Intent productDescriptionData;
    RelativeLayout rlBottom;
    View bottomSheet;
    int ecomPickupID = -1;

    SimilarProductAdapter similarProductAdapter;
    RecyclerView rvSimilarproducts;
    List<SubCategoryProductList> similarproducts = new ArrayList<>();
    ProgressDialog mLoadProgressDialog;
    LinearLayout layNoReviews, layratings;
    MyButtonEcom btnRate;
    boolean ableToReview = true;
    String avgRating;
    double defRate = 4.5;
    String defRating = String.valueOf(defRate);

    //String productOfferPrice, productMRP, productHomestore;
    SweetAlertDialogCart loadingout;
    boolean loadVariantDynamic = true;
   /* private ExpandableListView expandableListView;
    private ExpandableListViewAdapter expandableListViewAdapter;
    private List<String> listDataGroup;
    private HashMap<String, List<String>> listDataChild;*/

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        Utils.darkenStatusBar(this, R.color.colorNotification);

        initPref();
        initData();
        initUI();
        initoutOfStock();
        initClick();
        initView();
        initProgress();
        Utils.myPromocode.clear();
        Utils.promoAPIcall = false;
        checkPlayServices();
        callAPI();
        //initializing the objects
        //initObjects();
        //preparing list data
        //initListData();
    }
    /* */

    /**
     * method to initialize the objects
     *//*
    private void initObjects() {

        // initializing the list of groups
        listDataGroup = new ArrayList<>();

        // initializing the list of child
        listDataChild = new HashMap<>();

        // initializing the adapter object
        expandableListViewAdapter = new ExpandableListViewAdapter(this, listDataGroup, listDataChild);

        // setting list adapter
        expandableListView.setAdapter(expandableListViewAdapter);

    }

    *//*
     * Preparing the list data
     *
     * Dummy Items
     *//*
    private void initListData() {


        // Adding group data
        listDataGroup.add(getString(R.string.text_alcohol));
        listDataGroup.add(getString(R.string.text_coffee));
        listDataGroup.add(getString(R.string.text_pasta));
        listDataGroup.add(getString(R.string.text_cold_drinks));

        // array of strings
        String[] array;

        // list of alcohol
        List<String> alcoholList = new ArrayList<>();
        array = getResources().getStringArray(R.array.string_array_alcohol);
        for (String item : array) {
            alcoholList.add(item);
        }

        // list of coffee
        List<String> coffeeList = new ArrayList<>();
        array = getResources().getStringArray(R.array.string_array_coffee);
        for (String item : array) {
            coffeeList.add(item);
        }

        // list of pasta
        List<String> pastaList = new ArrayList<>();
        array = getResources().getStringArray(R.array.string_array_pasta);
        for (String item : array) {
            pastaList.add(item);
        }

        // list of cold drinks
        List<String> coldDrinkList = new ArrayList<>();
        array = getResources().getStringArray(R.array.string_array_cold_drinks);
        for (String item : array) {
            coldDrinkList.add(item);
        }

        // Adding child data
        listDataChild.put(listDataGroup.get(0), alcoholList);
        listDataChild.put(listDataGroup.get(1), coffeeList);
        listDataChild.put(listDataGroup.get(2), pastaList);
        listDataChild.put(listDataGroup.get(3), coldDrinkList);

        // notify the adapter
        expandableListViewAdapter.notifyDataSetChanged();
    }*/
    private void initClick() {
        String value = editSearch.getText().toString();

        imgSearchClose.setOnClickListener(view -> {
            if (value != null) {
                editSearch.getText().clear();
            }
        });

        imgSearch.setOnClickListener(view -> rlSearchView.setVisibility(View.VISIBLE));
        imgshare.setVisibility(View.GONE);
        imgshare.setOnClickListener(v -> {
            imgshare.setEnabled(false);
            utils.shareReferenceID(ProductDescription.this, session.getRefId(), String.valueOf(ID), tvProductName.getText().toString().trim(), session.getIboKeyId(), imgshare, mLoadProgressDialog);
        });
        layProductRate.setOnClickListener(v -> {
            if (showReviews)
                layReadMore.requestFocus();
        });
        btnRate.setOnClickListener(v -> {

            if (ableToReview) {
                String file = "";
                try {
                    file = banersListEcom.get(0).getHdImageFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(ProductDescription.this, AddReviewActivity.class);
                intent.putExtra("name", tvProductName.getText().toString().trim());
                intent.putExtra("short", tvProductShortDecs.getText().toString().trim());
                intent.putExtra("image", file);
                intent.putExtra("productId", productID);
                startActivityForResult(intent, REVIEW_UPDATE);

            } else {
                notAbleReviewDialog();
            }
        });
        imgBackArrow.setOnClickListener(view -> {
            onBackPressed();
        });

        imgFav.setOnClickListener(view -> {
            Intent login = new Intent(ProductDescription.this, MyWishListActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
        });

        imgMyCart.setOnClickListener(view -> {
            Intent login = new Intent(ProductDescription.this, MyCartActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(login, utils.productShare);
        });

        btnBuyNow.setOnClickListener(view -> {
            // Intent login = new Intent( ProductDescription.this, MyAddressActivity.class );
           /* Intent orderplace = new Intent( ProductDescription.this, OrderSummaryActivity.class );
            orderplace.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP );
            startActivity( orderplace );*/
            Toast.makeText(getApplicationContext(), "Under Development", Toast.LENGTH_SHORT).show();
        });

        btnAddToCart.setOnClickListener(view -> {
            // int currentQuantity = Integer.parseInt(tvProductCounter.getText().toString());
            //addToCart( m_deviceId, session.getIboKeyId(), productID, currentQuantity, "plus" );
            if (addCart)
                addToCart(m_deviceId, session.getShoppyUserID(), productID, 1, "plus");
            else
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            //  call

        });

        btnAddItem.setOnClickListener(view -> {
          /*  mCounter++;
            productCount.setText(Integer.toString(mCounter));*/
            addvalue = 1;
            addvalue = Integer.parseInt(tvProductCounter.getText().toString());
            Log.d("addval", "addval " + addvalue);
            int maxQuantity = Math.min(productMaxSaleQuantity, productQuantity);
            if (addvalue < maxQuantity) {
                addvalue++;
                tvProductCounter.setText(Integer.toString(addvalue));
            } else {
                Toast.makeText(ProductDescription.this, "Sorry you have reached max quantity.", Toast.LENGTH_SHORT).show();
            }
        });


        btnRemoveItem.setOnClickListener(view -> {
            int curval = 0;
            curval = Integer.parseInt(tvProductCounter.getText().toString());
            Log.d("curval", "curval " + curval);
            if (curval > 1) {
                curval--;
                tvProductCounter.setText(Integer.toString(curval));
            }
        });
        tvEnterPincode.setOnClickListener(view -> {
            lnAvailability.setVisibility(View.GONE);
            lnPincode.setVisibility(View.VISIBLE);
        });

        imgPincodeBack.setOnClickListener(view -> {
            lnPincode.setVisibility(View.GONE);
            lnAvailability.setVisibility(View.VISIBLE);
        });

        lnHome.setOnClickListener(v -> {
            imgHome.setColorFilter(ContextCompat.getColor(ProductDescription.this, R.color.black));
            tvHome.setTextColor(getResources().getColor(R.color.black));
            Intent login = new Intent(ProductDescription.this, MainActivity.class);

            login.putExtra("SELECTEDINNERHOME", 7);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);

        });

        lnAccount.setOnClickListener(v -> {
            imgMyAccount.setColorFilter(ContextCompat.getColor(ProductDescription.this, R.color.black));
            tvAccount.setTextColor(getResources().getColor(R.color.black));
            Intent login = new Intent(ProductDescription.this, MainActivity.class);
            login.putExtra("SELECTEDINNERACCOUNT", 8);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);

        });

        lnTrackOrder.setOnClickListener(v -> {
            imgTrackOrder.setColorFilter(ContextCompat.getColor(ProductDescription.this, R.color.black));
            tvTrackOrder.setTextColor(getResources().getColor(R.color.black));
            Intent login = new Intent(ProductDescription.this, MainActivity.class);
            login.putExtra("SELECTEDINNERTRACKORDER", 9);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);

        });
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });

        txtRetry.setOnClickListener(v -> {
           /* if (showLocation) {
                lnLocation.setVisibility(View.VISIBLE);
            } else {*/
            lnLocation.setVisibility(View.GONE);
            // }
            //getProject(String.valueOf(ID));
            callAPI();
        });

      /*  tvLocation.setOnClickListener(view -> {
            ProductDecsDetailsDialogFragment addPhotoBottomDialogFragment =
                    ProductDecsDetailsDialogFragment.newInstance();

            addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                    ProductDecsDetailsDialogFragment.TAG);

        });*/
        //  mSwipeRefreshLayout.setEnabled(false);
        // imgshare.setVisibility(session.getRole().equalsIgnoreCase("associate")?View.GONE:View.VISIBLE);
      /*  mSwipeRefreshLayout.setOnRefreshListener(
                this::refreshContent
        );*/
    }

    private void initView() {
        if (showReviews) {
            layReadMore.setVisibility(View.VISIBLE);
        } else {
            layReadMore.setVisibility(View.GONE);
        }

        if (productQuantity == 0) {
            btnAddToCart.setVisibility(View.GONE);
            outOfStock();
        } else {
            btnAddToCart.setVisibility(View.VISIBLE);
        }

        //   rlBottom.setVisibility(session.getLogin() ? View.VISIBLE : View.GONE);

       /* if (addressNameSave == null || addressNameSave.equals("")) {
            Log.d("Location Fill", "Location Fill " + addressNameSave);
        } else {
            Log.d("Location Empty", "Location Empty " + addressNameSave);
            if (isAddressType) {
                tvLocation.setText("Pickup from " + addressNameSave);
            } else {
                tvLocation.setText("Deliver to " + addressNameSave);
            }
            Log.d("Update Value", "Update Value" + isAddressType);
        }*/
        //if (showSimilar)
        initSimilarProductsList();

        //if (!showLocation) {
        lnLocation.setVisibility(View.GONE);
        // }
        // if (showVariants) {
        rvVariants.setVisibility(View.VISIBLE);
       /* } else {
            rvVariants.setVisibility(View.GONE);
        }*/
    }

    private void initUI() {
        //No Internet
        llEmpty = findViewById(R.id.llEmpty);
        imgError = findViewById(R.id.imgError);
        txtErrorTitle = findViewById(R.id.txtErrorTitle);
        txtErrorContent = findViewById(R.id.txt_error_content);
        txtRetry = findViewById(R.id.txtRetry);
        //expandableListView = findViewById(R.id.expandable_sku);

        //lnvariants = findViewById(R.id.ln_variants);
        /*lnsize = findViewById(R.id.ln_size);
        lnweight = findViewById(R.id.ln_weight);*/

        //getting the toolbar
        toolbar = findViewById(R.id.toolbar_search);
        imgBackArrow = findViewById(R.id.img_back);
        imgFav = findViewById(R.id.img_my_fav);
        imgMyCart = findViewById(R.id.img_my_cart);
        imgPincodeBack = findViewById(R.id.img_pincode_back);
        imgSearchClose = findViewById(R.id.img_search_close);
        layProductRate = findViewById(R.id.lay_product_rat);
        ratingBar = findViewById(R.id.ratingBar);
        imgshare = findViewById(R.id.img_share);
        imgSearch = toolbar.findViewById(R.id.img_search);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnRemoveItem = findViewById(R.id.btn_remove_item);
        btnBuyNow = findViewById(R.id.btn_buy_now);
        mProgressDialog = (MaterialProgressBar) findViewById(R.id.progresbar);
        tvCartBadge = findViewById(R.id.cart_badge);
        tvProductName = findViewById(R.id.tv_title_product_name);
        tvProductOfferPrice = findViewById(R.id.tv_product_offer_price_value);
        tvProductSaving = findViewById(R.id.tv_product_offer);
        tvProductIBO = findViewById(R.id.tv_product_price_value);
        tvProductMRP= findViewById(R.id.tv_mrp_price_value);
        tvproductMrpTitle = findViewById(R.id.tv_product_price);
        tvProductDecs = findViewById(R.id.tv_product_desc);
        tvProductReturn = findViewById(R.id.tv_product_return);
        tvProductWarranty = findViewById(R.id.tv_product_warranty);
        tvProductCounter = findViewById(R.id.tv_product_count);
        tvProductShortDecs = findViewById(R.id.tv_title_product_description);
        tvPvStatus = findViewById(R.id.tv_pv_status);
        lnProductDecs = findViewById(R.id.ln_product_decs);
        layNoReviews = findViewById(R.id.lay_no_reviews);
        layratings = findViewById(R.id.lay_rating);
        rlBottom = findViewById(R.id.rl_bottom);

        btnRate = findViewById(R.id.button_rate);
        layReadMore = findViewById(R.id.lay_ebc_readmore);
        //  mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        tvPV = findViewById(R.id.tv_pv_value);
        tvNV = findViewById(R.id.tv_bv_value);
        tvBV = findViewById(R.id.tv_nv_value);
        tvSkuValue = findViewById(R.id.tv_sku_value);
        txtRatings = findViewById(R.id.tv_ratings);
        txtReviewPerson = findViewById(R.id.tv_review_person);

        rvEBC = findViewById(R.id.rv_ebc);
        rvEBC.setNestedScrollingEnabled(false);
        //tvProductMRP.setVisibility(View.VISIBLE);
        tvProductIBO.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvProductMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        tvProductOfferPrice.setVisibility(View.VISIBLE);

        rvVariants = findViewById(R.id.recycler_view_variants);
      /*  rvSizes = findViewById(R.id.recycler_view_size);
        rvWeight = findViewById(R.id.recycler_view_weight);*/
        rvReviews = findViewById(R.id.rv_reviews);
        lnHome = findViewById(R.id.ln_home);
        lnTrackOrder = findViewById(R.id.ln_myorder);
        lnLocation = findViewById(R.id.ln_location);
        lnAccount = findViewById(R.id.ln_my_account);
        imgMyAccount = findViewById(R.id.img_my_account);
        imgHome = findViewById(R.id.img_home);
        imgTrackOrder = findViewById(R.id.img_order);
        tvHome = findViewById(R.id.tv_home);
        tvAccount = findViewById(R.id.tv_my_account);
        tvTrackOrder = findViewById(R.id.tv_my_order);

        rlBannerEcom = findViewById(R.id.rl_banner_ecom);
        mViewPagerEcom = findViewById(R.id.viewpager_ecom);
        mCircleIndicatorEcom = findViewById(R.id.indicator_ecom);

        rvAddress = findViewById(R.id.rv_address_list);

        tvLocation = findViewById(R.id.tv_location);
        tvDetectLocation = findViewById(R.id.tv_detect_location);
        tvEnterPincode = findViewById(R.id.tv_enter_pincode);
        lnPincode = findViewById(R.id.ln_pincode);
        lnAvailability = findViewById(R.id.ln_availability);
        svProductDesc = findViewById(R.id.sv_product_desc);
        rlSearchView = toolbar.findViewById(R.id.rl_search_view);
        tvProductCounter.setText("1");
        mDemoSliderProductDecs = findViewById(R.id.slider_product_decs);
        mDemoSliderProductDecs.stopAutoCycle();
        editSearch = findViewById(R.id.edt_search_search);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText("Product Description");
        tvProductSaving.setText(productSaving);
        setSupportActionBar(toolbar);

        bottomSheet = findViewById(R.id.bottom_sheet);
        rvAddress.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvAddress.setLayoutManager(mLayoutManager);

    }

    private void initData() {
        utilsVersion.checkVersion(this);

        Log.d("MDEVICEID uniqueID", "MDEVICEID uniqueID: " + uniqueID);
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
        Log.d("MDEVICEID", "MDEVICEID: " + m_deviceId);

        mAPIInterface = APIClient.getClient(ProductDescription.this).create(APIInterface.class);
        mAPIInterfaceShoppy = APIClient.getShoppyClient(ProductDescription.this).create(APIInterface.class);

        productDescriptionData = getIntent();
        if (productDescriptionData != null) {
            ID = productDescriptionData.getIntExtra("ebc_id", 0);
            productID = productDescriptionData.getIntExtra("product_id", 0);
            categoryid = productDescriptionData.getIntExtra("catid", 0);
            Log.d("product==", ID + " : " + productID);
            // productClickCheck = productDescriptionData.getIntExtra( "product_clicked_check", 0 );
            productName = productDescriptionData.getStringExtra("product_name");
            productShortDecs = productDescriptionData.getStringExtra("product_short_dec");
            //productOfferPrice = productDescriptionData.getStringExtra("product_offer_price");
            //productMRP = productDescriptionData.getStringExtra("product_mrp_price");
            productDecs = productDescriptionData.getStringExtra("product_desc");
            productSaving = productDescriptionData.getStringExtra("product_saving");
            productClick = productDescriptionData.getStringExtra("product_clicked");
            productWarranty = productDescriptionData.getStringExtra("product_return");
            productReturn = productDescriptionData.getStringExtra("product_warranty");
            productQuantity = productDescriptionData.getIntExtra("product_quantity", 0);
            productMaxSaleQuantity = productDescriptionData.getIntExtra("product_MaxSaleQuantity", 0);
            productSKU = productDescriptionData.getStringExtra("product_SKU");
            boolean fromNotification = productDescriptionData.getBooleanExtra("notification", false);
            if (fromNotification) {
                ecomPickupID = 0;
            } else
                ecomPickupID = productDescriptionData.getIntExtra("ecom_pick_up_id", -1);
            //Log.d( "getProductID", "getProductID " + ID );
            Log.d("getProductID123", "productQuantity " + productQuantity);
            Log.d("getProductID123", "getProductID123 " + ID);
            //Log.d("Data::", "productMRP " + productMRP);
        }

        //if (!showLocation) {
        ecomPickupID = 0;
        //cityId = 0;
        //}
       /* Log.d("bannerID ", "bannerID " + bannerID);
        Log.d("bannerID ", "bannerID " + ID);*/

    }

    private void initPref() {
        session = new Session(this);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/ember.ttf");

        m_deviceId = android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

     /*   SharedPreferences SharedPreferencesLocationName = getSharedPreferences(PrefUtils.prefLocation, 0);
        addressNameSave = SharedPreferencesLocationName.getString(PrefUtils.prefLocation, null);

        SharedPreferences SharedPreferencesUserName = getSharedPreferences(PrefUtils.prefMrp, 0);
        mrpResult = SharedPreferencesUserName.getBoolean(PrefUtils.prefMrp, false);

        spGetIsFirstPurchase = getSharedPreferences(PrefUtils.prefJoindays, 0);
        mrpisJoined30Days = spGetIsFirstPurchase.getBoolean(PrefUtils.prefJoindays, false);

        sharedPreferencesAddressTypeValue = getSharedPreferences(PrefUtils.prefAddresstypevalue, 0);
        addressType = sharedPreferencesAddressTypeValue.getString(PrefUtils.prefAddresstypevalue, null);

        sharedPreferencesFacility = getSharedPreferences(PrefUtils.prefFacility, 0);
        cityFacility = sharedPreferencesFacility.getString(PrefUtils.prefFacility, null);

        citySave = getSharedPreferences(PrefUtils.prefCityid, 0);
        cityId = citySave.getInt(PrefUtils.prefCityid, 0); //0 is the default value
        sharedPreferencesAddressType = getSharedPreferences(PrefUtils.prefAddresstype, 0);
        isAddressType = sharedPreferencesAddressType.getBoolean(PrefUtils.prefAddresstype, false);*/
    }

    private void initVariants(int defselid, int callID) {
        List<Integer> selids = new ArrayList<>();
        selids.add(defselid);
        setdatadefault = true;
        if (loadVariantDynamic) {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                mProgressDialog.setVisibility(View.VISIBLE);
                Call<OtherProducts> wsCallingRegistation = mAPIInterface.getProductVariants(ecomPickupID, callID);
                wsCallingRegistation.enqueue(new Callback<OtherProducts>() {
                    @Override
                    public void onResponse(Call<OtherProducts> call, Response<OtherProducts> response) {
                        if (mProgressDialog != null) {
                            mProgressDialog.setVisibility(View.INVISIBLE);
                        }
                        if (response.isSuccessful()) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {

                                List<OtherProducts.Datum> datumArrayList = response.body().getData();// setOtherListData();
                                if (datumArrayList.size() > 0) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDescription.this, LinearLayoutManager.VERTICAL, false);
                                    rvVariants.setLayoutManager(layoutManager);
                                    productVariantsAdapter = new ProductVariantsAdapter(ProductDescription.this, datumArrayList, selids, rvVariants, ProductDescription.this);
                                    rvVariants.setAdapter(productVariantsAdapter);
                                    rvVariants.setHasFixedSize(true);
                                    rvVariants.setVisibility(View.VISIBLE);
                                } else {
                                    rvVariants.setVisibility(View.GONE);
                                }
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR) {
                                rvVariants.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<OtherProducts> call, Throwable t) {
                        if (mProgressDialog != null) {
                            mProgressDialog.setVisibility(View.INVISIBLE);
                        }
                        rvVariants.setVisibility(View.GONE);
                    }
                });

            } else {
                noInternetAvailable();
                rvVariants.setVisibility(View.GONE);
            }
        } else {
           /* List<OtherProducts.Datum> datumArrayList = setOtherListData();
            LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDescription.this, LinearLayoutManager.VERTICAL, false);
            rvVariants.setLayoutManager(layoutManager);
            productVariantsAdapter = new ProductVariantsAdapter(ProductDescription.this, datumArrayList, selids, rvVariants, this);
            rvVariants.setAdapter(productVariantsAdapter);
            rvVariants.setHasFixedSize(true);
            rvVariants.setVisibility(View.VISIBLE);*/
        }
    }


    private void callAPI() {
        Log.d("callapi==", productID + " : " + ID + " : " + ecomPickupID);
        Log.d("callapi==", "200 : 49");
        //getMyCartListTotalCountResume(m_deviceId, session.getShoppyUserID());
        //getMyCartListPV(m_deviceId, session.getShoppyUserID());
        getMyCartListTotalCount(m_deviceId, session.getShoppyUserID());
        getSimilarProduct(); // // call while product change
        getProductDetails(ID, true);

    }

    private void getProductDetails(int pid, boolean callVariant) {
        Log.d("Others", "getProductDetails call. " + pid);

        getEBCDescription(String.valueOf(pid), String.valueOf(ecomPickupID), callVariant);

        if (showReviews) {
            callReviews(pid, m_deviceId, session.getIboKeyId());
        } else {
            ratingBar.setRating(Float.parseFloat(defRating));
        }
    }

    private void initSimilarProductsList() {
        rvSimilarproducts = findViewById(R.id.rv_similar_products);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvSimilarproducts.setLayoutManager(layoutManager);
        rvSimilarproducts.setHasFixedSize(true);
    }

    private void getSimilarProduct() {

        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mProgressDialog.setVisibility(View.VISIBLE);
            Call<NewCategoryProductDetails> wsCallingRegistation = mAPIInterface.getCategory(categoryid, ecomPickupID, 1, 50);
            wsCallingRegistation.enqueue(new Callback<NewCategoryProductDetails>() {
                @Override
                public void onResponse(Call<NewCategoryProductDetails> call, Response<NewCategoryProductDetails> response) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.INVISIBLE);
                    }
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                            List<SubCategoryProductList> results = response.body().getData().getData();
                            similarproducts.clear();
                            Log.d("product12", " 0 - " + results.get(0).getCategoryName());
                            Log.d("resonseall", "" + response.body().getData().getData());
                            for (int i = 0; i < results.size(); i++) {
                                int catId = results.get(i).getCategoryProductId();
                                if (productID != catId)
                                    similarproducts.add(results.get(i));
                                Log.d("product", i + "  - " + catId);
                            }
                            similarProductAdapter = new SimilarProductAdapter(ProductDescription.this, similarproducts);
                            rvSimilarproducts.setAdapter(similarProductAdapter);
                        } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR) {
                        }
                    }
                }

                @Override
                public void onFailure(Call<NewCategoryProductDetails> call, Throwable t) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.INVISIBLE);
                    }
                }
            });
        } else {
            noInternetAvailable();
        }
    }

    private void initProgress() {
        mLoadProgressDialog = new ProgressDialog(ProductDescription.this, ProgressDialog.THEME_HOLO_LIGHT);
        mLoadProgressDialog.setCancelable(false);
        mLoadProgressDialog.setMessage("Loading...");
    }

    private void refreshContent() {
        //  mSwipeRefreshLayout.setRefreshing(false);
       /* if (Utils.isNetworkAvailable(getApplicationContext())) {
            getMyCartListTotalCount(m_deviceId, session.getIboKeyId());
        }*/
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * Method to display the location on UI
     */
    private void getLocation() {
      /*  if (isPermissionGranted) {
        }*/
    }

    public Address getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getAddress() {

        Address locationAddress = getAddress(latitude, longitude);

        if (locationAddress != null) {

            String city = locationAddress.getLocality();

            String postalCode = locationAddress.getPostalCode();

            String currentLocation;

            if (!TextUtils.isEmpty(city)) {
                currentLocation = city;

                if (!TextUtils.isEmpty(city)) {

                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += " - " + postalCode;
                } else {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += "\n" + postalCode;
                }

                tvLocation.setText("Deliver to " + currentLocation);
            }
        }
    }

    public boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this, resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == utils.productShare) {
            callAPI();
        }
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;
                    default:
                        break;
                }
                break;
            case REVIEW_UPDATE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        callReviews(productID, m_deviceId, session.getIboKeyId());
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        utils.checkExpireUser(mAPIInterface, this, session);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // redirects to utils
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
/*

    @Override
    public void onItemClick(String item) {
    }
*/

    private void addToCart(String deviceId, String userId, Integer
            productId, Integer quantity, String CartFlag) {
        Log.d("cal====", "addtocart");
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mProgressDialog.setVisibility(View.VISIBLE);
            Call<AddToCartModel> wsCallingRegistation = mAPIInterfaceShoppy.getAddToCartModelCall(deviceId, userId, productId, quantity, CartFlag, false);
            wsCallingRegistation.enqueue(new Callback<AddToCartModel>() {
                @Override
                public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.INVISIBLE);
                    }

                    if (response.isSuccessful()) {
                        if (response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                            Log.e("Add To Cart", "Add To Cart: " + new Gson().toJson(response.body()));
                            //Toast.makeText(ProductDescription.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(ProductDescription.this, "Added to cart", Toast.LENGTH_SHORT).show();
                            //confirmDailogue();
                            getMyCartListTotalCount(m_deviceId, session.getShoppyUserID());
                        }
                    } else {
                        utils.showErrorDialog(ProductDescription.this, true, "Session Timeout", "Your Session has been expired. Please Login again.", true, session);

                    }
                }

                @Override
                public void onFailure(Call<AddToCartModel> call, Throwable t) {
                    // mProgressDialog.dismiss();
                    Log.e("Add To Cart", "Add To Cart: " + t);
                    mProgressDialog.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            //noInternetAvailable();
            utils.dialogueNoInternet(this);
        }
    }

    private void getBannersEcomList(Integer productid) {
        Log.d("cal====", "getBannersEcomList");
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Call<BanersEcom> wsCallingBanersList = mAPIInterface.getBanersListEcom(productid);
            Log.d("OnresponseStart", "OnresponseStart: ");
            wsCallingBanersList.enqueue(new Callback<BanersEcom>() {
                @Override
                public void onResponse(@NotNull Call<BanersEcom> call, @NotNull Response<BanersEcom> response) {
                    Log.d("OnresponseIF", "OnresponseIF: " + response);
                    if (response.isSuccessful()) {
                        banersListEcom.clear();
                        if (response.code() == 200) {
                            Log.d("Onresponse", "Onresponse: " + response);
                           /* if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                            } else */if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                                banersListEcom.addAll(response.body().getData());
                                adapters = new MockPagerBannerEcomAdapter(ProductDescription.this, banersListEcom);
                                mViewPagerEcom.setAdapter(adapters);
                                //mViewPagerEcom.setAutoScrollTime(6000);
                                mViewPagerEcom.stopAutoScroll();
                                mCircleIndicatorEcom.setViewPager(mViewPagerEcom);
                                android.util.Log.e("EBC DEC", "EBC DEC: getBanersListEcom " + new Gson().toJson(response.body()));
                                //getEBCDescription(String.valueOf(ID));
                            } /*else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            }*/
                        }

                    } else {
                        Log.d(TAG, "onResponse: " + response);
                    }
                }

                @Override
                public void onFailure(Call<BanersEcom> call, Throwable t) {
                    Log.d("OnresponseFail", "OnresponseFail: " + t);
                }
            });
        } else {
            noInternetAvailable();
        }
    }

    private void getMyCartListTotalCount(String deviceId, String userID) {
        Log.d("cal====", "getMyCartListTotalCount");
//getMyCartTotalCountListEcom
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            // tvCartBadge.setVisibility( View.GONE );
            Call<CartListTotalCountModelEcom> wsCallingEvents = mAPIInterfaceShoppy.getMyCartTotalCountListEcom(deviceId, userID);
            wsCallingEvents.enqueue(new Callback<CartListTotalCountModelEcom>() {
                @Override
                public void onResponse(Call<CartListTotalCountModelEcom> call, Response<CartListTotalCountModelEcom> response) {
                    myTotalCountCartData.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("CartAPI", "CartAPI: " + response);
                            if (response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                // tvCartBadge.setVisibility( View.VISIBLE );
                                tvCartBadge.setText("0");
                            } else if (response.body().getResponse().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                // tvCartBadge.setVisibility( View.VISIBLE );
                                Log.d("CartAPIIn", "CartAPIIn: " + response.body().getResponse().getData());
                                String count = String.valueOf(response.body().getResponse().getData().getSumOfQty());
                                tvCartBadge.setText(count);
                                //final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ProductDescription.this);
                                //getMyCartListPV(m_deviceId, session.getShoppyUserID());
                                android.util.Log.e("EBC DEC", "EBC DEC: getMyCartTotalCountListEcom " + new Gson().toJson(response.body()));
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListTotalCountModelEcom> call, Throwable t) {
                    Log.d("CartAPI", "CartAPI: " + t);
                }
            });
        } else {
            noInternetAvailable();
        }
    }

    private void getMyCartListTotalCountResume(String deviceId, String userID) {
        Log.d("cal====", "getMyCartListTotalCountResume");
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            // tvCartBadge.setVisibility( View.GONE );
            Call<CartListTotalCountModelEcom> wsCallingEvents = mAPIInterfaceShoppy.getMyCartTotalCountListEcom(deviceId, userID);
            wsCallingEvents.enqueue(new Callback<CartListTotalCountModelEcom>() {
                @Override
                public void onResponse(Call<CartListTotalCountModelEcom> call, Response<CartListTotalCountModelEcom> response) {
                    myTotalCountCartData.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("CartAPI", "CartAPI: " + response);
                            if (response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                // tvCartBadge.setVisibility( View.VISIBLE );
                                tvCartBadge.setText("0");
                            } else if (response.body().getResponse().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                // tvCartBadge.setVisibility( View.VISIBLE );
                                Log.d("CartAPIIn", "CartAPIIn: " + response.body().getResponse().getData());
                                String count = String.valueOf(response.body().getResponse().getData().getSumOfQty());
                                tvCartBadge.setText(count);
                                // final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ProductDescription.this);
                                //  getMyCartListPV(m_deviceId, session.getIboKeyId());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListTotalCountModelEcom> call, Throwable t) {
                    Log.d("CartAPI", "CartAPI: " + t);
                }
            });
        } else {
            noInternetAvailable();
        }
    }

    @Override
    public void onBackPressed() {
       /* if (isBottomSheetOpen) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            isBottomSheetOpen = false;
        } else {*/
            Intent intent = new Intent();
            setResult(utils.productShare, intent);
            finish();
            // super.onBackPressed();
            // toast.cancel();

       // }
    }
/*

    public void setSelectedLocation(String addressType, Integer allCityID, String address, boolean isAddressType, String cityFacility, boolean isPincodeServicable) {

        if (isAddressType) {
            tvLocation.setText("Pickup from " + address);
        } else {
            tvLocation.setText("Deliver to " + address);
        }
        Log.d("Update Value", "Update Value" + isAddressType);

        addressNameSave = address;
        allCityID = allCityID;
        addressType = addressType;
        cityFacility = cityFacility;
        SharedPreferences preferences = getSharedPreferences(PrefUtils.prefLocation, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PrefUtils.prefLocation, addressNameSave);
        editor.apply();
        Log.d("Location Empty SAVE", "Location Empty SAVE" + addressNameSave);

        SharedPreferences preferencesAddressType = getSharedPreferences(PrefUtils.prefAddresstype, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorpreferencesAddressType = preferencesAddressType.edit();
        editorpreferencesAddressType.putBoolean(PrefUtils.prefAddresstype, isAddressType);
        editorpreferencesAddressType.apply();

        SharedPreferences preferencesAddressCityID = getSharedPreferences(PrefUtils.prefPrefcityid, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorpreferencesAddressCityID = preferencesAddressCityID.edit();
        editorpreferencesAddressCityID.putInt(PrefUtils.prefPrefcityid, allCityID);
        editorpreferencesAddressCityID.apply();

        Log.d("Location ID", "Location ID" + allCityID);

        SharedPreferences sharedPreferencesAddressType = getSharedPreferences(PrefUtils.prefAddresstypevalue, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorAddressType = sharedPreferencesAddressType.edit();
        editorAddressType.putString(PrefUtils.prefAddresstypevalue, addressType);
        editorAddressType.apply();
        Log.d("Location Type", "Location Type" + addressType);


        SharedPreferences sharedPreferencesFacility = getSharedPreferences(PrefUtils.prefFacility, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorFacility = sharedPreferencesFacility.edit();
        editorFacility.putString(PrefUtils.prefFacility, cityFacility);
        editorFacility.apply();
        Log.d("Facility", "Facility: " + cityFacility);

        onBackPressed();
    }
*/

   /* private void getMyCartListPV(String deviceId, String userId) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Log.d("cal====", "getMyCartListPV");
            Log.d("ProductDescription", "getMyCartListPV: " + userId);
            mProgressDialog.setVisibility(View.VISIBLE);

            Call<CartListModelEcom> wsCallingEvents = mAPIInterfaceShoppy.getMyCartListEcom(deviceId, userId, cityId, session.getTempShoppyid());
            wsCallingEvents.enqueue(new Callback<CartListModelEcom>() {
                @Override
                public void onResponse(Call<CartListModelEcom> call, Response<CartListModelEcom> response) {
                    Log.d("CartResponse", "CartResponse11: " + response.body());

                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.INVISIBLE);
                    }

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("CartAPI", "CartAPI: " + response);
                            Log.d("CartResponse", "CartResponse200: " + response.body());

                            if (response.body().getResponse().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                pvResult = response.body().getResponse().getData().getTotalPV();
                                Log.d("CartAPIIn", "CartAPIIn: Success : " + response.body().getResponse().getData().getCart().size() + " : " + pvResult);
                                showData();
                            } else if (response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_FAIL) {
                                Log.d("CartAPIIn", "CartAPIIn: FAIL " + response.body().getResponse().getData().getCart().size());
                                pvResult = response.body().getResponse().getData().getTotalPV();
                                showFailData();
                                android.util.Log.e("CartListAPI", "CartListAPI: " + new Gson().toJson(response.body()));
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListModelEcom> call, Throwable t) {
                    mProgressDialog.setVisibility(View.INVISIBLE);
                    Log.d("CartAPI", "CartAPI: " + t);
                    Log.d("CartResponse", "CartResponseFil: " + t);
                    pvResult = 0;
                    showFailData();
                }
            });
        } else {
            noInternetAvailable();
        }
    }*/

  /*  private void setMrp(String value) {
        Log.d("200 OK", "value: set MRP: " + value);
        if (!TextUtils.isEmpty(value))
            tvProductMRP.setText("₹ " + value);
    }*/

   /* private void showFailData() {
        Log.d("pvResult", "pvResult: showFailData " + pvResult);
        Log.d("Data::", "showFailData  productMRP " + productMRP + " : productOfferPrice : " + productOfferPrice + " mrpResult " + mrpResult);
        if (!session.getLogin()) {
            tvProductOfferPrice.setText("" + productMRP);
            Log.d("Data::", "showData() set 0 productMRP " + productMRP);
            tvproductMrpTitle.setVisibility(View.GONE);
            tvProductMRP.setVisibility(View.GONE);
        } else if (!mrpResult) {
            tvProductMRP.setVisibility(View.VISIBLE);
            tvProductMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvProductOfferPrice.setVisibility(View.VISIBLE);
            Log.d("Data::", "showFailData  tvProductOfferPrice 1 " + productOfferPrice + " :");
            tvProductOfferPrice.setText(productOfferPrice);
            setMrp(productMRP);

        } else if (pvResult >= Const.PVValue && !mrpisJoined30Days) {
            tvProductMRP.setVisibility(View.VISIBLE);
            tvProductMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvproductMrpTitle.setVisibility(View.VISIBLE);
            tvProductOfferPrice.setVisibility(View.VISIBLE);
            Log.d("Data::", "tvProductOfferPrice : 2 " + productOfferPrice);
            tvProductOfferPrice.setText(productOfferPrice);
            setMrp(productMRP);
        } else {
            Log.d("Data::", "productMRP : " + productMRP);
            tvProductOfferPrice.setText("" + productMRP);
            tvproductMrpTitle.setVisibility(View.GONE);
            tvProductMRP.setVisibility(View.GONE);
        }

        if (pvResult < Const.PVValue && mrpResult) {
            tvPvStatus.setVisibility(View.VISIBLE);
            tvPvStatus.setText("PV value in your cart: " + pvResult + ". Add items worth " + Const.PVValue + " PV to buy the items at discounted Nebula IBO Price.");
        } else if (pvResult == 0) {
            tvPvStatus.setVisibility(View.GONE);
            try {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) svProductDesc.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 100);
                svProductDesc.setLayoutParams(layoutParams);
            } catch (Exception e) {
            }

        } else {
            tvPvStatus.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) svProductDesc.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 100);
            svProductDesc.setLayoutParams(layoutParams);
        }
    }*/

    private void showData() {
       // Log.d("Data::", "showData() : mrpResult " + mrpResult + " : productOfferPrice : " + productOfferPrice + " pvResult " + pvResult + " : mrpisJoined30Days " + mrpisJoined30Days);
      /*  if (!session.getLogin()) {
            tvProductOfferPrice.setText("" + productMRP);
            Log.d("Data::", "showData() set 0 productMRP " + productMRP);
            tvproductMrpTitle.setVisibility(View.GONE);
            tvProductMRP.setVisibility(View.GONE);
        } else if (!mrpResult) {
            tvProductMRP.setVisibility(View.VISIBLE);
            tvProductMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvProductOfferPrice.setVisibility(View.VISIBLE);
            tvProductOfferPrice.setText(productOfferPrice);
            Log.d("Data::", "showData() set 1 productOfferPrice " + productOfferPrice);
            setMrp(productMRP);
        } else if (pvResult >= Const.PVValue && !mrpisJoined30Days) {
            tvProductMRP.setVisibility(View.VISIBLE);
            tvProductMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvproductMrpTitle.setVisibility(View.VISIBLE);
            tvProductOfferPrice.setVisibility(View.VISIBLE);
            Log.d("Data::", "showData() productMRP " + productMRP);
            tvProductOfferPrice.setText(productOfferPrice);
            Log.d("Data::", "showData() set 2 productOfferPrice " + productOfferPrice);
            setMrp(productMRP);
        } else {
            tvProductOfferPrice.setText("" + productMRP);
            Log.d("Data::", "showData() set 3 productMRP " + productMRP);
            tvproductMrpTitle.setVisibility(View.GONE);
            tvProductMRP.setVisibility(View.GONE);
        }*/


       /* tvProductOfferPrice.setText(String.valueOf(productHomestore));
        tvProductMRP.setText(productMRP);
       // tvDiscountPrice.setText(String.valueOf(product.getCategoryMRP()));

        tvProductMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);*/
        //tvDiscountPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
/*
        try {
            Log.e("pvResult==", "pvResult " + pvResult + " mrpResult " + mrpResult + " Const.PVValue " + Const.PVValue);
            if (pvResult < Const.PVValue && mrpResult) {
                tvPvStatus.setVisibility(View.VISIBLE);
                tvPvStatus.setText("PV value in your cart: " + pvResult + ". Add items worth " + Const.PVValue + " PV to buy the items at discounted Nebula IBO Price.");

            } else if (pvResult == 0) {
                tvPvStatus.setVisibility(View.GONE);

            } else {
                tvPvStatus.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.e("pvResult==", "ERROR == " + e.getMessage());
            e.printStackTrace();
        }*/
    }

    private void getEBCDescription(String idEBC, String pickupidEBC, boolean callVariant) {

        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mProgressDialog.setVisibility(View.VISIBLE);
            Call<EBCDescriptionModel> wsCallingRegistation = mAPIInterface.getEBCDetailsWithUCQuantityV2(idEBC, pickupidEBC);
            wsCallingRegistation.enqueue(new Callback<EBCDescriptionModel>() {
                @Override
                public void onResponse(Call<EBCDescriptionModel> call, Response<EBCDescriptionModel> response) {

                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.INVISIBLE);
                    }
                    if (response.isSuccessful()) {
                        llEmpty.setVisibility(View.GONE);
                        if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {

                            if (response.body().getData() != null) {
                                ECBDescriptionDestails ecbdata = response.body().getData();
                                tvProductDecs.setText(HtmlCompat.fromHtml(ecbdata.getEbcDescription(), 0));
                                productName = ecbdata.getEbcName();
                                tvProductName.setText(productName);
                                tvProductShortDecs.setText(ecbdata.getEbcShortDescription());
                                tvPV.setText("" + ecbdata.getEbcPV());
                                tvNV.setText("" + ecbdata.getEbcBV());
                                tvBV.setText("" + ecbdata.getEbcNV());
                                tvSkuValue.setText("" + ecbdata.getEbcSKU());
                                Log.e("EBC DEC", "EBC DEC: UCQuantity " + new Gson().toJson(response.body()));
                                String productOfferPrice = "" + ecbdata.getEbcSalePrice();
                                String productMRP = "" + ecbdata.getEbcMRP();
                               String productHomestore = ""+ecbdata.getHomeStorePrice();

                                lnProductDecs.setVisibility(View.VISIBLE);
                                utils.setShare(imgshare, getApplicationContext(), false, ecbdata.getShowShareLink());
                                int ecomQuantityNew = ecbdata.getEbcQuantity();
                                Log.e("ecomQuantityNew", "ecomQuantityNew: " + ecomQuantityNew);
                                if (ecomQuantityNew == 0) {
                                    btnAddToCart.setVisibility(View.GONE);
                                    outOfStock();
                                } else {
                                    btnAddToCart.setVisibility(View.VISIBLE);
                                    addCart = true;
                                }
                                tvProductOfferPrice.setText(productHomestore);
                                tvProductIBO.setText("₹"+productOfferPrice);
                                tvProductMRP.setText("₹"+productMRP);

                                getProductData(ecbdata, callVariant);
                                getProject(String.valueOf(ID));
                            }
                        } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR) {
                        }
                    }
                }

                @Override
                public void onFailure(Call<EBCDescriptionModel> call, Throwable t) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.INVISIBLE);
                    }
                    Log.e("PaymentAPIError", "PaymentAPIError: " + t);
                }
            });
        } else {
            noInternetAvailable();
        }
    }

    private void getProductData(ECBDescriptionDestails ecbdata, boolean callVariant) {
        ID = ecbdata.getEbcID();
        productID = ecbdata.getEbcProductId();
        categoryid = ecbdata.getEbcCategoryId();

        if (callVariant) { // && showVariants
            try {
                highlightID = ecbdata.getHighlightsIds();
                initVariants(highlightID, ecbdata.getMainProduct() ? ID : ecbdata.getMasterProductId());
            } catch (Exception e) {
                e.printStackTrace();
                rvVariants.setVisibility(View.GONE);
            }
        }
        getBannersEcomList(productID);// call while product change

    }

    private void callReviews(int productID, String m_deviceId, String iboKeyId) {
        Log.d("cal====", "callReviews == " + productID + " : " + m_deviceId + " : " + iboKeyId);
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mProgressDialog.setVisibility(View.VISIBLE);
            Call<ReviewsModel> reviewsModelCall = mAPIInterface.getReviews(iboKeyId, String.valueOf(productID));
            reviewsModelCall.enqueue(new Callback<ReviewsModel>() {
                @Override
                public void onResponse(Call<ReviewsModel> call, Response<ReviewsModel> response) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.INVISIBLE);
                    }
                    if (response.isSuccessful()) {
                        llEmpty.setVisibility(View.GONE);
                        if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                            ReviewsModel.Data reviewsModel = response.body().getData();
                            if (reviewsModel != null) {
                                List<ReviewsModel.Reviews> reviewsListModel = reviewsModel.getReviewsList();
                                layratings.setVisibility(View.VISIBLE);
                                double avgRate = reviewsModel.getAverageRating();
                                avgRating = String.valueOf(avgRate);
                                txtRatings.setText(avgRating);
                                if (avgRating.equals("0.0")) {
                                    ratingBar.setRating(Float.parseFloat(defRating));
                                } else {
                                    ratingBar.setRating(Float.parseFloat(avgRating));
                                }
                                Log.d("ratval", "" + avgRating);
                                ableToReview = reviewsModel.getUserAbleToReview();
                                if (reviewsListModel.size() > 0) {
                                    layNoReviews.setVisibility(View.GONE);
                                    txtReviewPerson.setVisibility(View.VISIBLE);
                                    txtReviewPerson.setText(reviewsModel.getTotalReviews() + " ratings");
                                    mpProductReviewAdapter = new ProductReviewAdapter(getApplicationContext(), reviewsListModel);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ProductDescription.this, LinearLayoutManager.VERTICAL, false);
                                    rvReviews.setLayoutManager(mLayoutManager);
                                    rvReviews.setItemAnimator(new DefaultItemAnimator());
                                    rvReviews.setHasFixedSize(true);
                                    rvReviews.setAdapter(mpProductReviewAdapter);
                                } else
                                    noReviewsShow(reviewsModel.getUserAbleToReview());
                            } else
                                noReviewsShow(false);
                        } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR) {

                            noReviewsShow(false);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ReviewsModel> call, Throwable t) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.INVISIBLE);
                    }
                    android.util.Log.e("PaymentAPIError", "PaymentAPIError: " + t);
                    noReviewsShow(false);
                }
            });

        } else {
            noInternetAvailable();
        }
    }

    private void noReviewsShow(boolean isRate) {
        layNoReviews.setVisibility(View.VISIBLE);
        layratings.setVisibility(View.GONE);
        txtReviewPerson.setVisibility(View.GONE);
    }

   /* private void getEBCDescription(String idEBC) {
        Log.d("cal====", "getEBCDescription == " + idEBC);
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mProgressDialog.setVisibility(View.VISIBLE);
            Call<EBCDescriptionModel> wsCallingRegistation = mAPIInterface.getEBCDetails(idEBC);
            wsCallingRegistation.enqueue(new Callback<EBCDescriptionModel>() {
                @Override
                public void onResponse(Call<EBCDescriptionModel> call, Response<EBCDescriptionModel> response) {

                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.INVISIBLE);
                    }
                    if (response.isSuccessful()) {
                        llEmpty.setVisibility(View.GONE);
                        if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {

                            if (response.body().getData() != null) {
                                ECBDescriptionDestails ecbdata = response.body().getData();
                                lnProductDecs.setVisibility(View.VISIBLE);
                                tvProductDecs.setText(HtmlCompat.fromHtml(ecbdata.getEbcDescription(), 0));
                                tvProductName.setText(ecbdata.getEbcName());
                                tvProductShortDecs.setText(ecbdata.getEbcShortDescription());
                                productOfferPrice = "" + ecbdata.getEbcSalePrice();
                                productMRP = "" + ecbdata.getEbcMRP();
                                // utils.setShare(imgshare, getApplicationContext(), mrpResult, ecbdata.getShowShareLink());

                                //   imgshare.setVisibility(ecbdata.getShowShareLink() ? View.VISIBLE : View.GONE);
                                Log.d("Data::", "tvProductOfferPrice : getEBCDescription " + ecbdata.getEbcSalePrice() + " share link " + ecbdata.getShowShareLink());
                                //  tvProductOfferPrice.setText("" + response.body().getData().getEbcSalePrice());
                                setMrp(productMRP);
                                //if (session.getLogin()) {
                                tvPV.setText("" + ecbdata.getEbcPV());
                                tvNV.setText("" + ecbdata.getEbcBV());
                                tvBV.setText("" + ecbdata.getEbcNV());
                                *//*} else {
                                    tvPV.setText("0");
                                    tvNV.setText("0");
                                    tvBV.setText("0%");
                                }*//*
                                tvSkuValue.setText("" + ecbdata.getEbcSKU());
                                android.util.Log.e("EBC DEC", "EBC DEC: getEBCDetails " + new Gson().toJson(response.body()));
                                utils.setShare(imgshare, getApplicationContext(), mrpResult, ecbdata.getShowShareLink());
                                productID = ecbdata.getEbcProductId();
                                categoryid = ecbdata.getEbcCategoryId();
                                ID = ecbdata.getEbcID();
                                getProductData();
                                showData();
                                getProject(String.valueOf(ID));
                            }
                        } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR) {
                        }
                    }
                }

                @Override
                public void onFailure(Call<EBCDescriptionModel> call, Throwable t) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.INVISIBLE);
                    }
                    android.util.Log.e("PaymentAPIError", "PaymentAPIError: " + t);
                }
            });
        } else {
            noInternetAvailable();
        }
    }*/

    private void getProject(String idEBC) {
        Log.d("cal====", "getProject");

        mProgressDialog.setVisibility(View.VISIBLE);
        Call<EBCBannerModel> wsCallingSiteProducts = mAPIInterface.getSiteProductList(idEBC);
        wsCallingSiteProducts.enqueue(new Callback<EBCBannerModel>() {
            @Override
            public void onResponse(Call<EBCBannerModel> call, Response<EBCBannerModel> response) {

                if (mProgressDialog != null) {
                    mProgressDialog.setVisibility(View.INVISIBLE);
                }
                arrayListSiteProducts.clear();

                if (response.isSuccessful()) {

                    if (response.code() == 200) {
                      /*  if (response.body().getStatuscode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                        } else*/ if (response.body().getStatuscode() == REQUEST_STATUS_CODE_SUCCESS) {
                            arrayListSiteProducts.addAll(response.body().getData());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rvEBC.setLayoutManager(mLayoutManager);
                            rvEBC.setItemAnimator(new DefaultItemAnimator());
                            ebcAdapter = new EBCAdapter(ProductDescription.this, arrayListSiteProducts);
                            rvEBC.setAdapter(ebcAdapter);
                            android.util.Log.e("EBC", "EBC: " + new Gson().toJson(response.body()));

                        } /*else if (response.body().getStatuscode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatuscode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                        }*/
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<EBCBannerModel> call, Throwable t) {
                if (mProgressDialog != null) {
                    mProgressDialog.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void notAbleReviewDialog() {
        SweetAlertDialog loading = new SweetAlertDialog(ProductDescription.this, SweetAlertDialog.WARNING_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
            MyTextView textTitle = alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = alertDialog.findViewById(R.id.confirm_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            alertDialog.setCancelable(false);
            textTitle.setText("");
            textContent.setText("Please purchase this product to rate.");
            btnConfirm.setText("OK");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> {
                loading.dismiss();
                //finish();
            });
        });
        loading.show();
    }

    private void initoutOfStock() {
        loadingout = new SweetAlertDialogCart(ProductDescription.this, SweetAlertDialogCart.CUSTOM_IMAGE_TYPE);
        loadingout.setCancelable(true);
        loadingout.setConfirmText("OK");
        loadingout.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            MyTextView textTitle = alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = alertDialog.findViewById(R.id.content_text);
            ImageView imageView = alertDialog.findViewById(R.id.custom_image);
            imageView.setVisibility(View.VISIBLE);
            imageView.setBackground(ContextCompat.getDrawable(ProductDescription.this, R.drawable.ic_sold_out));
            MyButton btnConfirm = alertDialog.findViewById(R.id.confirm_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            alertDialog.setCancelable(false);
            textTitle.setText("This product may not be available at the selected address.");
            textContent.setText("Kindly change your address, to avail this product.");
            btnConfirm.setText("OK");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> loadingout.dismiss());
        });

    }

    private void outOfStock() {
        if (!loadingout.isShowing())
            loadingout.show();
    }

    private void noInternetAvailable() {
        lnProductDecs.setVisibility(View.GONE);
        lnLocation.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setText(R.string.error_content);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
    }

    int highlightID = 0;
    boolean addCart = true;
    String message = "";

    @Override
    public void onSubmit(int selProductID) {
        Log.d("Others", "Product selected. " + selProductID + " Prev. ID: " + highlightID);
        // Toast.makeText(this, "Product selected. " + selProductID + " Prev. ID: " + highlightID, Toast.LENGTH_SHORT).show();
        if (selProductID != highlightID) {
            getProductDetails(selProductID, false);
            highlightID = selProductID;
        } else
            addCart = true;
    }

    public static boolean setdatadefault = true;

    @Override
    public void onsetMessage(String message, boolean showMessage) {
        addCart = false;
        this.message = message;
        Log.d("Others", " onsetMessage " + message);
        if (showMessage) Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
