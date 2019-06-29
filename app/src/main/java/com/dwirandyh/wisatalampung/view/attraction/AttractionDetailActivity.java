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

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.adapter.TabViewPagerAdapter;
import com.dwirandyh.wisatalampung.databinding.ActivityAttractionDetailBinding;
import com.dwirandyh.wisatalampung.databinding.WishlistItemBinding;
import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.model.WishList;
import com.dwirandyh.wisatalampung.repository.WishListRepository;
import com.dwirandyh.wisatalampung.viewmodel.AttractionDetailViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.Locale;


public class AttractionDetailActivity extends AppCompatActivity {


    Attraction attraction = new Attraction();
    WishList wishList;

    CollapsingToolbarLayout collapsingToolbarLayout;
    ViewPager viewPager;
    TabLayout tabLayout;

    ActivityAttractionDetailBinding activityAttractionDetailBinding;
    AttractionDetailViewModel attractionDetailViewModel;
    AttractionDetailClickHandlers handlers;

    LatLng attractionLatlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_detail);

        activityAttractionDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_attraction_detail);
        activityAttractionDetailBinding.setLifecycleOwner(this);

        handlers = new AttractionDetailClickHandlers(this);
        activityAttractionDetailBinding.setClickHandler(handlers);

        attractionDetailViewModel = ViewModelProviders.of(this).get(AttractionDetailViewModel.class);

        attraction = getIntent().getParcelableExtra("attraction");

        activityAttractionDetailBinding.setAttraction(attraction);


        Toolbar toolbar = activityAttractionDetailBinding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
            lp.setMargins(0, 24, 0, 0);
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

        checkWishList();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getDetailAttraction(int id) {
        attractionDetailViewModel.getDetailAttraction(id).observe(this, new Observer<Attraction>() {
            @Override
            public void onChanged(Attraction attractionLivedata) {
                attraction = attractionLivedata;
                activityAttractionDetailBinding.setAttraction(attractionLivedata);
                if (!TextUtils.isEmpty(attraction.getLatitude()) && !TextUtils.isEmpty(attraction.getLongitude())) {
                    attractionLatlng = new LatLng(Double.valueOf(attraction.getLatitude()), Double.valueOf(attraction.getLongitude()));
                }
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OverviewFragment(), "Overview");
        adapter.addFragment(new GalleryFragment(), "Gallery");
        adapter.addFragment(new AttractionMapFragment(), "Location");
        viewPager.setAdapter(adapter);
    }

    private void getDirection() {
        if (attractionLatlng != null) {
            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", attractionLatlng.latitude, attractionLatlng.longitude, "Location");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                try {
                    Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(unrestrictedIntent);
                } catch (ActivityNotFoundException innerEx) {
                    Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void checkWishList() {
        attractionDetailViewModel.getWishlist(attraction.getId()).observe(this, new Observer<WishList>() {
            @Override
            public void onChanged(WishList wishListLiveData) {
                wishList = wishListLiveData;
                if (wishList == null) {
                    activityAttractionDetailBinding.imageWishList.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border));
                } else {
                    activityAttractionDetailBinding.imageWishList.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                }
            }
        });
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

    public class AttractionDetailClickHandlers {
        Context context;

        AttractionDetailClickHandlers(Context context) {
            this.context = context;
        }

        public void onGetDirectionClicked(View view) {
            getDirection();
        }

        public void onWishListClicked(View view) {
            if (wishList == null) {
                WishList obj = new WishList();
                obj.setAttractionId(attraction.getId());
                obj.setName(attraction.getName());
                obj.setAddress(attraction.getAddress());
                obj.setThumbnail(attraction.getThumbnail());
                obj.setShortDescription(attraction.getShortDescription());
                attractionDetailViewModel.insertWishList(obj);
                Toast.makeText(AttractionDetailActivity.this, "Successfully Added to wish list", Toast.LENGTH_SHORT).show();
            } else {
                attractionDetailViewModel.deleteWishList(wishList);
                Toast.makeText(AttractionDetailActivity.this, "Deleted from your wish list", Toast.LENGTH_SHORT).show();
            }
            //checkWishList();
        }
    }
}