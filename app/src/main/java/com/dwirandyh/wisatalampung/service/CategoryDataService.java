package com.dwirandyh.wisatalampung.service;

import com.dwirandyh.wisatalampung.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryDataService {

    @GET("category")
    Call<List<Category>> getCategories();
}
