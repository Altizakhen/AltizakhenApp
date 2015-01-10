package com.altizakhen.altizakhenapp.backend;

import com.google.appengine.api.datastore.Key;

/**
 * The object model for the data we are sending through endpoints
 */
public class Item {

    private String id;
    private String name;
    private String location;
    private int price;
    private int sellerId;
    private int viewCount;
    private String sellerName;
    private String description;
    private int countView;

    public Item(String id, String name, String location, int price, int sellerId, int viewCount, String sellerName, String description) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.price = price;
        this.sellerId = sellerId;
        this.viewCount = viewCount;
        this.sellerName = sellerName;
        this.description = description;
        this.countView = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}