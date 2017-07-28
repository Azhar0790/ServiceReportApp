package com.sarps.azhar_sarps.servicereportapp.Engineer_fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.Dealer_fragments.*;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.E_SparePartRequestInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.E_SpareStatusRequest;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.E_StartWorktInterface;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ESparePartrequest;
import com.sarps.azhar_sarps.servicereportapp.Pojo.SpareRequestStatusPojo;
import com.sarps.azhar_sarps.servicereportapp.R;

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

public class Complaint_detail extends Fragment {
    @BindView(R.id.tv_complaint)
    TextView tv_complaint;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_product_name)
    TextView tv_product_name;
    @BindView(R.id.btn_spare_request)
    Button btn_spare_request;
    @BindView(R.id.btn_complaint_accept)
    Button btn_complaint_accept;
    private SharedPrefClass mSharedPrefClass = null;
    public static final String TAG = com.sarps.azhar_sarps.servicereportapp.Dealer_fragments.ComplaintListFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.engineer_fragment_complaint_details, container, false);
        ButterKnife.bind(this, rootView);
        mSharedPrefClass = new SharedPrefClass(getActivity());
        String desc = getArguments().getString("desc");
        String date = getArguments().getString("date");
        String location = getArguments().getString("location");
        String product = getArguments().getString("product");
        final String cid = getArguments().getString("cid");
        final String custid = getArguments().getString("custid");

        tv_complaint.setText(desc);
        tv_date.setText(date);
        tv_location.setText(location);
        tv_product_name.setText(product);

        btn_spare_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Spareparts_engg();
                Bundle bundle = new Bundle();
                bundle.putString("cid", cid);
                bundle.putString("custid", custid);
                fragment.setArguments(bundle);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(TAG);
                ft.replace(R.id.container, fragment);
                ft.commit();
            }
        });
        try {
            get_data_spareStatus(cid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        btn_complaint_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                post_data(cid, custid);

                if (btn_complaint_accept.getText().equals("Stop")) {

                }
            }
        });

        if (mSharedPrefClass.getWorkStartCID().equals(cid)) {
            if (!mSharedPrefClass.getWorkStart().equals("")) {
                btn_complaint_accept.setText("Stop");
                btn_complaint_accept.setBackgroundResource(R.drawable.ripple_green);
            }
        }
        return rootView;
    }

    public void post_data(final String cid, final String custid) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        E_StartWorktInterface eStartWorktInterface = retrofit.create(E_StartWorktInterface.class);
        Call<ESparePartrequest> call = eStartWorktInterface.getJson(cid);
        call.enqueue(new Callback<ESparePartrequest>() {
            @Override
            public void onResponse(Call<ESparePartrequest> call, Response<ESparePartrequest> response) {
                ESparePartrequest dComplaintListPojo = response.body();
                if (dComplaintListPojo.getMessage().equals("Updated")) {
                    btn_complaint_accept.setText("Stop");
                    btn_complaint_accept.setBackgroundResource(R.drawable.ripple_green);
                    mSharedPrefClass.setWorkStart("start_work");
                    mSharedPrefClass.setWorkStartCID(cid);


                    Fragment fragment = new Detail_issue();
                    Bundle bundle = new Bundle();
                    bundle.putString("complaint_id", cid);
                    bundle.putString("custid", custid);
                    fragment.setArguments(bundle);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.container, fragment);
                    ft.commit();

                } else {
                    Toast.makeText(getActivity(), "Please try later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ESparePartrequest> call, Throwable t) {
                Toast.makeText(getActivity(), "Please try later", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void get_data_spareStatus(final String cid) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        E_SpareStatusRequest eSpareStatusRequest = retrofit.create(E_SpareStatusRequest.class);
        Call<SpareRequestStatusPojo> call = eSpareStatusRequest.getJson(cid);
        call.enqueue(new Callback<SpareRequestStatusPojo>() {
            @Override
            public void onResponse(Call<SpareRequestStatusPojo> call, Response<SpareRequestStatusPojo> response) {
                SpareRequestStatusPojo dComplaintListPojo = response.body();
                if (dComplaintListPojo.getSpareReqStatus().equals("Incomplete")) {
                    btn_spare_request.setText("Request is incomplete");
                    btn_spare_request.setBackgroundResource(R.color.holo_yellow_dark);


                } else {

//                    Fragment fragment = new Detail_issue();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("complaint_id", cid);
//                    fragment.setArguments(bundle);
//                    FragmentManager fm = getActivity().getSupportFragmentManager();
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.addToBackStack(null);
//                    ft.replace(R.id.container, fragment);
//                    ft.commit();
                }
            }

            @Override
            public void onFailure(Call<SpareRequestStatusPojo> call, Throwable t) {
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
