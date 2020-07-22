package com.example.tovisit_philip_c0778584_android;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class GetPlaces extends AsyncTask<Object, List<Place>, List<Place>> {
    GoogleMap mMap;
    LatLng location;
    String type;

    @Override
    protected List<Place> doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        List<Place> places = null;
        try {
            location = (LatLng) objects[1];
             type = (String) objects[2];
            places = GoogleAPIStore.fetchPlaces(location, type);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return places;
    }

    @Override
    protected void onPostExecute(List<Place> places) {
        super.onPostExecute(places);
        mMap.clear();
        for (Place place: places) {
            if (type == "cafe") {
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(place.getLocation())
                        .title(place.getName() + " : " + place.getVicinity())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                mMap.addMarker(markerOptions);
            }
            if (type == "restaurant") {
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(place.getLocation())
                        .title(place.getName() + " : " + place.getVicinity())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                mMap.addMarker(markerOptions);
            }
            if (type == "museum") {
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(place.getLocation())
                        .title(place.getName() + " : " + place.getVicinity())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));

                mMap.addMarker(markerOptions);
            }
            if (type == "school") {
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(place.getLocation())
                        .title(place.getName() + " : " + place.getVicinity())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                mMap.addMarker(markerOptions);
            }
        }

    }
}
