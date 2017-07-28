package com.sarps.azhar_sarps.servicereportapp.Dealer_fragments;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.D_ADSPInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.D_ComplaintListInterface;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ApproveDeclineSPPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DComplaintListPojo;
import com.sarps.azhar_sarps.servicereportapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpareRequestFragment extends Fragment {

    @BindView(R.id.tv_spare_request)
    TextView tv_spare_request;
    @BindView(R.id.btn_accept)
    Button btn_accept;
    @BindView(R.id.btn_reject)
    Button btn_reject;
    @BindView(R.id.cb_progress_bar)
    fr.castorflex.android.circularprogressbar.CircularProgressBar circularProgressBar;
    View rootView = null;
    String spare_request = null, c_id = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_d_spare_request, container, false);
        ButterKnife.bind(this, rootView);

        spare_request = getArguments().getString("spare_request");
        c_id = getArguments().getString("c_id");
        tv_spare_request.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));

        tv_spare_request.setText(spare_request);


        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circularProgressBar.setVisibility(View.VISIBLE);
                post_methodAcceptReject("Approved", c_id);
            }
        });
        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circularProgressBar.setVisibility(View.VISIBLE);
                post_methodAcceptReject("Decline", c_id);

            }
        });
        return rootView;
    }


    public void post_methodAcceptReject(final String status, String c_id) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        D_ADSPInterface d_adspInterface = retrofit.create(D_ADSPInterface.class);
        Call<ApproveDeclineSPPojo> call = d_adspInterface.postData(c_id, status);
        call.enqueue(new Callback<ApproveDeclineSPPojo>() {
            @Override
            public void onResponse(Call<ApproveDeclineSPPojo> call, Response<ApproveDeclineSPPojo> response) {

                circularProgressBar.setVisibility(View.GONE);
                ApproveDeclineSPPojo approveDeclineSPPojo = response.body();
                String message = approveDeclineSPPojo.getMessage();

                if (status.equals("Approved")) {
                    if (message.equals("Status Changed")) {
                        Toast.makeText(getActivity(), "Request approved successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Already approved", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (message.equals("Status Changed")) {
                        Toast.makeText(getActivity(), "Request rejected successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Already Rejected", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApproveDeclineSPPojo> call, Throwable t) {
                circularProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Some problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void dialog_rejection() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Rejection of spare request");
        dialog.setContentView(R.layout.dialog_rej_spare_req);
    }
}
