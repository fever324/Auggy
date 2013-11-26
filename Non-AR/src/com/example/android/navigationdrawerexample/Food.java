package com.example.android.navigationdrawerexample;

public class Food {
	private String name;
	private String desc;
	// public String imgURL;
	private int idImg;
	private int category;
	private double price;
	private int tag;

	public Food(String name, String desc, int idImg, int category, double price) {
		this.name = name;
		this.desc = desc;
		this.idImg = idImg;
		this.category = category;
		this.price = price;
		
		if(name.toLowerCase().contains("chicken")){
			this.tag = R.drawable.chicken;
		}
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

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}
	
	
}