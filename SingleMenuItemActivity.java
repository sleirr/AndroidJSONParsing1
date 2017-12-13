package com.androidhive.jsonparsing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleMenuItemActivity  extends Activity {
	
	// JSON node keys
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_IP = "ip";
    private static final String TAG_PORT = "port";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        // getting values from selected ListItem
        String id2 = in.getStringExtra(TAG_ID);
        String name = in.getStringExtra(TAG_NAME);
        String address = in.getStringExtra(TAG_ADDRESS);
        String ip = in.getStringExtra(TAG_IP);
        String port = in.getStringExtra(TAG_PORT);

        
        // Displaying all values on the screen
        TextView id2text = ((TextView) findViewById(R.id.id));
        TextView nametext = ((TextView) findViewById(R.id.name));
        TextView addresstext = ((TextView) findViewById(R.id.address));
        TextView idtext = ((TextView) findViewById(R.id.ip));
        TextView porttext = ((TextView) findViewById(R.id.port));

        id2text.setText(id2);
        nametext.setText(name);
        addresstext.setText(address);
        idtext.setText(ip);
        porttext.setText(port);
    }
}
