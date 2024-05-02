package com.example.mad_assignment2.models;

public class Vendor {
    private String name;
    private String email;
    private String password;
    private String description;
    private String category;
    private String imageUrl;
    private double rating;
    private int boothLocation;
    private String boothDirectory;
    private int buttonId;
    public Vendor(String name, String email, String password, String description, String category, String imageUrl, double rating, int boothLocation, String boothDirectory) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.buttonId = boothLocation;
        this.boothDirectory = boothDirectory ;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getBoothLocation() {
        return boothLocation;
    }

    public void setBoothLocation(int boothLocation) {
        this.boothLocation = boothLocation;
    }

    public String getBoothDirectory() {
        return boothDirectory;
    }

    public void setBoothDirectory(String boothDirectory) {
        this.boothDirectory = boothDirectory;
    }
    public int getButtonId() {
        return buttonId;
    }
}
