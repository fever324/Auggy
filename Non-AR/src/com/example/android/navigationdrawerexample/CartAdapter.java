package com.example.android.navigationdrawerexample;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CartAdapter extends ArrayAdapter {
	//private ArrayList planetList;
	private Context context;
	private Cart cart;

	private static class ViewHolder {
		public TextView name;
		public TextView desc;
		public TextView price;
		public ImageView image;
		public TextView count;

	}

	public CartAdapter( Context ctx, Cart cart) {
		super(ctx, R.layout.cart_rowlayout);
		//this.planetList =  cart.getFoodList();
		this.context = ctx;
		this.cart = cart;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		// First let's verify the convertView is not null
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// convertView = inflater.inflate(R.layout.row_layout,parent,false);
			rowView = inflater.inflate(R.layout.cart_rowlayout, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.name = (TextView) rowView.findViewById(R.id.nameCart);
			viewHolder.desc = (TextView) rowView.findViewById(R.id.descCart);
			viewHolder.image = (ImageView) rowView.findViewById(R.id.imgCart);
			viewHolder.price = (TextView) rowView.findViewById(R.id.priceCart);
			viewHolder.count = (TextView) rowView.findViewById(R.id.countCart);

			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		// Now we can fill the layout with the right values
		Log.w("Debug", "PlanetAdapter Position: " + position);
		Food p = (Food) cart.getFood(position);
		int count = cart.getFoodCount(position);
		
		Log.w("Debug", "Get Item ID: " + p.getName());
		holder.name.setText(p.getName());
		holder.desc.setText(" " + p.getDesc());
		holder.image.setImageResource(p.getIdImg());
		holder.price.setText(String.valueOf(p.getPrice()));
		holder.count.setText("x"+String.valueOf(count));
		Log.w("Debug", "Passed getView");
		// tv.setText(p.getName());
		// distView.setText(" "+p.getDistance());

		return rowView;

	}

	public void updateList(ArrayList newList) {
		//this.planetList = newList;
	}

	@Override
	public int getCount() {
		return cart.getCount();
		//return planetList != null ? planetList.size() : 0;
	}

}
