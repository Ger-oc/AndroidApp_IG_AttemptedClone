package com.example.cs4084project;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class Post{
    private String caption;
    //private GoogleMapsThing mapsThing;
    private Bitmap image;
    private String imageStr;

    public Post(Bitmap image, String caption){
        this.image = image;
        this.imageStr = getStringFromBitmap(image);
        this.caption = caption;
    }

    public String getCaption(){
        return caption;
    }

    public Bitmap getImage(){
        if(image != null){
            return image;
        } else {
            return null;
        }
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setImage(Bitmap image) {
        this.image = image;
        this.imageStr = getStringFromBitmap(image);
    }

    private String getStringFromBitmap(Bitmap bitmapPicture) {
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }
}


