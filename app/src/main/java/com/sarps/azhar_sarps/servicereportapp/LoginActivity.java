package com.sarps.azhar_sarps.servicereportapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.LoginInterface;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ApproveDeclineSPPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.LoginPojo;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by azhar-sarps on 07-Jun-17.
 */

public class LoginActivity extends AppCompatActivity {
    //    @BindView(R.id.iv_machine)
//    ImageView iv_machine;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.tv_forgetpassword)
    TextView tv_forgetpassword;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindString(R.string.email_error)
    String emailErrorMessage;
    @BindString(R.string.password_error)
    String passwordErrorMessage;
    ImageView iv_close;
    Button btn_submit;
    Call<LoginPojo> call;
    Call<ApproveDeclineSPPojo> call2;
    private SharedPrefClass mSharedPrefClass = null;
    String fcm_token = null;

    private static String TAG = "SERVICE REPORT APP";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mSharedPrefClass = new SharedPrefClass(getApplicationContext());
        Dialog_Userrole();

        tv_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_pasword();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!et_email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                    Toast.makeText(getApplicationContext(), "Please insert correct Email Id", Toast.LENGTH_SHORT).show();
                } else if (et_password.getText().toString().length() < 5) {
                    Toast.makeText(getApplicationContext(), "Please insert your password", Toast.LENGTH_SHORT).show();
                } else {
                    String user_role = mSharedPrefClass.getUserR();
                    fcm_token = FirebaseInstanceId.getInstance().getToken();
                    Log.d(TAG, "Token: " + fcm_token);
                    login(et_email.getText().toString(), et_password.getText().toString(), user_role, fcm_token);
                }
            }
        });


    }

    public void dialog_pasword() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_forget_password);
        iv_close = (ImageView) dialog.findViewById(R.id.iv_close);
        btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
        final EditText et_email = (EditText) dialog.findViewById(R.id.et_email);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                    Toast.makeText(getApplicationContext(), "Please insert correct email id", Toast.LENGTH_SHORT).show();
                } else {
                    forget_password(et_email.getText().toString());
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }


    public void forget_password(String email) {
        System.out.println("email :- " + email);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        LoginInterface request = retrofit.create(LoginInterface.class);
        call2 = request.getJson(email);
        call2.enqueue(new Callback<ApproveDeclineSPPojo>() {
                          @Override
                          public void onResponse(Call<ApproveDeclineSPPojo> call, Response<ApproveDeclineSPPojo> response) {
                              ApproveDeclineSPPojo loginPojo = response.body();

                              if (loginPojo.getMessage().equals("Success")) {
                                  Toast.makeText(getApplicationContext(), "Mail send successfully. Please check your email. ", Toast.LENGTH_SHORT).show();
                              }

                          }

                          @Override
                          public void onFailure(Call<ApproveDeclineSPPojo> call, Throwable t) {
                              Toast.makeText(getApplicationContext(), "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                          }
                      }

        );
    }

    public void login(String email, String password, String user_role, String token) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        LoginInterface request = retrofit.create(LoginInterface.class);
        call = request.getJson(email, password, user_role, token);
        call.enqueue(new Callback<LoginPojo>() {
                         @Override
                         public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                             LoginPojo loginPojo = response.body();
                             String user_r = loginPojo.getUserRole();
                             String Id = loginPojo.getId();
                             String dealerID = loginPojo.getDealerID();
                             mSharedPrefClass.setUserId(Id);
                             mSharedPrefClass.setUserRole(user_r);
                             mSharedPrefClass.setDealerID(dealerID);
                             if (loginPojo.getMessage().equals("success")) {
                                 Toast.makeText(getApplicationContext(), "You are login successfully", Toast.LENGTH_SHORT).show();

                                 System.out.println("user_r :- "+user_r);
                                 if (user_r.equals("customer")) {
                                     Intent i = new Intent(getApplicationContext(), MainActivity_Customer.class);
                                     startActivity(i);
                                     finish();
                                 } else if (user_r.equals("dealer")) {
                                     Intent i = new Intent(getApplicationContext(), MainActivity_Dealer.class);
                                     startActivity(i);
                                     finish();
                                 } else if (user_r.equals("engineer")) {
                                     Intent i = new Intent(getApplicationContext(), MainActivity_Engineers.class);
                                     startActivity(i);
                                     finish();
                                 } else if (user_r.equals("super")) {
                                     Intent i = new Intent(getApplicationContext(), MainActivity_Admin.class);
                                     startActivity(i);
                                     finish();
                                 }
                             }

                         }

                         @Override
                         public void onFailure(Call<LoginPojo> call, Throwable t) {
                             Toast.makeText(getApplicationContext(), "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                         }
                     }

        );
    }

    public void Dialog_Userrole() {

        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_userrole);
        final RadioGroup rg_userrole = (RadioGroup) dialog.findViewById(R.id.rg_userrole);
        Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rg_userrole.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) dialog.findViewById(selectedId);
                String u_role = rb.getText().toString().trim();

                if (null != rb) {
                    if (u_role.equals("Super Admin")) {

                        mSharedPrefClass.setUserR("super");

                    } else if (u_role.equals("Dealer")) {

                        mSharedPrefClass.setUserR("dealer");

                    } else if (u_role.equals("Engineer")) {

                        mSharedPrefClass.setUserR("engineer");

                    } else if (u_role.equals("Customer")) {

                        mSharedPrefClass.setUserR("cust");

                    } else {
                        Toast.makeText(getApplicationContext(), "Please select anyone", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }
}
