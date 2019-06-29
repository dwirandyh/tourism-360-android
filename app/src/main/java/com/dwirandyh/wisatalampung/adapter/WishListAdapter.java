package com.dwirandyh.wisatalampung.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.databinding.WishlistItemBinding;
import com.dwirandyh.wisatalampung.model.WishList;

import java.util.ArrayList;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {

    private OnItemClickListener onItemClickListener;
    private ArrayList<WishList> attractions = new ArrayList<>();

    @NonNull
    @Override
    public WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WishlistItemBinding wishlistItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.wishlist_item, parent, false);
        return new WishListViewHolder(wishlistItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListViewHolder holder, int position) {
        WishList wishList = attractions.get(position);
        holder.wishlistItemBinding.setWishlist(wishList);
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    public void setWishLists(ArrayList<WishList> attractions) {
        this.attractions = attractions;
    }

    class WishListViewHolder extends RecyclerView.ViewHolder {
        WishlistItemBinding wishlistItemBinding;

        WishListViewHolder(@NonNull WishlistItemBinding wishlistItemBinding){
            super(wishlistItemBinding.getRoot());

            this.wishlistItemBinding = wishlistItemBinding;
            wishlistItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();

                    if (onItemClickListener != null && clickedPosition != RecyclerView.NO_POSITION){
                        onItemClickListener.onItemClick(attractions.get(clickedPosition));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(WishList attraction);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
