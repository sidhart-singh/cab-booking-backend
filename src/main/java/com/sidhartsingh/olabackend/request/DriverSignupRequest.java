package com.sidhartsingh.olabackend.request;

import com.sidhartsingh.olabackend.modal.License;
import com.sidhartsingh.olabackend.modal.Vehicle;

public class DriverSignupRequest {
    private String name;
    private String email;
    private String mobile;
    private String password;
    private Double latitude;
    private Double longitude;
    private License license;
    private Vehicle vehicle;

    public DriverSignupRequest() {
    }

    public DriverSignupRequest(String name, String email, String mobile, String password, Double latitude, Double longitude, License license, Vehicle vehicle) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
        this.license = license;
        this.vehicle = vehicle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
