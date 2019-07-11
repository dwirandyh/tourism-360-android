package com.dwirandyh.wisatalampung.view.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.adapter.WishListAdapter;
import com.dwirandyh.wisatalampung.databinding.WishlistFragmentBinding;
import com.dwirandyh.wisatalampung.model.Attraction;
import com.dwirandyh.wisatalampung.model.WishList;
import com.dwirandyh.wisatalampung.view.attraction.AttractionDetailActivity;
import com.dwirandyh.wisatalampung.viewmodel.WishListViewModel;

import java.util.ArrayList;
import java.util.List;

public class WishListFragment extends Fragment {

    private WishlistFragmentBinding wishlistFragmentBinding;

    private WishListViewModel wishListViewModel;
    private ArrayList<WishList> wishLists = new ArrayList<>();
    private RecyclerView rvWishList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        wishlistFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.wishlist_fragment, container, false);

        Toolbar toolbar = wishlistFragmentBinding.toolbar;
        toolbar.setTitle("My Wishlist");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        return wishlistFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        wishListViewModel = ViewModelProviders.of(this).get(WishListViewModel.class);

        rvWishList = wishlistFragmentBinding.rvWishList;

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                WishList wishList = wishLists.get(viewHolder.getAdapterPosition());
                wishListViewModel.deleteWishList(wishList);
            }
        }).attachToRecyclerView(rvWishList);

        getWishList();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getWishList() {
        wishListViewModel.getWishList().observe(this, new Observer<List<WishList>>() {
            @Override
            public void onChanged(List<WishList> wishListsLiveData) {
                wishLists = (ArrayList<WishList>) wishListsLiveData;

                if (wishLists.size() > 0) {
                    wishlistFragmentBinding.layoutNotFound.setVisibility(View.INVISIBLE);
                    showOnWishListRecyclerView();
                } else {
                    wishlistFragmentBinding.layoutNotFound.setVisibility(View.VISIBLE);
                    wishlistFragmentBinding.rvWishList.setVisibility(View.GONE);
                }

            }
        });
    }


    private void showOnWishListRecyclerView() {


        WishListAdapter wishListAdapter = new WishListAdapter();
        wishListAdapter.setWishLists(wishLists);
        wishListAdapter.setOnItemClickListener(new WishListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WishList wishList) {
                Attraction attraction = new Attraction();
                attraction.setId(wishList.getAttractionId());
                attraction.setName(wishList.getName());
                attraction.setAddress(wishList.getAddress());
                attraction.setThumbnail(wishList.getThumbnail());
                Intent intent = new Intent(getActivity(), AttractionDetailActivity.class);
                intent.putExtra("attraction", attraction);
                startActivity(intent);
            }
        });

        rvWishList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvWishList.setItemAnimator(new DefaultItemAnimator());
        rvWishList.addItemDecoration(new DividerItemDecoration(rvWishList.getContext(), DividerItemDecoration.VERTICAL));
        rvWishList.setAdapter(wishListAdapter);
    }
}
