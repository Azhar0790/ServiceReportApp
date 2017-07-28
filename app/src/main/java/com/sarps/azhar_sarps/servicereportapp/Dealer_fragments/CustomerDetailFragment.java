package com.sarps.azhar_sarps.servicereportapp.Dealer_fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ScrollingView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.D_ComplaintRecord;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DEngineerListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DGetComplaintMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DGetComplaintRecordPojo;
import com.sarps.azhar_sarps.servicereportapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerDetailFragment extends Fragment {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_model_no)
    TextView tv_model_no;
    @BindView(R.id.tv_product_name)
    TextView tv_product_name;
    @BindView(R.id.tv_work_done)
    TextView tv_work_done;
    @BindView(R.id.tv_lue_oil_brand_grade)
    TextView tv_lue_oil_brand_grade;
    @BindView(R.id.tv_oil_pressure)
    TextView tv_oil_pressure;
    @BindView(R.id.tv_water_temp)
    TextView tv_water_temp;
    @BindView(R.id.tv_volt)
    TextView tv_volt;
    @BindView(R.id.tv_observation)
    TextView tv_observation;
    @BindView(R.id.tv_recommendation)
    TextView tv_recommendation;
    @BindView(R.id.tv_customer_remark)
    TextView tv_customer_remark;
    @BindView(R.id.iv_signature)
    ImageView iv_signature;
    @BindView(R.id.sc_customer_detail)
    ScrollView sc_customer_detail;
    @BindView(R.id.cb_progress_bar)
    fr.castorflex.android.circularprogressbar.CircularProgressBar circularProgressBar;

    DGetComplaintMessage dGetComplaintMessage;
    String cid;
    public static final String TAG = CustomerDetailFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_d_customer_detail, container, false);
        ButterKnife.bind(this, rootView);
        cid = getArguments().getString("cid");
        get_all_cus_complaint(cid);
        return rootView;
    }

    public void get_all_cus_complaint(String c_id) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        D_ComplaintRecord dComplaintRecord = retrofit.create(D_ComplaintRecord.class);
        Call<DGetComplaintRecordPojo> call = dComplaintRecord.getJson(c_id);
        call.enqueue(new Callback<DGetComplaintRecordPojo>() {
            @Override
            public void onResponse(Call<DGetComplaintRecordPojo> call, Response<DGetComplaintRecordPojo> response) {
                circularProgressBar.setVisibility(View.GONE);
                sc_customer_detail.setVisibility(View.VISIBLE);
                DGetComplaintRecordPojo dGetComplaintRecordPojo = response.body();

                tv_name.setText("" + dGetComplaintRecordPojo.getMessage().get(0).getDFname() + " " + dGetComplaintRecordPojo.getMessage().get(0).getDLname());
                tv_model_no.setText(dGetComplaintRecordPojo.getMessage().get(0).getPrModelno());
                tv_product_name.setText(dGetComplaintRecordPojo.getMessage().get(0).getPrName());
                tv_work_done.setText(dGetComplaintRecordPojo.getMessage().get(0).getWorkDone());
                tv_lue_oil_brand_grade.setText(dGetComplaintRecordPojo.getMessage().get(0).getLueOilBrandGrade());
                tv_oil_pressure.setText(dGetComplaintRecordPojo.getMessage().get(0).getOilPressure());
                tv_water_temp.setText(dGetComplaintRecordPojo.getMessage().get(0).getWaterTemp());
                tv_volt.setText(dGetComplaintRecordPojo.getMessage().get(0).getVolt());
                tv_observation.setText(dGetComplaintRecordPojo.getMessage().get(0).getObservation());
                tv_recommendation.setText(dGetComplaintRecordPojo.getMessage().get(0).getRecommendation());
                tv_customer_remark.setText(dGetComplaintRecordPojo.getMessage().get(0).getCustomerRemark());

                Picasso.with(getActivity()).load("http://sarps.sarpstechnologies.com/service/assets/uploads/signature/" + dGetComplaintRecordPojo.getMessage().get(0).getSignImageName()).error(R.mipmap.ic_launcher).into(iv_signature);

            }

            @Override
            public void onFailure(Call<DGetComplaintRecordPojo> call, Throwable t) {
                circularProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
