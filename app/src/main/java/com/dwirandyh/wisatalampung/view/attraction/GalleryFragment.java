package com.dwirandyh.wisatalampung.view.attraction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.adapter.AttractionAdapter;
import com.dwirandyh.wisatalampung.adapter.GalleryAdapter;
import com.dwirandyh.wisatalampung.databinding.AttractionGalleryFragmentBinding;
import com.dwirandyh.wisatalampung.databinding.AttractionOverviewFragmentBinding;
import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.model.Gallery;
import com.dwirandyh.wisatalampung.view.vr.VRActivity;
import com.dwirandyh.wisatalampung.viewmodel.AttractionDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {


    AttractionDetailViewModel attractionDetailViewModel;
    AttractionGalleryFragmentBinding attractionGalleryFragmentBinding;

    ArrayList<Gallery> galleries;

    RecyclerView recyclerView;
    GalleryAdapter galleryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        attractionGalleryFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.attraction_gallery_fragment, container, false);
        return attractionGalleryFragmentBinding.getRoot();
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
                getGalleries(attraction.getId());
            }
        });
    }

    private void getGalleries(int id){
        attractionDetailViewModel.getGalleries(id).observe(this, new Observer<List<Gallery>>() {
            @Override
            public void onChanged(List<Gallery> galleriesLiveData) {
                galleries = (ArrayList<Gallery>) galleriesLiveData;
                showOnGalleriesRecyclerView();
            }
        });
    }

    private void showOnGalleriesRecyclerView() {
        recyclerView = attractionGalleryFragmentBinding.rvGallery;

        galleryAdapter = new GalleryAdapter();
        galleryAdapter.setGallerys(galleries);
        galleryAdapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Gallery gallery) {
                Intent intent = new Intent(getActivity(), VRActivity.class);
                intent.putExtra("galleryFileName", gallery.getThumbnail());
                if (gallery.getOverviewFile().isEmpty()){
                    gallery.setOverviewFile("");
                }
                intent.putExtra("overviewFileName", gallery.getOverviewFile());
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(galleryAdapter);
    }
}
