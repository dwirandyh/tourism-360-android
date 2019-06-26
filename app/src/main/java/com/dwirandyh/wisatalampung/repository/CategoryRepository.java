package com.dwirandyh.wisatalampung.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dwirandyh.wisatalampung.model.Category;
import com.dwirandyh.wisatalampung.service.CategoryDataService;
import com.dwirandyh.wisatalampung.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private ArrayList<Category> categories = new ArrayList<>();
    private MutableLiveData<List<Category>> mutableCategories = new MutableLiveData<>();
    private Application application;


    public CategoryRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Category>> getMutableCategories() {
        CategoryDataService categoryDataService = RetrofitInstance.getCategoryService();
        Call<List<Category>> call = categoryDataService.getCategories();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> res = response.body();
                if (res != null) {
                    categories = (ArrayList<Category>) response.body();
                    mutableCategories.setValue(categories);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e(this.getClass().getSimpleName(), t.getMessage());
            }
        });

        return mutableCategories;
    }
}
