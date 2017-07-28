package com.sarps.azhar_sarps.servicereportapp.Customer_fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.C_EscalateFormInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.ComplaintFormInterface;
import com.sarps.azhar_sarps.servicereportapp.MainActivity_Customer;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CComplaintPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CEscalatePojo;
import com.sarps.azhar_sarps.servicereportapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by azhar-sarps on 18-Jun-17.
 */

public class EscalateFormFragment extends Fragment {

    @BindView(R.id.iv_product)
    ImageView iv_product;
    @BindView(R.id.tv_title)
    TextView tv_title;
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

        tv_title.setText("Escalate form");
        String model_no = getArguments().getString("model_no");
        String product_name = getArguments().getString("product_name");
        final String cid = getArguments().getString("cid");
        String img_url = getArguments().getString("img_url");


        tv_model_no.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
        tv_product_name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
        et_issue_description.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));

        Picasso.with(getActivity()).load("http://sarps.sarpstechnologies.com/service/assets/uploads/products/" + img_url).into(iv_product);
        tv_model_no.setText(model_no);
        tv_product_name.setText(product_name);
        et_issue_description.setTextColor(getResources().getColor(R.color.text_color));
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circularProgressBar.setVisibility(View.VISIBLE);
                circularProgressBar.setIndeterminate(true);
//                post_data(cid, et_issue_description.getText().toString());
            }
        });
        return rootView;
    }

}
