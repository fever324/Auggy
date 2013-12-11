package com.example.android.navigationdrawerexample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

public class ResMenu {
	private static HashMap<String,ArrayList<Integer>> foodCategory = new HashMap();
	private static HashMap<String,ArrayList<Integer>> foodTags = new HashMap();
	public static HashMap<Integer,Food> allFood = new HashMap(); 
	private static ArrayList<Integer>allFoodCatList = new ArrayList<Integer>(); 
	
	public ResMenu() {
		//Add all food
		allFood = new HashMap();
	}
	
	public static ArrayList<Integer> getAllFood(){
		if(allFoodCatList.size() == 0){
			int a = 1;
			for(String key : foodCategory.keySet()){
				allFoodCatList.add(-a); 	//Indicate beginning of a category
				allFoodCatList.addAll(foodCategory.get(key));
				a++;
			}
		}
		return allFoodCatList;
	}
	
	public static ArrayList<Integer> getCategory(String cat){
		if(cat == "All Food"){
			return getAllFood();
		}
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

				int food_id = Integer.parseInt(jsonChildNode.optString("food_id"));
				String category = jsonChildNode.optString("name");
				if(foodCategory.containsKey(category)){
					foodCategory.get(category).add(food_id);
				} else{
					ArrayList<Integer> tempCategoryList = new ArrayList();
					tempCategoryList.add(food_id);
					foodCategory.put(category, tempCategoryList);
				}
			}
		} catch (JSONException e) {

			Log.w("Error", e.toString());
		}
	}
	
	public static void sortTags(JSONArray json){
		try{

			for (int i = 0; i < json.length(); i++) {
				JSONObject jsonChildNode = json.getJSONObject(i);
				int food_id = Integer.parseInt(jsonChildNode.optString("food_id"));
				String tag = jsonChildNode.optString("icon");
				allFood.get(food_id).addTag(tag);
				
				if(foodTags.containsKey(tag)){
					foodCategory.get(tag).add(food_id);
				} else{
					ArrayList<Integer> tempCategoryList = new ArrayList();
					tempCategoryList.add(food_id);
					foodCategory.put(tag, tempCategoryList);
				}
				
			}
			
		}catch (JSONException e) {

			Log.w("Error", e.toString());
		}
	}
	
	public static void clear(){
		foodCategory.clear();
		allFood.clear();
		allFoodCatList.clear();
		
	}
	public static String [] getCategories(){
		List<String> tempList = new ArrayList<String>();
		tempList.add("All Food");
		tempList.addAll(foodCategory.keySet());
		return tempList.toArray(new String[0]);
	}
}
