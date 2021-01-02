package com.example.astronomicalpictures;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;

public class PictureDetail extends AppCompatActivity {

    TextView title, explanation;
    ImageView image;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);

        title = findViewById(R.id.pd_title);
        image = findViewById(R.id.pd_image);
        explanation = findViewById(R.id.pd_explanation);
        mProgressBar = findViewById(R.id.pd_progress);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Picture picture = extras.getParcelable("EXTRA_PICTURE");

        title.setText(picture.getTitle());

        Glide.with(image)
                .load(picture.getUrl())
                .placeholder(R.drawable.apod_dem)
                .error(R.drawable.apod_dem)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                        mProgressBar.setVisibility(View.GONE);
                        image.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mProgressBar.setVisibility(View.GONE);
                        image.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(image);

        explanation.setText(picture.getExplanation());

    }
}