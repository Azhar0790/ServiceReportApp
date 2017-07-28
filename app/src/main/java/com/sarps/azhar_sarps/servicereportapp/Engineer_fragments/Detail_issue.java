package com.sarps.azhar_sarps.servicereportapp.Engineer_fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sarps-preeti on 6/19/2017.
 */

public class Detail_issue extends Fragment {

    @BindView(R.id.btn_submit_issue)
    Button btn_submit_issue;
    @BindView(R.id.tv_work_done)
    EditText tv_work_done;
    @BindView(R.id.tv_running_hour)
    EditText tv_running_hour;
    @BindView(R.id.tv_service_type)
    EditText tv_service_type;
    @BindView(R.id.tv_lue_oil_brand_grade)
    EditText tv_lue_oil_brand_grade;
    @BindView(R.id.tv_oil_pressure)
    EditText tv_oil_pressure;
    @BindView(R.id.tv_water_temp)
    EditText tv_water_temp;
    @BindView(R.id.tv_volt)
    EditText tv_volt;
    @BindView(R.id.tv_observation)
    EditText tv_observation;
    @BindView(R.id.tv_recommendation)
    EditText tv_recommendation;
    @BindView(R.id.tv_customer_remark)
    EditText tv_customer_remark;
    private String complaint_id = null;
    private  String custid=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.engineer_fragment_detail_issue, container, false);

        ButterKnife.bind(this, rootView);
        complaint_id = getArguments().getString("complaint_id");
        custid = getArguments().getString("custid");

        btn_submit_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tv_work_done.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), "Please enter some data", Toast.LENGTH_SHORT).show();
                } else if (tv_running_hour.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter some data", Toast.LENGTH_SHORT).show();
                } else if (tv_service_type.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter some data", Toast.LENGTH_SHORT).show();
                } else if (tv_lue_oil_brand_grade.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter some data", Toast.LENGTH_SHORT).show();
                } else if (tv_oil_pressure.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter some data", Toast.LENGTH_SHORT).show();
                } else if (tv_water_temp.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter some data", Toast.LENGTH_SHORT).show();
                } else if (tv_volt.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter some data", Toast.LENGTH_SHORT).show();
                } else if (tv_observation.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter some data", Toast.LENGTH_SHORT).show();
                } else if (tv_recommendation.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter some data", Toast.LENGTH_SHORT).show();
                } else if (tv_customer_remark.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter some data", Toast.LENGTH_SHORT).show();
                } else {

                    Fragment fragment = new SignatureFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("work_done", tv_work_done.getText().toString());
                    bundle.putString("running_hour", tv_running_hour.getText().toString());
                    bundle.putString("service_type", tv_service_type.getText().toString());
                    bundle.putString("lue_oil_brand_grade", tv_lue_oil_brand_grade.getText().toString());
                    bundle.putString("oil_pressure", tv_oil_pressure.getText().toString());
                    bundle.putString("water_temp", tv_water_temp.getText().toString());
                    bundle.putString("volt", tv_volt.getText().toString());
                    bundle.putString("observation", tv_observation.getText().toString());
                    bundle.putString("recommendation", tv_recommendation.getText().toString());
                    bundle.putString("customer_remark", tv_customer_remark.getText().toString());
                    bundle.putString("complaint_id", complaint_id);
                    bundle.putString("custid", custid);
                    fragment.setArguments(bundle);
                    FragmentManager fm = getActivity().getSupportFragmentManager();

                    FragmentTransaction ft = fm.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.container, fragment);
                    ft.commit();
                }


            }
        });
        return rootView;
    }
}

