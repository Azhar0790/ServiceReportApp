package com.sarps.azhar_sarps.servicereportapp.Engineer_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.E_SparePartRequestInterface;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ESparePartrequest;
import com.sarps.azhar_sarps.servicereportapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sarps-preeti on 6/19/2017.
 */

public class ProfileFragment extends Fragment {
    @BindView(R.id.btn_spare_request)
    Button btn_spare_request;
    @BindView(R.id.iv_profile)
    ImageView iv_profile;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_emailid)
    TextView tv_emailid;
    private SharedPrefClass mSharedPrefClass = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, rootView);
        mSharedPrefClass = new SharedPrefClass(getActivity());
        post_data(mSharedPrefClass.getUserId(), mSharedPrefClass.getUserRole());
        return rootView;
    }


    public void post_data(String cid, String userrole) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        E_SparePartRequestInterface eSparePartRequestInterface = retrofit.create(E_SparePartRequestInterface.class);
        Call<ESparePartrequest> call = eSparePartRequestInterface.postData(cid, userrole,null);
        call.enqueue(new Callback<ESparePartrequest>() {
            @Override
            public void onResponse(Call<ESparePartrequest> call, Response<ESparePartrequest> response) {
                ESparePartrequest dComplaintListPojo = response.body();
                if (dComplaintListPojo.getMessage().equals("Updated")) {
                    Toast.makeText(getActivity(), "Request Send Successfully", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                } else {
                    Toast.makeText(getActivity(), "Please try later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ESparePartrequest> call, Throwable t) {
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }

}