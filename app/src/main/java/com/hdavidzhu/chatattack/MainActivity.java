package com.hdavidzhu.chatattack;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        username = getSharedPreferences("ChatAttack", MODE_PRIVATE).getString("username","");

        // If we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
        if (savedInstanceState != null) {
            return;
        }

        // Create a new Fragment to be placed in the activity layout.
        ChatFragment chatFragment = new ChatFragment();

        // Add the fragment to the 'fragment_holder' FrameLayout.
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_holder, chatFragment).commit();
    }

}