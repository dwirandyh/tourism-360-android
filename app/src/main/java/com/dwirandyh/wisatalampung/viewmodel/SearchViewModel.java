package com.dwirandyh.wisatalampung.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.repository.AttractionRepository;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    private AttractionRepository attractionRepository;
    private MutableLiveData<List<Attraction>> attractions;

    public SearchViewModel(Application application) {
        super(application);

        attractionRepository = new AttractionRepository(application);
    }

    public MutableLiveData<List<Attraction>> searchAttractions(String q) {
        return attractionRepository.searchAttractions(q);
    }

}
