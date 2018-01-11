package com.elna.grandpaj;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * PrayerBook
 * Created by Arash Payan on 7/4/15.
 */
public class CategoryViewHolder extends RecyclerView.ViewHolder {

    protected final TextView category;
    private final View mCategoryView;


    public CategoryViewHolder(View itemView) {
        super(itemView);

        mCategoryView = itemView;
        category = (TextView) itemView.findViewById(R.id.category_title);
        
    }


}
