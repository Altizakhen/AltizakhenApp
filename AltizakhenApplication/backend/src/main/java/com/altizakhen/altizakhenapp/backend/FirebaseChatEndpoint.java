package com.altizakhen.altizakhenapp.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "firebaseChatApi",
        version = "v1",
        resource = "firebaseChat",
        namespace = @ApiNamespace(
                ownerDomain = "backend.altizakhenapp.altizakhen.com",
                ownerName = "backend.altizakhenapp.altizakhen.com",
                packagePath = ""
        )
)
public class FirebaseChatEndpoint {

    private static final Logger logger = Logger.getLogger(FirebaseChatEndpoint.class.getName());

    List<FirebaseChat> chats = new ArrayList<FirebaseChat>();

    @ApiMethod(name = "getFirebaseChat")
    public FirebaseChat getFirebaseChat(@Named("userId1") String userId1, @Named("userId2") String userId2) {
        for (FirebaseChat chat : chats) {
            if ((chat.getUserId1().equals(userId1) && chat.getUserId2().equals(userId2)) ||
                    (chat.getUserId1().equals(userId2) && chat.getUserId2().equals(userId1))) {
                return chat;
            }
        }

        int randomInt = new Random().nextInt(500000000);
        FirebaseChat fbchat = new FirebaseChat(userId1, userId2, Integer.toString(randomInt));
        chats.add(fbchat);
        return fbchat;
    }

    @ApiMethod(name = "getAll", path = "get_all")
    public List<FirebaseChat> getAll() {
        return chats;
    }
}