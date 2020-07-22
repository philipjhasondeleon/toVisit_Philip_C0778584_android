package com.example.tovisit_philip_c0778584_android;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tovisit_philip_c0778584_android.DashboardFragment;
import com.example.tovisit_philip_c0778584_android.PlaceServiceImpl;
import com.example.tovisit_philip_c0778584_android.DetailMapFragment;
import com.example.tovisit_philip_c0778584_android.Place;
import com.example.tovisit_philip_c0778584_android.Helper;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private List<Place> places;
    public PlaceAdapter(List<Place> places) {
        this.places = places;
    }
    private PlaceServiceImpl placeService;

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new PlaceAdapter.PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaceViewHolder holder, int position) {
        final Place place = places.get(position);
        holder.txtVisited.setVisibility(place.isVisited() ? View.VISIBLE : View.GONE);
        holder.itemView.setBackgroundColor(place.isVisited() ? Color.parseColor("#d3d3d3") : Color.parseColor("#ffffff"));
        holder.txtName.setText("Name: " + place.getName());
        holder.txtAddress.setText("Address: " + place.getVicinity());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushToEdit(v, place, false);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(v.getContext(), place, true, v);
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(v.getContext(), place, false, v);
            }
        });
    }

    private void showAlert(final Context context, final Place place, final boolean isDelete, final View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Alert");
        alertDialogBuilder.setMessage(isDelete ? "Are you sure you want to delete this place?" : "Do you want to edit this place?");
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isDelete) {
                    deletePlace(place, context);
                } else {
                    pushToEdit(v, place, true);
                }
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

    private void deletePlace(Place place, Context context) {
        placeService = new PlaceServiceImpl(context);
        placeService.delete(place);
        FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
        List<Fragment> fragments =  manager.getFragments();
        DashboardFragment dashboardFragment = null;
        for (int i=0; i<fragments.size(); i++) {
            if (fragments.get(i) instanceof DashboardFragment) {
                dashboardFragment = (DashboardFragment) fragments.get(i);
            }
        }
        if (dashboardFragment != null) {
            dashboardFragment.getPlacesFromDB();
        }
        Helper.showAlert(context, "Alert!", "Place deleted!");
    }

    private void pushToEdit(View v, Place place, boolean isEdit) {
        DetailMapFragment detailMapFragment = new DetailMapFragment();
        Bundle args = new Bundle();
        args.putParcelable(detailMapFragment.getClass().getName(), place);
        args.putBoolean("isEdit", isEdit);
        detailMapFragment.setArguments(args);
        replaceFragment(detailMapFragment, v);
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

    @Override
    public int getItemCount() {
        return places.size();
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtAddress, txtVisited;
        public ImageView imgDelete, imgEdit;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            txtVisited = itemView.findViewById(R.id.txtVisited);
        }
    }
}
