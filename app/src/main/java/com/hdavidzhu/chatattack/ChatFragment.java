package com.hdavidzhu.chatattack;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashSet;
import java.util.Set;

public class ChatFragment extends Fragment{

    MainActivity context;    // Context from activity;
    ChatFirebase chatFirebase;
    Set<String> storedChats;
    String myName;
    ChatAdapter chatAdapter;

    Firebase chatRef;

    ListView chatListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = (MainActivity) getActivity();
        chatFirebase = new ChatFirebase();
        chatRef = new Firebase("https://mobileproto2014.firebaseio.com/chatroom/0");
        storedChats = new HashSet<String>();

        chatAdapter = new ChatAdapter(context, chatFirebase, R.layout.chat_item);
        chatRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (storedChats.contains(child.getName())) {
                        continue;
                    } else {
                        storedChats.add(child.getName());
                    }
                    chatAdapter.addChat(new Chat(
                            (String) child.child("name").getValue(),
                            (String) child.child("message").getValue(),
                            child.child("timestamp").getValue().toString()));
                }

                chatAdapter.notifyDataSetChanged();
                chatListView.setSelection(chatAdapter.getCount()-1);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        // see http://androidsnippets.com/prompt-user-input-with-an-alertdialog
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        view = setUpViews(view); // Set up the views in this content view.

        return view;
    }

    private View setUpViews(View v){
        chatListView = (ListView) v.findViewById(R.id.chat_list_view);
        final EditText inputChat = (EditText) v.findViewById(R.id.et_input_chat);


        AlertDialog.Builder alert = new AlertDialog.Builder(this.context);

        alert.setTitle("Username");
        alert.setMessage("Input your name here.");

        // Set an EditText view to get user input
        final EditText input = new EditText(this.context);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                myName = input.getText().toString();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                myName = "Default";
            }
        });

        alert.show();

        chatListView.setAdapter(chatAdapter);

        Button sendButton = (Button) v.findViewById(R.id.bt_send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String typedText = inputChat.getText().toString();
                inputChat.setText("");

                if (typedText.equals("")){
                    Toast.makeText(context, "Woah there, slow down and type some stuff."
                            , Toast.LENGTH_SHORT).show();
                } else {
                    Chat chat = new Chat(myName,typedText);
                    chatFirebase.postChat(chat);
                }
            }
        });

        return v;
    }
}