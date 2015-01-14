package com.altizakhen.altizakhenapp.backend;

/**
 * Created by personal on 1/14/15.
 */
public class FirebaseChat {
    private String userId1;
    private String userId2;
    private String firebaseChatId;

    public FirebaseChat(String userId1, String userId2, String firebaseChatId) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.firebaseChatId = firebaseChatId;
    }

    public String getUserId1() {
        return userId1;
    }

    public String getUserId2() {
        return userId2;
    }

    public String getFirebaseChatId() {
        return firebaseChatId;
    }
}
