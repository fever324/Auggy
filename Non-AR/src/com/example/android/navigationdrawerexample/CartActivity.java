package com.example.android.navigationdrawerexample;

import android.os.Bundle;
import com.example.android.navigationdrawerexample.*;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CartActivity extends Activity {

	Cart cart = MainActivity.cart;
	ListView cartItemsLV;
	CartAdapter cartAdapter;
	TextView totalPrice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		
		totalPrice = (TextView) findViewById(R.id.totalPrice);
		totalPrice.setText("Total Price: $"+String.valueOf(cart.getTotalPrice()));
		
		cartItemsLV = (ListView) findViewById(R.id.listviewCart);
		
		cartAdapter = new CartAdapter(this,cart);
		cartItemsLV.setAdapter(cartAdapter);

		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cart, menu);
		return true;
	}
	
	public void checkOut(View view){
		
		if(cart.checkOut()){
			Toast.makeText(this, "Check Out Successful", Toast.LENGTH_LONG).show();
			//cart.clearCart();
			cartAdapter.notifyDataSetChanged();
			
		}
	}

}
