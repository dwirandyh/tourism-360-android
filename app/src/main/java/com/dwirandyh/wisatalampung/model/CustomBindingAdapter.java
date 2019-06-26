package com.dwirandyh.wisatalampung.model;

import android.os.Build;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.dwirandyh.wisatalampung.utils.Constant;

public class CustomBindingAdapter extends BaseObservable {

    @BindingAdapter({"htmlText"})
    public static void htmlText(TextView textView, String text){
        if (text != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textView.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
            }else{
                textView.setText(Html.fromHtml(text));
            }
        }
    }

    @BindingAdapter({"thumbnailPath"})
    public static void thumbnailPath(ImageView imageView, String imageUrl){
        String imagePath = Constant.BASE_IMAGE_URL + imageUrl;
        Glide.with(imageView.getContext())
                .load(imagePath)
                .into(imageView);
    }
}
