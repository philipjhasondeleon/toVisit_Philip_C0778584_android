package com.example.tovisit_philip_c0778584_android;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FetchAddressStore {
    public static String execute(Context context, LatLng location) {
        StringBuilder addressName = new StringBuilder();
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses =  geocoder.getFromLocation(location.latitude, location.longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                if (address.getSubThoroughfare() != null) {
                    addressName.append(address.getSubThoroughfare());
                }
                if (address.getThoroughfare() != null) {
                    addressName.append(" " + address.getThoroughfare());
                }
                if (address.getLocality() != null) {
                    addressName.append(", " + address.getLocality());
                }
            } else {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                addressName.append(formatter.format(date));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressName.toString();
    }
}
