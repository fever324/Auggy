package com.example.android.navigationdrawerexample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ImageHelper.ImageLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
		Object[] imageGetterInput = new Object[2];
		imageGetterInput[0] = p.imgURL;
		imageGetterInput[1] = holder.image;	
		
		holder.name.setText(p.getName());
		holder.desc.setText(" " + p.getDesc().substring(0, 40)+"...");
		
		
		ImageLoader imgLoader = new ImageLoader(this.context); 
	        // whenever you want to load an image from url
	        // call DisplayImage function
	        // url    -    image url to load
	        // loader -    loader image, will be displayed before getting image
	        // image  -    ImageView 
	    imgLoader.DisplayImage(p.imgURL, R.drawable.ajax_loader, holder.image);
		
		//profile_photo.setImageBitmap(mIcon_val);
		//holder.image.setImageResource(p.getIdImg());
		holder.icon.setImageResource(p.getTag());
		holder.price.setText(String .format("$%.2f", p.getPrice()));
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