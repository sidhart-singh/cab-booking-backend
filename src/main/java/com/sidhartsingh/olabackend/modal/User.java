package com.sidhartsingh.olabackend.modal;

import com.sidhartsingh.olabackend.domain.UserRole;
import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String mobile;

    private String password;

    private String profilePicture;

    private UserRole role;

    public User() {
    }
    public User(Integer id, String fullName, String email, String mobile, String password, String profilePicture, UserRole role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.profilePicture = profilePicture;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public UserRole getRote() {
        return role;
    }

    public void setRote(UserRole role) {
        this.role = role;
    }
}
