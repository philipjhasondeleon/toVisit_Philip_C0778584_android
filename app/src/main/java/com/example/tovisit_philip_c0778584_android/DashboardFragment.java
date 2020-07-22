package com.example.tovisit_philip_c0778584_android;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tovisit_philip_c0778584_android.MapFragment;
import com.example.tovisit_philip_c0778584_android.Place;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private RecyclerView listView;
    private PlaceAdapter placeAdapter;
    private PlaceServiceImpl placeService;
    private List<Place> placeList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_dashboard_fragment, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initRecyclerView(view);
        placeService = new PlaceServiceImpl(getContext());
        getPlacesFromDB();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initRecyclerView(View view) {
        listView = view.findViewById(R.id.recycle_list);
        placeAdapter = new PlaceAdapter(placeList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listView.addItemDecoration(new DividerItemDecoration(listView.getContext(), DividerItemDecoration.VERTICAL));

        listView.setLayoutManager(layoutManager);
        listView.setAdapter(placeAdapter);
    }

    private void initViews(View view) {
        FloatingActionButton floatingActionButton = view.findViewById(R.id.btn_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapFragment mapFragment = new MapFragment();
                replaceFragment(mapFragment, view);
            }
        });
    }

    private void replaceFragment (Fragment fragment, View v){
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.container, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    public void getPlacesFromDB() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                placeList.clear();
                placeList.addAll(placeService.getAll());
                return null;
            }

            @Override
            protected void onPostExecute(Void agentsCount) {
                placeAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}