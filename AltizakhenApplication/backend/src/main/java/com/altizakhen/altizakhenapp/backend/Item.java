package com.altizakhen.altizakhenapp.backend;

/**
 * The object model for the data we are sending through endpoints
 */
public class Item {

    private String id;
    private String name;
    private String location;
    private String categoryName;
    private int price;

    //Manar
    private String addDate;
    private int iconId;

    private String userId;
    private int viewCount;
    private String sellerName;
    private String description;

    public Item(String id, String name, String location, String categoryName, int price, String userId, int viewCount, String sellerName, String description) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.categoryName = categoryName;
        this.price = price;
        this.userId = userId;
        this.viewCount = viewCount;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getAddDate() {
        return addDate;
    }
    public int getIconId() {
        return iconId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}