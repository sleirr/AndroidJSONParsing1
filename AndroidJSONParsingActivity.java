package com.androidhive.jsonparsing;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;



public class AndroidJSONParsingActivity extends ListActivity {

	// url to make request
	//private static String url = "http://api.androidhive.info/contacts/";

	// JSON Node names
	private static final String TAG_PORTS = "ports";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_ADDRESS = "address";
	private static final String TAG_IP = "ip";
	private static final String TAG_PORT = "port";

	// ports JSONArray
	JSONArray ports = null;

	ListView lv;


	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// selecting single ListView item
		lv = getListView();

		// Launching new screen on Selecting Single ListItem
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// getting values from selected ListItem
				String id2 = ((TextView) view.findViewById(R.id.id)).getText().toString();
				String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
				String address = ((TextView) view.findViewById(R.id.address)).getText().toString();
				String ip = ((TextView) view.findViewById(R.id.ip)).getText().toString();
				String port = ((TextView) view.findViewById(R.id.port)).getText().toString();


				// Starting new intent
				Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
				in.putExtra(TAG_ID, id2);
				in.putExtra(TAG_NAME, name);
				in.putExtra(TAG_ADDRESS, address);
				in.putExtra(TAG_IP, ip);
				in.putExtra(TAG_PORT, port);
				startActivity(in);

			}
		});

		//Starting the task. Pass a URL to the parameter
		String url = "http://172.20.240.4:7003/";
		new ParseTask().execute(url);


	}

	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	private class ParseTask extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {



		@Override
		protected void onPreExecute() {
			ProgressBar bar = (ProgressBar) findViewById(R.id.progressbar);
			bar.setVisibility(View.VISIBLE);
		}

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(String... params) {

			String url = params[0];
			//Creating JSON Parser Instance
			com.androidhive.jsonparsing.JSONParser jParser = new com.androidhive.jsonparsing.JSONParser();
			//getting JSON String from URL
			JSONObject json = jParser.getJSONFromUrl(url);
			//Hashmap for ListView
			ArrayList<HashMap<String, String>> portList = new ArrayList<HashMap<String, String>>();
			try {
				// Getting Array of Contacts
				ports = json.getJSONArray(TAG_PORTS);

				// looping through All Contacts
				for (int i = 0; i < ports.length(); i++) {
					JSONObject c = ports.getJSONObject(i);

					// Storing each json item in variable
					String id = c.getString(TAG_ID);
					String name = c.getString(TAG_NAME);
					String address = c.getString(TAG_ADDRESS);
					String ip = c.getString(TAG_IP);
					String port = c.getString(TAG_PORT);


					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_ID, id);
					map.put(TAG_NAME, name);
					map.put(TAG_ADDRESS, address);
					map.put(TAG_IP, ip);
					map.put(TAG_PORT, port);

					// adding HashList to ArrayList
					portList.add(map);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return portList;
		}

		protected void onPostExecute(ArrayList<HashMap<String, String>> portList) {
			ListAdapter adapter = new SimpleAdapter(
					AndroidJSONParsingActivity.this, portList,
					R.layout.list_item, new String[]{TAG_ID, TAG_NAME,
					TAG_ADDRESS, TAG_IP, TAG_PORT }, new int[]{R.id.id, R.id.name, R.id.address, R.id.ip, R.id.port});

			lv.setAdapter(adapter);
			ProgressBar bar = (ProgressBar) findViewById(R.id.progressbar);
			bar.setVisibility(View.INVISIBLE);
		}
	}
}

