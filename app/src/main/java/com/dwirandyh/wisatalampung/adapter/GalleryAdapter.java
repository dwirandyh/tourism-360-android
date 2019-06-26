package com.dwirandyh.wisatalampung.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.databinding.AttractionGalleryItemBinding;
import com.dwirandyh.wisatalampung.model.Gallery;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private OnItemClickListener onItemClickListener;
    private ArrayList<Gallery> galleries = new ArrayList<>();

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AttractionGalleryItemBinding attractionGalleryItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.attraction_gallery_item, parent, false);
        return new GalleryViewHolder(attractionGalleryItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        Gallery attractionGallery = galleries.get(position);
        holder.attractionGalleryItemBinding.setGallery(attractionGallery);
    }

    @Override
    public int getItemCount() {
        return galleries.size();
    }

    public void setGallerys(ArrayList<Gallery> galleries) {
        this.galleries = galleries;
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder {
        AttractionGalleryItemBinding attractionGalleryItemBinding;

        GalleryViewHolder(@NonNull AttractionGalleryItemBinding attractionGalleryItemBinding){
            super(attractionGalleryItemBinding.getRoot());

            this.attractionGalleryItemBinding = attractionGalleryItemBinding;
            attractionGalleryItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();

                    if (onItemClickListener != null && clickedPosition != RecyclerView.NO_POSITION){
                        onItemClickListener.onItemClick(galleries.get(clickedPosition));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Gallery attractionGallery);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
