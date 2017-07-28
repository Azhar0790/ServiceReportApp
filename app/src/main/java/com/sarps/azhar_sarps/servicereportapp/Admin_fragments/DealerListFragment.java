package com.sarps.azhar_sarps.servicereportapp.Admin_fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.Engineer_fragments.Complaint_detail;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.A_DealerListInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.E_ComplaintListInterface;
import com.sarps.azhar_sarps.servicereportapp.MainActivity_Admin;
import com.sarps.azhar_sarps.servicereportapp.MainActivity_Engineers;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ACityListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ACityMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.AComplaintMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ACountryMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ACountrytListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ADealerListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ADealerMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.AStateListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.AStateMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ECompalintMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.EComplaintList;
import com.sarps.azhar_sarps.servicereportapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DealerListFragment extends Fragment {

    //    @BindView(R.id.tv_title)
//    TextView tv_title;
    @BindView(R.id.rv_dealer_list)
    RecyclerView rv_dealer_list;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.cb_progress_bar)
    fr.castorflex.android.circularprogressbar.CircularProgressBar circularProgressBar;
    public static final String[] PERMISSION_ALL = {
            Manifest.permission.CALL_PHONE,
    };
    public static final int PERMISSION_REQUEST_CODE = 100;

    private DealerlistAdapter dealerlistAdapter;
    List<ADealerMessage> list;
    RecyclerView.LayoutManager layoutManager;
    String user_id;
    private SharedPrefClass mSharedPrefClass;

    List<ACountryMessage> aCountryMessages;
    List<AStateMessage> aStateMessages;
    List<ACityMessage> aCityMessages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_a_dealer_list, container, false);
        ButterKnife.bind(this, rootView);
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSION_ALL, PERMISSION_REQUEST_CODE);
        }
        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_location();
            }
        });
