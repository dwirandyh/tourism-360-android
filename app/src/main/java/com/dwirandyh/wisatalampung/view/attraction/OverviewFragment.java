package com.dwirandyh.wisatalampung.view.attraction;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.databinding.AttractionOverviewFragmentBinding;
import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.viewmodel.AttractionDetailViewModel;
import com.dwirandyh.wisatalampung.viewmodel.MainViewModel;

public class OverviewFragment extends Fragment {


    AttractionDetailViewModel attractionDetailViewModel;
    AttractionOverviewFragmentBinding attractionOverviewFragmentBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        attractionOverviewFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.attraction_overview_fragment, container, false);
       //mainFragmentBinding = DataBindingUtil.inflate(
        //        inflater, R.layout.main_fragment, container, false);
        return attractionOverviewFragmentBinding.getRoot();
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
                attractionOverviewFragmentBinding.setAttraction(attraction);
            }
        });
    }
}
