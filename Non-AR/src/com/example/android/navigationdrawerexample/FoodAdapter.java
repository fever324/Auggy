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

public class FoodAdapter extends ArrayAdapter {
	private ArrayList<Integer> planetList;
	private Context context;
	private ResMenu menu;

	private static class ViewHolder {
		public TextView name;
		public TextView desc;
		public TextView price;
		public ImageView image;
		public ImageView icon;

	}

	public FoodAdapter(ArrayList planetList, Context ctx) {
		super(ctx, R.layout.image_rowlayout, planetList);
		this.planetList = planetList;
		this.context = ctx;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		// First let's verify the convertView is not null
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// convertView = inflater.inflate(R.layout.row_layout,parent,false);
			rowView = inflater.inflate(R.layout.image_rowlayout, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.name = (TextView) rowView.findViewById(R.id.name);
			viewHolder.desc = (TextView) rowView.findViewById(R.id.desc);
			viewHolder.image = (ImageView) rowView.findViewById(R.id.img);
			viewHolder.price = (TextView) rowView.findViewById(R.id.price);
			viewHolder.icon = (ImageView) rowView.findViewById(R.id.icon);

			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		// Now we can fill the layout with the right values
		Food p = (Food) ResMenu.getFood((Integer) planetList.get(position));
		
		holder.name.setText(p.getName());
		holder.desc.setText(" " + p.getDesc());
		holder.image.setImageResource(p.getIdImg());
		holder.icon.setImageResource(p.getTag());
		holder.price.setText(String.valueOf(p.getPrice()));
		// tv.setText(p.getName());
		// distView.setText(" "+p.getDistance());
		
		return rowView;

	}

	public void updateList(ArrayList newList) {
		this.planetList = newList;
	}

	public ArrayList<Integer> getList(){
		return  this.planetList;
	}
	@Override
	public int getCount() {
		return planetList != null ? planetList.size() : 0;
	}

}