//        circularProgressBar.setVisibility(View.VISIBLE);
//        circularProgressBar.setIndeterminate(true);
//        list = new ArrayList<>();
//        mSharedPrefClass = new SharedPrefClass(getActivity());
//        user_id = mSharedPrefClass.getUserId();
//        getComplaintList(user_id);
        return rootView;
    }


    public class DealerlistAdapter extends RecyclerView.Adapter<DealerlistAdapter.MyViewHolder> {
        Context context;
        private List<ADealerMessage> list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tv_dealer, tv_place, tv_email, tv_address, tv_phone;

            public MyViewHolder(View view) {
                super(view);
                tv_dealer = (TextView) view.findViewById(R.id.tv_dealer);
                tv_place = (TextView) view.findViewById(R.id.tv_place);
                tv_email = (TextView) view.findViewById(R.id.tv_mail);
                tv_address = (TextView) view.findViewById(R.id.tv_address);
                tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            }
        }

        public DealerlistAdapter(Context context, List<ADealerMessage> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.a_dealer_item, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.tv_dealer.setText(list.get(position).getDFname() + " " + list.get(position).getDLname());
            holder.tv_place.setText(list.get(position).getDCity() + ", " + list.get(position).getDState() + ", " + list.get(position).getDCountry());
            holder.tv_email.setText(list.get(position).getDMail());
            holder.tv_address.setText(list.get(position).getDAddress());
            holder.tv_phone.setText(list.get(position).getDMobile());
            holder.tv_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + list.get(position).getDMobile()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
            });
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Fragment fragment = new Complaint_detail();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("location", list.get(position).getDAddress());
//                    fragment.setArguments(bundle);
//                    FragmentManager fm = getActivity().getSupportFragmentManager();
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.replace(R.id.container, fragment);
//                    ft.commit();
//                }
//            });


        }

        @Override
        public int getItemCount() {

            return list.size();
        }
    }

    public void dialog_location() {


        Dialog dialog = new Dialog((MainActivity_Admin) getActivity());
        dialog.setContentView(R.layout.dialog_location);
        Spinner spinner_country = (Spinner) dialog.findViewById(R.id.spinner_country);
        Spinner spinner_state = (Spinner) dialog.findViewById(R.id.spinner_state);
        Spinner spinner_city = (Spinner) dialog.findViewById(R.id.spinner_city);
        Button btn_select = (Button) dialog.findViewById(R.id.btn_select);
        getCountryList(spinner_country, spinner_state, spinner_city, btn_select,dialog);
        dialog.show();

    }


    public void getCountryList(final Spinner country, final Spinner state, final Spinner city, final Button btn_select, final Dialog dialog) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        A_DealerListInterface aDealerListInterface = retrofit.create(A_DealerListInterface.class);
        Call<ACountrytListPojo> call = aDealerListInterface.getJson();
        call.enqueue(new Callback<ACountrytListPojo>() {
            @Override
            public void onResponse(Call<ACountrytListPojo> call, Response<ACountrytListPojo> response) {
                circularProgressBar.setVisibility(View.GONE);
                ACountrytListPojo aCountrytListPojo = response.body();
                aCountryMessages = aCountrytListPojo.getMessage();
                String[] items = new String[aCountryMessages.size()];
                for (int i = 0; i < aCountryMessages.size(); i++) {
                    items[i] = aCountryMessages.get(i).getCName();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
                country.setAdapter(adapter);
                country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ACountryMessage country = aCountryMessages.get(position);
                        getStateList(state, country.getCId(), city, btn_select,dialog);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onFailure(Call<ACountrytListPojo> call, Throwable t) {
                circularProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getStateList(final Spinner state, String cid, final Spinner city, final Button btn_select, final Dialog dialog) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        A_DealerListInterface aDealerListInterface = retrofit.create(A_DealerListInterface.class);
        Call<AStateListPojo> call = aDealerListInterface.getJson(cid);
        call.enqueue(new Callback<AStateListPojo>() {
            @Override
            public void onResponse(Call<AStateListPojo> call, Response<AStateListPojo> response) {
                AStateListPojo aStateListPojo = response.body();
                aStateMessages = aStateListPojo.getMessage();
                String[] items = new String[aStateMessages.size()];
                for (int i = 0; i < aStateMessages.size(); i++) {
                    items[i] = aStateMessages.get(i).getSName();
                }


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
                state.setAdapter(adapter);

                state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        AStateMessage sta = aStateMessages.get(position);
                        Toast.makeText(getActivity(), "Country ID: " + sta.getSId(), Toast.LENGTH_SHORT).show();

                        getCityList(city, sta.getSId(), btn_select,dialog);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onFailure(Call<AStateListPojo> call, Throwable t) {
            }
        });
    }

    public void getCityList(final Spinner city, final String cid, final Button btn_select, final Dialog dialog) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        A_DealerListInterface aDealerListInterface = retrofit.create(A_DealerListInterface.class);
        Call<ACityListPojo> call = aDealerListInterface.getJson_city(cid);
        call.enqueue(new Callback<ACityListPojo>() {
            @Override
            public void onResponse(Call<ACityListPojo> call, Response<ACityListPojo> response) {
                ACityListPojo aStateListPojo = response.body();
                aCityMessages = aStateListPojo.getMessage();
                String[] items = new String[aCityMessages.size()];
                //Traversing through the whole list to get all the names
                for (int i = 0; i < aCityMessages.size(); i++) {
                    items[i] = aCityMessages.get(i).getCityName();
                }


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
                city.setAdapter(adapter);

                city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        final ACityMessage cit = aCityMessages.get(position);

                        btn_select.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getDealerList(cit.getCityId(), dialog);
                            }
                        });


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onFailure(Call<ACityListPojo> call, Throwable t) {
            }
        });
    }

    public void getDealerList(String city_id, final Dialog dialog) {

        circularProgressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        A_DealerListInterface a_DealerListInterface = retrofit.create(A_DealerListInterface.class);
        Call<ADealerListPojo> call = a_DealerListInterface.getJson_dealers(city_id);
        call.enqueue(new Callback<ADealerListPojo>() {
            @Override
            public void onResponse(Call<ADealerListPojo> call, Response<ADealerListPojo> response) {
                dialog.dismiss();
                circularProgressBar.setVisibility(View.GONE);
                ADealerListPojo aDealerListPojo = response.body();
                list = aDealerListPojo.getMessage();
                layoutManager = new LinearLayoutManager(getActivity());
                rv_dealer_list.setLayoutManager(layoutManager);
                dealerlistAdapter = new DealerlistAdapter(getActivity(), list);
                rv_dealer_list.setAdapter(dealerlistAdapter);
            }

            @Override
            public void onFailure(Call<ADealerListPojo> call, Throwable t) {
                circularProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
