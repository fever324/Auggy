package com.example.android.navigationdrawerexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.android.navigationdrawerexample.*;

public class TestPHP extends Activity {
	private String jsonResult;
	private String url = "http://ec2-54-202-51-8.us-west-2.compute.amazonaws.com/Auggy/scripts/phpScripts/newRestItems.php";
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_php);
		listView = (ListView) findViewById(R.id.listView1);
		new LoadFood().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_ph, menu);
		return true;
	}
	
	//LoadFood class references the JsonReadTask class. It takes in the URL of the PHP script, and an arraylist
	//that represents the input variables for the PHP script. The JSON result will be stored in "jsonResult" global variable,
	//which is later used in the ListDrawer method. 
	class LoadFood extends AsyncTask<String,String,String> {
		@Override
		protected String doInBackground(String... params) {
			JsonReadTask task = new JsonReadTask();
			List<NameValuePair> restaurantID = new ArrayList<NameValuePair>();
			restaurantID.add(new BasicNameValuePair("id", "1"));
			// passes values for the urls string array
			jsonResult = task.makeRequest(url, restaurantID);
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			ListDrawer();
		}
		
	}

	// ListDrawer is responsible for parsing the JSON result retrieved from the LoadFood class, 
	// and turn them into a food item to be added into the food list. This method will also update 
	// the list view in the activity. 
	public void ListDrawer() {
		List<Map<String, String>> foodList = new ArrayList<Map<String, String>>();
		Log.w("fevea", jsonResult);
		try {
			JSONObject jsonResponse = new JSONObject(jsonResult);
			JSONArray jsonMainNode = jsonResponse.optJSONArray("foods");

			for (int i = 0; i < jsonMainNode.length(); i++) {
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

				String name = jsonChildNode.optString("name");
				String number = jsonChildNode.optString("price");
				String outPut = name + "-" + number;
				foodList.add(createEmployee("employees", outPut));
			}
		} catch (JSONException e) {

			Log.w("Error", e.toString());
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					Toast.LENGTH_SHORT).show();
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, foodList,
				android.R.layout.simple_list_item_1,
				new String[] { "employees" }, new int[] { android.R.id.text1 });
		listView.setAdapter(simpleAdapter);
	}

	private HashMap<String, String> createEmployee(String name, String number) {
		HashMap<String, String> employeeNameNo = new HashMap<String, String>();
		employeeNameNo.put(name, number);
		return employeeNameNo;
	}
}
