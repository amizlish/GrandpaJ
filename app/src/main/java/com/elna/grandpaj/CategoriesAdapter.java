package com.elna.grandpaj;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elna.grandpaj.entities.Category;

import java.util.List;

class CategoriesAdapter extends RecyclerView.Adapter<CategoryViewHolder> {


    private final List<Category> mCategories;
    private OnCategorySelectedListener mListener;

    CategoriesAdapter() {
        mCategories = DB.get().getCategories();
        setHasStableIds(false);
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
        final CategoryViewHolder holder = new CategoryViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) {
                    return;
                }
                mListener.onCategorySelected(holder.getLayoutPosition());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        String category = mCategories.get(position).getName();
        holder.category.setText(category);

    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    void setListener(OnCategorySelectedListener l) {
        mListener = l;
    }

    interface OnCategorySelectedListener {
        void onCategorySelected(int categoryId);
    }
}
