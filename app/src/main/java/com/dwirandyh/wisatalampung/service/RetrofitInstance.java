package com.dwirandyh.wisatalampung.service;

import com.dwirandyh.wisatalampung.utils.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;

    public static Retrofit getInstance(){
        if (retrofit == null){
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static CategoryDataService getCategoryService(){
        return getInstance().create(CategoryDataService.class);
    }

    public static AttractionDataService getAttractionService(){
        return getInstance().create(AttractionDataService.class);
    }
}
