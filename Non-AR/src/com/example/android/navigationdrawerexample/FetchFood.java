package com.example.android.navigationdrawerexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
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

public class FetchFood {
	private static String jsonResult;
	private static String url = "http://ec2-54-202-51-8.us-west-2.compute.amazonaws.com/Auggy/scripts/phpScripts/newRestItems.php";
	public static Hashtable<String, Food> foodList;
	private static int restID;
	
	public static void accessWebService(int inputRestID) {
		restID = inputRestID;
		JsonReadTask task = new JsonReadTask();
		// passes values for the urls string array
		task.execute(new String[] { url });
		
	}
	
	
	private static class JsonReadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			List<NameValuePair> restaurantID = new ArrayList<NameValuePair>();
			restaurantID.add(new BasicNameValuePair("id", String.valueOf(restID)));
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);
			try {
				// Log.w(httppost.)
				httppost.setEntity(new UrlEncodedFormEntity(restaurantID));
				HttpResponse response = httpclient.execute(httppost);
				jsonResult = inputStreamToString(
						response.getEntity().getContent()).toString();
			}

			catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		private StringBuilder inputStreamToString(InputStream is) {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			}

			catch (IOException e) {
				// e.printStackTrace();
				Log.w("Error", e.toString());
			}
			return answer;
		}
		
		@Override
		protected void onPostExecute(String result){
			getFoodList();
		}

	}// end async task

	

	// build hash set for list view
	public static void getFoodList() {
		foodList = new Hashtable<String, Food>();
		// Log.w("fevea",jsonResult);
		try {
			JSONObject jsonResponse = new JSONObject(jsonResult);
			JSONArray foodNode = jsonResponse.optJSONArray("foods");
			JSONArray tagNode = jsonResponse.optJSONArray("tags");

			// fetch foods
			for (int i = 0; i < foodNode.length(); i++) {
				JSONObject jsonChildNode = foodNode.getJSONObject(i);
				String id = jsonChildNode.optString("id");
				foodList.put(id, createFood(jsonChildNode));
			}

			for (int i = 0; i < tagNode.length(); i++) {
				JSONObject jsonChildNode = tagNode.getJSONObject(i);
				String food_id = jsonChildNode.optString("food_id");
				String icon = jsonChildNode.optString("icon");
				foodList.get(food_id).addTag(icon);
			}

			// fetch tags

		} catch (JSONException e) {
			Log.w("Error", e.toString());
		}
	}

	private static Food createFood(JSONObject a) {
		String id = a.optString("food_id");
		String name = a.optString("name");
		String desc = a.optString("description");
		Double price = a.optDouble("price");
		int like = a.optInt("likecount");

		return new Food(name, desc, R.drawable.chicken_nuggets, 0, price, like);
	}
}
