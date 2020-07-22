package com.example.tovisit_philip_c0778584_android;

import com.example.tovisit_philip_c0778584_android.Place;

import java.util.List;

public interface PlaceService {
    List<Place> getAll();

    void insertAll(Place... places);

    void delete(Place place);

    void update(Place place);
}
