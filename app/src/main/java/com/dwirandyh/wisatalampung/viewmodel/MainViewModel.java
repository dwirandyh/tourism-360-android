package com.dwirandyh.wisatalampung.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.model.Category;
import com.dwirandyh.wisatalampung.repository.AttractionRepository;
import com.dwirandyh.wisatalampung.repository.CategoryRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

    private CategoryRepository categoryRepository;
    private AttractionRepository attractionRepository;

    public MainViewModel(@NonNull Application application){
        super(application);

        categoryRepository = new CategoryRepository(application);
        attractionRepository = new AttractionRepository(application);
    }

    public LiveData<List<Category>> getCategories(){
        return categoryRepository.getMutableCategories();
    }

    public LiveData<List<Attraction>> getPopularAttraction(){
        return attractionRepository.getPopularAttraction();
    }
}
