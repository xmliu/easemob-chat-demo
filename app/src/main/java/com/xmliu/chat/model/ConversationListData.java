package com.xmliu.chat.model;

/**
 * Date: 2016/9/1 11:35
 * Email: diyangxia@163.com
 * Author: diyangxia
 * Description: TODO
 */
public class ConversationListData {
    int userId;
    String username;
    String friend;
    String head;
    String message;
    String time;

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
