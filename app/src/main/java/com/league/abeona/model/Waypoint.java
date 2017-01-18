package com.league.abeona.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jimmy on 1/16/2017.
 */

public class Waypoint {
    @SerializedName("geocoder_status")
    private String Status;
    @SerializedName("place_id")
    private String PlaceID;
    @SerializedName("types")
    private String[] Types;

    public Waypoint(String status, String placeID, String[] types) {
        Status = status;
        PlaceID = placeID;
        Types = types;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPlaceID() {
        return PlaceID;
    }

    public void setPlaceID(String placeID) {
        PlaceID = placeID;
    }

    public String[] getTypes() {
        return Types;
    }

    public void setTypes(String[] types) {
        Types = types;
    }
}
