package com.elna.grandpaj;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

class CategoriesAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private final Cursor mCategoriesCursor;
    private OnCategorySelectedListener mListener;

    CategoriesAdapter() {
        mCategoriesCursor = DB.get().getCategories();
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

                mListener.onCategorySelected(holder.itemView.getId());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        mCategoriesCursor.moveToPosition(position);
        int categoryColIdx = mCategoriesCursor.getColumnIndexOrThrow(DB.CATEGORY_NAME_COLUMN);
        String category = mCategoriesCursor.getString(categoryColIdx);
        holder.category.setText(category);

    }

    @Override
    public int getItemCount() {
        return mCategoriesCursor.getCount();
    }

    void setListener(OnCategorySelectedListener l) {
        mListener = l;
    }

    interface OnCategorySelectedListener {
        void onCategorySelected(int categoryId);
    }
}
