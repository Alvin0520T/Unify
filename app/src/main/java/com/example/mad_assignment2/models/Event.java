package com.example.mad_assignment2.models;

import java.util.ArrayList;
import java.util.Date;

public class Event {
    private int id; // Change data type to int
    private String title;
    private String description;
    private String organizer;
    private String backgroundLogoUrl;
    private Date startDate;
    private Date endDate;
    private String time;
    private ArrayList<String> vendorIdList;

    public Event(int id, String title, String description, String organizer, String backgroundLogoUrl, Date startDate, Date endDate, String time, ArrayList<String> vendorIdList) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.organizer = organizer;
        this.backgroundLogoUrl = backgroundLogoUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.vendorIdList = vendorIdList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getBackgroundLogoUrl() {
        return backgroundLogoUrl;
    }

    public void setBackgroundLogoUrl(String backgroundLogoUrl) {
        this.backgroundLogoUrl = backgroundLogoUrl;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<String> getVendorIdList() {
        return vendorIdList;
    }

    public void setVendorIdList(ArrayList<String> vendorIdList) {
        this.vendorIdList = vendorIdList;
    }
}
