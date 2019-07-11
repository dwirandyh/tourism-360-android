package com.dwirandyh.wisatalampung.view.search;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.adapter.AttractionMapAdapter;
import com.dwirandyh.wisatalampung.databinding.ActivitySearchBinding;
import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.view.attraction.AttractionDetailActivity;
import com.dwirandyh.wisatalampung.viewmodel.SearchViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {


    ActivitySearchBinding activitySearchBinding;
    SearchViewModel searchViewModel;

    private String q = "";

    private SearchView searchView;
    private ArrayList<Attraction> attractions;
    private RecyclerView rvAttractions;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

                Toolbar toolbar = activitySearchBinding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Show status bar
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        swipeRefreshLayout = activitySearchBinding.swipContainer;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchAttraction(q);
            }
        });

        rvAttractions = activitySearchBinding.rvAttraction;

        rvAttractions.setLayoutManager(new LinearLayoutManager(this));
        rvAttractions.setItemAnimator(new DefaultItemAnimator());
        rvAttractions.addItemDecoration(new DividerItemDecoration(rvAttractions.getContext(), DividerItemDecoration.VERTICAL));
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchViewItem.getActionView();
        searchView.setQueryHint("Where are you going?");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                q = query;
                searchAttraction(q);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        searchViewItem.expandActionView();

        return super.onCreateOptionsMenu(menu);
    }

    private void searchAttraction(String query) {
        swipeRefreshLayout.setRefreshing(true);
        searchViewModel.searchAttractions(query).observe(this, new Observer<List<Attraction>>() {
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
                Intent intent = new Intent(SearchActivity.this, AttractionDetailActivity.class);
                intent.putExtra("attraction", attraction);
                startActivity(intent);
            }
        });

        rvAttractions.setAdapter(attractionMapAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }
}
