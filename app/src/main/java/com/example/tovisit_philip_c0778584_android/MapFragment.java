package com.example.tovisit_philip_c0778584_android;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.tovisit_philip_c0778584_android.Place;
import com.example.tovisit_philip_c0778584_android.BaseMapFragment;
import com.example.tovisit_philip_c0778584_android.FetchAddressStore;
import com.example.tovisit_philip_c0778584_android.GetLocation;
import com.example.tovisit_philip_c0778584_android.GetPlaces;
import com.example.tovisit_philip_c0778584_android.Helper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends BaseMapFragment implements GoogleMap.OnMarkerClickListener {

    private PlaceServiceImpl placeService;
    private EditText et_search;
    private ImageView img_find;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        if (!checkPermission()) {
            requestPermission();
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    dest_lat = latLng.latitude;
                    dest_lng =latLng.longitude;
                    setMarker(latLng);
                }
            });
        }
        mMap.setOnMarkerClickListener(this);
    }

    private void setMarker(LatLng location) {
        mMap.clear();
        String title = FetchAddressStore.execute(getContext(), location);
        MarkerOptions markerOptions = new MarkerOptions().position(location)
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .draggable(true);
        mMap.addMarker(markerOptions);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflateFragment(R.layout.activity_map_fragment, inflater ,container);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        placeService = new PlaceServiceImpl(getContext());
    }

    private void initViews(View view) {
        et_search = view.findViewById(R.id.et_search);
        img_find = view.findViewById(R.id.find);
        img_find.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
    }

    private void getLocation() {
        String searchText = et_search.getText().toString().trim();
        if (searchText.length() > 0) {
            MainActivity activity = (MainActivity) getActivity();
            activity.hideKeyboard();
            et_search.setText("");
            Object[] data = new Object[2];
            data[0] = mMap;
            data[1] = searchText;
            GetLocation location = new GetLocation();
            location.execute(data);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_restaurant:
                searchNearBy("restaurant");
                break;
            case R.id.action_cafe:
                searchNearBy("cafe");
                break;
            case R.id.action_museums:
                searchNearBy("museum");
                break;
            case R.id.action_school:
                searchNearBy("school");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchNearBy(String type) {
        Object[] data = new Object[3];
        data[0] = mMap;
        data[1] = new LatLng(latitude, longitude);
        data[2] = type;
        GetPlaces getPlaces = new GetPlaces();
        getPlaces.execute(data);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        showAlert(getContext(), marker, getName(marker));
        return false;
    }

    private String getName(Marker marker) {
        String[] title = marker.getTitle().split(" : ");
        String name, vicinity;
        if (title.length==2) {
            name = title[0];
            vicinity = title[1];
        } else {
            name = "N/A";
            vicinity = marker.getTitle();
        }
        return  name + " : " + vicinity;
    }

    private void addToVisit(Marker marker) {
        String[] title = getName(marker).split(" : ");
        String name, vicinity;
        name = title[0];
        vicinity = title[1];
        String lat = String.valueOf(marker.getPosition().latitude);
        String lng = String.valueOf(marker.getPosition().longitude);
        Place place = new Place(name, vicinity, lat, lng);
        placeService.insertAll(place);
        Helper.showAlert(getContext(), getName(marker), "Saved successfully.");
    }

    private void showAlert(Context context, final Marker marker, String title) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Do you want to visit this place?");
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addToVisit(marker);
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}