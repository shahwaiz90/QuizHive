package com.devtuts.quizhive.views;
/**
 * author: Ahmad Shahwaiz
 * www.ahmadshahwaiz.com
 */

import com.devtuts.quizhive.R;
import com.devtuts.quizhive.models.CustomDialogBox;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent; 
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button registerButton; 	
	Button signInButton; 	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_main);
		
		registerButton		= 	(Button) findViewById(R.id.MainActivityRegister);
		signInButton		= 	(Button) findViewById(R.id.MainActivitySignIn);
		
		registerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				   
				 Intent openRegisterActivity = new Intent(MainActivity.this, RegisterActivity.class);
		         startActivity(openRegisterActivity);
			}
		});
		
		signInButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent openRegisterActivity = new Intent(MainActivity.this, SignInActivity.class);
		        startActivity(openRegisterActivity);
			}
		});
	}
	@Override
	public void onBackPressed() {
		finish();
	}

}
