package com.hdavidzhu.chatattack;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment{

    MainActivity context;    // Context from activity;
    List<Chat> storedChats;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = (MainActivity) getActivity();
        storedChats = new ArrayList<Chat>();
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
        final ListView chatListView = (ListView) v.findViewById(R.id.chat_list_view);
        final EditText inputChat = (EditText) v.findViewById(R.id.et_input_chat);

        final ChatAdapter chatAdapter = new ChatAdapter(context, storedChats, R.layout.chat_item);
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
                    chatAdapter.addChat(new Chat("David",typedText));
                    chatListView.setSelection(chatAdapter.getCount() - 1);
                }
            }
        });

        return v;
    }
}