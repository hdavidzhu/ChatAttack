package com.hdavidzhu.chatattack;

import java.io.Serializable;

// Comparable allows the Chat class for things to compareTo one another.
// Serializable allows for Chat to
public class Chat implements Comparable, Serializable {
    private String name, message;
    private long timestamp;

    public Chat(String name, String message){
        this.name = name;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters
    // They get stuff for Chat
    public String getName(){ return name; }
    public Long getTimestamp(){ return timestamp; }
    public String getMessage(){ return message; }

    // Setters
    public void setName(String name){ this.name = name; }
    public void setTimestamp(Long timestamp){ this.timestamp = timestamp; }
    public void setMessage(String message){ this.message = message; }

    @Override
    public int compareTo(Object object) {
        return (timestamp < ((Chat) object).timestamp)?1:0;
    }
}