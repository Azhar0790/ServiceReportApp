package com.sarps.azhar_sarps.servicereportapp.Customer_fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.C_CompanyListInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.C_EscalateFormInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.ComplaintFormInterface;
import com.sarps.azhar_sarps.servicereportapp.MainActivity_Customer;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CComplaintListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CComplaintPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CEscalatePojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.Complaintlist;
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

public class ComplaintListFragment extends Fragment {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv_complaint_list)
    RecyclerView rv_complaint_list;
    @BindView(R.id.cb_progress_bar)
    fr.castorflex.android.circularprogressbar.CircularProgressBar circularProgressBar;
    ComplaintListAadpter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Complaintlist> list;
    private SharedPrefClass mSharedPrefClass = null;
    public static final String TAG = ComplaintListFragment.class.getSimpleName();
    String cust_id = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_c_complaint_list, container, false);
        ButterKnife.bind(this, rootView);
        circularProgressBar.setVisibility(View.VISIBLE);
        circularProgressBar.setIndeterminate(true);
        mSharedPrefClass = new SharedPrefClass(getActivity());
        list = new ArrayList<>();
        tv_title.setText("Complaints List");
        complaintList(mSharedPrefClass.getUserId());
        return rootView;
    }


    public void complaintList(String cust_id) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        C_CompanyListInterface c_companyListInterface = retrofit.create(C_CompanyListInterface.class);
        Call<CComplaintListPojo> call = c_companyListInterface.getJson(cust_id);
        call.enqueue(new Callback<CComplaintListPojo>() {
            @Override
            public void onResponse(Call<CComplaintListPojo> call, Response<CComplaintListPojo> response) {
                circularProgressBar.setVisibility(View.GONE);
                CComplaintListPojo cComplaintListPojo = response.body();
                list = cComplaintListPojo.getComplaintlist();

                layoutManager = new LinearLayoutManager(getActivity());
                adapter = new ComplaintListAadpter(getActivity(), list);
                rv_complaint_list.setLayoutManager(layoutManager);
                rv_complaint_list.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<CComplaintListPojo> call, Throwable t) {
                circularProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }


    class ComplaintListAadpter extends RecyclerView.Adapter<ComplaintListAadpter.ViewHolder> {
        Context context;
        List<Complaintlist> list;
        View itemView;


        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tv_model_no, tv_product_name, tv_assigned_engineer, tv_complaint, tv_date, tv_escalate_form,tv_complaint_status;
            public ImageView iv_product;

            public ViewHolder(View itemView) {
                super(itemView);
                iv_product = (ImageView) itemView.findViewById(R.id.iv_product);
                tv_model_no = (TextView) itemView.findViewById(R.id.tv_model_no);
                tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
                tv_assigned_engineer = (TextView) itemView.findViewById(R.id.tv_assigned_engineer);
                tv_complaint = (TextView) itemView.findViewById(R.id.tv_complaint);
                tv_date = (TextView) itemView.findViewById(R.id.tv_date);
                tv_escalate_form = (TextView) itemView.findViewById(R.id.tv_escalate_form);
                tv_complaint_status = (TextView) itemView.findViewById(R.id.tv_complaint_status);
            }
        }

        public ComplaintListAadpter(Context context, List<Complaintlist> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public ComplaintListAadpter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.c_complaint_item, parent, false);
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


            list.get(position).getPrModelno();
            holder.tv_product_name.setText(list.get(position).getPrName());
            holder.tv_model_no.setText("Model No: " + list.get(position).getPrModelno());
            holder.tv_assigned_engineer.setText(list.get(position).getAssignEnggId());
            holder.tv_complaint.setText(list.get(position).getComplaintDesc());
            holder.tv_date.setText(list.get(position).getComplaintDate());

            holder.tv_product_name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_assigned_engineer.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_date.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_escalate_form.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));

            Picasso.with(context).load("http://sarps.sarpstechnologies.com/service/assets/uploads/products/" + list.get(position).getPrImage()).into(holder.iv_product);
            holder.tv_escalate_form.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog_escalate_form(list.get(position).getCId(), list.get(position).getCustId());
                }
            });
            holder.tv_complaint_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle=new Bundle();
                    bundle.putString("c_id",list.get(position).getCId());
                    ComplainStatusFragment fragment = new ComplainStatusFragment();
                    fragment.setArguments(bundle);
//                    .addSharedElement(holder.iv_product, "1")
                    getFragmentManager()
                            .beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.container, fragment)
                            .commit();
                }
            });

        }

        @Override
        public int getItemCount() {

            int list_size = 0;
            if (list_size == 0) {

                if (list.size() != 0) {
                    list_size = list.size();
                } else {
                    Toast.makeText(getActivity(), "No complaints", Toast.LENGTH_SHORT).show();
                }
            } else {
                list_size = list.size();
            }
            return list_size;
        }


        public void dialog_escalate_form(final String cid, final String cust_id) {

            final Dialog dialog = new Dialog((MainActivity_Customer) getActivity());
            dialog.setContentView(R.layout.fragment_c_escalate_form);
            dialog.setCancelable(false);
            final EditText et_issue_description = (EditText) dialog.findViewById(R.id.et_issue_description);
            Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
            ImageView iv_close = (ImageView) dialog.findViewById(R.id.iv_close);
            et_issue_description.setTextColor(getResources().getColor(R.color.text_color));
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    circularProgressBar.setVisibility(View.VISIBLE);
                    circularProgressBar.setIndeterminate(true);
                    if (et_issue_description.getText().toString().length() != 0) {
                        post_data(cid, et_issue_description.getText().toString(), cust_id);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Please enter some data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        }

        public void post_data(String c_id, String escalate_desc, String cust_id) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
            C_EscalateFormInterface c_escalateFormInterface = retrofit.create(C_EscalateFormInterface.class);
            Call<CEscalatePojo> call = c_escalateFormInterface.postData(c_id, escalate_desc, cust_id);
            call.enqueue(new Callback<CEscalatePojo>() {
                @Override
                public void onResponse(Call<CEscalatePojo> call, Response<CEscalatePojo> response) {
                    circularProgressBar.setVisibility(View.GONE);
                    CEscalatePojo cEscalatePojo = response.body();
                    String message = cEscalatePojo.getMessage();
                    if (message.equals("Inserted"))
                    {
                        Toast.makeText(getActivity(), "Escalate complaint sent successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), MainActivity_Customer.class));
                        getActivity().finish();
                    }
                }

                @Override
                public void onFailure(Call<CEscalatePojo> call, Throwable t) {
                    circularProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
