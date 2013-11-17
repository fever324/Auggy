package com.example.listview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.example.listview.PlanetAdapter.ViewHolder;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	//List<Planet> planetsList = new ArrayList<Planet>();
	//List<Map<String, String>> planetsList = new ArrayList<Map<String,String>>();
	List<Planet> planetsList = new ArrayList<Planet>();
	PlanetAdapter aAdpt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initList();
		
		

// Context
// Data list
// The row layout that is used during the row creation
// The keys used to retrieve the data
// The View id used to show the data. The key number and the view id must match

	    ListView lv = (ListView) findViewById(R.id.listview);
	    aAdpt = new PlanetAdapter(planetsList,this); 
	    lv.setAdapter(aAdpt);
	    
	    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id){
	    		LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		View layout = inflater.inflate(R.layout.itemcard, null,false);
	    		final PopupWindow pw = new PopupWindow(
	    				layout,
	    				200,
	    				300,
	    				true);
	    		
	    		ImageView itemImage = (ImageView)layout.findViewById(R.id.ItemCard_Image);
	    		TextView itemName = (TextView) layout.findViewById(R.id.ItemCardName);
	    		Button button = (Button) layout.findViewById(R.id.ItemCardCloseBTN);
	    		button.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						pw.dismiss();
						
					}
				});
	    		
	    		Planet p = planetsList.get(position);
	    		
	    		itemImage.setImageResource(p.getIdImg());
	    		itemName.setText(p.getName());
	    		pw.showAtLocation(layout, Gravity.CENTER, 0, 0);	  
	    		}
	    });
	    
	    registerForContextMenu(lv);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu,View v, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) menuInfo;
		
		menu.setHeaderTitle("Options for "+ planetsList.get(aInfo.position).getName());
		//what is the difference in groups?
		menu.add(1,1,1,"Details");
		menu.add(1,2,2,"Delete");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item){
		int itemId = item.getItemId();
		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		switch(itemId){
		case 2:
			planetsList.remove(aInfo.position);
			break;
			default:
		}
		
		Toast.makeText(this,"MenuItem id"+itemId, Toast.LENGTH_SHORT).show();
		aAdpt.notifyDataSetChanged();
		return true;
	}
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//	
	
	
    private void initList() {
    // We populate the planets
   
    planetsList.add(new Planet("Mercury", 10,"lala",R.drawable.baseball));
    planetsList.add(new Planet("Venus", 2,"lala",R.drawable.basketball));
    planetsList.add(new Planet("Mars", 30,"lala",R.drawable.football));
    planetsList.add(new Planet("Jupiter", 40,"lala",R.drawable.soccer));
    planetsList.add(new Planet("Saturn", 50,"lala",R.drawable.baseball));
    planetsList.add(new Planet("Uranus", 60,"lala",R.drawable.baseball));
    planetsList.add(new Planet("Neptune", 70,"lala",R.drawable.baseball));
      
    }
    // Handle user click
    public void addPlanet(View view) {
            final Dialog d = new Dialog(this);
            d.setContentView(R.layout.dialog);
            d.setTitle("Add planet");
            d.setCancelable(true);
            
            final EditText edit = (EditText) d.findViewById(R.id.editTextPlanet);
            Button b = (Button) d.findViewById(R.id.button1);
            b.setOnClickListener(new View.OnClickListener() {
                        
                        public void onClick(View v) {
                                String planetName = edit.getText().toString();
                                MainActivity.this.planetsList.add(new Planet(planetName, 0));
                                MainActivity.this.aAdpt.notifyDataSetChanged(); // We notify the data model is changed
                                d.dismiss();
                        }
                });
            
            d.show();
    }

    
	
}
