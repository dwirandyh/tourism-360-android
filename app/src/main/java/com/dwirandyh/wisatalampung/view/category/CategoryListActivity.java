package com.dwirandyh.wisatalampung.view.category;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import com.dwirandyh.wisatalampung.adapter.CategoryAdapter;
import com.dwirandyh.wisatalampung.databinding.ActivityCategoryListBinding;
import com.dwirandyh.wisatalampung.model.Category;
import com.dwirandyh.wisatalampung.viewmodel.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    CategoryViewModel categoryViewModel;
    ActivityCategoryListBinding activityCategoryListBinding;

    ArrayList<Category> categories = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView rvCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        activityCategoryListBinding = DataBindingUtil.setContentView(this, R.layout.activity_category_list);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);


        Toolbar toolbar = activityCategoryListBinding.toolbar;
        toolbar.setTitle("Category");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        swipeRefreshLayout = activityCategoryListBinding.swipContainer;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCategory();
            }
        });

        rvCategory = activityCategoryListBinding.rvCategory;
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        rvCategory.setItemAnimator(new DefaultItemAnimator());

        getCategory();
    }

    private void getCategory() {
        swipeRefreshLayout.setRefreshing(true);
        categoryViewModel.getCategory().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categoryList) {
                categories = (ArrayList<Category>) categoryList;
                showOnRecyclerView();
            }
        });
    }

    private void showOnRecyclerView(){
        CategoryAdapter categoryAdapter = new CategoryAdapter();
        categoryAdapter.setCategories(categories);
        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                Intent intent = new Intent(CategoryListActivity.this, CategoryActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        rvCategory.setAdapter(categoryAdapter);
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
