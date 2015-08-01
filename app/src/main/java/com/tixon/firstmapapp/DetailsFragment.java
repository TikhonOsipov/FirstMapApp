package com.tixon.firstmapapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
    public static final String ARG_TITLE = "details_fragment_title";
    public static final String ARG_DESCRIPTION = "details_fragment_description";
    public static final String ARG_IMAGE = "details_fragment_image";

    public static DetailsFragment newInstance(String title, String description) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ARG_TITLE, title);
        arguments.putString(ARG_DESCRIPTION, description);
        detailsFragment.setArguments(arguments);
        return detailsFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.details_fragment_layout, container, false);
        TextView tv_title, tv_description;
        ImageView iv;
        tv_title = (TextView) v.findViewById(R.id.details_fragment_tv_title);
        tv_description = (TextView) v.findViewById(R.id.details_fragment_tv_description);
        iv = (ImageView) v.findViewById(R.id.details_fragment_image_view);

        tv_title.setText(getArguments().getString(ARG_TITLE));
        tv_description.setText(getArguments().getString(ARG_DESCRIPTION));
        return v;
    }
}
