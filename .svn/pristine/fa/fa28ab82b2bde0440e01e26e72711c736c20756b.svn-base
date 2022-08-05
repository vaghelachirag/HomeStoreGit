package com.nebulacompanies.ibo.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import com.nebulacompanies.ibo.ecom.MainActivity;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.shoppy.R;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;

import pl.droidsonroids.gif.BuildConfig;

import static com.nebulacompanies.ibo.ecom.utils.PrefUtils.prefSkiplogin;

public class StartUp extends Activity {
    private Handler myHandler;
    private Runnable myRunnable;
    Session session;
    ConnectionDetector cd;
    String versionName, versionNameSave;

    // int launch_count;
    SharedPreferences prefs;
    //private static APIInterface mAPIInterface;
    SharedPreferences settings;
    boolean skiplogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        Utils.darkenStatusBar(this, R.color.colorNotification);

        session = new Session(this);
        settings = getSharedPreferences(PrefUtils.prefMyprefsfile, 0);


        prefs = getSharedPreferences(PrefUtils.prefMyappnebula, MODE_PRIVATE);
        cd = new ConnectionDetector(getApplicationContext());

        SharedPreferences SharedPreferencesUserName = getSharedPreferences(PrefUtils.prefVersion, 0);
        versionNameSave = SharedPreferencesUserName.getString(PrefUtils.prefVersion, null);
        skiplogin = settings.getBoolean(prefSkiplogin, false);

        if (versionName == null || versionName.equals("")) {
            if (versionNameSave == null || versionNameSave.equals("")) {
                int versionCode = BuildConfig.VERSION_CODE;
                versionName = BuildConfig.VERSION_NAME;

                SharedPreferences preferences = getSharedPreferences(PrefUtils.prefVersion, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefVersion, versionName);
                editor.apply();
            } else {
                versionName = versionNameSave;
            }
        }

/*

        launch_count = prefs.getInt("launch_count_app", 0);
        if (launch_count < 2) {
            prefs.edit()
                    .putInt("launch_count_app", launch_count + 1)
                    .apply();
        }
*/

        myHandler = new Handler();
        myRunnable = () -> {
            SharedPreferences sharedPrefs = getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.clear();
            editor.apply();

            if (session.getLogin())
                openDashboard();
            else
                openLogin();
        };
        myHandler.postDelayed(myRunnable, 2590);
    }

    private void openLogin() {
        Intent login = new Intent(StartUp.this, LoginActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(login);

        finish();
    }

    private void openDashboard() {
        int shoppy = session.getShoppyid();
        Log.d("shoppy", " id: " + shoppy);
        Intent dash;
        if (shoppy == 0) {
            dash = new Intent(StartUp.this, ChooseShopyActivity.class);
        } else {
            dash = new Intent(StartUp.this, ShoppyHomeScreen.class);
        }
        // dash.putExtra(PrefUtils.prefMyappnebula, launch_count);
        startActivity(dash);
        finish();
    }

    @Override
    protected void onDestroy() {
        myHandler.removeCallbacks(myRunnable);
        super.onDestroy();
    }
}