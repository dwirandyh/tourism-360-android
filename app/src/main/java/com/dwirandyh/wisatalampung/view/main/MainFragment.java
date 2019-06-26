package com.dwirandyh.wisatalampung.view.main;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.adapter.AttractionAdapter;
import com.dwirandyh.wisatalampung.adapter.CategoryAdapter;
import com.dwirandyh.wisatalampung.databinding.MainFragmentBinding;
import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.model.Category;
import com.dwirandyh.wisatalampung.view.attraction.AttractionDetailActivity;
import com.dwirandyh.wisatalampung.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private ArrayList<Category> categories;
    private ArrayList<Attraction> attractions;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private AttractionAdapter attractionAdapter;

    private MainViewModel mViewModel;
    private MainFragmentBinding mainFragmentBinding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mainFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.main_fragment, container, false);
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        getCategories();
        getPopularAttractions();
    }

    private void showOnCategoryRecyclerView() {
        recyclerView = getView().findViewById(R.id.rvCategory);

        categoryAdapter = new CategoryAdapter();
        categoryAdapter.setCategories(categories);
        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                Toast.makeText(getContext(), category.getName() + " Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(categoryAdapter);
    }

    private void showOnPopularRecyclerView() {
        recyclerView = getView().findViewById(R.id.rvPopular);

        attractionAdapter = new AttractionAdapter();
        attractionAdapter.setAttractions(attractions);
        attractionAdapter.setOnItemClickListener(new AttractionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Attraction attraction) {
                Intent intent = new Intent(getActivity(), AttractionDetailActivity.class);
                intent.putExtra("attraction", attraction);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(attractionAdapter);
    }

    private void getCategories() {
        mViewModel.getCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categoriesLiveData) {
                categories = (ArrayList<Category>) categoriesLiveData;
                showOnCategoryRecyclerView();
            }
        });
    }

    private void getPopularAttractions(){
        mViewModel.getPopularAttraction().observe(this, new Observer<List<Attraction>>() {
            @Override
            public void onChanged(List<Attraction> attractionsLiveData) {
                attractions = (ArrayList<Attraction>) attractionsLiveData;
                showOnPopularRecyclerView();
            }
        });
    }
}
