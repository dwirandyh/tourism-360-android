package com.dwirandyh.wisatalampung.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.repository.AttractionRepository;
import com.dwirandyh.wisatalampung.repository.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository categoryRepository;
    private MutableLiveData<List<Attraction>> attractions;

    public CategoryViewModel(Application application) {
        super(application);

        categoryRepository = new CategoryRepository(application);
    }

    public MutableLiveData<List<Attraction>> getAttractionsByCategory(int categoryId){
        attractions =  categoryRepository.getMutableAttractionsByCategory(categoryId);
        return attractions;
    }

}
