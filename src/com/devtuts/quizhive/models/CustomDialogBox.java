package com.devtuts.quizhive.models;

import com.devtuts.quizhive.R;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialogBox  extends Dialog implements
android.view.View.OnClickListener {

	public Activity c;
	public Dialog d;
	public Button okButton;
	public TextView title, detail;

	public CustomDialogBox(Activity a) {
		super(a);
		// TODO Auto-generated constructor stub
		this.c = a;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		setContentView(R.layout.custom_dialog);  
		okButton 	= (Button)   findViewById(R.id.okButton); 
		title 		= (TextView) findViewById(R.id.title); 
		detail 		= (TextView) findViewById(R.id.detail);
		okButton.setOnClickListener(this); 
	} 
 
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) { 
		case R.id.okButton:
			dismiss();
			break;
		default:
			break;
		}
		dismiss();
	}
}
