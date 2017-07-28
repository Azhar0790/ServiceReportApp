package com.sarps.azhar_sarps.servicereportapp.Customer_fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.C_EscalateListInterface;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CEscalateListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CEscalatePojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.Message;
import com.sarps.azhar_sarps.servicereportapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EscalateComplaintFragment extends Fragment {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv_complaint_list)
    RecyclerView rv_complaint_list;
    List<Message> list;
    RecyclerView.LayoutManager layoutManager;
    EscalateComplaintListAadpter aadpter;
    private SharedPrefClass mSharedPrefClass = null;
    String cust_id = null;
    public static final String TAG = EscalateComplaintFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_c_complaint_list, container, false);
        ButterKnife.bind(this, rootView);
        list = new ArrayList<>();
        mSharedPrefClass = new SharedPrefClass(getActivity());
        cust_id = mSharedPrefClass.getUserId();
        tv_title.setText("Escalate Complaints");
        EscalateList(cust_id);

        return rootView;
    }


    public void EscalateList(String cust_id) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        C_EscalateListInterface c_escalateListInterface = retrofit.create(C_EscalateListInterface.class);
        retrofit2.Call<CEscalateListPojo> call = c_escalateListInterface.getJson(cust_id);
        call.enqueue(new Callback<CEscalateListPojo>() {
            @Override
            public void onResponse(retrofit2.Call<CEscalateListPojo> call, Response<CEscalateListPojo> response) {

                CEscalateListPojo escalateListPojo = response.body();
                list = escalateListPojo.getMessage();
                aadpter = new EscalateComplaintListAadpter(getActivity(), list);
                layoutManager = new LinearLayoutManager(getActivity());
                rv_complaint_list.setLayoutManager(layoutManager);
                rv_complaint_list.setAdapter(aadpter);

            }

            @Override
            public void onFailure(retrofit2.Call<CEscalateListPojo> call, Throwable t) {

                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();

            }
        });
    }

    class EscalateComplaintListAadpter extends RecyclerView.Adapter<EscalateComplaintListAadpter.ViewHolder> {
        Context context;
        List<Message> list;
        View itemView;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tv_model_no, tv_product_name, tv_assigned_engineer, tv_date, tv_es_complaint;
            public ImageView iv_product;

            public ViewHolder(View itemView) {
                super(itemView);
                iv_product = (ImageView) itemView.findViewById(R.id.iv_product);
                tv_model_no = (TextView) itemView.findViewById(R.id.tv_model_no);
                tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
                tv_assigned_engineer = (TextView) itemView.findViewById(R.id.tv_assigned_engineer);
                tv_date = (TextView) itemView.findViewById(R.id.tv_date);
                tv_es_complaint = (TextView) itemView.findViewById(R.id.tv_es_complaint);
            }
        }


        public EscalateComplaintListAadpter(Context context, List<Message> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public EscalateComplaintListAadpter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                          int viewType) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.c_escalate_item, parent, false);
            EscalateComplaintListAadpter.ViewHolder vh = new EscalateComplaintListAadpter.ViewHolder(itemView);
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
        public void onBindViewHolder(final EscalateComplaintListAadpter.ViewHolder holder, final int position) {


            holder.tv_product_name.setText(list.get(position).getPrName());
            holder.tv_model_no.setText(list.get(position).getPrModelno());
            holder.tv_assigned_engineer.setText(list.get(position).getAssignEnggId());
            holder.tv_date.setText(list.get(position).getComplaintDate());
            holder.tv_es_complaint.setText(list.get(position).getEscalateDesc());

            holder.tv_assigned_engineer.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_date.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
//            holder.tv_es_complaint.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
//
//            Picasso.with(context).load("https://cash2kart.com/upload/store/" + "").into(holder.iv_product);

        }

        @Override
        public int getItemCount() {

            return list.size();
        }
    }

}
