package com.devtuts.quizhive.views;
/**
 * author: Ahmad Shahwaiz
 * www.ahmadshahwaiz.com
 */
  

import com.devtuts.quizhive.R; 
import com.devtuts.quizhive.controller.ServerController;
import com.devtuts.quizhive.models.CustomDialogBox; 
import com.devtuts.quizhive.models.UserModel;
import com.devtuts.quizhive.util.Util;

import android.net.ConnectivityManager;
import android.net.NetworkInfo; 
import android.os.Bundle;
import android.app.Activity; 
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable; 
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView; 

public class RegisterActivity extends Activity {
	
	TextView alreadyHaveId;  
	Button registerButton; 	 
	Button crossSign; 		 
	
	EditText DisplayName, EmailAddress, Password;
	UserModel userModel;
	
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
		setContentView(R.layout.activity_register);
		
		Bundle extras = getIntent().getExtras();
		if (getIntent().hasExtra("ControllerStatus")) {
		    String controllerStatus		= extras.getString("ControllerStatus");
		    String controllerTitle		= extras.getString("ControllerTitle");
		    String controllerMsg		= extras.getString("ControllerMessage");
		    if(controllerStatus.equalsIgnoreCase("Error")){
		    	 CustomDialogBox prompt = new CustomDialogBox(RegisterActivity.this);
					prompt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					prompt.show();
					prompt.setCancelable(false);
					prompt.setCanceledOnTouchOutside(false);
					prompt.title.setText(controllerTitle);
					prompt.detail.setText(controllerMsg);
		    } 
		}
		alreadyHaveId 		= 	(TextView) findViewById(R.id.AlreadyHaveId);
		registerButton 		= 	(Button) findViewById(R.id.RegActivityRegister);
		crossSign			= 	(Button) findViewById(R.id.RegActivityCross);
		
		DisplayName 		= 	(EditText) findViewById(R.id.FirstName);
		EmailAddress 		= 	(EditText) findViewById(R.id.EmailAddress);
		Password			= 	(EditText) findViewById(R.id.Password);
		 
		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 
				if(isNetworkAvailable()){
					if(DisplayName.getText().toString().length() < 4 ){
						CustomDialogBox prompt = new CustomDialogBox(RegisterActivity.this);
						prompt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						prompt.show();
						prompt.setCancelable(false);
						prompt.setCanceledOnTouchOutside(false);
						prompt.title.setText("Registeration Error");
						prompt.detail.setText("Display Name should be more than 3 characters.");
					}
					else if(EmailAddress.getText().toString().length() < 5){
						CustomDialogBox prompt = new CustomDialogBox(RegisterActivity.this);
						prompt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						prompt.show();
						prompt.setCancelable(false);
						prompt.setCanceledOnTouchOutside(false);
						prompt.title.setText("Registeration Error");
						prompt.detail.setText("Email should be more than 4 characters.");
					} 
					else if(!Util.isValidEmail(EmailAddress.getText().toString())){
						CustomDialogBox prompt = new CustomDialogBox(RegisterActivity.this);
						prompt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						prompt.show();
						prompt.setCancelable(false);
						prompt.setCanceledOnTouchOutside(false);
						prompt.title.setText("Registeration Error");
						prompt.detail.setText("This Emaill Address is not Valid.");
					}else if(Password.getText().toString().length() < 6){
						CustomDialogBox prompt = new CustomDialogBox(RegisterActivity.this);
						prompt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						prompt.show();
						prompt.setCancelable(false);
						prompt.setCanceledOnTouchOutside(false);
						prompt.title.setText("Registeration Error");
						prompt.detail.setText("Password Length should be greater than 5 characters.");
					}else{
						Intent openServerHitActivity = new Intent(RegisterActivity.this, ServerController.class);
						openServerHitActivity.putExtra("Server","Register");
						openServerHitActivity.putExtra("cameFromClass","RegisterActivity");
						openServerHitActivity.putExtra("targetServerHit","Register");
						openServerHitActivity.putExtra("DisplayName",DisplayName.getText().toString());
						openServerHitActivity.putExtra("Email",EmailAddress.getText().toString());
						openServerHitActivity.putExtra("Password",Password.getText().toString());
						
				        startActivity(openServerHitActivity);
					}  
				}else{
					CustomDialogBox prompt = new CustomDialogBox(RegisterActivity.this);
					prompt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					prompt.show();
					prompt.setCancelable(false);
					prompt.setCanceledOnTouchOutside(false);
					prompt.title.setText("Registeration Error");
					prompt.detail.setText("You must be connected to Internet to SignIn.");
				}
			}
		});
		
		crossSign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent openRegisterActivity = new Intent(RegisterActivity.this, MainActivity.class);
		        startActivity(openRegisterActivity);
			}
		});
		alreadyHaveId.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent openRegisterActivity = new Intent(RegisterActivity.this, SignInActivity.class);
		        startActivity(openRegisterActivity);
			}
		}); 
	} 
}
