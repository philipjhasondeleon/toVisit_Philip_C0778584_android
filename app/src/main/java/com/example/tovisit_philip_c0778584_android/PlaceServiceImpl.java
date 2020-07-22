package com.example.tovisit_philip_c0778584_android;

import android.content.Context;

import com.example.tovisit_philip_c0778584_android.Place;

import java.util.List;

public class PlaceServiceImpl implements PlaceService  {
    private PlaceDao placeDao;

    public PlaceServiceImpl(Context context) {
        placeDao = AppDatabase.getInstance(context).placeDao();
    }

    @Override
    public List<Place> getAll() {
        return placeDao.getPlaces();
    }

    @Override
    public void insertAll(Place... places) {
        placeDao.insertAll(places);
    }

    @Override
    public void delete(Place place) {
        placeDao.delete(place);
    }

    @Override
    public void update(Place place) {
        placeDao.update(place);
    }
}
