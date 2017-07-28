package com.sarps.azhar_sarps.servicereportapp.Customer_fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.A_OtherListInterface;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.AdminMessageList;
import com.sarps.azhar_sarps.servicereportapp.Pojo.AdminMsgListPojo;
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

public class OtherRequestListFragment extends Fragment {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.rv_otherreq_list)
    RecyclerView rv_otherreq_list;

    @BindView(R.id.fab)
    ImageView fab;

    @BindView(R.id.ll_fab)
    LinearLayout ll_fab;

    @BindView(R.id.cb_progress_bar)
    fr.castorflex.android.circularprogressbar.CircularProgressBar circularProgressBar;

    private RecyclerView rv_assigned_engineers;
    private Button btn_submit;
    List<AdminMessageList> list;
    OtherReqListAadpter adapter;
    List<DEngineerMessage> eng_list;
    RecyclerView.LayoutManager layoutManager;
    private SharedPrefClass mSharedPrefClass = null;
    String user_id = null;
    public static final String TAG = OtherRequestListFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_otherreq_list, container, false);
        ButterKnife.bind(this, rootView);
        fab.setVisibility(View.GONE);
        ll_fab.setVisibility(View.GONE);
        circularProgressBar.setVisibility(View.VISIBLE);
        circularProgressBar.setIndeterminate(true);
        list = new ArrayList<>();
        mSharedPrefClass = new SharedPrefClass(getActivity());
        tv_title.setText("Other Request List");
        user_id = mSharedPrefClass.getUserId();
        post_data(user_id);


        return rootView;
    }

    public void post_data(String user_id) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        A_OtherListInterface a_otherListInterface = retrofit.create(A_OtherListInterface.class);
        Call<AdminMsgListPojo> call = a_otherListInterface.postDataCust(user_id);
        call.enqueue(new Callback<AdminMsgListPojo>() {
            @Override
            public void onResponse(Call<AdminMsgListPojo> call, Response<AdminMsgListPojo> response) {
                circularProgressBar.setVisibility(View.GONE);
                AdminMsgListPojo adminMsgListPojo = response.body();
                list = adminMsgListPojo.getMessage();
                layoutManager = new LinearLayoutManager(getActivity());
                rv_otherreq_list.setLayoutManager(layoutManager);
                adapter = new OtherReqListAadpter(getActivity(), list);
                rv_otherreq_list.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<AdminMsgListPojo> call, Throwable t) {
                circularProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class OtherReqListAadpter extends RecyclerView.Adapter<OtherReqListAadpter.ViewHolder> {
        Context context;
        List<AdminMessageList> list;
        View itemView;

        public OtherReqListAadpter(Context context, List<AdminMessageList> list) {
            this.context = context;
            this.list = list;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tv_role, tv_name, tv_date, tv_msg;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_role = (TextView) itemView.findViewById(R.id.tv_role);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_date = (TextView) itemView.findViewById(R.id.tv_date);
                tv_msg = (TextView) itemView.findViewById(R.id.tv_msg);
            }
        }


        @Override
        public OtherReqListAadpter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.otherreq_item, parent, false);
            ViewHolder vh = new ViewHolder(itemView);
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
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.tv_role.setText(list.get(position).getRole());
            if(list.get(position).getDLname()!=null) {
                holder.tv_name.setText(list.get(position).getDFname() + " " + list.get(position).getDLname());
            } else {
                holder.tv_name.setText(list.get(position).getDFname());
            }
            holder.tv_date.setText(list.get(position).getReqDate());
            holder.tv_msg.setText(list.get(position).getOMsg());


            holder.tv_date.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_msg.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }
}
