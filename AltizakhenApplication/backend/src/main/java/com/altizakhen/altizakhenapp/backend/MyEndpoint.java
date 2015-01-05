/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.altizakhen.altizakhenapp.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public void addItem(@Named("Name") String name, @Named("Location") String location,
                        @Named("Price") int price,  @Named("SellerId") int sellerId,
                        @Named("SellerName") String sellerName,
                        @Named("Description") String description) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Entity item = new Entity("Item");
        item.setProperty("Name", name);
        item.setProperty("Location", location);
        item.setProperty("Price", price);
        item.setProperty("SellerId", sellerId);
        item.setProperty("SellerName", sellerName);
        item.setProperty("Description", description);

        datastore.put(item);
    }

    @ApiMethod(name = "getAllItems")
    public List<Item> getAllItems() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query("Item");
        PreparedQuery pq = datastore.prepare(query);

        List<Item> items = new ArrayList<Item>();
        List<Entity> entities = pq.asList(FetchOptions.Builder.withDefaults());
        for (Entity entity : entities) {
            items.add(entityToItem(entity));
        }

        return items;
    }

    private Item entityToItem(Entity entity) {
        Item item = new Item(
                entity.getProperty("Name").toString(), entity.getProperty("Location").toString(),
                Integer.parseInt(entity.getProperty("Price").toString()), Integer.parseInt(entity.getProperty("SellerId").toString()),
                entity.getProperty("SellerName").toString(), entity.getProperty("Description").toString());
        return item;
    }


}
