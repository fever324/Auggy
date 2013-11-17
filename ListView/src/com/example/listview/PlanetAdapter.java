package com.example.listview;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlanetAdapter extends ArrayAdapter<Planet>{
	private List<Planet> planetList;
	private Context context;
	
	private static class ViewHolder{
		public TextView name;
		public TextView dist;
		public ImageView image;
		
	}
	
	public PlanetAdapter(List<Planet> planetList, Context ctx){
		super(ctx,R.layout.row_layout, planetList);
		this.planetList = planetList;
		this.context = ctx;
	}
	
	public View getView(int position,View convertView, ViewGroup parent){
		View rowView = convertView;
		// First let's verify the convertView is not null
		if(rowView == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//convertView = inflater.inflate(R.layout.row_layout,parent,false);
			rowView = inflater.inflate(R.layout.image_rowlayout, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.name = (TextView) rowView.findViewById(R.id.name);
			viewHolder.dist = (TextView) rowView.findViewById(R.id.dist);
			viewHolder.image = (ImageView) rowView.findViewById(R.id.img);
			
			rowView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		// Now we can fill the layout with the right values
		Planet p = planetList.get(position);
		holder.name.setText(p.getName());
		holder.dist.setText(" "+p.getDistance());
		holder.image.setImageResource(p.getIdImg());
		//tv.setText(p.getName());
		//distView.setText(" "+p.getDistance());
		
		return rowView;

	}

}
