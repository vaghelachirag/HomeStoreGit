package com.nebulacompanies.ibo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.nebulacompanies.ibo.shoppy.R;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Palak Mehta on 7/12/2016.R
 */
public class Config {

    public static final String M_ID = "xxxxxxxx"; //Paytm Merchand Id we got it in paytm credentials
    /*public static final String CHANNEL_ID = "WEB"; //Paytm Channel Id, got it in paytm credentials
    public static final String INDUSTRY_TYPE_ID = "Retail"; //Paytm industry type got it in paytm credential
    public static final String WEBSITE = "APP_STAGING";*/
    public static final String CALLBACK_URL = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
    public static String FONT_STYLE = "fonts/Montserrat-Regular.ttf";
    // public static String FONT_STYLE_ECOM = "fonts/ember.ttf";
    public static String FONT_STYLE_BOLD = "fonts/Montserrat-Regular.ttf";
    public static String FONT_STYLE_BOLD_LATO = "fonts/Montserrat-Regular.ttf";
    // public static String HOLIDAY = "Holiday";
    public static boolean NOTIFICATIONS = false;
    // public static String EnterPin = "";
    public static String NEB_API = "https://nebulacompanies.net";
    public static boolean isFirstTime = true;
    public static int NOTIFICATION_COUNT = 0;

    /*LIVE URL (RELEASE)*/

//    public static String TESTING_API = "https://nebulacompanies.net";
//    public static String shareMainUrl = "https://shop.nebulacare.in/";
//    public static String TESTING_API_SHOPPY = "https://nebulacare.shop";
//    public static int quantityTshirt = 100;
//    public static int myProductidTshirt = 227;
//    public static int variantidTshirt = 231;

//    /*LOCAL URL (STAGING)*/
    public static String TESTING_API = "http://203.88.139.169:9064";
    public static String shareMainUrl = "http://203.88.139.169:9066/";
    public static String TESTING_API_SHOPPY = "http://203.88.139.169:8086";
    public static int quantityTshirt = 100;
    public static int myProductidTshirt = 227;
    public static int variantidTshirt = 231;


    //public static String TESTING_API = "https://nebuladevelopers.com/";
    //  public static String TESTING_API = "http://151.106.35.35:9080/";
    //  public static String TESTING_API = " http://151.106.35.35:7788/";
    //  public static String TESTING_API = " http://151.106.35.35:7788/";
    //  public static String TESTING_API = "http://107.180.71.37:9090/";

    /*UNICOMMERCE */
   /* public static String UNICOMMERCE_BASE_API = "https://nebula.unicommerce.com/";
    public static String RANK_ID = "";
    public static String RegistationOTP = "";*/
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static String REGISTERED_TOKEN = "";
    public static String CUSTOMER_LOGIN_ID = "";
    public static String OTP = "";
    public static String gender = "";

   /* public static String[] rankValues = {
            "Associate",
            "IBO",
            "Bronze",
            "Silver",
            "Gold",
            "Platinum",
            "Star Platinum",
            "2 Star Platinum",
            "3 Star Platinum",
            "4 Star Platinum",
            "5 Star Platinum",
            "6 Star Platinum",
            "7 Star Platinum",
            "Crown",
            "Noble Crown",
            "Royal Crown",
    };*/


    public static String[] rankValues = {
            "Associate",
            "IBO",
            "Bronze",
            "Silver",
            "Gold",
            "Platinum",
            "Star Platinum",
            "2 Star Platinum",
            "3 Star Platinum",
            "4 Star Platinum",
            "5 Star Platinum",
            "Brand Ambassador",
            "Universal Brand Ambassador"
    };
/*


    public static int[] NewrankImages = {
            R.drawable.share_associate,
            R.drawable.share_ibo,
            R.drawable.share_bronze,
            R.drawable.share_silver,
            R.drawable.share_gold,
            R.drawable.share_platinum,
            R.drawable.share_star_platinum,
            R.drawable.share_two_share,
            R.drawable.share_three_share,
            R.drawable.share_four_share,
            R.drawable.share_five_share,
            R.drawable.share_brand_ambassador,
            R.drawable.share_universal_ambassador,
            */
/*  R.drawable.share_six_share,
              R.drawable.share_seven_share,
              R.drawable.share_crown,*//*

    };
*/


    public static double[] incomeValues = {
            Double.parseDouble("100000"),
            Double.parseDouble("500000"),
            Double.parseDouble("1000000"),
            Double.parseDouble("2500000"),
            Double.parseDouble("5000000"),
            Double.parseDouble("10000000"),
            Double.parseDouble("25000000"),
            Double.parseDouble("50000000"),
            Double.parseDouble("100000000"),
            Double.parseDouble("250000000")
    };

    public static int[] incomeImages = {
            R.drawable.ic_one_lakhs,
            R.drawable.ic_five_lakhss,
            R.drawable.ic_ten_lakhss,
            R.drawable.ic_twentyfive_lakhss,
            R.drawable.ic_fifty_lakhss,
            R.drawable.ic_one_crores,
            R.drawable.ic_twopfive_croress,
            R.drawable.ic_five_croress,
            R.drawable.ic_ten_croress,
            R.drawable.ic_twentyfive_croress
    };

   /* public static int[] incomeRankImages = {
            R.drawable.ic_one_lakhs_rank,
            R.drawable.ic_five_lakhs_rank,
            R.drawable.ic_ten_lakhs_rank,
            R.drawable.ic_twentyfive_lakhs_rank,
            R.drawable.ic_fifty_lakhs_rank,
            R.drawable.ic_one_crores_rank,
            R.drawable.ic_twopfive_crores_rank,
            R.drawable.ic_five_crores_rank,
            R.drawable.ic_ten_crores_rank,
            R.drawable.ic_twentyfive_crores_rank
    };*/

    /*public static String[] incomeValuesRs =
            {"₹1,00,000", "₹5,00,000", "₹10,00,000", " ₹25,00,000", "₹50,00,000", "₹100,00,000", "₹2,50,00,000", "₹5,00,00,000", " ₹10,00,00,000", "₹25,00,00,000"};
*/
    /*public static String[] incomeValues1 =
            {"On reaching", "On reaching", "On reaching", "On reaching", "On reaching", "On reaching", "On reaching", "On reaching", "On reaching", "On reaching"};
*/
    public static NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
