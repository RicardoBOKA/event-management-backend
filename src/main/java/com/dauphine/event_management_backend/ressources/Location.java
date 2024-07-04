package com.dauphine.event_management_backend.ressources;

public class Location {
    private String addressLine;

    private String city;

    private String postalCode;

    private String country;

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return addressLine + ", " + city + ", " + postalCode + ", " + country;
    }

    public static Location fromString(String locationString) {
        String[] parts = locationString.split(", ");
        Location location = new Location();
        location.setAddressLine(parts[0]);
        location.setCity(parts[1]);
        location.setPostalCode(parts[2]);
        location.setCountry(parts[3]);
        return location;
    }
}
