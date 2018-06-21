package com.elna.grandpaj;

import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.elna.grandpaj.entities.BioPicture;
import com.elna.grandpaj.entities.Category;
import com.elna.grandpaj.gallery.ImageTypeBig;
import com.elna.grandpaj.gallery.ImageTypeSmallList;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amitm on 21/02/18.
 */


public class BioPictureActivity extends AppCompatActivity {

    private PlaceHolderView mGalleryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_activity);
        mGalleryView = (PlaceHolderView)findViewById(R.id.galleryView);
        setupGallery();
    }

    private void setupGallery(){

        List<BioPicture> imageList = DB.get().getAllPictures();
        List<BioPicture> newImageList = new ArrayList<>();
        for (int i = 0; i <  (imageList.size() > 10 ? 10 : imageList.size()); i++) {
            newImageList.add(imageList.get(i));
        }
        mGalleryView.addView(new ImageTypeSmallList(this.getApplicationContext(), newImageList));

        for (int i = imageList.size() - 1; i >= 0; i--) {
            BioPicture bioPicture = imageList.get(i);
            mGalleryView.addView(new ImageTypeBig(this.getApplicationContext(),
                    mGalleryView,
                    bioPicture.getImage(),
                    bioPicture.getTitle()));
        }

    }

    public static Intent newIntent(Context context, Category category) {
        return new Intent(context, BioPictureActivity.class);
    }
}

