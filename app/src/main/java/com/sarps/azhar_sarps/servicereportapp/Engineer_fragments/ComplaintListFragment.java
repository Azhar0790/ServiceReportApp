package com.sarps.azhar_sarps.servicereportapp.Engineer_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.Engineer_fragments.Complaint_detail;
import com.sarps.azhar_sarps.servicereportapp.DividerItemDecoration;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.D_EngineerListInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.E_ComplaintListInterface;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DEngineerListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ECompalintMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.EComplaintList;
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
    private ComplainlistAdapter complainlistAdapter;
    List<ECompalintMessage> list;
    RecyclerView.LayoutManager layoutManager;
    String user_id;
    private SharedPrefClass mSharedPrefClass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_c_complaint_list, container, false);
        ButterKnife.bind(this, rootView);
        circularProgressBar.setVisibility(View.VISIBLE);
        circularProgressBar.setIndeterminate(true);
        tv_title.setText("Complaint lists");
        list=new ArrayList<>();
        mSharedPrefClass=new SharedPrefClass(getActivity());
        user_id=mSharedPrefClass.getUserId();
        getComplaintList(user_id);
        return rootView;
    }


    public void getComplaintList(String eng_id) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        E_ComplaintListInterface e_complaintListInterface = retrofit.create(E_ComplaintListInterface.class);
        Call<EComplaintList> call = e_complaintListInterface.getJson(eng_id);//eg: eng_id=2
        call.enqueue(new Callback<EComplaintList>() {
            @Override
            public void onResponse(Call<EComplaintList> call, Response<EComplaintList> response) {
                circularProgressBar.setVisibility(View.GONE);
                EComplaintList dComplaintListPojo = response.body();
                list = dComplaintListPojo.getMessage();
                layoutManager = new LinearLayoutManager(getActivity());
                rv_complaint_list.setLayoutManager(layoutManager);
                complainlistAdapter = new ComplainlistAdapter(getActivity(),list);
                rv_complaint_list.setAdapter(complainlistAdapter);
            }

            @Override
            public void onFailure(Call<EComplaintList> call, Throwable t) {
                circularProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class ComplainlistAdapter extends RecyclerView.Adapter<ComplainlistAdapter.MyViewHolder> {
        Context context;
        private List<ECompalintMessage> list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tv_customer_name, tv_location, tv_product_name, tv_date, tv_complaint;

            public MyViewHolder(View view) {
                super(view);
                tv_complaint = (TextView) view.findViewById(R.id.tv_complaint);
                tv_customer_name = (TextView) view.findViewById(R.id.tv_customer_name);
                tv_location = (TextView) view.findViewById(R.id.tv_location);
                tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
                tv_date = (TextView) view.findViewById(R.id.tv_date);
            }
        }

        public ComplainlistAdapter(Context context, List<ECompalintMessage> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.complainlist_engg, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.tv_complaint.setText(list.get(position).getComplaintDesc());
            holder.tv_location.setText(list.get(position).getDAddress());
            holder.tv_customer_name.setText(list.get(position).getDFname() + " " + list.get(position).getDLname());
            holder.tv_date.setText(list.get(position).getComplaintDate());
            holder.tv_product_name.setText(list.get(position).getPrName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment fragment = new Complaint_detail();
                    Bundle bundle=new Bundle();
                    bundle.putString("desc",list.get(position).getComplaintDesc());
                    bundle.putString("date",list.get(position).getComplaintDate());
                    bundle.putString("location",list.get(position).getDAddress());
                    bundle.putString("product",list.get(position).getPrName());
                    bundle.putString("cid",list.get(position).getCId());
                    bundle.putString("custid",list.get(position).getCustId());
                    fragment.setArguments(bundle);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.container, fragment);
                    ft.commit();
                }
            });


        }

        @Override
        public int getItemCount() {

            return list.size();
        }
    }
}
