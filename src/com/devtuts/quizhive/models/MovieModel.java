package com.devtuts.quizhive.models;

import java.util.Vector;
 
public  class MovieModel  {
  
	
	public 	String 			movieName;
	public 	String			movieId;  
	private String 			setMovieCount;
	Vector	<MovieModel> 	movieObject;
	public MovieModel(){  
		movieObject = new Vector<MovieModel>();
	} 
	public void setMovieId(String string){ 
		this.movieId = string;
	}
	public String getMovieId(){ 
		return movieId;
	} 
	 
	public void setMovieName(String str){
		this.movieName = str;
	}
	public String getMovieName(){
		return this.movieName;
	} 
	public void setCategoryCount(String string){
		this.setMovieCount = string;
	}
	public String getCategoryCount(){
		return this.setMovieCount;
	}
	public void addMovieObject(MovieModel movieobj){ 
		movieObject.add(movieobj);
	}
	public Vector<MovieModel> getMovieObject(){ 
		return movieObject;
	}
}
