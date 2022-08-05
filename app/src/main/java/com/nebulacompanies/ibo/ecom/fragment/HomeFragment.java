package com.nebulacompanies.ibo.ecom.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.ecom.MainActivity;
import com.nebulacompanies.ibo.ecom.MyCartActivity;
import com.nebulacompanies.ibo.ecom.MyWishListActivity;
import com.nebulacompanies.ibo.ecom.adapter.CategoryAdapter;
import com.nebulacompanies.ibo.ecom.adapter.MockPagerBannerEcomDashBoardAdapter;
import com.nebulacompanies.ibo.ecom.adapter.TodaysDealProductsAdapter;
import com.nebulacompanies.ibo.ecom.customBanner.CirclePageIndicator;
import com.nebulacompanies.ibo.ecom.customBanner.InfiniteViewPager;
import com.nebulacompanies.ibo.ecom.model.AdvertisementImageListEcom;
import com.nebulacompanies.ibo.ecom.model.BannerImages;
import com.nebulacompanies.ibo.ecom.model.BottomBannerModel;
import com.nebulacompanies.ibo.ecom.model.Category;
import com.nebulacompanies.ibo.ecom.model.CategoryListModelEcom;
import com.nebulacompanies.ibo.ecom.model.ImageBannerInfoEcom;
import com.nebulacompanies.ibo.ecom.model.MinimumPvModel;
import com.nebulacompanies.ibo.ecom.model.ProfileModelEcom;
import com.nebulacompanies.ibo.ecom.utils.AdapterCallback;
import com.nebulacompanies.ibo.ecom.utils.CallBackListener;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.shoppy.R;
import com.nebulacompanies.ibo.util.Const;
import com.nebulacompanies.ibo.util.Constants;

