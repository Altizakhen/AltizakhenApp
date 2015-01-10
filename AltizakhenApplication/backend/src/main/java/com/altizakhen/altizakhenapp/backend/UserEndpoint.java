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
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "userApi",
        version = "v1",
        resource = "user",
        namespace = @ApiNamespace(
                ownerDomain = "backend.altizakhenapp.altizakhen.com",
                ownerName = "backend.altizakhenapp.altizakhen.com",
                packagePath = ""
        )
)
public class UserEndpoint {

    private static final Logger logger = Logger.getLogger(UserEndpoint.class.getName());

    @ApiMethod(name = "addUser")
    public User addUser(@Named("Name") String name, @Named("facebookId") String facebookId) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Key key = KeyFactory.createKey("User", name);

        Entity item = new Entity("User", key);
        item.setProperty("name", name);
        item.setProperty("facebookId", facebookId);

        datastore.put(item);

        return entityToUser(item);
    }

    @ApiMethod(name = "getUserItems")
    public List<Item> getUserItems(@Named("userId") String userId) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter itemFilter =
                new Query.FilterPredicate("userId",
                        Query.FilterOperator.EQUAL,
                        userId);

        Query query = new Query("Item").setFilter(itemFilter);
        PreparedQuery pq = datastore.prepare(query);

        List<Item> items = new ArrayList<Item>();
        List<Entity> entities = pq.asList(FetchOptions.Builder.withDefaults());
        for (Entity entity : entities) {
            items.add(ItemEndpoint.entityToItem(entity));
        }

        return items;
    }

    /* internal functions */
    public static Entity getUserFromId(String userId) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Key key = KeyFactory.stringToKey(userId);

        Query.Filter itemFilter =
                new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY,
                        Query.FilterOperator.EQUAL,
                        key);

        Query query = new Query("User").setFilter(itemFilter);
        PreparedQuery pq = datastore.prepare(query);
        return pq.asSingleEntity();

    }


    private User entityToUser(Entity entity) {
        User user = new User(KeyFactory.keyToString(entity.getKey()), entity.getProperty("name").toString(), entity.getProperty("facebookId").toString());
        return user;
    }
}