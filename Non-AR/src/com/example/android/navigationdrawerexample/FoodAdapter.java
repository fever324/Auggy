package com.example.android.navigationdrawerexample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ImageHelper.ImageLoader;
import ResourceHelper.IconHelper;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodAdapter extends ArrayAdapter {
	private ArrayList<Integer> planetList;
	private Context context;
	private ResMenu menu;
	private Integer headerCount;
	private boolean withHeader;

	private static class ViewHolder {
		public TextView name;
		public TextView desc;
		public TextView price;
		public ImageView image;
		public ImageView [] icons;
		public Animation animation;
	}
	
	private static class HeaderViewHolder extends ViewHolder{
		public TextView header;
	}

	public FoodAdapter(ArrayList<Integer> planetList, Context ctx) {
		super(ctx, R.layout.image_rowlayout, planetList);
		this.planetList = planetList;
		this.context = ctx;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View rowView = convertView;
		View headerView = convertView;
		
		int foodIndex = (Integer) planetList.get(position);
		if(foodIndex <= 0){
			foodIndex = -foodIndex;
			if (headerView == null || rowView.getTag() instanceof ViewHolder) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				// convertView = inflater.inflate(R.layout.row_layout,parent,false);
				headerView = inflater.inflate(R.layout.image_rowheaderlayout, null);
				HeaderViewHolder headerViewHolder = new HeaderViewHolder();
				headerViewHolder.header = (TextView)headerView.findViewById(R.id.categoryHeader);
				headerView.setTag(headerViewHolder);
			}
			HeaderViewHolder headerHolder = (HeaderViewHolder) headerView.getTag();
			String category = ResMenu.getCategories()[foodIndex];
			headerHolder.header.setText(category);
			
			return headerView;
			
		} else {
			// First let's verify the convertView is not null
			if (rowView == null || rowView.getTag() instanceof HeaderViewHolder) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				// convertView = inflater.inflate(R.layout.row_layout,parent,false);
				rowView = inflater.inflate(R.layout.image_rowlayout, null);
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.name = (TextView) rowView.findViewById(R.id.name);
				viewHolder.desc = (TextView) rowView.findViewById(R.id.desc);
				viewHolder.image = (ImageView) rowView.findViewById(R.id.img);
				viewHolder.price = (TextView) rowView.findViewById(R.id.price);
				viewHolder.icons = new ImageView[]{(ImageView) rowView.findViewById(R.id.icon1),
													(ImageView) rowView.findViewById(R.id.icon2),
													(ImageView) rowView.findViewById(R.id.icon3),
													(ImageView) rowView.findViewById(R.id.icon4)};
				rowView.setTag(viewHolder);
			}
			ViewHolder holder = (ViewHolder) rowView.getTag();
			// Now we can fill the layout with the right values
			Food p = (Food) ResMenu.getFood(foodIndex);
			
			//name & description
			holder.name.setText(p.getName());
			holder.desc.setText(" " + p.getDesc().substring(0, 40)+"...");
			
			
			//food image
			Object[] imageLoaderInput = new Object[2];
			imageLoaderInput[0] = p.imgURL;
			imageLoaderInput[1] = holder.image;	
			ImageLoader imgLoader = new ImageLoader(this.context); 
		        // whenever you want to load an image from url
		        // call DisplayImage function
		        // url    -    image url to load
		        // loader -    loader image, will be displayed before getting image
		        // image  -    ImageView 
		    imgLoader.DisplayImage(p.imgURL, R.drawable.ajax_loader, holder.image);
		    
		    //fade in animation
		    holder.animation = AnimationUtils.loadAnimation(this.context,R.anim.fadein);
	        holder.image.startAnimation(holder.animation);
		    
			//tags
	        String [] tag = p.getAllTags();
	        //clear previous cache
	        for(int i = 0; i<4; i++){
	        	holder.icons[i].setImageResource(android.R.color.transparent);
	        }
	        
	        if(tag.length != 0){
		        for(int i = 0; i < 4 && (i<tag.length); i++){
		        	holder.icons[i].setImageResource(IconHelper.getIcon(tag[i]));
		        }
	        }
			
			//price
			holder.price.setText(String .format("$%.2f", p.getPrice()));
			// tv.setText(p.getName());
			// distView.setText(" "+p.getDistance());
			
		}
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
