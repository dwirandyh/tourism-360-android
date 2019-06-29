package com.dwirandyh.wisatalampung.view.attraction;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.viewmodel.AttractionDetailViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AttractionMapFragment extends Fragment implements OnMapReadyCallback {

    AttractionDetailViewModel attractionDetailViewModel;

    GoogleMap mMap;
    LatLng attractionLatlng = new LatLng(-5.3773611, 105.2497009);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.attraction_map_fragment, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        attractionDetailViewModel = ViewModelProviders.of(getActivity()).get(AttractionDetailViewModel.class);
        attractionDetailViewModel.getAttractionMutableLiveData().observe(this, new Observer<Attraction>() {
            @Override
            public void onChanged(Attraction attraction) {
                if (TextUtils.isEmpty(attraction.getLatitude()) && TextUtils.isEmpty(attraction.getLongitude())) {
                    attractionLatlng = new LatLng(Double.valueOf(attraction.getLatitude()), Double.valueOf(attraction.getLongitude()));

                    addMarker(attraction.getName());
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);


        addMarker("Position");
    }

    private void addMarker(String title) {

        mMap.addMarker(new MarkerOptions()
                .position(attractionLatlng)
                .title(title));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(attractionLatlng, 15f)); // 15f is zoom
    }
}
