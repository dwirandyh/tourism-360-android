package com.dwirandyh.wisatalampung.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.model.Gallery;
import com.dwirandyh.wisatalampung.model.WishList;
import com.dwirandyh.wisatalampung.repository.AttractionRepository;
import com.dwirandyh.wisatalampung.repository.WishListRepository;

import java.util.List;

public class AttractionDetailViewModel extends AndroidViewModel {


    private AttractionRepository attractionRepository;
    private WishListRepository wishListRepository;

    private MutableLiveData<WishList> wishList = new MutableLiveData<>();
    private Application application;

    public AttractionDetailViewModel(@NonNull Application application) {
        super(application);
        this.application = application;

        attractionRepository = new AttractionRepository(application);
        wishListRepository = new WishListRepository(application);

    }

    public LiveData<Attraction> getDetailAttraction(int id) {
        return attractionRepository.getAttraction(id);
    }

    public MutableLiveData<Attraction> getAttractionMutableLiveData() {
        return attractionRepository.getMutableAttraction();
    }

    public MutableLiveData<List<Gallery>> getGalleries(int id) {
        return attractionRepository.getGalleries(id);
    }

    public LiveData<WishList> getWishlist(int attractionId) {
        return wishListRepository.getWishLIstByAttractionId(attractionId);
    }


    public void insertWishList(WishList obj) {
        wishListRepository.insertWishList(obj);
        this.wishList.postValue(obj);
    }

    public void deleteWishList(WishList obj) {
        wishListRepository.deleteWishList(obj);
        this.wishList.postValue(null);
    }

}
