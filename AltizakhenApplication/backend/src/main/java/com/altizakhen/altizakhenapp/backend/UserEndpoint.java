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

        Entity user = getUserByFacebookId(facebookId);
        if (user != null) {
            // User already added to database. Return it.
            return entityToUser(user);
        }

        Key key = KeyFactory.createKey("User", name);

        Entity item = new Entity("User", key);
        item.setProperty("name", name);
        item.setProperty("facebookId", facebookId);

        datastore.put(item);

        return entityToUser(item);
    }

    @ApiMethod(name = "getAllUsers")
    public List<User> getAllUsers() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query("User");
        PreparedQuery pq = datastore.prepare(query);

        List<User> items = new ArrayList<User>();
        List<Entity> entities = pq.asList(FetchOptions.Builder.withDefaults());
        for (Entity entity : entities) {
            items.add(entityToUser(entity));
        }

        return items;
    }

    /* internal functions */

    private Entity getUserByFacebookId(String facebookId) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter itemFilter =
                new Query.FilterPredicate("facebookId",
                        Query.FilterOperator.EQUAL,
                        facebookId);

        Query query = new Query("User").setFilter(itemFilter);
        PreparedQuery pq = datastore.prepare(query);
        return pq.asSingleEntity();

    }

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

    public static String getUserNameFromId(String userId) {
        return getUserFromId(userId).getProperty("name").toString();
    }


    private User entityToUser(Entity entity) {
        User user = new User(KeyFactory.keyToString(entity.getKey()), entity.getProperty("name").toString(), entity.getProperty("facebookId").toString());
        return user;
    }
}