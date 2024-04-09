package com.sidhartsingh.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class License {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String licenseNumber;
    private String licenseState;
    private String licenseExpiration;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Driver driver;

    public License() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseState() {
        return licenseState;
    }

    public void setLicenseState(String licenseState) {
        this.licenseState = licenseState;
    }

    public String getLicenseExpiration() {
        return licenseExpiration;
    }

    public void setLicenseExpiration(String licenseExpiration) {
        this.licenseExpiration = licenseExpiration;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

}
