package com.dwirandyh.wisatalampung.view.attraction;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.adapter.TabViewPagerAdapter;
import com.dwirandyh.wisatalampung.databinding.ActivityAttractionDetailBinding;
import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.viewmodel.AttractionDetailViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;


public class AttractionDetailActivity extends AppCompatActivity {

    Attraction attraction = new Attraction();

    CollapsingToolbarLayout collapsingToolbarLayout;
    ViewPager viewPager;
    TabLayout tabLayout;

    ActivityAttractionDetailBinding activityAttractionDetailBinding;
    AttractionDetailViewModel attractionDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_detail);

        activityAttractionDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_attraction_detail);
        activityAttractionDetailBinding.setLifecycleOwner(this);

        attractionDetailViewModel = ViewModelProviders.of(this).get(AttractionDetailViewModel.class);

        attraction = getIntent().getParcelableExtra("attraction");

        activityAttractionDetailBinding.setAttraction(attraction);

        Toolbar toolbar = activityAttractionDetailBinding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
            lp.setMargins(0,24,0,0);
            toolbar.setLayoutParams(lp);
        }

        Attraction attraction = getIntent().getParcelableExtra("attraction");

        collapsingToolbarLayout = activityAttractionDetailBinding.collapsingToolbar;
        collapsingToolbarLayout.setTitle(attraction.getName());

        AppBarLayout appBarLayout = activityAttractionDetailBinding.appbar;
        appBarLayout.addOnOffsetChangedListener(onOffsetChangedListener);

        activityAttractionDetailBinding.scrollView.setFillViewport(true);

        viewPager = activityAttractionDetailBinding.viewpager;
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        setupViewPager(viewPager);

        tabLayout = activityAttractionDetailBinding.tabs;
        tabLayout.setupWithViewPager(viewPager);

        getDetailAttraction(attraction.getId());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getDetailAttraction(int id){
        attractionDetailViewModel.getDetailAttraction(id).observe(this, new Observer<Attraction>() {
            @Override
            public void onChanged(Attraction attractionLivedata) {
                activityAttractionDetailBinding.setAttraction(attractionLivedata);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager){
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OverviewFragment(), "Overview");
        adapter.addFragment(new GalleryFragment(), "Gallery");
        adapter.addFragment(new AttractionMapFragment(), "Location");
        viewPager.setAdapter(adapter);
    }


    AppBarLayout.OnOffsetChangedListener onOffsetChangedListener = new AppBarLayout.OnOffsetChangedListener() {
        boolean isShow = true;
        int scrollRange = -1;

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (scrollRange == -1) {
                scrollRange = appBarLayout.getTotalScrollRange();
            }
            if ((scrollRange) + verticalOffset <= 200) {
                collapsingToolbarLayout.setTitle(attraction.getName());
                isShow = true;
            } else if (isShow) {
                collapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                isShow = false;
            }
        }
    };

}
