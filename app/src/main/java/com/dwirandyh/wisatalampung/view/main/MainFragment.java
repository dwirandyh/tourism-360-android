package com.dwirandyh.wisatalampung.view.main;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.dwirandyh.wisatalampung.view.category.CategoryActivity;
import com.dwirandyh.wisatalampung.view.category.CategoryListActivity;
import com.dwirandyh.wisatalampung.view.search.SearchActivity;
import com.dwirandyh.wisatalampung.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private ArrayList<Attraction> attractions;
    private RecyclerView recyclerView;

    private MainViewModel mViewModel;
    private MainFragmentBinding mainFragmentBinding;
    private MainFragmentClickHanlders mainFragmentClickHanlders;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mainFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.main_fragment, container, false);

        mainFragmentClickHanlders = new MainFragmentClickHanlders(getContext());
        mainFragmentBinding.setClickHandler(mainFragmentClickHanlders);


        return mainFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        getPopularAttractions();
    }


    private void showOnPopularRecyclerView() {
        recyclerView = getView().findViewById(R.id.rvPopular);

        AttractionAdapter attractionAdapter = new AttractionAdapter();
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


    private void getPopularAttractions(){
        mViewModel.getPopularAttraction().observe(this, new Observer<List<Attraction>>() {
            @Override
            public void onChanged(List<Attraction> attractionsLiveData) {
                attractions = (ArrayList<Attraction>) attractionsLiveData;
                showOnPopularRecyclerView();
            }
        });
    }

    public class MainFragmentClickHanlders {
        Context context;

        public MainFragmentClickHanlders(Context context){
            this.context = context;
        }

        public void onTextSearchClicked(View view){
            Intent intent = new Intent(context, SearchActivity.class);
            startActivity(intent);
        }

        public void onButtonCategoryClicked(View view){
            Intent intent = new Intent(context, CategoryListActivity.class);
            startActivity(intent);
        }
    }
}
