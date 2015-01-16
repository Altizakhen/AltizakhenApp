package com.altizakhen.altizakhenapp.backend;

/**
 * Created by personal on 1/14/15.
 */
public class FirebaseChat {
    private String userId1;
    private String userId2;
    private String firebaseChatId;
    private String userName1;
    private String userName2;

    public FirebaseChat(String userId1, String userName1, String userId2, String userName2, String firebaseChatId) {
        this.userId1 = userId1;
        this.userName1 = userName1;
        this.userId2 = userId2;
        this.userName2 = userName2;
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

    public String getUserName2() {
        return userName2;
    }

    public String getUserName1() {
        return userName1;
    }
}
