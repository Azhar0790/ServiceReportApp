package com.sarps.azhar_sarps.servicereportapp.Admin_fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.Dealer_fragments.CustomerDetailFragment;
import com.sarps.azhar_sarps.servicereportapp.Dealer_fragments.SpareRequestFragment;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.A_ComplaintListInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.D_AssignedEngInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.D_ComplaintListInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.D_EngineerListInterface;
import com.sarps.azhar_sarps.servicereportapp.MainActivity_Dealer;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.AComplaintListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.AComplaintMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DAssignedEngineerPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DComplaintListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DComplaintMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DEngineerListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DEngineerMessage;
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

public class ComplaintListFragment extends Fragment {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.rv_complaint_list)
    RecyclerView rv_complaint_list;

    @BindView(R.id.cb_progress_bar)
    fr.castorflex.android.circularprogressbar.CircularProgressBar circularProgressBar;

    private RecyclerView rv_assigned_engineers;
    private Button btn_submit;
    List<AComplaintMessage> list;
    ComplaintListAadpter adapter;
    List<DEngineerMessage> eng_list;
    RecyclerView.LayoutManager layoutManager;
    private SharedPrefClass mSharedPrefClass = null;
    String dealer_id = null;
    public static final String TAG = ComplaintListFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_c_complaint_list, container, false);
        ButterKnife.bind(this, rootView);
        circularProgressBar.setVisibility(View.VISIBLE);
        circularProgressBar.setIndeterminate(true);
        list = new ArrayList<>();
        mSharedPrefClass = new SharedPrefClass(getActivity());
        tv_title.setText("Complaints List");
        dealer_id = mSharedPrefClass.getUserId();
        post_data(dealer_id);
        return rootView;
    }

    public void post_data(String dealer_id) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        A_ComplaintListInterface a_complaintListInterface = retrofit.create(A_ComplaintListInterface.class);
        Call<AComplaintListPojo> call = a_complaintListInterface.getJson();
        call.enqueue(new Callback<AComplaintListPojo>() {
            @Override
            public void onResponse(Call<AComplaintListPojo> call, Response<AComplaintListPojo> response) {
                circularProgressBar.setVisibility(View.GONE);
                AComplaintListPojo aComplaintListPojo = response.body();
                list = aComplaintListPojo.getMessage();
                layoutManager = new LinearLayoutManager(getActivity());
                rv_complaint_list.setLayoutManager(layoutManager);
                adapter = new ComplaintListAadpter(getActivity(), list);
                rv_complaint_list.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<AComplaintListPojo> call, Throwable t) {
                circularProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class ComplaintListAadpter extends RecyclerView.Adapter<ComplaintListAadpter.ViewHolder> {
        Context context;
        List<AComplaintMessage> list;
        View itemView;

        public ComplaintListAadpter(Context context, List<AComplaintMessage> list) {
            this.context = context;
            this.list = list;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tv_complaint, tv_product_name, tv_assigned_engineer, tv_dealer, tv_date, tv_customer_name, tv_location, tv_status, tv_spare_request, tv_escalate_request;
            public RelativeLayout rl_spare_request;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_complaint = (TextView) itemView.findViewById(R.id.tv_complaint);
                tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
                tv_assigned_engineer = (TextView) itemView.findViewById(R.id.tv_assigned_engineer);
                tv_date = (TextView) itemView.findViewById(R.id.tv_date);
                tv_customer_name = (TextView) itemView.findViewById(R.id.tv_customer_name);
                tv_location = (TextView) itemView.findViewById(R.id.tv_location);
                tv_status = (TextView) itemView.findViewById(R.id.tv_status);
                tv_spare_request = (TextView) itemView.findViewById(R.id.tv_spare_request);
                tv_dealer = (TextView) itemView.findViewById(R.id.tv_dealer);
                tv_escalate_request = (TextView) itemView.findViewById(R.id.tv_escalate_request);
            }
        }


        @Override
        public ComplaintListAadpter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.a_complaint_item, parent, false);
            ComplaintListAadpter.ViewHolder vh = new ComplaintListAadpter.ViewHolder(itemView);
            return vh;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }


        @Override
        public void onBindViewHolder(final ComplaintListAadpter.ViewHolder holder, final int position) {

            holder.tv_product_name.setText(list.get(position).getPrName());
            holder.tv_complaint.setText(list.get(position).getComplaintDesc());
            holder.tv_assigned_engineer.setText("Assigned Engineer: " + list.get(position).getEnggfname() + " " + list.get(position).getEngglname());
            holder.tv_dealer.setText("Dealer Engineer: " + list.get(position).getDFname() + " " + list.get(position).getDLname());
            holder.tv_date.setText(list.get(position).getComplaintDate());
            holder.tv_customer_name.setText(list.get(position).getDFname() + " " + list.get(position).getDLname());
            holder.tv_location.setText(list.get(position).getDAddress());
            holder.tv_status.setText(list.get(position).getComplaintStatus());
            holder.tv_spare_request.setText(list.get(position).getSpareReqStatus());


            holder.tv_product_name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_complaint.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_assigned_engineer.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_date.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_customer_name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_location.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));

            // Spare part request


            if (!list.get(position).getSpareReqStatus().equals("No_request")) {
                holder.tv_spare_request.setVisibility(View.VISIBLE);
            }
            if (!list.get(position).getEscalateStatus().equals("no")) {
                holder.tv_escalate_request.setVisibility(View.VISIBLE);
            }
            holder.tv_spare_request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_spare_req(list.get(position).getSparepartRequest());
                }
            });
            holder.tv_escalate_request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_escalate_comp(list.get(position).getEscalateDesc());
                }
            });

            // Assigned engineer
            holder.tv_assigned_engineer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            // On completion of work status will change to Work Done, and on click of work done it will display customer details, product details and signature

            if (holder.tv_status.getText().toString().equals("Pending")) {
                holder.tv_assigned_engineer.setVisibility(View.GONE);
                holder.tv_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });

            } else if (holder.tv_status.getText().toString().equals("Assigned")) {
                holder.tv_status.setTextColor(Color.RED);
            }
//            else {
//                holder.tv_status.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        holder.tv_status.setTextColor(Color.GREEN);
//                        getFragmentManager()
//                                .beginTransaction()
//                                .addToBackStack(TAG)
//                                .replace(R.id.container, new CustomerDetailFragment())
//                                .commit();
//                    }
//                });
//            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void dialog_spare_req(String spare_req) {

            new AlertDialog.Builder(context)
                    .setTitle("Spare Part Request")
                    .setMessage(spare_req)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
        public void dialog_escalate_comp(String esc) {

            new AlertDialog.Builder(context)
                    .setTitle("Escalate Complaint")
                    .setMessage(esc)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }
}
