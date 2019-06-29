package com.dwirandyh.wisatalampung.view.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.adapter.AttractionMapAdapter;
import com.dwirandyh.wisatalampung.adapter.CategoryAdapter;
import com.dwirandyh.wisatalampung.adapter.CategoryMapAdapter;
import com.dwirandyh.wisatalampung.databinding.MapFragmentBinding;
import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.model.Category;
import com.dwirandyh.wisatalampung.viewmodel.MainViewModel;
import com.dwirandyh.wisatalampung.viewmodel.MapViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback {


    private ArrayList<Category> categories = new ArrayList<>();
    private RecyclerView rvCategory;
    private ArrayList<Attraction> attractions = new ArrayList<>();
    private RecyclerView rvAttractions;

    private MapViewModel mapViewModel;
    private MapFragmentBinding mapFragmentBinding;

    GoogleMap mMap;
    LatLng centerPosition = new LatLng(-5.3773611, 105.2497009);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mapFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.map_fragment, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return mapFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);

        getCategories();
        getNearestAttraction();
    }


    private void getCategories() {
        mapViewModel.getCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categoriesLiveData) {
                categories = (ArrayList<Category>) categoriesLiveData;
                showOnCategoryRecyclerView();
            }
        });
    }

    private void showOnCategoryRecyclerView() {
        rvCategory = mapFragmentBinding.rvCategory;

        CategoryMapAdapter categoryAdapter = new CategoryMapAdapter();
        categoryAdapter.setCategories(categories);
        categoryAdapter.setOnItemClickListener(new CategoryMapAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                Toast.makeText(getContext(), category.getName() + " Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        rvCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCategory.setItemAnimator(new DefaultItemAnimator());
        rvCategory.setAdapter(categoryAdapter);
    }

    private void getNearestAttraction() {
        mapViewModel.getNearestAttractions().observe(this, new Observer<List<Attraction>>() {
            @Override
            public void onChanged(List<Attraction> attractionsLiveData) {
                attractions = (ArrayList<Attraction>) attractionsLiveData;
                showOnAttractionRecyclerView();
                showAttractionMarker();
            }
        });
    }

    private void showOnAttractionRecyclerView() {
        rvAttractions = mapFragmentBinding.rvNearestAttraction;

        AttractionMapAdapter attractionMapAdapter = new AttractionMapAdapter();
        attractionMapAdapter.setAttractions(attractions);
        attractionMapAdapter.setOnItemClickListener(new AttractionMapAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Attraction attraction) {
                Toast.makeText(getContext(), attraction.getName() + " clicked", Toast.LENGTH_SHORT).show();
            }
        });

        rvAttractions.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAttractions.setItemAnimator(new DefaultItemAnimator());
        rvAttractions.addItemDecoration(new DividerItemDecoration(rvAttractions.getContext(), DividerItemDecoration.VERTICAL));
        rvAttractions.setAdapter(attractionMapAdapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centerPosition, 12f));

        if (attractions.size() > 0){
            showAttractionMarker();
        }
    }

    private void showAttractionMarker() {
        for (Attraction attraction : attractions) {
            LatLng position = new LatLng(Double.valueOf(attraction.getLatitude()), Double.valueOf(attraction.getLongitude()));
            addMarker(attraction.getName(), position);
        }
    }

    private void addMarker(String title, LatLng position) {
        if (mMap != null){
            mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title(title));
        }

        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15f)); // 15f is zoom
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
