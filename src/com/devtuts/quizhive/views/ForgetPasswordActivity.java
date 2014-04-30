package com.devtuts.quizhive.views;
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
import org.json.JSONObject;

import com.devtuts.quizhive.R;
import com.devtuts.quizhive.controller.ServerController;
import com.devtuts.quizhive.models.CustomDialogBox;
import com.devtuts.quizhive.models.Singleton;
import com.devtuts.quizhive.models.UserModel;
import com.devtuts.quizhive.util.Util; 

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent; 
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button; 
import android.widget.EditText; 

public class ForgetPasswordActivity extends Activity {
 
	Button emailSubmitButton; 	
	Button crossSign; 			 
	EditText EmailAddress;
	
	public boolean isNetworkAvailable() {
	    ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	    // if no network is available networkInfo will be null
	    // otherwise check if we are connected
	    if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    }
	    return false;
	} 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);
		
		Bundle extras = getIntent().getExtras();
		if (getIntent().hasExtra("ControllerStatus")) {
		    String controllerStatus		= extras.getString("ControllerStatus");
		    String controllerTitle		= extras.getString("ControllerTitle");
		    String controllerMsg		= extras.getString("ControllerMessage");
		    
		    if(controllerStatus.equalsIgnoreCase("Error")){
		    	 CustomDialogBox prompt = new CustomDialogBox(ForgetPasswordActivity.this);
					prompt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					prompt.show();
					prompt.setCancelable(false);
					prompt.setCanceledOnTouchOutside(false);
					prompt.title.setText(controllerTitle);
					prompt.detail.setText(controllerMsg);
		    } else if(controllerStatus.equalsIgnoreCase("Success")){
		    	 CustomDialogBox prompt = new CustomDialogBox(ForgetPasswordActivity.this);
					prompt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					prompt.show();
					prompt.setCancelable(false);
					prompt.setCanceledOnTouchOutside(false);
					prompt.title.setText(controllerTitle);
					prompt.detail.setText(controllerMsg);
		    }
		}
		
		emailSubmitButton	= 	(Button) findViewById(R.id.ForgetActivitySubmit);
		crossSign			=	(Button) findViewById(R.id.ForgetActivityCross);
		EmailAddress		=	(EditText) findViewById(R.id.EmailAddress);
		emailSubmitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				if(isNetworkAvailable()){
					if(EmailAddress.getText().toString().length() < 5){
						CustomDialogBox prompt = new CustomDialogBox(ForgetPasswordActivity.this);
						prompt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						prompt.show();
						prompt.setCancelable(false);
						prompt.setCanceledOnTouchOutside(false);
						prompt.title.setText("ForgetPassword Error");
						prompt.detail.setText("Email should be more than 4 characters.");
					}
					else if(!Util.isValidEmail(EmailAddress.getText().toString())){
						CustomDialogBox prompt = new CustomDialogBox(ForgetPasswordActivity.this);
						prompt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						prompt.show();
						prompt.setCancelable(false);
						prompt.setCanceledOnTouchOutside(false);
						prompt.title.setText("ForgetPassword Error");
						prompt.detail.setText("This Emaill Address is not Valid.");
					}else{
						Intent openServerHitActivity = new Intent(ForgetPasswordActivity.this, ServerController.class);
						openServerHitActivity.putExtra("cameFromClass","ForgetPasswordActivity"); 
						openServerHitActivity.putExtra("targetServerHit","ForgetPassword");
						openServerHitActivity.putExtra("Email",EmailAddress.getText().toString()); 
						
				        startActivity(openServerHitActivity); 
					}
				}
				else{
					CustomDialogBox prompt = new CustomDialogBox(ForgetPasswordActivity.this);
					prompt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					prompt.show();
					prompt.setCancelable(false);
					prompt.setCanceledOnTouchOutside(false);
					prompt.title.setText("ForgetPassword Error");
					prompt.detail.setText("You must be connected to Internet to SignIn.");
				}
			}
		});
		
		crossSign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				Intent openRegisterActivity = new Intent(ForgetPasswordActivity.this, MainActivity.class);
		        startActivity(openRegisterActivity);
			}
		});  
	}  
	

}
