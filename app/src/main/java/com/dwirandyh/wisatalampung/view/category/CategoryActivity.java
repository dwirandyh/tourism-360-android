package com.dwirandyh.wisatalampung.view.category;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.adapter.AttractionMapAdapter;
import com.dwirandyh.wisatalampung.databinding.ActivityCategoryBinding;
import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.model.Category;
import com.dwirandyh.wisatalampung.view.attraction.AttractionDetailActivity;
import com.dwirandyh.wisatalampung.view.search.SearchActivity;
import com.dwirandyh.wisatalampung.viewmodel.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    CategoryViewModel categoryViewModel;
    ActivityCategoryBinding activityCategoryBinding;

    Category category = new Category();
    ArrayList<Attraction> attractions = new ArrayList<>();

    private RecyclerView rvAttractions;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        activityCategoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        category = getIntent().getParcelableExtra("category");

        Toolbar toolbar = activityCategoryBinding.toolbar;
        toolbar.setTitle(category.getName());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        swipeRefreshLayout = activityCategoryBinding.swipContainer;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAttractionByCategory();
            }
        });

        rvAttractions = activityCategoryBinding.rvAttraction;

        rvAttractions.setLayoutManager(new LinearLayoutManager(this));
        rvAttractions.setItemAnimator(new DefaultItemAnimator());
        rvAttractions.addItemDecoration(new DividerItemDecoration(rvAttractions.getContext(), DividerItemDecoration.VERTICAL));

        getAttractionByCategory();
    }

    private void getAttractionByCategory(){
        swipeRefreshLayout.setRefreshing(true);
        categoryViewModel.getAttractionsByCategory(category.getId()).observe(this, new Observer<List<Attraction>>() {
            @Override
            public void onChanged(List<Attraction> attractionsLiveData) {
                attractions = (ArrayList<Attraction>) attractionsLiveData;
                showOnRecyclerView();
            }
        });
    }

    private void showOnRecyclerView(){
        AttractionMapAdapter attractionMapAdapter = new AttractionMapAdapter();
        attractionMapAdapter.setAttractions(attractions);
        attractionMapAdapter.setOnItemClickListener(new AttractionMapAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Attraction attraction) {
                Intent intent = new Intent(CategoryActivity.this, AttractionDetailActivity.class);
                intent.putExtra("attraction", attraction);
                startActivity(intent);
            }
        });

        rvAttractions.setAdapter(attractionMapAdapter);
        swipeRefreshLayout.setRefreshing(false);
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
}
