package application.medical.model;

import java.io.Serializable;

public class Hospital implements Serializable {
    private String hospitalName;
    private double hospitalLatitude;
    private double hospitalLongitude;
    private String hospitalLocation;
    private double hospitalRating;


    public Hospital(String hospitalName, double hospitalLatitude, double hospitalLongitude, String hospitalLocation, double hospitalRating) {
        this.hospitalName = hospitalName;
        this.hospitalLatitude = hospitalLatitude;
        this.hospitalLongitude = hospitalLongitude;
        this.hospitalLocation = hospitalLocation;
        this.hospitalRating = hospitalRating;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public double getHospitalLatitude() {
        return hospitalLatitude;
    }

    public double getHospitalLongitude() {
        return hospitalLongitude;
    }

    public String getHospitalLocation() {
        return hospitalLocation;
    }

    public double getHospitalRating() {
        return hospitalRating;
    }
}
