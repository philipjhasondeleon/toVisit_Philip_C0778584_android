package com.example.tovisit_philip_c0778584_android;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONException;

import java.io.IOException;

public class GetDistance extends AsyncTask<Object, Object, Object[]> {
    GoogleMap mMap;
    Context context;

    @Override
    protected Object[] doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        context = (Context) objects[1];
        Object[] obj = new Object[2];
        Distance distance;
        Polyline polyLine;
        try {
            LatLng[] locations = (LatLng[]) objects[2];
            distance = GoogleAPIStore.fetchDistance(locations[0], locations[1]);
            polyLine = GoogleAPIStore.fetchDirections(locations[0], locations[1]);
            obj[0] = distance;
            obj[1] = polyLine;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    protected void onPostExecute(Object... objects) {
        super.onPostExecute(objects);
        for (Object object: objects) {
            if (object instanceof Polyline) {
                Polyline polyLine = (Polyline) object;
                displayDirections(polyLine.getPoints());
            }
            if (object instanceof Distance) {
                Distance distance = (Distance) object;
                StringBuilder builder = new StringBuilder();
                builder.append("Duration: "+ distance.getDuration() + "\n");
                builder.append("Distance: "+ distance.getDistance());
                Helper.showAlert(context, "Information", builder.toString());
            }
        }
    }

    private void displayDirections(String[] directionList) {
        for (int i=0; i<directionList.length; i++) {
            PolylineOptions options = new PolylineOptions()
                    .color(Color.BLUE)
                    .width(10)
                    .addAll(PolyUtil.decode(directionList[i]));
            mMap.addPolyline(options);
        }
    }

}
