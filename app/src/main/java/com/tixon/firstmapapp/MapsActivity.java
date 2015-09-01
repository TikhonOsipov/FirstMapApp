package com.tixon.firstmapapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    ArrayList<Marker> markers;

    public static final String KEY_TITLE = "key_title";
    public static final String KEY_DESCRIPTION = "key_description";
    public static final String KEY_IMAGE = "key_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary700));

        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        setUpMapIfNeeded();

        LocationManager locationManager;
        String svcName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(svcName);

        String provider = LocationManager.GPS_PROVIDER;

        //Location l = locationManager.getLastKnownLocation(provider);
        //updateWithNewLocation(l);
    }

    private void updateWithNewLocation(Location l) {
        String latLngString = "No location found";
        Log.d("myLogs", "is null: " + (l == null));
        if(l != null) {
            double lat = l.getLatitude();
            double lng = l.getLongitude();
            Log.d("myLogs", "latitude = " + lat);
            Log.d("myLogs", "longitude = " + lng);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                //Setting up info window layout
                @Override
                public View getInfoContents(Marker marker) {
                    View view = getLayoutInflater().inflate(R.layout.info_window_layout, null);
                    final String title = marker.getTitle();
                    String markerId = marker.getId();

                    TextView tv_title = (TextView) view.findViewById(R.id.info_window_tv_title);
                    ImageView iv = (ImageView) view.findViewById(R.id.info_window_image_view);

                    tv_title.setText(title);
                    iv.setImageDrawable(getDrawable(markerId));
                    return view;
                }
            });

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Log.d("myLogs", "map clicked");
                    /*FragmentTransaction fragmentTransaction =
                            fragmentManager.beginTransaction();
                    if(detailsFragment != null) {
                        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        fragmentTransaction.remove(detailsFragment);
                        detailsFragment = null;
                    }
                    fragmentTransaction.commit();*/
                }
            });

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    /*Log.d("myLogs", "info window clicked, marker id = " + marker.getId());
                    FragmentTransaction fragmentTransaction =
                            fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                    if(detailsFragment == null) {
                        detailsFragment = DetailsFragment.newInstance(marker.getTitle(), marker.getSnippet());
                        fragmentTransaction.add(R.id.container_details, detailsFragment);
                    } else {
                        detailsFragment = DetailsFragment.newInstance(marker.getTitle(), marker.getSnippet());
                        fragmentTransaction.replace(R.id.container_details, detailsFragment);
                    }
                    fragmentTransaction.commit();*/
                    Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                    intent.putExtra(KEY_TITLE, marker.getTitle());
                    intent.putExtra(KEY_DESCRIPTION, marker.getSnippet());
                    intent.putExtra(KEY_IMAGE, marker.getId());
                    startActivity(intent);
                }
            });

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        markers = new ArrayList<>();

        addMarker(59.9407, 30.3218, "Музей-квартира Пушкина",
                getResources().getString(R.string.pushkin_apartment_snippet));
        addMarker(59.9339, 30.3065, "Исаакиевский собор",
                getResources().getString(R.string.cathedral_snippet));
        addMarker(59.9404, 30.3138, "Зимний дворец",
                getResources().getString(R.string.winter_palace_snippet));
        addMarker(59.931935, 30.304934, getResources().getString(R.string.nabokov_mansion_title),
                getResources().getString(R.string.nabokov_mansion_snippet));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markers.get(0).getPosition(), 12F));
        //CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 12F)
        //markers.get(0).showInfoWindow();
        //moveMap();
    }

    /**
     *
     * @param lat: latitude of place
     * @param lng: longitude of place
     * @param title: title of place
     * @param description: description of place
     */
    private void addMarker(double lat, double lng, String title, String description) {
        LatLng mLatLng = new LatLng(lat, lng);
        MarkerOptions marker =
                new MarkerOptions().position(mLatLng).snippet(description).title(title);
        markers.add(mMap.addMarker(marker));
    }

    private void moveMap() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(Marker marker: markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(cu);
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

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
}
