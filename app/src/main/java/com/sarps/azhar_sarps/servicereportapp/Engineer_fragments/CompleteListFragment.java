package com.sarps.azhar_sarps.servicereportapp.Engineer_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.E_ComplaintListInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.E_CompleteListInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.E_StartWorktInterface;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ECompalintMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.EComplaintList;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ECompleteListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ECompleteMessage;
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

public class CompleteListFragment extends Fragment {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv_complaint_list)
    RecyclerView rv_complaint_list;
    @BindView(R.id.cb_progress_bar)
    fr.castorflex.android.circularprogressbar.CircularProgressBar circularProgressBar;
    private CompletelistAdapter completelistAdapter;
    List<ECompleteMessage> list;
    RecyclerView.LayoutManager layoutManager;
    String user_id;
    private SharedPrefClass mSharedPrefClass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_c_complaint_list, container, false);
        ButterKnife.bind(this, rootView);
        circularProgressBar.setVisibility(View.VISIBLE);
        circularProgressBar.setIndeterminate(true);
        tv_title.setText("Work Done List ");
        list = new ArrayList<>();
        mSharedPrefClass = new SharedPrefClass(getActivity());
        user_id = mSharedPrefClass.getUserId();
        getCompletetList(user_id);
        return rootView;
    }


    public void getCompletetList(String eng_id) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        E_CompleteListInterface eCompleteListInterface = retrofit.create(E_CompleteListInterface.class);
        Call<ECompleteListPojo> call = eCompleteListInterface.getJson(eng_id);
        call.enqueue(new Callback<ECompleteListPojo>() {
            @Override
            public void onResponse(Call<ECompleteListPojo> call, Response<ECompleteListPojo> response) {
                circularProgressBar.setVisibility(View.GONE);
                ECompleteListPojo eCompleteListPojo = response.body();
                list = eCompleteListPojo.getMessage();
                if (list.isEmpty()) {
                    Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                } else {
                    layoutManager = new LinearLayoutManager(getActivity());
                    rv_complaint_list.setLayoutManager(layoutManager);
                    completelistAdapter = new CompletelistAdapter(getActivity(), list);
                    rv_complaint_list.setAdapter(completelistAdapter);
                }

            }

            @Override
            public void onFailure(Call<ECompleteListPojo> call, Throwable t) {
                circularProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class CompletelistAdapter extends RecyclerView.Adapter<CompletelistAdapter.MyViewHolder> {
        Context context;
        private List<ECompleteMessage> list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tv_customer_name, tv_location, tv_product_name, tv_sw_date, tv_ew_date, tv_complaint, tv_work_status;

            public MyViewHolder(View view) {
                super(view);
                tv_complaint = (TextView) view.findViewById(R.id.tv_complaint);
                tv_customer_name = (TextView) view.findViewById(R.id.tv_customer_name);
                tv_location = (TextView) view.findViewById(R.id.tv_location);
                tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
                tv_sw_date = (TextView) view.findViewById(R.id.tv_sw_date);
                tv_ew_date = (TextView) view.findViewById(R.id.tv_ew_date);
                tv_work_status = (TextView) view.findViewById(R.id.tv_work_status);
            }
        }

        public CompletelistAdapter(Context context, List<ECompleteMessage> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.e_completelist, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.tv_complaint.setText(list.get(position).getComplaintDesc());
            holder.tv_customer_name.setText(list.get(position).getCustfname() + " " + list.get(position).getCustlname());
            holder.tv_sw_date.setText(list.get(position).getComplaintSolveStDatetime());
            holder.tv_ew_date.setText(list.get(position).getComplaintSolveEndDatetime());
            holder.tv_location.setText(list.get(position).getDCity());
            holder.tv_work_status.setText(list.get(position).getComplaintStatus());
        }

        @Override
        public int getItemCount() {

            return list.size();
        }
    }
}
