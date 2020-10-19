package com.aor.refactoring.example6;

public class Location {
    private String latitude;
    private String longitude;
    private String name;

    public Location(String lat, String longi, String name) {
        this.latitude = lat;
        this.longitude = longi;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return latitude + "," + longitude + " (" + name + ")";
    }
}
