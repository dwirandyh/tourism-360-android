package com.dwirandyh.wisatalampung.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.databinding.CategoryItemBinding;
import com.dwirandyh.wisatalampung.databinding.CategoryMapItemBinding;
import com.dwirandyh.wisatalampung.model.Category;

import java.util.ArrayList;

public class CategoryMapAdapter extends RecyclerView.Adapter<CategoryMapAdapter.CategoryViewHolder> {

    private OnItemClickListener onItemClickListener;
    private ArrayList<Category> categories = new ArrayList<>();

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryMapItemBinding categoryItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.category_map_item, parent, false);
        return new CategoryViewHolder(categoryItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int position) {
        Category category = categories.get(position);
        categoryViewHolder.categoryItemBinding.setCategory(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{

        CategoryMapItemBinding categoryItemBinding;

        CategoryViewHolder(@NonNull CategoryMapItemBinding categoryItemBinding) {
            super(categoryItemBinding.getRoot());

            this.categoryItemBinding = categoryItemBinding;
            categoryItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();

                    if (onItemClickListener != null && clickedPosition != RecyclerView.NO_POSITION){
                        onItemClickListener.onItemClick(categories.get(clickedPosition));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Category category);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }
}
