package com.sarps.azhar_sarps.servicereportapp.Customer_fragments;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.Custom_Adapter.CustomPagerAdapter;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.C_ProductListInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.LoginInterface;
import com.sarps.azhar_sarps.servicereportapp.MainActivity_Admin;
import com.sarps.azhar_sarps.servicereportapp.MainActivity_Customer;
import com.sarps.azhar_sarps.servicereportapp.MainActivity_Dealer;
import com.sarps.azhar_sarps.servicereportapp.MainActivity_Engineers;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CProductListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.LoginPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.Pager_Item;
import com.sarps.azhar_sarps.servicereportapp.Pojo.Rate;
import com.sarps.azhar_sarps.servicereportapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductListFragment extends Fragment {


    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.search_view)
    ImageView search_view;
    @BindView(R.id.iv_refresh)
    ImageView iv_refresh;
    @BindView(R.id.rv_product_list)
    RecyclerView rv_product_list;
    @BindView(R.id.rl_search2)
    RelativeLayout rl_search2;
    @BindView(R.id.rl_search)
    RelativeLayout rl_search;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.imageView)
    View imageView;
    @BindView(R.id.cb_progress_bar)
    fr.castorflex.android.circularprogressbar.CircularProgressBar circularProgressBar;
    View rootView = null;
    Call<CProductListPojo> call;
    ArrayList<CProductListPojo> cProductListPojos;
    ArrayList<CProductListPojo> templist;


    String id = null;
    private SharedPrefClass mSharedPrefClass = null;
    public static final String TAG = ProductListFragment.class.getSimpleName();

    private ProductListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Rate> list;
    ArrayList<Pager_Item> list2;
    Pager_Item pager_item;
    CustomPagerAdapter customPagerAdapter;

    int[] mResources = {
            R.drawable.icon_close,
            R.drawable.icon_close_app,
            R.drawable.icon_dealer_list,
            R.drawable.icon_customers_list,
            R.drawable.icon_profile
    };

    float pixelDensity;
    boolean flag = true;
    int currentPage = 0;
    int NUM_PAGES = 5;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_c_product_list, container, false);
        ButterKnife.bind(this, rootView);
        mSharedPrefClass = new SharedPrefClass(getActivity());
        id = mSharedPrefClass.getUserId();
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        cProductListPojos = new ArrayList<>();

//        pager_item=new Pager_Item();
//        pager_item.setImage(R.drawable.add2);
//        pager_item.setImage(R.drawable.icn_1);
//        pager_item.setImage(R.drawable.icn_2);
//        pager_item.setImage(R.drawable.icn_3);

