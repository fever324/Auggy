package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

public class Cart {

	private FoodWithCount foods;
	private double totalPrice;
	
	public Cart(){
		foods = new FoodWithCount();
		setTotalPrice(0);
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
	private void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getCount() {
		return foods.getNumberOfFood();
	}
	
	public int getFoodCount(int index){
		return foods.getFoodCount(index);
	}
	
	public void addFood(Food food){
		setTotalPrice(getTotalPrice() + foods.addFood(food));
	
	}
	
	public void setFoodCount(int index,int count){
		setTotalPrice(getTotalPrice() + foods.setFoodCount(index, count));
	}
	
	public Food getFood(int index){
		return foods.getItem(index);
	}
	
	public void removeFood(int index){
		//removeFood returns a negative number
		setTotalPrice(getTotalPrice() + foods.removeFood(index));
	}
	
	public double getPriceOfItem(int index){
		return foods.getPriceOfItem(index);
	}
	
	public ArrayList<Food> getFoodList(){
		return foods.getItems();
	}
	
	public boolean checkOut(){
		///TODO:
		///Implement checkout function here
		//for(int i = 0; i< this.getCount(); i++){
			//double price = foods.getItem(i).getPrice();
			//double totalPrice = price * foods.getCount(i);
			//do something
		//}
		
		//Transaction successful
		return true;
	}
	
	public void clearCart(){
		foods.clear();
	}

}




