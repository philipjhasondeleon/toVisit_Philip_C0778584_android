package com.example.tovisit_philip_c0778584_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Distance {
    public static final String DISTANCE_KEY = "distance", DURATION_KEY = "duration", TEXT_KEY = "text", ROUTES_KEY = "routes", LEGS_KEY = "legs";
    private String distance, duration;

    public Distance(JSONObject json) throws JSONException {
        JSONArray jsonArray;
        jsonArray = json.getJSONArray(ROUTES_KEY).getJSONObject(0).getJSONArray(LEGS_KEY);
        setDuration(jsonArray.getJSONObject(0).getJSONObject(DURATION_KEY).getString(TEXT_KEY));
        setDistance(jsonArray.getJSONObject(0).getJSONObject(DISTANCE_KEY).getString(TEXT_KEY));
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
