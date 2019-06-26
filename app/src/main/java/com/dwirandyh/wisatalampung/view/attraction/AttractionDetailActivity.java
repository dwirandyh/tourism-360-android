package com.dwirandyh.wisatalampung.view.attraction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.adapter.TabViewPagerAdapter;
import com.dwirandyh.wisatalampung.databinding.ActivityAttractionDetailBinding;
import com.dwirandyh.wisatalampung.model.Attraction;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

public class AttractionDetailActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    ViewPager viewPager;
    TabLayout tabLayout;

    ActivityAttractionDetailBinding activityAttractionDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_detail);


        Toolbar toolbar = activityAttractionDetailBinding.toolbar;
        setSupportActionBar(toolbar);
        Attraction attraction = getIntent().getParcelableExtra("attraction");


        collapsingToolbarLayout = activityAttractionDetailBinding.collapsingToolbar;
        collapsingToolbarLayout.setTitle(attraction.getName());

        AppBarLayout appBarLayout = activityAttractionDetailBinding.appbar;
        appBarLayout.addOnOffsetChangedListener(onOffsetChangedListener);

        viewPager = activityAttractionDetailBinding.viewpager;
        tabLayout = activityAttractionDetailBinding.tabs;
    }

    private void setupViewPager(ViewPager viewPager){
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());
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
                collapsingToolbarLayout.setTitle("Title");
                isShow = true;
            } else if (isShow) {
                collapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                isShow = false;
            }
        }
    };
}
