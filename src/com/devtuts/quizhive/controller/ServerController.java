package com.devtuts.quizhive.controller;


/**
 * author: Ahmad Shahwaiz
 * www.ahmadshahwaiz.com
 */

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.devtuts.quizhive.R;
import com.devtuts.quizhive.models.CategoryModel; 
import com.devtuts.quizhive.models.CustomDialogBox;
import com.devtuts.quizhive.models.MovieModel;
import com.devtuts.quizhive.models.Singleton;  
import com.devtuts.quizhive.models.UserModel;
import com.devtuts.quizhive.views.DashboardActivity;  
import com.devtuts.quizhive.views.ForgetPasswordActivity;
import com.devtuts.quizhive.views.RegisterActivity;
import com.devtuts.quizhive.views.SignInActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;  
import android.app.Activity;
import android.app.ProgressDialog; 
import android.content.Intent; 
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

// Implemented Facade Design Pattern - Central Access, Client ask him to Generate a Response.
public class ServerController extends Activity {
  
	String DisplayName 		= 	"";
	String Email 			= 	"";
	String Password 		= 	"";
	String Session  		= 	"";
	String cameFromClass	=	"";	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);  
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String targetServerHit 		= extras.getString("targetServerHit");
		    cameFromClass				= extras.getString("cameFromClass");
		    
		    if(targetServerHit.equalsIgnoreCase("DashBoard")){
		    	new ServerHitGetCategory().execute(com.devtuts.quizhive.util.Util.getCategoryUrl);
		    }
		    else if(targetServerHit.equalsIgnoreCase("Register")){
		    	 DisplayName  = extras.getString("DisplayName");
		    	 Email 		  = extras.getString("Email");
		    	 Password 	  = extras.getString("Password");
		    	
		    	new ServerHitRegisteration().execute(com.devtuts.quizhive.util.Util.registerationUrl);
				 
		    }
		    else if(targetServerHit.equalsIgnoreCase("SignIn")){ 
		    	 Email 		  = extras.getString("Email");
		    	 Password 	  = extras.getString("Password");
		    	
		    	new ServerHitSignIn().execute(com.devtuts.quizhive.util.Util.signInUrl); 
		    }
		    else if(targetServerHit.equalsIgnoreCase("ForgetPassword")){ 
		    	 Email 		  = extras.getString("Email"); 
		    	
		    	new ServerHitForgetPassword().execute(com.devtuts.quizhive.util.Util.forgetPasswordUrl); 
		    }
		} 
	} 
	private class ServerHitGetCategory  extends AsyncTask<String, Void, Void> { 
		 
        private final HttpClient Client = new DefaultHttpClient(); 
        private String Error = null;
        String result;
        String errorName;
        String errorCode;
        
        private ProgressDialog Dialog = new ProgressDialog(ServerController.this);
         	  
        @Override
		protected void onPreExecute() {
            // NOTE: You can call UI Element here.
             
            //UI Element 
            Dialog.setMessage("Getting Topics...");
            Dialog.setCanceledOnTouchOutside(false);
            Dialog.show();
        }
 
        // Call after onPreExecute method
        @Override
		protected Void doInBackground(String... urls) {
            try { 
                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.
                 
                // Server url call by GET method
                HttpPost httppost = new HttpPost(urls[0]); 
                
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("SessionId",Singleton.getInstance().getUserModel().lastElement().getUserToken()));
                nameValuePairs.add(new BasicNameValuePair("Email", Singleton.getInstance().getUserModel().lastElement().getEmailAddress())); 
                 
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response 		  = Client.execute(httppost);
                HttpEntity entity 	  		  = response.getEntity();
                InputStream instream  		  = entity.getContent();
                result 						  = com.devtuts.quizhive.util.Util.convertStreamToString(instream);
                
                JSONObject jObject 			  = new JSONObject(result); 
                errorCode 			  		  = jObject.getString("errorCode"); 
                
                JSONObject dataObject 		  = jObject.getJSONObject("data"); 
                JSONObject notficationsObject = jObject.getJSONObject("notifications"); 
                
                if(errorCode.equals("0")){
                	 
	                JSONArray categoryArray = dataObject.getJSONArray("categories");  
	                Singleton.getInstance().getCategoryModel().removeAllElements();
	                for(int i=0; i < categoryArray.length(); i++){
	                	
	                	CategoryModel categoryModel = new CategoryModel();
		                categoryModel.setCategoryId(categoryArray.getJSONObject(i).getString("id"));
		                categoryModel.setCategoryName(categoryArray.getJSONObject(i).getString("name")); 
		                categoryModel.setCategoryCount(categoryArray.getJSONObject(i).getString("count")); 
		                Log.i("Category Name: ",categoryArray.getJSONObject(i).getString("name"));
		                
		                for(int j=0; j < categoryArray.getJSONObject(i).getJSONArray("quizzes").length(); j++){
		                	MovieModel movieModelObject = new MovieModel();
		                	movieModelObject.setMovieId(categoryArray.getJSONObject(i).getJSONArray("quizzes").getJSONObject(j).getString("id"));
		                	movieModelObject.setMovieName(categoryArray.getJSONObject(i).getJSONArray("quizzes").getJSONObject(j).getString("name")); 
		                	Log.i("-MovieName: ",categoryArray.getJSONObject(i).getJSONArray("quizzes").getString(1));
		                	categoryModel.addMovieObject(movieModelObject);
		                }
		                
		                Singleton.getInstance().addCategoryModel(categoryModel);  
	                }   
                }
                else{
                	errorName 	= notficationsObject.getString("errorName"); 
                } 
                
            } catch (Exception e) {
                Error = e.getMessage();   
                cancel(true);
                Log.i("Category Exception",Error);
                Dialog.dismiss(); 
            }
			return null; 
        }
         
        @Override
		protected void onPostExecute(Void unused) { 
            // NOTE: You can call UI Element here. 
        	Dialog.dismiss();
    	 	if(errorCode.equals("0")){ 
    	 		Intent openDashboardActivity = new Intent(ServerController.this, DashboardActivity.class);
		        startActivity(openDashboardActivity);
    	 	}else{ 
    	 		if(cameFromClass.equalsIgnoreCase("DashboardActivity")){
    				Intent openServerHitActivity = new Intent(ServerController.this, DashboardActivity.class);
    				openServerHitActivity.putExtra("ControllerStatus","Error");
    				openServerHitActivity.putExtra("ControllerTitle","Dashboard Error");
    				openServerHitActivity.putExtra("ControllerMessage",errorName);
    				
    		        startActivity(openServerHitActivity);
    			}  
    	 	}  
        }
	} 
	private class ServerHitRegisteration  extends AsyncTask<String, Void, Void> { 
		 
        private final HttpClient Client = new DefaultHttpClient(); 
        private String Error = null;
        String result;
        String errorName;
        String errorCode;
        
        private ProgressDialog Dialog = new ProgressDialog(ServerController.this);
         	  
        @Override
		protected void onPreExecute() {
            // NOTE: You can call UI Element here.
             
            //UI Element 
            Dialog.setMessage("Signing Up...");
            Dialog.setCanceledOnTouchOutside(false);
            Dialog.show();
        }
 
        // Call after onPreExecute method
        @Override
		protected Void doInBackground(String... urls) {
            try { 
                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.
                 
                // Server url call by POST method
                HttpPost httppost = new HttpPost(urls[0]); 
                
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("DisplayName",DisplayName));
                nameValuePairs.add(new BasicNameValuePair("Email",Email)); 
                nameValuePairs.add(new BasicNameValuePair("Password", Password)); 
                 
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

             // Execute HTTP Post Request
                HttpResponse response 		  = Client.execute(httppost);
                HttpEntity entity 	  		  = response.getEntity();
                InputStream instream  		  = entity.getContent();
                result 						  = com.devtuts.quizhive.util.Util.convertStreamToString(instream);
                
                JSONObject jObject 			  = new JSONObject(result); 
                errorCode 			  		  = jObject.getString("errorCode"); 
                
                JSONObject dataObject 		  = jObject.getJSONObject("data"); 
                JSONObject notficationsObject = jObject.getJSONObject("notifications"); 
                
                if(errorCode.equals("0")){
                	
	                String sessionKey  		  = dataObject.getString("sessionKey");  
	                
	                UserModel userModel = new UserModel(); 
	        		userModel.setEmailAddress(Email);
	        		userModel.setUserPassword(Password);
	        		userModel.setdisplayName(DisplayName);
	        		userModel.setUserToken(sessionKey); 
	        		Singleton.getInstance().addUserModel(userModel);
	        		
                }
                else{
                	errorName 	= notficationsObject.getString("errorName"); 
                } 
                
            } catch (Exception e) {
                Error = e.getMessage();   
                cancel(true);
                Log.i("RegisterationException",Error);
                Dialog.dismiss(); 
            }
			return null; 
        }
         
        @Override
		protected void onPostExecute(Void unused) { 
            // NOTE: You can call UI Element here. 
        	Dialog.dismiss();
    	 	if(errorCode.equals("0")){ 
    	 		Intent openDashboardActivity = new Intent(ServerController.this, DashboardActivity.class);
    	 		startActivity(openDashboardActivity);
    	 	}else{ 
    	 		
    	 		if(cameFromClass.equalsIgnoreCase("RegisterActivity")){
    				Intent openServerHitActivity = new Intent(ServerController.this, RegisterActivity.class);
    				openServerHitActivity.putExtra("ControllerStatus","Error");
    				openServerHitActivity.putExtra("ControllerTitle","Registeration Error");
    				openServerHitActivity.putExtra("ControllerMessage",errorName);
    				
    		        startActivity(openServerHitActivity);
    			} 
    	 	}  
        }
	}    
	private class ServerHitSignIn  extends AsyncTask<String, Void, Void> { 
		 
        private final HttpClient Client = new DefaultHttpClient(); 
        private String Error = null;
        String result;
        String sessionKey,errorName;
        String errorCode;
        private ProgressDialog Dialog = new ProgressDialog(ServerController.this);
         	  
        @Override
		protected void onPreExecute() {
            // NOTE: You can call UI Element here.
             
            //UI Element 
            Dialog.setMessage("Signing In..."); 
            Dialog.setCanceledOnTouchOutside(false);
            Dialog.show(); 
        }
 
        // Call after onPreExecute method
        @Override
		protected Void doInBackground(String... urls) {
            try {
                  
                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.
                 
                // Server url call by GET method
                HttpPost httppost = new HttpPost(urls[0]); 
                
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2); 
                nameValuePairs.add(new BasicNameValuePair("Email", Email));
                nameValuePairs.add(new BasicNameValuePair("Password", Password));
                
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response 		  = Client.execute(httppost);
                HttpEntity entity 	  		  = response.getEntity();
                InputStream instream  		  = entity.getContent();
                result 						  = com.devtuts.quizhive.util.Util.convertStreamToString(instream);
                
                JSONObject jObject 			  = new JSONObject(result); 
                errorCode 			  		  = jObject.getString("errorCode"); 
                
                JSONObject dataObject 		  = jObject.getJSONObject("data"); 
                JSONObject notficationsObject = jObject.getJSONObject("notifications"); 
                
                if(errorCode.equals("0")){
                	
	                sessionKey  		= dataObject.getString("sessionKey"); 
	                String  displayName = dataObject.getString("displayName");
	                
	                UserModel userModel = new UserModel(); 
	        		userModel.setEmailAddress(Email);
	        		userModel.setUserPassword(Password);
	        		userModel.setdisplayName(displayName);
	        		userModel.setUserToken(sessionKey);
	                
	        		Singleton.getInstance().addUserModel(userModel);
	        		
                }
                else{
                	errorName 	= notficationsObject.getString("errorName"); 
                } 
                
            } catch (Exception e) {
                Error = e.getMessage();   
                cancel(true);
                Log.i("SignInException",Error);
                Dialog.dismiss(); 
            }
			return null; 
        }
         
        @Override
		protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here. 
        	Dialog.dismiss();
    		if(errorCode.equals("0")){ 
				Intent openServerHitActivity = new Intent(ServerController.this, ServerController.class);
				openServerHitActivity.putExtra("targetServerHit","DashBoard");
				openServerHitActivity.putExtra("cameFromClass","DashboardActivity"); 
		        startActivity(openServerHitActivity);
		        
    		}else{
    			if(cameFromClass.equalsIgnoreCase("SignInActivity")){
    				Intent openServerHitActivity = new Intent(ServerController.this, SignInActivity.class);
    				openServerHitActivity.putExtra("ControllerStatus","Error");
    				openServerHitActivity.putExtra("ControllerTitle","SignIn Error");
    				openServerHitActivity.putExtra("ControllerMessage",errorName);
    				
    		        startActivity(openServerHitActivity);
    			}
    			 
    		}  
        } 
    } 
	private class ServerHitForgetPassword  extends AsyncTask<String, Void, Void> { 
		 
        private final HttpClient Client = new DefaultHttpClient(); 
        private String Error = null;
        String result;
        String  errorName,message;
        String errorCode;
        private ProgressDialog Dialog = new ProgressDialog(ServerController.this);
         	  
        @Override
		protected void onPreExecute() {
            // NOTE: You can call UI Element here.
             
            //UI Element 
            Dialog.setMessage("Submitting Email...");
            Dialog.show();
        }
 
        // Call after onPreExecute method
        @Override
		protected Void doInBackground(String... urls) {
            try {
                  
                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.
                  
                HttpPost httppost = new HttpPost(urls[0]); 
                
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1); 
                nameValuePairs.add(new BasicNameValuePair("Email", Email)); 
                
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response 		  = Client.execute(httppost);
                HttpEntity entity 	  		  = response.getEntity();
                InputStream instream  		  = entity.getContent();
                result 						  = com.devtuts.quizhive.util.Util.convertStreamToString(instream);
                
                JSONObject jObject 			  = new JSONObject(result); 
                errorCode 			  		  = jObject.getString("errorCode"); 
                
                JSONObject dataObject 		  = jObject.getJSONObject("data"); 
                JSONObject notficationsObject = jObject.getJSONObject("notifications"); 
                
                if(errorCode.equals("0")){  
	                message 			= dataObject.getString("message");  
                }
                else{
                	errorName 	= notficationsObject.getString("errorName"); 
                } 
                
            } catch (Exception e) {
                Error = e.getMessage();   
                cancel(true);
                Log.i("ForgetPasswordException",Error);
                Dialog.dismiss(); 
            }
			return null; 
        }
         
        @Override
		protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here. 
        	Dialog.dismiss(); 
    		if(errorCode.equals("0")){ 
    	 		Intent openDashboardActivity = new Intent(ServerController.this, ForgetPasswordActivity.class);
    	 		openDashboardActivity.putExtra("ControllerStatus","Success");
    	 		openDashboardActivity.putExtra("ControllerTitle","Don't Worry");
    	 		openDashboardActivity.putExtra("ControllerMessage","Password Has Been Sent, Check Your Email.");
				
    	 		startActivity(openDashboardActivity);
    	 	}else{ 
    	 		
    	 		if(cameFromClass.equalsIgnoreCase("ForgetPasswordActivity")){
    				Intent openServerHitActivity = new Intent(ServerController.this, ForgetPasswordActivity.class);
    				openServerHitActivity.putExtra("ControllerStatus","Error");
    				openServerHitActivity.putExtra("ControllerTitle","ForgetPassword Error");
    				openServerHitActivity.putExtra("ControllerMessage",errorName);
    				
    		        startActivity(openServerHitActivity);
    			} 
    	 	}  
        } 
    }  
}
