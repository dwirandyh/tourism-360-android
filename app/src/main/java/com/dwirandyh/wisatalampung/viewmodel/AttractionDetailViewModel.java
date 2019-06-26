package com.dwirandyh.wisatalampung.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.model.Gallery;
import com.dwirandyh.wisatalampung.repository.AttractionRepository;

import java.util.List;

public class AttractionDetailViewModel extends AndroidViewModel {


    private AttractionRepository attractionRepository;

    public AttractionDetailViewModel(@NonNull Application application) {
        super(application);

        attractionRepository = new AttractionRepository(application);
    }

    public LiveData<Attraction> getDetailAttraction(int id){
        return attractionRepository.getAttraction(id);
    }

    public MutableLiveData<Attraction> getAttractionMutableLiveData(){
        return attractionRepository.getMutableAttraction();
    }

    public MutableLiveData<List<Gallery>> getGalleries(int id){
        return attractionRepository.getGalleries(id);
    }

}
