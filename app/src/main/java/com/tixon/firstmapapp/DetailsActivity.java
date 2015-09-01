package com.tixon.firstmapapp;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailsActivity extends ActionBarActivity {
    ImageView iv;
    TextView tv_description;
    ScrollView scrollView;
    String title, description, markerId;
    Drawable image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary700));

        title = getIntent().getStringExtra(MapsActivity.KEY_TITLE);
        description = getIntent().getStringExtra(MapsActivity.KEY_DESCRIPTION);
        markerId = getIntent().getStringExtra(MapsActivity.KEY_IMAGE);
        image = getDrawable(markerId);

        iv = (ImageView) findViewById(R.id.details_activity_image_view);
        tv_description = (TextView) findViewById(R.id.details_activity_tv_description);
        scrollView = (ScrollView) findViewById(R.id.details_activity_scroll_view);

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        iv.setImageDrawable(image);
        tv_description.setText(description);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
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