import java.util.ArrayList;
import java.util.UUID;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener,
        AdapterCallback {

    RecyclerView  recyclerViewCategory;
    private CategoryAdapter categoryAdapter;
    private OnFragmentInteractionListener mListener;
    ArrayList<Category> categoryArrayList = new ArrayList<>();
    APIInterface mAPIInterface;
    MaterialProgressBar mProgressDialog, progresbar3, progresbar2;
    RelativeLayout rlBannerEcom;
    private ArrayList<ImageBannerInfoEcom> banersListEcom = new ArrayList<>();
    MockPagerBannerEcomDashBoardAdapter adapters;
    InfiniteViewPager mViewPagerEcom;
    CirclePageIndicator mCircleIndicatorEcom;
    public RelativeLayout lnCart, rlSearchView;
    ImageView imgSearch, imgFav, imgCart, imgSearchClose, imgMainBack, imgError;
    MyBoldTextViewEcom txtErrorTitle;
    NebulaEditTextEcom editSearch;
    MyTextViewEcom tvCartBadge, txtErrorContent, txtRetry, tvToolbarTitle;
    String deviceId, uniqueID, userName;
    com.nebulacompanies.ibo.util.Session sessionNormal;
    private CallBackListener callBackListener;
    RelativeLayout rlCardOne, llEmpty;
    LinearLayout lnCard;
    RecyclerView rvTodaysDeals;
    private TodaysDealProductsAdapter todaysDealProductsAdapter;
    private ArrayList<BannerImages> bannerImages = new ArrayList();
    MainActivity mainActivity;

    public HomeFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        sessionNormal = new com.nebulacompanies.ibo.util.Session(getActivity());
        deviceId = android.provider.Settings.Secure.getString(getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        SharedPreferences deviceSharedPreferences = getActivity().getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

        Log.d("MDEVICEID Home uniqueID", "MDEVICEID Home uniqueID: " + uniqueID);
        if (deviceId == null || deviceId.equals("")) {

            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();
                SharedPreferences preferences = getActivity().getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefDeviceid, randomID);
                editor.apply();
                deviceId = randomID;
            } else {
                deviceId = uniqueID;
            }
        }
        Log.d("MDEVICEID Home", "MDEVICEID Home: " + deviceId);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar_dashboard);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        lnCart = toolbar.findViewById(R.id.toolbar_settings);

        imgSearch = toolbar.findViewById(R.id.img_search);
        imgSearch.setVisibility(View.GONE);
        imgFav = toolbar.findViewById(R.id.img_my_fav);
        imgCart = toolbar.findViewById(R.id.img_my_cart);
        imgMainBack = toolbar.findViewById(R.id.img_main_back);
        tvCartBadge = toolbar.findViewById(R.id.cart_badge);

        imgSearchClose = toolbar.findViewById(R.id.img_search_close);
        editSearch = toolbar.findViewById(R.id.editMobileNo);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);

        rlSearchView = toolbar.findViewById(R.id.rl_search_view_main);
        rlSearchView.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        initUI(view);
        callAPI();
    }

    private void initUI(View view) {

        mProgressDialog = view.findViewById(R.id.progresbar);
        progresbar3 = view.findViewById(R.id.progresbar3);
        progresbar2 = view.findViewById(R.id.progresbar2);

        mViewPagerEcom = view.findViewById(R.id.viewpager_ecom);
        mCircleIndicatorEcom = view.findViewById(R.id.indicator_ecom);
        rlBannerEcom = view.findViewById(R.id.rl_banner_ecom);
        rlCardOne = view.findViewById(R.id.rl_card_one);
        lnCard = view.findViewById(R.id.ln_card);


        llEmpty = view.findViewById(R.id.llEmpty);
        txtErrorTitle = view.findViewById(R.id.txtErrorTitle);
        txtErrorContent = view.findViewById(R.id.txt_error_content);
        txtRetry = view.findViewById(R.id.txtRetry);
        imgError = view.findViewById(R.id.imgError);
        recyclerViewCategory = view.findViewById(R.id.recyclerview_category_home);
        userName = sessionNormal.getUserName();
        if (userName == null || userName.equals("")) {
            getMyProfile();
            Log.d("UserName Fill", "UserName Fill " + userName);
        } else {
            Log.d("UserName Empty", "UserName Empty " + userName);
            tvToolbarTitle.setText("Hi, " + userName);
        }

        imgSearchClose.setOnClickListener(view1 -> editSearch.getText().clear());

        imgCart.setOnClickListener(view12 -> {
            Intent login = new Intent(getActivity(), MyCartActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
        });

        imgFav.setOnClickListener(view13 -> {
            Intent login = new Intent(getActivity(), MyWishListActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
        });

        imgMainBack.setOnClickListener(view14 -> {
            try {
                getActivity().onBackPressed();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        rvTodaysDeals = view.findViewById(R.id.rview_todays_deals);
        rvTodaysDeals.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManagerDeal = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvTodaysDeals.setLayoutManager(mLayoutManagerDeal);
        rvTodaysDeals.setItemAnimator(new DefaultItemAnimator());
        txtRetry.setOnClickListener(v -> refreshcontent());
    }

    private void callAPI() {
        getTotalPV();
        doGetAdvertisementImages();
    }

    private void refreshcontent() {
        if (Utils.isNetworkAvailable(getActivity())) {
            mainActivity = (MainActivity) getActivity();
            mainActivity.callAPI();
            callAPI();
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();

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

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void doGetAdvertisementImages() {

        if (Utils.isNetworkAvailable(getContext())) {
            mProgressDialog.setVisibility(View.VISIBLE);
            Call<AdvertisementImageListEcom> wsCallingBanersList = mAPIInterface.getAdvertisementListEcom(false, true);
            Log.d("OnresponseStart", "OnresponseStart: ");
            wsCallingBanersList.enqueue(new Callback<AdvertisementImageListEcom>() {
                @Override
                public void onResponse(Call<AdvertisementImageListEcom> call, Response<AdvertisementImageListEcom> response) {
                    Log.d("OnresponseIF", "OnresponseIF: " + response);
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.VISIBLE);
                    }
                    if (response.isSuccessful()) {
                        banersListEcom.clear();
                        if (response.code() == 200) {
                            Log.d("Onresponse", "Onresponse: " + response);
                            if (response.body().getStatuscode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                banersListEcom.addAll(response.body().getData());
                                visibleLayout();
                                if (getActivity() != null) {
                                    adapters = new MockPagerBannerEcomDashBoardAdapter(getActivity(), banersListEcom);
                                    mViewPagerEcom.setAdapter(adapters);
                                    mViewPagerEcom.setAutoScrollTime(4000);
                                    mViewPagerEcom.startAutoScroll();
                                    mCircleIndicatorEcom.setViewPager(mViewPagerEcom);
                                }
                                getCategoryList();
                            }
                        }

                    } else {
                        Log.d("onResponse", "onResponse: " + response);
                    }
                }

                @Override
                public void onFailure(Call<AdvertisementImageListEcom> call, Throwable t) {
                    Log.d("OnresponseFail", "OnresponseFail: " + t);
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.VISIBLE);
                    }
                }
            });
        } else {
            noInternetAvailable();
        }
    }

    private void getTotalPV() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PrefUtils.prefPv, 0);
        Call<MinimumPvModel> wsCallingPv = mAPIInterface.getMinimumPV();
        wsCallingPv.enqueue(new Callback<MinimumPvModel>() {
            @Override
            public void onResponse(Call<MinimumPvModel> call, Response<MinimumPvModel> response) {
                if (response.isSuccessful() || response.code() == 200) {
                    MinimumPvModel model = response.body();
                    if (model.getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                        sharedPreferences.edit().putInt(PrefUtils.prefMinpv, Integer.parseInt(model.getData())).apply();
                    }
                }
                setPvValue(sharedPreferences);
            }

            @Override
            public void onFailure(Call<MinimumPvModel> call, Throwable t) {
                setPvValue(sharedPreferences);
            }
        });
    }

    private void setPvValue(SharedPreferences sharedPreferences) {
        Const.PVValue = sharedPreferences.getInt(PrefUtils.prefMinpv, Const.DefPVValue);
        Log.d("minpv", " : " + Const.PVValue);
    }

    @Override
    public void onMethodCallbackMain() {
        if (callBackListener != null)
            callBackListener.onCallBack();
    }

    @Override
    public void onPause() {
        super.onPause();
        mProgressDialog.setVisibility(View.GONE);
    }


    private void getMyProfile() {
        Call<ProfileModelEcom> wsCallingEvents = mAPIInterface.getMyProfileEcom();
        wsCallingEvents.enqueue(new Callback<ProfileModelEcom>() {
            @Override
            public void onResponse(Call<ProfileModelEcom> call, Response<ProfileModelEcom> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                       // Log.d("PROFILEAPI", "PROFILEAPI: " + response);
                        if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                           // Log.d("PROFILEAPIResponse", "PROFILEAPIResponse: " + new Gson().toJson(response.body()));
                            String firstName = response.body().getData().getFirstName();
                           // Log.d("firstName", "firstName: " + firstName);
                            userName = "Hi, " + firstName;
                            tvToolbarTitle.setText(userName);
                            sessionNormal.setUserName(firstName);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileModelEcom> call, Throwable t) {
                Log.d("PROFILEAPIINERROR", "PROFILEAPIINERROR: " + t);
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener)
            callBackListener = (CallBackListener) getActivity();
    }


    private void getCategoryList() {
        if (Utils.isNetworkAvailable(getActivity())) {

            mProgressDialog.setVisibility(View.GONE);
            progresbar2.setVisibility(View.VISIBLE);

            Call<CategoryListModelEcom> wsCallingEvents = mAPIInterface.getCategoryListEcom();
            wsCallingEvents.enqueue(new Callback<CategoryListModelEcom>() {
                @Override
                public void onResponse(Call<CategoryListModelEcom> call, Response<CategoryListModelEcom> response) {

                    if (progresbar2 != null) {
                        progresbar2.setVisibility(View.GONE);
                    }

                    categoryArrayList.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                categoryArrayList.addAll(response.body().getData());
                                    recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                                recyclerViewCategory.setItemAnimator(new DefaultItemAnimator());
                                categoryAdapter = new CategoryAdapter(getActivity(), categoryArrayList, HomeFragment.this);
                                recyclerViewCategory.setAdapter(categoryAdapter);
                                getBottomBanner();
                            }
                            if (categoryArrayList.size() > 0) {
                                // llEmpty.setVisibility(View.GONE);
                                recyclerViewCategory.setVisibility(View.VISIBLE);
                            } else {
                                //   llEmpty.setVisibility(View.VISIBLE);
                                //   listView.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        //  serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<CategoryListModelEcom> call, Throwable t) {
                    // mSwipeRefreshLayout.setEnabled(false);
                    Log.e("CategoryListAPIError", "CategoryListAPIError: " + t);
                    if (progresbar2 != null) {
                        progresbar2.setVisibility(View.GONE);
                    }
                    //  mProgressDialog.dismiss();
                    //  serviceUnavailable();
                }
            });
        } else {
            noInternetAvailable();
        }
    }

    private void getBottomBanner() {
        if (Utils.isNetworkAvailable(getActivity())) {
            //Log.e("cityId call INS ", "cityId call INS: " + cityID);
            // Log.e("cityId call IN ","cityId call IN: "+cityId);
            progresbar2.setVisibility(View.GONE);
            progresbar3.setVisibility(View.VISIBLE);
            //  if (!showLocation) {
            String cityID = "0";
            //   }
            Call<BottomBannerModel> wsCallingBanersList = mAPIInterface.getBottomBanner(cityID,
                    false, true);
            Log.d("OnresponseStart", "OnresponseStart: ");
            wsCallingBanersList.enqueue(new Callback<BottomBannerModel>() {
                @Override
                public void onResponse(Call<BottomBannerModel> call, Response<BottomBannerModel> response) {
                    Log.d("OnresponseIF", "OnresponseIF: " + response);
                    if (progresbar3 != null) {
                        progresbar3.setVisibility(View.GONE);
                    }

                    if (response.isSuccessful()) {
                        bannerImages.clear();
                        if (response.code() == 200) {
                           // Log.d("Onresponse", "Onresponse: " + response);
                          /*  if (response.body().getStatuscode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                            } else*/
                            if (response.body().getStatuscode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                bannerImages.addAll(response.body().getData());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                rvTodaysDeals.setLayoutManager(mLayoutManager);
                                rvTodaysDeals.setItemAnimator(new DefaultItemAnimator());
                                todaysDealProductsAdapter = new TodaysDealProductsAdapter(getActivity(), bannerImages, cityID);
                                rvTodaysDeals.setAdapter(todaysDealProductsAdapter);

                            } /*else if (response.body().getStatuscode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatuscode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            }*/
                        }
                    } else {
                        Log.d("onResponse: ", "onResponse: " + response);
                    }
                }

                @Override
                public void onFailure(Call<BottomBannerModel> call, Throwable t) {
                    Log.d("OnresponseFail", "OnresponseFail: " + t);
                    if (progresbar3 != null) {
                        progresbar3.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            noInternetAvailable();
        }
    }

    private void noInternetAvailable() {
        lnCard.setVisibility(View.GONE);
        llEmpty.setVisibility(View.VISIBLE);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setText(R.string.error_content);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        txtRetry.setVisibility(View.VISIBLE);
    }

    private void visibleLayout() {
        lnCard.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.GONE);
    }
}
