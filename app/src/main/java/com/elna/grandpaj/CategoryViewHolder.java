package com.elna.grandpaj;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class CategoryViewHolder extends RecyclerView.ViewHolder {

    protected final TextView category;
    private final View mCategoryView;


    public CategoryViewHolder(View itemView) {
        super(itemView);

        mCategoryView = itemView;
        category = (TextView) itemView.findViewById(R.id.category_title);
        
    }


}
