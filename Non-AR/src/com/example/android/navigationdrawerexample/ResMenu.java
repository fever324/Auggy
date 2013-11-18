package com.example.android.navigationdrawerexample;

import java.util.ArrayList;
import java.util.Arrays;

public class ResMenu {
	public ArrayList[] foodCategory;
	public ArrayList allFood; 
	
	public ResMenu() {
		//Add all food
		allFood = new ArrayList(); 
		allFood.add(new Food("Fried Chicken","Good fried chicken", R.drawable.fried_chicken, 0, 5.99));
		allFood.add(new Food("Chicken Nuggets","Really good nuggets", R.drawable.chicken_nuggets, 0, 3.99));
		allFood.add(new Food("Pasta","Tomato crab meat pasta", R.drawable.crabmeat_pasta, 0, 10.99));
		allFood.add(new Food("Sprite","Soda drink", R.drawable.sprite, 0, 2.99));
		
		//Add category Arraylist
		foodCategory = new ArrayList[3];
		foodCategory[0] = new ArrayList(Arrays.asList(0,1,2));
		foodCategory[1] = new ArrayList(Arrays.asList(2,1));
		foodCategory[2] = new ArrayList(Arrays.asList(3));

	}

}
