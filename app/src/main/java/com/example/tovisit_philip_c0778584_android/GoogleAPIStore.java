package com.example.tovisit_philip_c0778584_android;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleAPIStore {
    private static final String RESULT_KEY = "results";
    private static final int RADIUS = 1500;
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/";
    private static final String API_KEY = "AIzaSyC4-tcWhHKfX1nNspuNUxJVYBPbvjGVW78";

    public static List<Place> fetchPlaces(LatLng location, String type) throws JSONException, IOException {

        StringBuilder placeURL = new StringBuilder(BASE_URL+"place/nearbysearch/json?");
        placeURL.append("location="+location.latitude+","+location.longitude);
        placeURL.append("&radius="+RADIUS);
        placeURL.append("&type="+type);
        placeURL.append("&key="+API_KEY);

        String jsonData = BaseApiStore.request(placeURL.toString());
        List<Place> places = new ArrayList<>();
        if (jsonData != null) {
            JSONArray jsonArray;
            JSONObject json = new JSONObject(jsonData);
            jsonArray = json.getJSONArray(RESULT_KEY);
            // Iteration of json array
            for (int i = 0; i<jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    places.add(new Place(jsonObject));
                } catch (JSONException e) {
                    e.getStackTrace();
                }
            }
            return  places;
        } else {
            return places;
        }
    }

    public static Distance fetchDistance(LatLng location, LatLng dest_location) throws JSONException, IOException {
        StringBuilder url = new StringBuilder(BASE_URL+"directions/json?");
        url.append("origin="+location.latitude+","+location.longitude);
        url.append("&destination="+dest_location.latitude+","+dest_location.longitude);
        url.append("&key="+API_KEY);

        String jsonData = BaseApiStore.request(url.toString());
        Distance distance;
        if (jsonData != null) {
            JSONObject json = new JSONObject(jsonData);
            distance = new Distance(json);
            return  distance;
        }
        return null;
    }

    public static Polyline fetchDirections(LatLng location, LatLng dest_location) throws JSONException, IOException {
        StringBuilder url = new StringBuilder(BASE_URL+"directions/json?");
        url.append("origin="+location.latitude+","+location.longitude);
        url.append("&destination="+dest_location.latitude+","+dest_location.longitude);
        url.append("&key="+API_KEY);

        String jsonData = BaseApiStore.request(url.toString());
        Polyline polyLine;
        if (jsonData != null) {
            JSONObject json = new JSONObject(jsonData);
            polyLine = new Polyline(json);
            return  polyLine;
        }
        return null;
    }

    public static Place FetchLocation(String name) throws JSONException, IOException {
        StringBuilder url = new StringBuilder(BASE_URL+"place/findplacefromtext/json?");
        url.append("input="+name);
        url.append("&inputtype=textquery&fields=formatted_address,name,geometry");
        url.append("&key="+API_KEY);

        Place place = new Place();
        String jsonData = BaseApiStore.request(url.toString());

        if (jsonData != null) {
            JSONObject json = new JSONObject(jsonData);
            JSONObject result = json.getJSONArray("candidates").getJSONObject(0);
            try {
                String address = result.getString("formatted_address");
                String title = result.getString("name");
                JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
                String lat = location.getString("lat");
                String lng = location.getString("lng");
                // create place object
                place.setVicinity(address);
                place.setName(title);
                place.setLatitude(lat);
                place.setLongitude(lng);
                place.setVisited(false);

                return place;
            } catch (JSONException e) {
                e.getStackTrace();
            }
        }
        return null;
    }
}
