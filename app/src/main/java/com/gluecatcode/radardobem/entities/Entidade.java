package com.gluecatcode.radardobem.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by caio on 13/04/17.
 */

public class Entidade implements Serializable {

    @SerializedName("ci_local")
    private Integer id;
    @SerializedName("nm_local")
    private String name;
    @SerializedName("nr_lat")
    private Float latitude;
    @SerializedName("nr_lng")
    private Float longitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
}
