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
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.navigationdrawerexample.*;

public class TestPHP extends Activity {

	// Drawer Section/**********************
	private DrawerLayout mDrawerLayout;
	private ArrayAdapter drawerAdapter;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mFoodCategories;
	// *****************************************//

	private String jsonResult;
	private String url = "http://ec2-54-202-51-8.us-west-2.compute.amazonaws.com/Auggy/scripts/phpScripts/newRestItems.php";
	private ListView listView;
	private FoodAdapter adapter;

	boolean nutritionVisible = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_php);

		listView = (ListView) findViewById(R.id.testPHP_listView);
		new LoadFood().execute();

		mTitle = mDrawerTitle = getTitle();
		mFoodCategories = ResMenu.getCategories();
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.testPHPdrawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
	    drawerAdapter = new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mFoodCategories);
		mDrawerList.setAdapter(drawerAdapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			@Override
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
										// onPrepareOptionsMenu()

			}

			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
										// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parentAdapter, View view,
					int position, long id) {
				LayoutInflater inflater = (LayoutInflater) TestPHP.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.itemcard, null, false);
				final PopupWindow pw = new PopupWindow(layout, 600, 600, true);
				
				//Tapping outside of the popup dismisses the window (next 3 lines)
				pw.setBackgroundDrawable(new BitmapDrawable());
				pw.setOutsideTouchable(true);
				pw.setFocusable(true);
				
				final ImageView itemImage = (ImageView) layout
						.findViewById(R.id.ItemCard_Image);
				final int positionTemp = position; 
				nutritionVisible = false;
				itemImage.setClickable(true);

				int foodID = adapter.getList().get(positionTemp);
				final Food p =  ResMenu.getFood(foodID);

				TextView itemName = (TextView) layout
						.findViewById(R.id.ItemCardName);
				itemImage.setImageResource(p.getIdImg());
				itemName.setText(p.getName());
				
				itemImage.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(!nutritionVisible)itemImage.setImageResource(R.drawable.nutrition);
						else itemImage.setImageResource(p.getIdImg());
						nutritionVisible = !nutritionVisible;
					}
				});
				
				
				
				pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
			}
		});

		registerForContextMenu(listView);
	

	}

	public void updateDrawerAdapter() {
		mFoodCategories = ResMenu.getCategories();
		drawerAdapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, mFoodCategories);
		mDrawerList.setAdapter(drawerAdapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_ph, menu);
		return true;
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position, id);
		}
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	private void selectItem(int position, long id) {

		Log.w("Debug", "Drawer Menu Selection: " + position);
		// displayedArray.clear();
		// aAdpt.updateList(menu.foodCategory[position]);
		// displayedArray = menu.foodCategory[position];
		mDrawerLayout.closeDrawer(mDrawerList);
		adapter.updateList(ResMenu.getCategory((mFoodCategories[position])));
		adapter.notifyDataSetChanged();
		// lv.invalidateViews();
		Log.w("Debug", "Pass datasetchanged()");
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	// /*************************************************
	// / DB connection part

	// LoadFood class references the JsonReadTask class. It takes in the URL of
	// the PHP script, and an arraylist
	// that represents the input variables for the PHP script. The JSON result
	// will be stored in "jsonResult" global variable,
	// which is later used in the ListDrawer method.
	class LoadFood extends AsyncTask<String, String, String> {
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
			updateDrawerAdapter();
			Log.w("Debug",mFoodCategories[0]);
		}

	}

	// ListDrawer is responsible for parsing the JSON result retrieved from the
	// LoadFood class,
	// and turn them into a food item to be added into the food list. This
	// method will also update
	// the list view in the activity.
	public void ListDrawer() {

		Log.w("fevea", jsonResult);
		try {
			JSONObject jsonResponse = new JSONObject(jsonResult);
			JSONArray jsonMainNode = jsonResponse.optJSONArray("foods");

			for (int i = 0; i < jsonMainNode.length(); i++) {
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				int id = jsonChildNode.optInt("id");
				String name = jsonChildNode.optString("name");
				String desc = jsonChildNode.optString("description");
				Double price = Double.parseDouble(jsonChildNode
						.optString("price"));
				Food food = new Food(id, name, desc, R.drawable.crabmeat_pasta,
						0, price);
				ResMenu.addFood(food);
			}
			ResMenu.sortCategory(jsonResponse.optJSONArray("category"));
		} catch (JSONException e) {

			Log.w("Error", e.toString());
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					Toast.LENGTH_SHORT).show();
		}

		adapter = new FoodAdapter(ResMenu.getCategory("Entree"), this);
		listView.setAdapter(adapter);

		// SimpleAdapter simpleAdapter = new SimpleAdapter(this, foodList,
		// android.R.layout.simple_list_item_1,
		// new String[] { "employees" }, new int[] { android.R.id.text1 });
		// listView.setAdapter(simpleAdapter);
	}

	// End of DB connection
	// ************************************

}
