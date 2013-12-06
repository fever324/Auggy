package com.example.android.navigationdrawerexample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

public class ResMenu {
	private static HashMap<String,ArrayList<Integer>> foodCategory = new HashMap();
	public static HashMap<Integer,Food> allFood = new HashMap(); 
	
	public ResMenu() {
		//Add all food
		allFood = new HashMap();
		//foodCategory = new ArrayList();
//		allFood.add(new Food("Fried Chicken","Good fried chicken", R.drawable.fried_chicken, 0, 5.99));
//		allFood.add(new Food("Chicken Nuggets","Really good nuggets", R.drawable.chicken_nuggets, 0, 3.99));
//		allFood.add(new Food("Pasta","Tomato crab meat pasta", R.drawable.crabmeat_pasta, 0, 10.99));
//		allFood.add(new Food("Sprite","Soda drink", R.drawable.sprite, 0, 2.99));
//		
//		//Add category Arraylist
//		foodCategory = new ArrayList[3];
//		foodCategory[0] = new ArrayList(Arrays.asList(0,1,2));
//		foodCategory[1] = new ArrayList(Arrays.asList(2,1));
//		foodCategory[2] = new ArrayList(Arrays.asList(3));
//
	}
	
	public static ArrayList<Integer> getCategory(String cat){
		return foodCategory.get(cat);
	}
	
	public static void addFood(Food food){
		allFood.put(food.getId(),food);
	}
	
	public static Food getFood(int id){
		return allFood.get(id);
	}
	
	public static void sortCategory(JSONArray json){

		try {

			for (int i = 0; i < json.length(); i++) {
				JSONObject jsonChildNode = json.getJSONObject(i);

				String food_id = jsonChildNode.optString("food_id");
				String category = jsonChildNode.optString("name");
				if(foodCategory.containsKey(category)){
					foodCategory.get(category).add(Integer.parseInt(food_id));
				} else{
					ArrayList<Integer> tempCategoryList = new ArrayList();
					tempCategoryList.add(Integer.parseInt(food_id));
					foodCategory.put(category, tempCategoryList);
				}
			}
		} catch (JSONException e) {

			Log.w("Error", e.toString());
		}
	}
	
	public static void clear(){
		foodCategory.clear();
		allFood.clear();
	}
	public static String [] getCategories(){
		return foodCategory.keySet().toArray(new String[0]);
	}
}
