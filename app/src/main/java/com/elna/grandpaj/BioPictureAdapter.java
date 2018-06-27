package com.elna.grandpaj;

/**
 * Created by amitm on 21/02/18.
 */


import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.elna.grandpaj.entities.BioPicture;

public class BioPictureAdapter extends ArrayAdapter<BioPicture>{

    Context context;
    int layoutResourceId;
    // BcardImage data[] = null;
    ArrayList<BioPicture> data=new ArrayList<BioPicture>();

    public BioPictureAdapter(Context context, int layoutResourceId, ArrayList<BioPicture> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ImageHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ImageHolder();
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            row.setTag(holder);

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        else
        {
            holder = (ImageHolder)row.getTag();
        }

        BioPicture picture = data.get(position);
        holder.txtTitle.setText(picture.getTitle());

        //convert byte to bitmap take from contact class

        byte[] outImage=picture.getImage();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        holder.imgIcon.setImageBitmap(theImage);
        return row;

    }

    static class ImageHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}
