package com.example.tovisit_philip_c0778584_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Polyline {
    public String[] points;

    private static final String POLYLINE_KEY = "polyline", POINTS_KEY = "points", ROUTES_KEY = "routes", LEGS_KEY = "legs", STEPS_KEY = "steps";

    public Polyline(JSONObject json) throws JSONException {
        JSONArray jsonArray;
        jsonArray = json.getJSONArray(ROUTES_KEY).getJSONObject(0).getJSONArray(LEGS_KEY).getJSONObject(0).getJSONArray(STEPS_KEY);
        points = new String[jsonArray.length()];
        for (int i=0; i<jsonArray.length(); i++) {
            try {
                setPoints(jsonArray.getJSONObject(i).getJSONObject(POLYLINE_KEY).getString(POINTS_KEY), i);
            } catch (JSONException e) {
                e.getStackTrace();
            }
        }
    }

    public String[] getPoints() {
        return points;
    }

    public void setPoints(String point, int index) {
        this.points[index] = point;
    }
}
