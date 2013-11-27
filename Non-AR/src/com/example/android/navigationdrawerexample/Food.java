package com.example.android.navigationdrawerexample;

import java.util.ArrayList;
import java.util.List;

public class Food {
	private String name;
	private String desc;
	// public String imgURL;
	private int idImg;
	private int category;
	private double price;
	private ArrayList<String> tag;
	private int likeCount;

	public Food(String name, String desc, int idImg, int category, double price, int likeCount) {
		this.name = name;
		this.desc = desc;
		this.idImg = idImg;
		this.category = category;
		this.price = price;
		this.likeCount = likeCount;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getIdImg() {
		return idImg;
	}

	public void setIdImg(int idImg) {
		this.idImg = idImg;
	}

	public void addTag(String newTag){
		this.tag.add(newTag);
	}
	
	public ArrayList<String> getTag(){
		return this.tag;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	
	
}
