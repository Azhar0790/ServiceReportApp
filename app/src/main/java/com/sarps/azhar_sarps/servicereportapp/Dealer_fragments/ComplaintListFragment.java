package com.sarps.azhar_sarps.servicereportapp.Dealer_fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.ComplaintFormInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.D_AssignedEngInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.D_ComplaintListInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.D_EngineerListInterface;
import com.sarps.azhar_sarps.servicereportapp.MainActivity_Customer;
import com.sarps.azhar_sarps.servicereportapp.MainActivity_Dealer;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CComplaintPojo;
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
    // dialog assigned views
//    @BindView(R.id.rv_assigned_engineers)
//    RecyclerView rv_assigned_engineers;

//    @BindView(R.id.btn_submit)
//    Button btn_submit;

    private RecyclerView rv_assigned_engineers;
    private Button btn_submit;
    List<DComplaintMessage> list;
    ComplaintListAadpter adapter;
    List<DEngineerMessage> eng_list;
    AssignedEngineerAadpter assignedEngineerAadpter;
    RecyclerView.LayoutManager layoutManager, layoutManager2;
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
        D_ComplaintListInterface dComplaintListInterface = retrofit.create(D_ComplaintListInterface.class);
        Call<DComplaintListPojo> call = dComplaintListInterface.getJson(dealer_id);
        call.enqueue(new Callback<DComplaintListPojo>() {
            @Override
            public void onResponse(Call<DComplaintListPojo> call, Response<DComplaintListPojo> response) {
                circularProgressBar.setVisibility(View.GONE);
                DComplaintListPojo dComplaintListPojo=response.body();
                list = dComplaintListPojo.getMessage();
                layoutManager = new LinearLayoutManager(getActivity());
                rv_complaint_list.setLayoutManager(layoutManager);
                adapter = new ComplaintListAadpter(getActivity(), list);
                rv_complaint_list.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<DComplaintListPojo> call, Throwable t) {
                circularProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class ComplaintListAadpter extends RecyclerView.Adapter<ComplaintListAadpter.ViewHolder> {
        Context context;
        List<DComplaintMessage> list;
        View itemView;

        public ComplaintListAadpter(Context context, List<DComplaintMessage> list) {
            this.context = context;
            this.list = list;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tv_cid, tv_complaint, tv_product_name, tv_assigned_engineer, tv_date, tv_customer_name, tv_location, tv_status, tv_spare_request;
            public ImageView iv_spare_image;
            public RelativeLayout rl_spare_request;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_cid = (TextView) itemView.findViewById(R.id.tv_cid);
                tv_complaint = (TextView) itemView.findViewById(R.id.tv_complaint);
                tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
                tv_assigned_engineer = (TextView) itemView.findViewById(R.id.tv_assigned_engineer);
                tv_date = (TextView) itemView.findViewById(R.id.tv_date);
                tv_customer_name = (TextView) itemView.findViewById(R.id.tv_customer_name);
                tv_location = (TextView) itemView.findViewById(R.id.tv_location);
                tv_status = (TextView) itemView.findViewById(R.id.tv_status);
                tv_spare_request = (TextView) itemView.findViewById(R.id.tv_spare_request);
                rl_spare_request = (RelativeLayout) itemView.findViewById(R.id.rl_spare_request);
                iv_spare_image = (ImageView) itemView.findViewById(R.id.iv_spare_image);
            }
        }


        @Override
        public ComplaintListAadpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.d_complaint_item, parent, false);
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

            holder.tv_cid.setText(list.get(position).getCId());
            holder.tv_product_name.setText(list.get(position).getPrName());
            holder.tv_complaint.setText(list.get(position).getComplaintDesc());
            holder.tv_assigned_engineer.setText(list.get(position).getEnggfname() + " " + list.get(position).getEngglname());
            holder.tv_date.setText(list.get(position).getComplaintDate());
            holder.tv_customer_name.setText(list.get(position).getDFname() + " " + list.get(position).getDLname());
            holder.tv_location.setText(list.get(position).getDAddress());
            holder.tv_status.setText(list.get(position).getComplaintStatus());
            holder.tv_product_name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_complaint.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_assigned_engineer.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_date.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_customer_name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_location.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));

            // Spare part request

            Animation myAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);
            holder.iv_spare_image.setAnimation(myAnim);


            if (list.get(position).getEnggfname() != null) {
                holder.tv_assigned_engineer.setVisibility(View.VISIBLE);
            }

            if (!list.get(position).getSparereq().equals("")) {
                holder.rl_spare_request.setVisibility(View.VISIBLE);
            }

            holder.tv_spare_request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SpareRequestFragment spareRequestFragment = new SpareRequestFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("spare_request", list.get(position).getSparereq());
                    bundle.putString("c_id", list.get(position).getCId());
                    spareRequestFragment.setArguments(bundle);
                    getFragmentManager()
                            .beginTransaction()
                            .addToBackStack(TAG)
                            .replace(R.id.container, spareRequestFragment)
                            .commit();
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
                holder.tv_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_engineersList(dealer_id, list.get(position).getCId());
                    }
                });
            } else if (holder.tv_status.getText().toString().equals("Inprocess")) {
                holder.tv_status.setTextColor(Color.GRAY);

            } else if (holder.tv_status.getText().toString().equals("Assigned")) {
                holder.tv_status.setTextColor(Color.RED);
                holder.tv_status.setTextSize(18);
            } else {
                holder.tv_status.setTextColor(Color.GREEN);
                holder.tv_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("cid", list.get(position).getCId());
                        Fragment fragment = new CustomerDetailFragment();
                        fragment.setArguments(bundle);
                        getFragmentManager()
                                .beginTransaction()
                                .addToBackStack(TAG)
                                .replace(R.id.container, fragment)
                                .commit();
                    }
                });
            }
        }

        @Override
        public int getItemCount() {

            return list.size();
        }
    }

    public void dialog_engineersList(String dealer_id, String cid) {
        final Dialog dialog = new Dialog((MainActivity_Dealer) getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_d_assign_engineers);
        rv_assigned_engineers = (RecyclerView) dialog.findViewById(R.id.rv_assigned_engineers);
        ImageView iv_close = (ImageView) dialog.findViewById(R.id.iv_close);
        eng_list = new ArrayList<>();
        getEngineersList(dealer_id, cid);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void getEngineersList(String dealer_id, final String cid) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        D_EngineerListInterface dEngineerListInterface = retrofit.create(D_EngineerListInterface.class);
        Call<DEngineerListPojo> call = dEngineerListInterface.getJson(dealer_id);

        call.enqueue(new Callback<DEngineerListPojo>() {
            @Override
            public void onResponse(Call<DEngineerListPojo> call, Response<DEngineerListPojo> response)
            {
                DEngineerListPojo dComplaintListPojo = response.body();
                eng_list = dComplaintListPojo.getMessage();
                layoutManager = new LinearLayoutManager(getActivity());
                rv_assigned_engineers.setLayoutManager(layoutManager);
                assignedEngineerAadpter = new AssignedEngineerAadpter(getActivity(), eng_list, cid);
                rv_assigned_engineers.setAdapter(assignedEngineerAadpter);
            }

            @Override
            public void onFailure(Call<DEngineerListPojo> call, Throwable t)
            {
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class AssignedEngineerAadpter extends RecyclerView.Adapter<AssignedEngineerAadpter.ViewHolder> {
        Context context;
        List<DEngineerMessage> list_en;
        String cid;
        View itemView;


        public AssignedEngineerAadpter(Context context, List<DEngineerMessage> list, String cid) {
            this.context = context;
            this.list_en = list;
            this.cid = cid;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tv_assign_engineer_name, tv_assigned_task;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_assign_engineer_name = (TextView) itemView.findViewById(R.id.tv_assign_engineer_name);
                tv_assigned_task = (TextView) itemView.findViewById(R.id.tv_assigned_task);
            }
        }


        @Override
        public AssignedEngineerAadpter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.d_assigned_engineers_item, parent, false);
            AssignedEngineerAadpter.ViewHolder vh = new AssignedEngineerAadpter.ViewHolder(itemView);
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
        public void onBindViewHolder(final AssignedEngineerAadpter.ViewHolder holder, final int position) {

            holder.tv_assign_engineer_name.setText(list_en.get(position).getDFname() + " " + list_en.get(position).getDLname());
            holder.tv_assigned_task.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    post_AsssignedEngineer(cid, list_en.get(position).getDId());
                }
            });
        }

        @Override
        public int getItemCount() {
            return list_en.size();
        }
    }

    public void post_AsssignedEngineer(String c_id, String eng_id) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        D_AssignedEngInterface dAssignedEngInterface = retrofit.create(D_AssignedEngInterface.class);
        Call<DAssignedEngineerPojo> call = dAssignedEngInterface.postData(c_id, eng_id);

        call.enqueue(new Callback<DAssignedEngineerPojo>() {
            @Override
            public void onResponse(Call<DAssignedEngineerPojo> call, Response<DAssignedEngineerPojo> response) {
                DAssignedEngineerPojo dAssignedEngineerPojo = response.body();
                String message = dAssignedEngineerPojo.getMessage();
                if (message.equals("Updated")) {
                    startActivity(new Intent(getActivity(), MainActivity_Dealer.class));
                    getActivity().finish();
                    Toast.makeText(getActivity(), "Work is assigned to this engineer", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DAssignedEngineerPojo> call, Throwable t) {
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
