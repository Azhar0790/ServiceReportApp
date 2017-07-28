package com.sarps.azhar_sarps.servicereportapp.Dealer_fragments;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.A_DealerListInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.D_EngineerListInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.DealerListInterface;
import com.sarps.azhar_sarps.servicereportapp.MainActivity_Dealer;
import com.sarps.azhar_sarps.servicereportapp.MainActivity_Engineers;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ADealerMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CustListMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CustListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DEngineerListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DEngineerMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DealerListMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DealerListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.EngineerListMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.EngineerListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.OInsertMsgPojo;
import com.sarps.azhar_sarps.servicereportapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OtherRequestFragment extends Fragment {

    //    @BindView(R.id.tv_title)
//    TextView tv_title;
    @BindView(R.id.spinner_user)
    Spinner spinner_user;
    @BindView(R.id.spinner_username)
    Spinner spinner_username;
    @BindView(R.id.rl_username)
    RelativeLayout rl_username;
    @BindView(R.id.et_ot_req)
    EditText et_ot_req;
    @BindView(R.id.btn_send)
    Button btn_send;
    @BindView(R.id.ll_other_req)
    LinearLayout ll_other_req;
    @BindView(R.id.imageView)
    View imageView;

    @BindView(R.id.cb_progress_bar)
    fr.castorflex.android.circularprogressbar.CircularProgressBar circularProgressBar;

    List<ADealerMessage> list;
    RecyclerView.LayoutManager layoutManager;
    String user_id;
    private SharedPrefClass mSharedPrefClass;

    List<DealerListMessage> dealerListMessages;
    List<DEngineerMessage> engineerMessages;
    List<CustListMessage> custListMessages;

    String[] user;

    float pixelDensity;
    boolean flag = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_otherreq, container, false);
        ButterKnife.bind(this, rootView);
        pixelDensity = getResources().getDisplayMetrics().density;
        mSharedPrefClass = new SharedPrefClass(getActivity());
        user_id = mSharedPrefClass.getUserId();
        user = new String[]{"Admin", "Engineer", "Customer"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.tv_spinner, user);
        spinner_user.setAdapter(adapter);
        rl_username.setVisibility(View.GONE);
        spinner_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        getEngList(spinner_username, "Admin");
                        rl_username.setVisibility(View.GONE);
                        break;
                    case 1:
                        getEngList(spinner_username, "Engineer");
                        rl_username.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        getEngList(spinner_username, "Customer");
                        rl_username.setVisibility(View.VISIBLE);
                        break;
                    default:
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ll_other_req.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    viewMenu();
                }
            });
        }

    }

    public void viewMenu() {

        int x = imageView.getRight();
        int y = imageView.getBottom();
        x -= ((28 * pixelDensity) + (16 * pixelDensity));

        int hypotenuse = (int) Math.hypot(imageView.getWidth(), imageView.getHeight());

        if (flag) {
            FrameLayout.LayoutParams parameters = (FrameLayout.LayoutParams) ll_other_req.getLayoutParams();
            parameters.height = imageView.getHeight();
            ll_other_req.setLayoutParams(parameters);
            Animator anim = ViewAnimationUtils.createCircularReveal(ll_other_req, x, y, 0, hypotenuse);
            anim.setDuration(700);
            ll_other_req.setVisibility(View.VISIBLE);
            anim.start();
            flag = false;
        }
    }

    public void getEngList(final Spinner username, final String user) {


        if (user.equals("Engineer")) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
            D_EngineerListInterface dEngineerListInterface = retrofit.create(D_EngineerListInterface.class);
            Call<DEngineerListPojo> call = dEngineerListInterface.getJson(user_id);
            call.enqueue(new Callback<DEngineerListPojo>() {
                @Override
                public void onResponse(Call<DEngineerListPojo> call, Response<DEngineerListPojo> response) {
                    DEngineerListPojo dEngineerListPojo = response.body();
                    engineerMessages = dEngineerListPojo.getMessage();
                    String[] items = new String[engineerMessages.size()];
                    //Traversing through the whole list to get all the names
                    for (int i = 0; i < engineerMessages.size(); i++) {
                        items[i] = engineerMessages.get(i).getDFname() + " " + engineerMessages.get(i).getDLname();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.tv_spinner, items);
                    username.setAdapter(adapter);
                    username.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            final DEngineerMessage eng = engineerMessages.get(position);

                            btn_send.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (et_ot_req.getText().length() != 0) {
                                        InsertMsg("engineer", user_id, et_ot_req.getText().toString(), eng.getDId());
                                    } else {
                                        Toast.makeText(getActivity(), "Please insert some data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }

                @Override
                public void onFailure(Call<DEngineerListPojo> call, Throwable t) {
                }
            });
        } else if (user.equals("Customer")) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
            DealerListInterface dealerListInterface = retrofit.create(DealerListInterface.class);
            Call<CustListPojo> call = dealerListInterface.getJsonCust(user_id);
            call.enqueue(new Callback<CustListPojo>() {
                @Override
                public void onResponse(Call<CustListPojo> call, Response<CustListPojo> response) {
                    CustListPojo custListPojo = response.body();
                    custListMessages = custListPojo.getMessage();
                    String[] items = new String[custListMessages.size()];
                    //Traversing through the whole list to get all the names
                    for (int i = 0; i < custListMessages.size(); i++) {
                        items[i] = custListMessages.get(i).getDFname() + " " + custListMessages.get(i).getDLname();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.tv_spinner, items);
                    username.setAdapter(adapter);
                    username.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            final CustListMessage cus = custListMessages.get(position);

                            btn_send.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (et_ot_req.getText().length() != 0) {
                                        InsertMsg("cust", user_id, et_ot_req.getText().toString(), cus.getDId());
                                    } else {
                                        Toast.makeText(getActivity(), "Please insert some data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }

                @Override
                public void onFailure(Call<CustListPojo> call, Throwable t) {
                }
            });
        } else {
            btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (et_ot_req.getText().length() != 0) {
                        InsertMsgAdmin("super", "", et_ot_req.getText().toString());
                    } else {
                        Toast.makeText(getActivity(), "Please insert some data", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }

    }

    public void InsertMsgAdmin(String user_role, String id, String msg) {

        circularProgressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        DealerListInterface dealerListInterface = retrofit.create(DealerListInterface.class);
        Call<OInsertMsgPojo> call = dealerListInterface.post_reply(id, user_role, msg, "dealer");


        call.enqueue(new Callback<OInsertMsgPojo>() {
            @Override
            public void onResponse(Call<OInsertMsgPojo> call, Response<OInsertMsgPojo> response) {
                circularProgressBar.setVisibility(View.GONE);
                OInsertMsgPojo oInsertMsgPojo = response.body();
                if (oInsertMsgPojo.getMessage().equals("inserted successfully")) {
                    Toast.makeText(getActivity(), "Message send successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), MainActivity_Dealer.class));
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<OInsertMsgPojo> call, Throwable t) {
                circularProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Sorry. Some problem with server. Please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void InsertMsg(String user_role, String id, String msg, String reciever_id) {

        circularProgressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        DealerListInterface dealerListInterface = retrofit.create(DealerListInterface.class);
        Call<OInsertMsgPojo> call = dealerListInterface.postData_E(id, user_role, msg, reciever_id,"dealer");


        call.enqueue(new Callback<OInsertMsgPojo>() {
            @Override
            public void onResponse(Call<OInsertMsgPojo> call, Response<OInsertMsgPojo> response) {
                circularProgressBar.setVisibility(View.GONE);
                OInsertMsgPojo oInsertMsgPojo = response.body();
                if (oInsertMsgPojo.getMessage().equals("inserted successfully")) {
                    Toast.makeText(getActivity(), "Message send successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), MainActivity_Dealer.class));
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<OInsertMsgPojo> call, Throwable t) {
                circularProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Sorry. Some problem with server. Please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
