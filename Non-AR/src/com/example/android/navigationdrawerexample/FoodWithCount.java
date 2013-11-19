package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

public class FoodWithCount{
	private ArrayList<Food> items;
	private ArrayList<Integer> count;
	
	public FoodWithCount(){
		items = new ArrayList<Food>();
		count= new ArrayList<Integer>();
	}
	
	public ArrayList<Food> getItems() {
		return items;
	}
	
	public Food getItem(int index) {
		return items.get(index);
	}
	
	public int getNumberOfFood(){
		return items.size();
	}
	
	//returns differences in total price
	public double addFood(Food food){
		if(items.contains(food)){
			int index= items.indexOf(food);
			setFoodCount(index,count.get(index) + 1 );
		} else {
			items.add(food);
			int index= items.indexOf(food);
			setFoodCount(index,1);
		}
		
		return food.getPrice();
	}
	
	//returns differences in total price
	public double removeFood(int index){
		double price = getPriceOfItem(index);
		items.remove(index);
		count.remove(index);
		return -price;
	}
	
	//returns differences in total price
	public double setFoodCount(int index, int number){
		double originalPrice = getPriceOfItem(index);
		count.set(index, number);
		
		return getPriceOfItem(index) - originalPrice;
	}
	
	public double getPriceOfItem(int index){
		return items.get(index).getPrice() * count.get(index);
	}
	
	public int getFoodCount(int index){
		return count.get(index);
	}
}
