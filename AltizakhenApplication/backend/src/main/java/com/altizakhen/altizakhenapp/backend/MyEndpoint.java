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
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
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

    @ApiMethod(name = "addItem")
    public Item addItem(@Named("Name") String name, @Named("Location") String location,
                        @Named("Price") int price,  @Named("SellerId") int sellerId,
                        @Named("SellerName") String sellerName,
                        @Named("Description") String description) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Key key = KeyFactory.createKey("Item", name);

        Entity item = new Entity("Item", key);
        item.setProperty("Name", name);
        item.setProperty("Location", location);
        item.setProperty("Price", price);
        item.setProperty("SellerId", sellerId);
        item.setProperty("SellerName", sellerName);
        item.setProperty("Description", description);

        datastore.put(item);

        return entityToItem(item);
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

    @ApiMethod(name = "deleteItem")
    public void deleteItem(@Named("itemId") String itemId) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        datastore.delete(KeyFactory.stringToKey(itemId));
    }

    private Item entityToItem(Entity entity) {
        Item item = new Item(KeyFactory.keyToString(entity.getKey()),
                entity.getProperty("Name").toString(), entity.getProperty("Location").toString(),
                Integer.parseInt(entity.getProperty("Price").toString()), Integer.parseInt(entity.getProperty("SellerId").toString()),
                entity.getProperty("SellerName").toString(), entity.getProperty("Description").toString());

        return item;
    }


}
