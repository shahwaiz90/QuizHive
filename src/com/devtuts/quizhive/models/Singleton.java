package com.devtuts.quizhive.models;
 
import java.util.Vector;
 
public class  Singleton {
 	
	public Vector<UserModel> userModel;
	public Vector<CategoryModel> categoryModel;
	public Vector<MovieModel> quizesList;
	
	public Vector<Singleton> quizAttributesObject;
	private static Singleton singleton;
	
	private  Singleton(){
		
		userModel 				= 	new Vector<UserModel>();
		categoryModel 			= 	new Vector<CategoryModel>();
		quizesList 				= 	new Vector<MovieModel>(); 
	} 
	
	public static Singleton getInstance(){
		if(singleton == null){
			singleton = new Singleton();
		}
		return singleton;
	}
	
	public void addUserModel(UserModel uModel){
		userModel.add(uModel);
	} 
	public Vector<UserModel> getUserModel(){
		return userModel;
	}
	
	public void addCategoryModel(CategoryModel cModel){
		categoryModel.add(cModel);
	} 
	public Vector<CategoryModel> getCategoryModel(){
		return categoryModel;
	}
	
	public void addQuizList(MovieModel sModel){
		quizesList.add(sModel);
	} 
	public Vector<MovieModel> getQuizList(){
		return quizesList;
	}
 
}
