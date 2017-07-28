package com.sarps.azhar_sarps.servicereportapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by azhar-sarps on 07-Jun-17.
 */

public class SplashScreen extends AppCompatActivity {
    @BindView(R.id.iv_logo)
    ImageView iv_logo;
    @BindView(R.id.tv_appname)
    TextView tv_appname;
    @BindView(R.id.tv_about)
    TextView tv_about;
    @BindView(R.id.tv_products)
    TextView tv_products;
    @BindView(R.id.btn_start)
    Button btn_start;
    Animation animation, animation2;
    private SharedPrefClass mSharedPrefClass = null;
    public static String TAG = "SERVICE_REPORT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(getResources().getColor(android.R.color.white));
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        mSharedPrefClass = new SharedPrefClass(getApplicationContext());
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.grid_anim);
        iv_logo.startAnimation(animation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_appname.setText(R.string.app_name);
//                tv_about.setText(R.string.about_us);
//                animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
//                tv_appname.startAnimation(animation2);


                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.grid_anim);
                tv_appname.startAnimation(animation);

            }
        }, 500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_about.setText(R.string.about_us);
//                animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
//                tv_appname.startAnimation(animation2);

                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.grid_anim);
                tv_about.startAnimation(animation);


            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_products.setText(R.string.product_item);
//                animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
//                tv_appname.startAnimation(animation2);

                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.grid_anim);
                tv_products.startAnimation(animation);


            }
        }, 1500);


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckNetwork.isInternetAvailable(getApplicationContext())) //returns true if internet available
                {

                    Log.d(TAG, "Get User Role :- " + mSharedPrefClass.getUserRole());

                    if (mSharedPrefClass.getUserRole().equals("customer")) {
                        Intent i = new Intent(getApplicationContext(), MainActivity_Customer.class);
                        startActivity(i);
                        finish();
                    } else if (mSharedPrefClass.getUserRole().equals("dealer")) {
                        Intent i = new Intent(getApplicationContext(), MainActivity_Dealer.class);
                        startActivity(i);
                        finish();
                    } else if (mSharedPrefClass.getUserRole().equals("engineer")) {

                        Intent i = new Intent(getApplicationContext(), MainActivity_Engineers.class);
                        startActivity(i);
                        finish();

                    } else if (mSharedPrefClass.getUserRole().equals("super")) {

                        Intent i = new Intent(getApplicationContext(), MainActivity_Admin.class);
                        startActivity(i);
                        finish();

                    } else {

                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                } else {
                    dialog_network();
                    Toast.makeText(getApplicationContext(), "Please check the internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void dialog_network() {

        Dialog dialog = new Dialog(SplashScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.no_network);
        Button btn = (Button) dialog.findViewById(R.id.btn_tryagain);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SplashScreen.class));
                finish();
            }
        });
        dialog.show();
    }
}
