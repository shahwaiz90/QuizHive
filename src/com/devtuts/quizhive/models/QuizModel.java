package com.devtuts.quizhive.models;

import java.util.ArrayList;
 
public class  QuizModel {
	
	String quizQuestion;
	int questionId;
	public String correctAnswer;
	
	public ArrayList<String> options;
	public ArrayList<QuizModel> quizAttributesObject;

	public QuizModel(){
		
		options 				= 	new ArrayList<String>();
		quizAttributesObject	= 	new ArrayList<QuizModel>();
	} 
	public void setQuestionId(int id){ 
		this.questionId = id;
	}
	public int getQuestionId(){ 
		return questionId;
	}
	
	public String getQuizQuestion(){ 
		return quizQuestion;
	}
	
	public void setQuizQuestion(String type){ 
		this.quizQuestion = type;
	}
	
	public void addQuizOptions(String obj){
	    options.add(obj);
	}
	
	public ArrayList<String> getQuizOptions(){
	    return this.options;
	}
	
	public void setCorrectAnswer(String str){
		this.correctAnswer = str;
	}
	public String getCorrectAnswer(){
		return this.correctAnswer;
	} 
	public void addQuizAttributesObjects(QuizModel obj){
		quizAttributesObject.add(obj);
	}
	
	public ArrayList<QuizModel> getQuizAttributesObjects(){
		return quizAttributesObject;
	}
}
