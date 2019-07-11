package com.dwirandyh.wisatalampung.service;

import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryDataService {

    @GET("category")
    Call<List<Category>> getCategories();

    @GET("category/{id}/attraction")
    Call<List<Attraction>> getAttractions(@Path("id") int categoryId);
}
