package com.elna.grandpaj.gallery;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elna.grandpaj.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.LongClick;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;


/**
 * Created by janisharali on 19/08/16.
 */
@Animate(Animation.ENTER_LEFT_DESC)
@NonReusable
@Layout(R.layout.gallery_item_big)
public class ImageTypeBig {

    @View(R.id.photo_view)
    private PhotoView photoView;

//    PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
    //photoView.setImageResource(R.drawable.image);

    @View(R.id.captionTxt)
    private TextView captionTxt;

    private byte[] mImageBlob;
    private String mCaption;
    private Context mContext;
    private PlaceHolderView mPlaceHolderView;

    public ImageTypeBig(Context context, PlaceHolderView placeHolderView, byte[] imageBlob, String caption) {
        mContext = context;
        mPlaceHolderView = placeHolderView;
        mImageBlob = imageBlob;
        mCaption = caption;
    }

    @Resolve
    private void onResolved() {
        captionTxt.setText(mCaption);
        Glide.with(mContext).load(mImageBlob).into(photoView);
    }

//    @LongClick(R.id.imageView)
//    private void onLongClick(){
//        mPlaceHolderView.removeView(this);
//    }
}
