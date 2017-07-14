package com.gluecatcode.radardobem.entities;

import java.io.Serializable;

/**
 * Created by caio on 13/04/17.
 */

public class Localizacao implements Serializable {

    private Float lat = -3.76999f;
    private Float lng = -38.52562f;

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }
}
