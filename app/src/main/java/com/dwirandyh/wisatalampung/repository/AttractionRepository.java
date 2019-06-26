package com.dwirandyh.wisatalampung.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.service.AttractionDataService;
import com.dwirandyh.wisatalampung.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttractionRepository {
    private static String TAG = "AttractionRepository";

    private ArrayList<Attraction> attractions = new ArrayList<>();
    private MutableLiveData<List<Attraction>> mutableAttractions = new MutableLiveData<>();
    private Application application;

    public AttractionRepository(Application application){
        this.application = application;
    }

    public MutableLiveData<List<Attraction>> getPopularAttraction() {
        AttractionDataService attractionDataService = RetrofitInstance.getAttractionService();
        Call<List<Attraction>> call = attractionDataService.getPopularAttraction();

        call.enqueue(new Callback<List<Attraction>>() {
            @Override
            public void onResponse(Call<List<Attraction>> call, Response<List<Attraction>> response) {
                List<Attraction> res = response.body();
                if (res != null){
                    attractions = (ArrayList<Attraction>) res;
                    mutableAttractions.setValue(attractions);
                }
            }

            @Override
            public void onFailure(Call<List<Attraction>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

        return mutableAttractions;
    }
}
