package com.sarps.azhar_sarps.servicereportapp.Dealer_fragments;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.D_EscalateListInterface;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DEscListMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DEscalateListPojo;
import com.sarps.azhar_sarps.servicereportapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EscalateComplaintFragment extends Fragment {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv_complaint_list)
    RecyclerView rv_complaint_list;
    RecyclerView.LayoutManager layoutManager;
    EscalateComplaintListAadpter aadpter;
    List<DEscListMessage> list;
    private SharedPrefClass mSharedPrefClass = null;
    public static final String TAG = EscalateComplaintFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_c_complaint_list, container, false);
        ButterKnife.bind(this, rootView);
        list = new ArrayList<>();
        mSharedPrefClass = new SharedPrefClass(getActivity());
        tv_title.setText("Escalate Complaints");
        getEscalateList(mSharedPrefClass.getUserId());
        return rootView;
    }

    public void getEscalateList(String dealer_id) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        D_EscalateListInterface dEscalateListInterface = retrofit.create(D_EscalateListInterface.class);
        Call<DEscalateListPojo> call = dEscalateListInterface.getJson(dealer_id);
        call.enqueue(new Callback<DEscalateListPojo>() {
            @Override
            public void onResponse(Call<DEscalateListPojo> call, Response<DEscalateListPojo> response) {

                DEscalateListPojo dEscalateListPojo = response.body();
                list = dEscalateListPojo.getMessage();
                layoutManager = new LinearLayoutManager(getActivity());
                rv_complaint_list.setLayoutManager(layoutManager);
                aadpter = new EscalateComplaintListAadpter(getActivity(), list);
                rv_complaint_list.setAdapter(aadpter);
            }

            @Override
            public void onFailure(Call<DEscalateListPojo> call, Throwable t) {
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class EscalateComplaintListAadpter extends RecyclerView.Adapter<EscalateComplaintListAadpter.ViewHolder> {
        Context context;
        List<DEscListMessage> list;
        View itemView;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tv_customer, tv_location, tv_product_name, tv_assigned_engineer, tv_date, tv_escalate_form, tv_es_spare_request, tv_status;
            public ImageView iv_spare_image;
            public RelativeLayout rl_spare_request;

            public ViewHolder(View itemView) {
                super(itemView);
                iv_spare_image = (ImageView) itemView.findViewById(R.id.iv_spare_image);
                tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
                tv_customer = (TextView) itemView.findViewById(R.id.tv_customer_name);
                tv_location = (TextView) itemView.findViewById(R.id.tv_location);
                tv_assigned_engineer = (TextView) itemView.findViewById(R.id.tv_assigned_engineer);
                tv_date = (TextView) itemView.findViewById(R.id.tv_date);
                tv_escalate_form = (TextView) itemView.findViewById(R.id.tv_es_complaint);
                tv_es_spare_request = (TextView) itemView.findViewById(R.id.tv_es_spare_request);
                tv_status = (TextView) itemView.findViewById(R.id.tv_es_status);
            }
        }

        public EscalateComplaintListAadpter(Context context, List<DEscListMessage> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public EscalateComplaintListAadpter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                          int viewType) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.d_escalate_item, parent, false);
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
            holder.tv_assigned_engineer.setText(list.get(position).getEnggfname()+" "+list.get(position).getEngglname());
            holder.tv_date.setText(list.get(position).getEscalateDate());
            holder.tv_escalate_form.setText("Escalate Complaint: "+list.get(position).getEscalateDesc());
            holder.tv_status.setText(list.get(position).getComplaintStatus());
            holder.tv_location.setText(list.get(position).getDCityName());
            holder.tv_es_spare_request.setText(list.get(position).getSparepartRequest());
            holder.tv_customer.setText(list.get(position).getCustfname() + " " + list.get(position).getCustlname());

            holder.tv_product_name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_assigned_engineer.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_date.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}
