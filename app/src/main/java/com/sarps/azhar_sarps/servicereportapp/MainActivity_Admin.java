package com.sarps.azhar_sarps.servicereportapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.Admin_fragments.ComplaintListFragment;
import com.sarps.azhar_sarps.servicereportapp.Admin_fragments.DealerListFragment;
import com.sarps.azhar_sarps.servicereportapp.Admin_fragments.OtherRequestListFragment;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.ProfileInterface;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ProfilePojo;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity_Admin extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    @BindView(R.id.iv_profile)
    ImageView iv_profile;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_emailid)
    TextView tv_emailid;
    private SharedPrefClass mSharedPrefClass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPrefClass = new SharedPrefClass(getApplicationContext());
        fragmentManager = getSupportFragmentManager();
        initToolbar();
        initMenuFragment();
        Bundle bundle = getIntent().getExtras();
        try {
            String subject = bundle.getString("subject");
            if (subject.equals("Other Request Subject")) {
                addFragment(new OtherRequestListFragment(), true, R.id.container);
            }
        } catch (Exception e) {
            e.printStackTrace();
            addFragment(new ComplaintListFragment(), true, R.id.container);
        }

    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icon_close);

        MenuObject send = new MenuObject("Dealer List");
        send.setResource(R.drawable.icon_dealer_list);

        MenuObject addFr = new MenuObject("Other Request");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.icon_other_request));
        addFr.setDrawable(bd);

        MenuObject closeApp = new MenuObject("Close App");
        closeApp.setResource(R.drawable.icon_close_app);

        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(addFr);
        menuObjects.add(closeApp);
        return menuObjects;
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationIcon(R.drawable.icon_home);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new ComplaintListFragment(), true, R.id.container);
            }
        });
        mToolBarTextView.setText("Santech Electricals");
    }

    protected void addFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(containerId, fragment, backStackName)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() == 1) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(MainActivity_Admin.this);
            alertbox.setTitle(getResources().getString(R.string.app_name));
            alertbox.setMessage("Do you want to close the app?");
            alertbox.setPositiveButton(getResources().getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    });
            alertbox.setNegativeButton(getResources().getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });
            alertbox.show();
        } else if (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStack();

        } else if (fragmentManager.getBackStackEntryCount() == 0) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(MainActivity_Admin.this);
            alertbox.setTitle(getResources().getString(R.string.app_name));
            alertbox.setMessage("Do you want to close the app?");
            alertbox.setPositiveButton(getResources().getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    });
            alertbox.setNegativeButton(getResources().getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });
            alertbox.show();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onMenuItemClick(View clickedView, int position) {

        switch (position) {
            case 1:
                addFragment(new DealerListFragment(), true, R.id.container);
                break;
            case 2:
                addFragment(new OtherRequestListFragment(), true, R.id.container);
                break;
            case 3:
                finish();
                break;
            default:
        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
        Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();


    }

    public void post_data(String userid, String userole) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ProfileInterface profileInterface = retrofit.create(ProfileInterface.class);
        Call<ProfilePojo> call = profileInterface.postData(userid, userole);
        call.enqueue(new Callback<ProfilePojo>() {

            @Override
            public void onResponse(Call<ProfilePojo> call, Response<ProfilePojo> response) {
                ProfilePojo profilePojo = response.body();

                String name = profilePojo.getMessage().getDFname() + " " + profilePojo.getMessage().getDLname();
                String location = profilePojo.getMessage().getDAddress() + ", " + profilePojo.getMessage().getDCity() + ", " + profilePojo.getMessage().getDCountry();
                String email = profilePojo.getMessage().getDMail();
                String phone = profilePojo.getMessage().getDMobile();
                dialog(name, location, email, phone);
            }

            @Override
            public void onFailure(Call<ProfilePojo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void dialog(String name, String location, String email, String phone) {
        Dialog dialog = new Dialog(MainActivity_Admin.this);
        dialog.setContentView(R.layout.fragment_profile);
        ButterKnife.bind(this, dialog);
        tv_name.setText(name);
        tv_location.setText(location);
        tv_emailid.setText(email);
        tv_phone.setText(phone);
        dialog.show();
    }
}