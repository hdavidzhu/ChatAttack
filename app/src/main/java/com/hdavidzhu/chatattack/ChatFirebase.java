package com.hdavidzhu.chatattack;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class ChatFirebase {

    Firebase chatRef;

    public ChatFirebase(){
        this.chatRef = new Firebase("https://mobileproto2014.firebaseio.com/chatroom/0");
    }

    public void postChat(Chat chat){
        Map<String, String> chatMap = new HashMap<String, String>();
        chatMap.put("name",chat.getName());
        chatMap.put("message",chat.getMessage());
        chatMap.put("timestamp",String.valueOf(chat.getTimestamp()));

        chatRef.push().setValue(chatMap);
    }
}
