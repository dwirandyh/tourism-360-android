package com.dwirandyh.wisatalampung.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dwirandyh.wisatalampung.model.WishList;
import com.dwirandyh.wisatalampung.repository.WishListRepository;

import java.util.List;

public class WishListViewModel extends AndroidViewModel {

    private WishListRepository wishListRepository;
    private LiveData<List<WishList>> wishList;

    public WishListViewModel(@NonNull Application application){
        super(application);

        wishListRepository = new WishListRepository(application);
    }

    public LiveData<List<WishList>> getWishList(){
        wishList = wishListRepository.getWishList();
        return wishList;
    }

    public void addNewWishList(WishList wishList){
        wishListRepository.insertWishList(wishList);
    }

    public void deleteWishList(WishList wishList){
        wishListRepository.deleteWishList(wishList);
    }
}
