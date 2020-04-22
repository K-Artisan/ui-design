package com.arzirtime.remote.client.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.arzirtime.remote.R;
import com.arzirtime.remote.client.ui.device.DeviceCenterFragment;
import com.arzirtime.remote.client.ui.mycenter.MyCenterFragment;
import com.arzirtime.remote.client.ui.smart.SmartFragment;
import com.arzirtime.support.util.ToastUtils;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements ISingleActivity {
    private String TAG = MainActivity.class.getSimpleName();

    //bottomNavigationBar，
    //   github:https://github.com/Ashok-Varma/BottomNavigation
    //   Wiki:https://github.com/Ashok-Varma/BottomNavigation/wiki
    private BottomNavigationBar bottomNavigationBar;

    //Fragment
    private List<Fragment> fragments = new ArrayList<>(3);
    private DeviceCenterFragment deviceCenterFragment;
    private SmartFragment smartFragment;
    private MyCenterFragment myCenterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadDefaultFrament();
    }

    /**
     * 在加载Frament之前初始化自身的界面
     */
    @Override
    public void initBeforeLoadFragment(Fragment fragment) {
        if (DeviceCenterFragment.class.isInstance(fragment)) {
            initBeforeLoadDeviceFragment(fragment);
        } else if (SmartFragment.class.isInstance(fragment)) {
            initBeforeLoadSamrtFragment(fragment);
        } else if (MyCenterFragment.class.isInstance(fragment)) {
            initBeforeLoadMyCenterFragment(fragment);
        }
    }

    /**
     * 初始化底部导航栏
     */
    private void initBottomNavigationBar(int firstSelectedPosition) {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.main_bottom_navigation_bar);
/*
        TextBadgeItem numberBadgeItem = new TextBadgeItem();
        ShapeBadgeItem shapeBadgeItem = new ShapeBadgeItem();
*/
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_nav_device_selected, R.string.nav_title_device)
                        .setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.ic_nav_device_normal)))
                .addItem(new BottomNavigationItem(R.drawable.ic_nav_smart_selected, R.string.nav_title_smart)//.setBadgeItem(shapeBadgeItem)
                        .setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.ic_nav_smart_normal)))
                .addItem(new BottomNavigationItem(R.drawable.ic_nav_my_center_selected, R.string.nav_title_my_center)//.setBadgeItem(numberBadgeItem)
                        .setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.ic_nav_my_center_normal)))
                .setFirstSelectedPosition(firstSelectedPosition)
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT)
                .initialise();

        bottomNavigationBar.setAutoHideEnabled(true);
/*
        numberBadgeItem.setBorderWidth(1)
                .setBackgroundColorResource(R.color.black)
                .setText("...")
                .setHideOnSelect(true);

        shapeBadgeItem.setShape(ShapeBadgeItem.SHAPE_OVAL)
                .setGravity(Gravity.TOP | Gravity.END)
                .setHideOnSelect(true).hide();
*/
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            // 设置导航选中的事件
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        loadDeviceFragment();
                        break;

                    case 1:
                        loadSmartFragment();
                        break;

                    case 2:
                        loadMyCenterFragment();
                        break;

                    default:
                        break;
                }
            }

            //设置未选中Fragment 事务
            @Override
            public void onTabUnselected(int position) {
            }

            //设置释放Fragment 事务
            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    /**
     * 加载默认Fragment
     */
    private void loadDefaultFrament() {
        initBottomNavigationBar(0);
        loadDeviceFragment();
    }

    public void loadDeviceFragment() {
        if (deviceCenterFragment == null) {
            deviceCenterFragment = DeviceCenterFragment.newInstance(this);
            fragments.add(deviceCenterFragment);
        }
        switchFragment(deviceCenterFragment);
    }

    public void loadSmartFragment() {
        if (smartFragment == null) {
            smartFragment = SmartFragment.newInstance(this, null, null);
            fragments.add(smartFragment);
        }
        switchFragment(smartFragment);
    }

    public void loadMyCenterFragment() {
        if (myCenterFragment == null) {
            myCenterFragment = MyCenterFragment.newInstance(this);
            fragments.add(myCenterFragment);
        }
        switchFragment(myCenterFragment);
    }

    /**
     * 替换 Fragment(Fragment 会被销毁)
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

    /**
     * 切换 fragment(Fragment 不会被销毁)
     */
    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment.isAdded() == false) {
            transaction.add(R.id.main_fragment_container, fragment);
        }
        for (Fragment fg : fragments) {
            if (fg != fragment) {
                transaction.hide(fg);
            }
        }
        transaction.show(fragment);//.commitAllowingStateLoss();
        transaction.commit();
    }

    private void initBeforeLoadDeviceFragment(Fragment fragment) {
        View fragmentView = fragment.getView();

        //使用Toolbar替换系统的ActionBar
        Toolbar toolbar = fragmentView.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setDisplayHomeAsUpEnabled(true); //启用HomeAsUp按钮，其id永远为：android.R.id.home
            //actionBar.setHomeAsUpIndicator();
        }

        //折叠布局：CollapsingToolbarLayout
        CollapsingToolbarLayout collapsingToolbarLayout = fragmentView.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle(" ");

        //图片
        ImageView deviceImageView = fragmentView.findViewById(R.id.device_imgview);
        Glide.with(this).load(R.drawable.boot_splash2)
                .placeholder(R.drawable.boot_splash2) //加载中显示d 图片
                //.error(R.drawable.device_default) //异常显示的图片
                //.fallback(R.drawable.device_default) //图片为null时显示的图片
                .centerCrop()
                .into(deviceImageView);
    }

    private void initBeforeLoadSamrtFragment(Fragment fragment) {
        View fragmentView = fragment.getView();

        //使用Toolbar替换系统的ActionBar
        Toolbar toolbar = fragmentView.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setDisplayHomeAsUpEnabled(true); //启用HomeAsUp按钮，其id永远为：android.R.id.home
            //actionBar.setHomeAsUpIndicator();
        }

        //折叠布局：CollapsingToolbarLayout
        CollapsingToolbarLayout collapsingToolbarLayout = fragmentView.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle("智能中心");

        //图片
        ImageView deviceImageView = fragmentView.findViewById(R.id.device_imgview);
        Glide.with(this).load(R.drawable.boot_splash)
                .placeholder(R.drawable.boot_splash) //加载中显示d 图片
                //.error(R.drawable.device_default) //异常显示的图片
                //.fallback(R.drawable.device_default) //图片为null时显示的图片
                .centerCrop()
                .into(deviceImageView);

    }

    private void initBeforeLoadMyCenterFragment(Fragment fragment) {

        View fragmentView = fragment.getView();

        //使用Toolbar替换系统的ActionBar
        Toolbar toolbar = fragmentView.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setDisplayHomeAsUpEnabled(true); //启用HomeAsUp按钮，其id永远为：android.R.id.home
            //actionBar.setHomeAsUpIndicator();
        }

        //折叠布局：CollapsingToolbarLayout
        CollapsingToolbarLayout collapsingToolbarLayout = fragmentView.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle("个人中心");

        //图片
        ImageView deviceImageView = fragmentView.findViewById(R.id.mycenter_bar_img);
        Glide.with(this).load(R.drawable.ads_pic2)
                .placeholder(R.drawable.boot_splash2) //加载中显示d 图片
                //.error(R.drawable.device_default) //异常显示的图片
                //.fallback(R.drawable.device_default) //图片为null时显示的图片
                .centerCrop()
                .into(deviceImageView);
    }
}
