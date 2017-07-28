package com.sarps.azhar_sarps.servicereportapp.Customer_fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ScrollingView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.ComplaintFormInterface;
import com.sarps.azhar_sarps.servicereportapp.MainActivity_Customer;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CComplaintPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CProductListPojo;
import com.sarps.azhar_sarps.servicereportapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceRequestFragment extends Fragment {

    @BindView(R.id.iv_product)
    ImageView iv_product;
    @BindView(R.id.tv_model_no)
    TextView tv_model_no;
    @BindView(R.id.tv_product_name)
    TextView tv_product_name;
    @BindView(R.id.et_issue_description)
    EditText et_issue_description;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.cb_progress_bar)
    fr.castorflex.android.circularprogressbar.CircularProgressBar circularProgressBar;

    View rootView = null;
    private SharedPrefClass mSharedPrefClass = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_c_service_request, container, false);
        ButterKnife.bind(this, rootView);
        mSharedPrefClass = new SharedPrefClass(getActivity());

        String model_no = getArguments().getString("model_no");
        String product_name = getArguments().getString("product_name");
        final String pr_id = getArguments().getString("pr_id");
        String img_url = getArguments().getString("img_url");

        tv_model_no.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
        tv_product_name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
        Picasso.with(getActivity()).load("http://sarps.sarpstechnologies.com/service/assets/uploads/products/" + img_url).into(iv_product);

        tv_model_no.setText("Model No: " + model_no);
        tv_product_name.setText("Product Name: " + product_name);
        et_issue_description.setTextColor(getResources().getColor(R.color.text_color));
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_issue_description.getText().length()==0) {
                    Toast.makeText(getActivity(),"Please enter some data",Toast.LENGTH_SHORT).show();
                }else {
                    circularProgressBar.setVisibility(View.VISIBLE);
                    circularProgressBar.setIndeterminate(true);
                    post_data(mSharedPrefClass.getUserId(), pr_id, et_issue_description.getText().toString(), mSharedPrefClass.getUserRole());

                }
            }
        });
        return rootView;
    }

    public void post_data(String cust_id, String pr_id, String complaint_desc, String user_role) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ComplaintFormInterface complaintFormInterface = retrofit.create(ComplaintFormInterface.class);
        Call<CComplaintPojo> call = complaintFormInterface.postData(cust_id, pr_id, complaint_desc, user_role);
        call.enqueue(new Callback<CComplaintPojo>() {
            @Override
            public void onResponse(Call<CComplaintPojo> call, Response<CComplaintPojo> response) {
                circularProgressBar.setVisibility(View.GONE);
                CComplaintPojo cComplaintPojo = response.body();
                String message = cComplaintPojo.getMessage();
                if (message.equals("Inserted")) {
                    Toast.makeText(getActivity(), "Complaint sent successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), MainActivity_Customer.class));
                } else {
                    Toast.makeText(getActivity(), "Sorry!! Please try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CComplaintPojo> call, Throwable t) {
                circularProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
