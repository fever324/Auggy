package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

public class Food {
	private int id;
	private String name;
	private String desc;
	public String imgURL;
	private int idImg;
	private int category;
	private double price;
	private ArrayList<String> tag;

	public Food(int id ,String name, String desc, int idImg, int category, double price) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.idImg = idImg;
		this.category = category;
		this.price = price;
		
	}
	public Food(int id ,String name, String desc, String imgURL, int category, double price) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.imgURL = imgURL;
		this.category = category;
		this.price = price;
		this.tag = new ArrayList<String>();
		
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

	public String getTag(int i) {
		return tag.get(i);
	}

	public void addTag(String toAdd) {
		this.tag.add(toAdd);
	}
	
	public String[] getAllTags(){
		return this.tag.toArray(new String[0]);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
