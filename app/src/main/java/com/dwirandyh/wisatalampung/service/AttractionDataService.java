package com.dwirandyh.wisatalampung.service;

import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.model.Gallery;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AttractionDataService {

    @GET("attraction/popular")
    Call<List<Attraction>> getPopularAttraction();

    @GET("attraction/{id}")
    Call<Attraction> getAttractionDetail(@Path("id") int id);

    @GET("attraction/{id}/gallery")
    Call<List<Gallery>> getGallery(@Path("id") int id);
}
