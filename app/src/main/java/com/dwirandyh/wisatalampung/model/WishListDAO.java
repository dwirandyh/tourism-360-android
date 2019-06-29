package com.dwirandyh.wisatalampung.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WishListDAO {

    @Insert
    void insert(WishList wishList);

    @Delete
    void delete(WishList wishList);

    @Query("select * from wish_list")
    LiveData<List<WishList>> getWishLists();

    @Query("select * from wish_list where attraction_id==:attractionId")
    LiveData<WishList> getWishList(int attractionId);
}