//        list2.add(pager_item);
        customPagerAdapter = new CustomPagerAdapter(getActivity());
        pager.setAdapter(customPagerAdapter);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES - 1) {
                    currentPage = 0;
                }
                pager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 500, 3000);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rl_search2.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    viewMenu();
                }
            });
        }

        product_list(id);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                System.out.println("s1 :-" + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                adapter.getFilter().filter(s.toString());
                System.out.println("s2 :-" + s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("s3 :-" + s);
                adapter.getFilter().filter(s.toString());

                if (s.length() == 0) {
                    search_view.setVisibility(View.GONE);
                    iv_refresh.setVisibility(View.VISIBLE);
                    iv_refresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            iv_refresh.setVisibility(View.GONE);
                            search_view.setVisibility(View.VISIBLE);
                            product_list(id);
                        }
                    });
                }else {
                    iv_refresh.setVisibility(View.GONE);
                    search_view.setVisibility(View.VISIBLE);
                }
            }
        });



        return rootView;
    }

    public void viewMenu() {

        int x = imageView.getRight();
        int y = imageView.getBottom();
        x -= ((28 * pixelDensity) + (16 * pixelDensity));

        int hypotenuse = (int) Math.hypot(imageView.getWidth(), imageView.getHeight());

        if (flag) {
            FrameLayout.LayoutParams parameters = (FrameLayout.LayoutParams) rl_search2.getLayoutParams();
            parameters.height = imageView.getHeight();
            rl_search2.setLayoutParams(parameters);
            Animator anim = ViewAnimationUtils.createCircularReveal(rl_search2, x, y, 0, hypotenuse);
            anim.setDuration(700);
            rl_search2.setVisibility(View.VISIBLE);
            anim.start();
            flag = false;
        }
    }


    public void product_list(String id) {
        rv_product_list.setVisibility(View.GONE);
        circularProgressBar.setVisibility(View.VISIBLE);
        circularProgressBar.setIndeterminate(true);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        C_ProductListInterface request = retrofit.create(C_ProductListInterface.class);
        call = request.getJson(id);
        call.enqueue(new Callback<CProductListPojo>() {
            @Override
            public void onResponse(Call<CProductListPojo> call, Response<CProductListPojo> response) {
                circularProgressBar.setVisibility(View.GONE);
                rv_product_list.setVisibility(View.VISIBLE);
                CProductListPojo cProductListPojo = response.body();
                list = cProductListPojo.getRates();
                adapter = new ProductListAdapter(getActivity(), list);
                layoutManager = new LinearLayoutManager(getActivity());
                rv_product_list.setLayoutManager(layoutManager);
                rv_product_list.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<CProductListPojo> call, Throwable t) {
                circularProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
        Context context;
        View itemView;
        List<Rate> list;
        List<Rate> list2;
        CProductListPojo cProductListPojo;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tv_product_name, tv_model_no, tv_pr_id;
            public ImageView iv_product;

            public ViewHolder(View itemView) {
                super(itemView);
                iv_product = (ImageView) itemView.findViewById(R.id.iv_product);
                tv_model_no = (TextView) itemView.findViewById(R.id.tv_model_no);
                tv_pr_id = (TextView) itemView.findViewById(R.id.tv_pr_id);
                tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
            }
        }

        public ProductListAdapter(Context context, List<Rate> list) {
            this.context = context;
            this.list = list;
            this.list2 = list;
            cProductListPojo = new CProductListPojo();
        }

        @Override
        public ProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_item, parent, false);
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


            holder.tv_product_name.setText(list.get(position).getPrName());
            holder.tv_model_no.setText(list.get(position).getPrModelno());
            holder.tv_pr_id.setText(list.get(position).getAsPrId());

            holder.tv_product_name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));
            holder.tv_model_no.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fax-sans.beta.otf"));


            Picasso.with(context).load("http://sarps.sarpstechnologies.com/service/assets/uploads/products/" + list.get(position).getPrImage()).error(R.drawable.logo_santech_transparent).into(holder.iv_product);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ViewCompat.setTransitionName(holder.iv_product, "1");
                    Bundle bundle = new Bundle();
                    bundle.putString("model_no", holder.tv_model_no.getText().toString());
                    bundle.putString("product_name", holder.tv_product_name.getText().toString());
                    bundle.putString("pr_id", holder.tv_pr_id.getText().toString());
                    bundle.putString("img_url", list.get(position).getPrImage());
                    ServiceRequestFragment fragment = new ServiceRequestFragment();
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

            int size = 0;
            try {
                size = list.size();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return size;
        }

        private ArrayList<CProductListPojo> filterData(String sel) {
            templist = new ArrayList<>();
            for (int i = 0; i < cProductListPojos.size(); i++) {

                int genrelistsize = cProductListPojos.get(i).getRates().size();

                for (int j = 0; j < genrelistsize; j++) {
                    if (cProductListPojos.get(i).getRates().get(j).getPrName().equals(sel))
                        templist.add(templist.get(i));
                }
            }

            return templist;
        }

        public Filter getFilter() {
            return new Filter() {
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    list = (List<Rate>) results.values;
                    ProductListAdapter.this.notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    List<Rate> filteredResults = null;
                    if (constraint.length() == 0) {
                        filteredResults = list;
                    } else {
                        filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                    }

                    FilterResults results = new FilterResults();
                    results.values = filteredResults;
                    return results;
                }
            };
        }

        protected List<Rate> getFilteredResults(String constraint) {
            List<Rate> results = new ArrayList<>();

            for (Rate item : list) {
                if (item.getPrName().toLowerCase().contains(constraint)) {
                    results.add(item);
                }
            }
            return results;
        }

    }

    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        ArrayList<Pager_Item> list;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context, ArrayList<Pager_Item> list) {
            mContext = context;
            this.list = list;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView.setImageResource(mResources[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
