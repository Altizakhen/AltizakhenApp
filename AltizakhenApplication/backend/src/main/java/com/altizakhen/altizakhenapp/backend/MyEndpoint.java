/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.altizakhen.altizakhenapp.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(name = "altizakhenApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.altizakhenapp.altizakhen.com", ownerName = "backend.altizakhenapp.altizakhen.com", packagePath = ""))
public class MyEndpoint {

    List<Item> items = new ArrayList<Item>() {{
        add(new Item("Chair", "Taub", 20, 13, "3bse", "Best Chair Ever! but its broken.."));
        add(new Item("Bed", "Taub", 20, 13, "3bse", "Its a bed.. broken though.."));
    }};

    @ApiMethod(name = "addItem")
    public Item addItem(@Named("name") String name, @Named("location") String location,
                        @Named("price") int price,  @Named("sellerId") int sellerId,
                        @Named("sellerName") String sellerName,
                        @Named("description") String description) {
        Item item = new Item(name, location, price, sellerId, sellerName, description);
        items.add(item);

        return item;
    }

    @ApiMethod(name = "getAllItems")
    public List<Item> getAllItems() {
        return items;
    }


}
