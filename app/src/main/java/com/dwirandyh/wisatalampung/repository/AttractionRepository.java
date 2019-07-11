package com.dwirandyh.wisatalampung.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.model.Gallery;
import com.dwirandyh.wisatalampung.service.AttractionDataService;
import com.dwirandyh.wisatalampung.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AttractionRepository {
    private static String TAG = "AttractionRepository";

    private ArrayList<Attraction> attractions = new ArrayList<>();
    private MutableLiveData<List<Attraction>> mutableAttractions = new MutableLiveData<>();
    private MutableLiveData<Attraction> mutableAttraction = new MutableLiveData<>();
    private MutableLiveData<List<Gallery>> mutableGalleries = new MutableLiveData<>();
    private MutableLiveData<List<Attraction>> mutableSearchAttractions = new MutableLiveData<>();

    private Application application;

    public AttractionRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Gallery>> getGalleries(int id){
        AttractionDataService attractionDataService = RetrofitInstance.getAttractionService();
        Call<List<Gallery>> call = attractionDataService.getGallery(id);

        call.enqueue(new Callback<List<Gallery>>() {
            @Override
            public void onResponse(Call<List<Gallery>> call, Response<List<Gallery>> response) {
                List<Gallery> res = response.body();
                if (res != null){
                    mutableGalleries.setValue(res);
                }
            }

            @Override
            public void onFailure(Call<List<Gallery>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

        return mutableGalleries;
    }

    public MutableLiveData<List<Attraction>> getPopularAttraction() {
        AttractionDataService attractionDataService = RetrofitInstance.getAttractionService();
        Call<List<Attraction>> call = attractionDataService.getPopularAttraction();

        call.enqueue(new Callback<List<Attraction>>() {
            @Override
            public void onResponse(Call<List<Attraction>> call, Response<List<Attraction>> response) {
                List<Attraction> res = response.body();
                if (res != null) {
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

    public MutableLiveData<List<Attraction>> getNearestAtrractions() {
        AttractionDataService attractionDataService = RetrofitInstance.getAttractionService();
        Call<List<Attraction>> call = attractionDataService.getPopularAttraction();

        call.enqueue(new Callback<List<Attraction>>() {
            @Override
            public void onResponse(Call<List<Attraction>> call, Response<List<Attraction>> response) {
                List<Attraction> res = response.body();
                if (res != null) {
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

    public MutableLiveData<List<Attraction>> searchAttractions(String q){
        mutableSearchAttractions = new MutableLiveData<>();
        AttractionDataService attractionDataService = RetrofitInstance.getAttractionService();
        Call<List<Attraction>> call = attractionDataService.searchAttraction(q);

        call.enqueue(new Callback<List<Attraction>>() {
            @Override
            public void onResponse(Call<List<Attraction>> call, Response<List<Attraction>> response) {
                List<Attraction> res = response.body();
                if (res != null){
                    attractions = (ArrayList<Attraction>) res;
                    mutableSearchAttractions.setValue(attractions);
                }
            }

            @Override
            public void onFailure(Call<List<Attraction>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

        return mutableSearchAttractions;
    }

    public MutableLiveData<Attraction> getMutableAttraction() {
        return mutableAttraction;
    }

    public MutableLiveData<Attraction> getAttraction(int id) {
        AttractionDataService attractionDataService = RetrofitInstance.getAttractionService();
        Call<Attraction> call = attractionDataService.getAttractionDetail(id);

        call.enqueue(new Callback<Attraction>() {
            @Override
            public void onResponse(Call<Attraction> call, Response<Attraction> response) {
                Attraction res = response.body();
                if (res != null) {
                    mutableAttraction.setValue(res);
                }
            }

            @Override
            public void onFailure(Call<Attraction> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

        return mutableAttraction;
    }


}
