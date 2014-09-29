package com.hdavidzhu.chatattack;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends ArrayAdapter<Chat> {
    private List<Chat> chats = new ArrayList<Chat>();

    private Context context;
    private int resource;

    Firebase myFirebaseRef;

    public ChatAdapter(Context context, List<Chat> chats, int resource){
        super(context, R.layout.chat_item);
        this.context = context;
        this.resource = resource;
        myFirebaseRef = new Firebase("https://chat-attack.firebaseio.com/");

        addChats(chats);
    }

    // Create a private class called ChatHolder
    // that declares the name, message, and time.
    private class ChatHolder {
        TextView name, message, timestamp;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        // If the view is null, the list view has never rendered the child before.
        // Otherwise, the child is recycled and we have a reference to it.
        ChatHolder holder = new ChatHolder();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);

            holder.name = (TextView) view.findViewById(R.id.item_profile_name);
            holder.message = (TextView) view.findViewById(R.id.item_chat_message);
            holder.timestamp = (TextView) view.findViewById(R.id.item_chat_timestamp);
            view.setTag(holder);


        } else {
            // Else if we have the recycled view,
            // all we need to do is get the view holder from the tag of the view.
            holder = (ChatHolder) view.getTag();
        }

        fillViews(holder, getItem(position));
        return view;
    }

    private void fillViews(ChatHolder holder, Chat chat){
        holder.name.setText(chat.getName());
        holder.message.setText(chat.getMessage());
        holder.timestamp.setText(formatTime(chat.getTimestamp()));
    }

    private String formatTime(long time){
        if (DateUtils.isToday(time)){
            return new SimpleDateFormat("hh:mm:ss a").format(new Date(time));
        }
        return new SimpleDateFormat("MM/DD, hh:mm:ss a").format(new Date(time));
    }

    @Override
    public int getCount() {
        return this.chats.size();
    }

    public void addChat(Chat chat) {
        this.chats.add(chat);
        myFirebaseRef.child("message").setValue("Do you have data? You'll love Firebase.");
        myFirebaseRef.child("message").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
            }

            @Override public void onCancelled(FirebaseError error) { }

        });

        notifyDataSetChanged();
    }

    @Override
    public Chat getItem(int position) {
        return this.chats.get(position);
    }

    private void addChats(List<Chat> newChats) {
        this.chats.addAll(newChats);
        notifyDataSetChanged();
    }
}