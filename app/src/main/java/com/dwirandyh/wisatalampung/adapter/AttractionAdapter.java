package com.dwirandyh.wisatalampung.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.databinding.AttractionItemBinding;
import com.dwirandyh.wisatalampung.model.Attraction;

import java.util.ArrayList;

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.AttractionViewHolder> {

    private OnItemClickListener onItemClickListener;
    private ArrayList<Attraction> attractions = new ArrayList<>();

    @NonNull
    @Override
    public AttractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AttractionItemBinding attractionItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.attraction_item, parent, false);
        return new AttractionViewHolder(attractionItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionViewHolder holder, int position) {
        Attraction attraction = attractions.get(position);
        holder.attractionItemBinding.setAttraction(attraction);
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    public void setAttractions(ArrayList<Attraction> attractions) {
        this.attractions = attractions;
    }

    class AttractionViewHolder extends RecyclerView.ViewHolder {
        AttractionItemBinding attractionItemBinding;

        AttractionViewHolder(@NonNull AttractionItemBinding attractionItemBinding){
            super(attractionItemBinding.getRoot());

            this.attractionItemBinding = attractionItemBinding;
            attractionItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
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
        void onItemClick(Attraction attraction);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
