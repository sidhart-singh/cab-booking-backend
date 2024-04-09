package com.sidhartsingh.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sidhartsingh.domain.UserRole;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Driver {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String fullName;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String mobile;
    private Double rating;
    private String password;
    private Double latitude;
    private Double longitude;
    private String profilePicture;
    private UserRole role;
    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
    private License license;
    @JsonIgnore
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ride> rides;
    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Vehicle vehicle;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Ride currentRide;
    private Integer totalRevenue = 0;

    public Driver() {
    }

    public Driver(Integer id, String fullName, String email, String mobile, Double rating, String password, Double latitude, Double longitude, String profilePicture, UserRole role, License license, List<Ride> rides, Vehicle vehicle, Ride currentRide, Integer totalRevenue) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.mobile = mobile;
        this.rating = rating;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
        this.profilePicture = profilePicture;
        this.role = role;
        this.license = license;
        this.rides = rides;
        this.vehicle = vehicle;
        this.currentRide = currentRide;
        this.totalRevenue = totalRevenue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Ride getCurrentRide() {
        return currentRide;
    }

    public void setCurrentRide(Ride currentRide) {
        this.currentRide = currentRide;
    }

    public Integer getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Integer totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
