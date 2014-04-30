package com.devtuts.quizhive.models;

import java.util.Vector;

public  class CategoryModel  {
  
	
	public 	String 			categoryName;
	public 	String				categoryId;
	public 	String 			categoryImage;
	Vector	<MovieModel> 	movieObject;
	private String 			setCategoryCount;
	
	public CategoryModel(){ 
		movieObject = new Vector<MovieModel>();
		
	} 
	public void setCategoryId(String string){ 
		this.categoryId = string;
	}
	public String getCategoryId(){ 
		return categoryId;
	} 
	public void setCategoryName(String str){
		this.categoryName = str;
	}
	public String getCategoryName(){
		return this.categoryName;
	}
	
	public void setCategoryImage(String str){
		this.categoryImage = str;
	}
	public String getCategoryImage(){
		return this.categoryImage;
	}
	public void addMovieObject(MovieModel movieobj){ 
		movieObject.add(movieobj);
	}
	public Vector<MovieModel> getMovieObject(){ 
		return movieObject;
	}
	public void setCategoryCount(String string){
		this.setCategoryCount = string;
	}
	public String getCategoryCount(){
		return this.setCategoryCount;
	}
}
