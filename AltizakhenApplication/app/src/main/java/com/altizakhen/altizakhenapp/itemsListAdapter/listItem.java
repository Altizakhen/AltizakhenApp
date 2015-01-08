package com.altizakhen.altizakhenapp.itemsListAdapter;

/**
 * Created by t-mansh on 1/7/2015.
 */
public class listItem {
    private String Name;
    private int Price;
    private String addDate;
    private int iconId;

    public listItem(String name, int price, String date, int iconId) {
        super();
        this.Name = name;
        this.Price = price;
        this.addDate = date;
        this.iconId = iconId;
    }

    public String getName() {
        return Name;
    }
    public int getPrice() {
        return Price;
    }
    public String getAddDate() {
        return addDate;
    }
    public int getIconId() {
        return iconId;
    }
}
