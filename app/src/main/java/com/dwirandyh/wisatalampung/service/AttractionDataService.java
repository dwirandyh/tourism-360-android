package com.dwirandyh.wisatalampung.service;

import com.dwirandyh.wisatalampung.model.Attraction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AttractionDataService {

    @GET("attraction/popular")
    Call<List<Attraction>> getPopularAttraction();
}
