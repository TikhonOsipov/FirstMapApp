package com.tixon.firstmapapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailsActivity extends ActionBarActivity {
    Toolbar toolbar;
    ImageView iv;
    TextView tv_description;
    ScrollView scrollView;
    String title, description, markerId;
    Drawable image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        title = getIntent().getStringExtra(MapsActivity.KEY_TITLE);
        description = getIntent().getStringExtra(MapsActivity.KEY_DESCRIPTION);
        markerId = getIntent().getStringExtra(MapsActivity.KEY_IMAGE);
        image = getDrawable(markerId);

        iv = (ImageView) findViewById(R.id.details_activity_image_view);
        tv_description = (TextView) findViewById(R.id.details_activity_tv_description);
        scrollView = (ScrollView) findViewById(R.id.details_activity_scroll_view);
        toolbar = (Toolbar) findViewById(R.id.details_activity_toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        iv.setImageDrawable(image);
        tv_description.setText(description);

        scrollView.scrollTo(0, 64);
    }

    private Drawable getDrawable(String markerId) {
        Drawable drawable = null;
        int id = Integer.parseInt(markerId.substring(1));
        switch(id) {
            case 0:
                drawable = getResources().getDrawable(R.drawable.pushkin_apartment);
                break;
            case 1:
                drawable = getResources().getDrawable(R.drawable.cathedral);
                break;
            case 2:
                drawable = getResources().getDrawable(R.drawable.winter_palace);
                break;
            default: break;
        }
        return drawable;
    }
}
