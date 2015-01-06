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
    private String sellerName;
    private String description;

    public Item(String id, String name, String location, int price, int sellerId, String sellerName, String description) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.price = price;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.description = description;
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
}