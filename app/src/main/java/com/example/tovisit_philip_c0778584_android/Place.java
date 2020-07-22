package com.example.tovisit_philip_c0778584_android;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "place_table")
public class Place implements Parcelable {
    public static final String NAME_KEY = "name", VIC_KEY = "vicinity", LAT_KEY = "lat", LNG_KEY = "lng", REF_KEY = "reference", GEO_KEY = "geometry", LOC_KEY = "location";

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "vicinity")
    private String vicinity;
    @ColumnInfo(name = "latitude")
    private String latitude;
    @ColumnInfo(name = "longitude")
    private String longitude;
    @ColumnInfo(name = "reference")
    private String reference;
    @ColumnInfo(name = "isVisited")
    private boolean isVisited;

    protected Place(Parcel in) {
        id = in.readInt();
        name = in.readString();
        vicinity = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        reference = in.readString();
        isVisited = in.readBoolean();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public void setId(int id) {this.id = id;}
    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLocation() {
        return new LatLng(Double.valueOf(getLatitude()), Double.valueOf(getLongitude()));
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Ignore
    public Place(JSONObject json) throws JSONException {
        if (!json.isNull(NAME_KEY)) {
            setName(json.getString(NAME_KEY));
        } else {
            setName("N/A");
        }
        if (!json.isNull(VIC_KEY)) {
            setVicinity(json.getString(VIC_KEY));
        } else {
            setVicinity("N/A");
        }
        JSONObject geometry = json.getJSONObject(GEO_KEY);
        JSONObject location = geometry.getJSONObject(LOC_KEY);
        setLatitude(location.getString(LAT_KEY));
        setLongitude(location.getString(LNG_KEY));
        setReference(json.getString(REF_KEY));
    }

    public Place(@NonNull String name, @NonNull String vicinity, @NonNull String latitude, @NonNull String longitude) {
        this.name = name;
        this.vicinity = vicinity;
        this.latitude = latitude;
        this.longitude = longitude;
        setVisited(false);
    }

    @Ignore
    public Place() {}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(vicinity);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(reference);
        dest.writeBoolean(isVisited);
    }

}
