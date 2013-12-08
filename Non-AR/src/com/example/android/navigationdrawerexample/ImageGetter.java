package com.example.android.navigationdrawerexample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
//
//ImageGetter downloads an image and set to an ImageView
public class ImageGetter extends AsyncTask<Object, String, String> {

	Bitmap image;
	ImageView imageView;
	@Override
	//params[0] = url
	//params[1] = imageview
	protected String doInBackground(Object... params) {
		try {
			Log.w("Debug", "before");
			URL newurl = new URL(String.valueOf(params[0])); 
			image = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
			imageView = (ImageView) params[1];
			Log.w("Debug", "after");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		Log.w("Debug", "before2");
		imageView.setImageBitmap(image);
		Log.w("Debug", "after2");
	}

}
