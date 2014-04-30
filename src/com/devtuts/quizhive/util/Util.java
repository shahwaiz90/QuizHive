package com.devtuts.quizhive.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader; 
import android.app.Activity; 

public  class Util extends Activity {

	public static String registerationUrl 	= "http://ahmadshahwaiz.com/QuizHive/signupService.php";
	public static String signInUrl			= "http://ahmadshahwaiz.com/QuizHive/loginService.php"; 
	public static String forgetPasswordUrl	= "http://ahmadshahwaiz.com/QuizHive/forgetPassword.php";
	public static String getCategoryUrl		= "http://ahmadshahwaiz.com/QuizHive/categoryService.php";
	
	public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
        	return false;
        } else {
        	return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    } 
	public static String convertStreamToString(InputStream is) {

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
}
