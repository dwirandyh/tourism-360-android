package com.dwirandyh.wisatalampung.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dwirandyh.wisatalampung.model.WishList;
import com.dwirandyh.wisatalampung.model.WishListDAO;
import com.dwirandyh.wisatalampung.utils.AttractionDatabase;

import java.util.List;

public class WishListRepository {

    private WishListDAO wishListDAO;

    public WishListRepository(Application application){
        AttractionDatabase attractionDatabase = AttractionDatabase.getInstance(application);
        wishListDAO = attractionDatabase.wishListDAO();
    }

    public LiveData<List<WishList>> getWishList(){
        return wishListDAO.getWishLists();
    }

    public LiveData<WishList> getWishLIstByAttractionId(int attractionId){
        return wishListDAO.getWishList(attractionId);
    }

    public void insertWishList(WishList wishList){
        new InsertWishListAsyncTask(wishListDAO).execute(wishList);
    }

    private static class InsertWishListAsyncTask extends AsyncTask<WishList, Void, Void>{

        private WishListDAO wishListDAO;

        public InsertWishListAsyncTask(WishListDAO wishListDAO){
            this.wishListDAO = wishListDAO;
        }

        @Override
        protected Void doInBackground(WishList... wishLists) {
            wishListDAO.insert(wishLists[0]);
            return null;
        }
    }

    public void deleteWishList(WishList wishList){
        new DeleteWishListAsyncTask(wishListDAO).execute(wishList);
    }

    private static class DeleteWishListAsyncTask extends AsyncTask<WishList, Void, Void>{
        private WishListDAO wishListDAO;

        public DeleteWishListAsyncTask(WishListDAO wishListDAO){
            this.wishListDAO = wishListDAO;
        }

        @Override
        protected Void doInBackground(WishList... wishLists) {
            wishListDAO.delete(wishLists[0]);
            return null;
        }
    }

}
