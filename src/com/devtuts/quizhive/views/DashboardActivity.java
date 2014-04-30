package com.devtuts.quizhive.views;

/**
 * author: Ahmad Shahwaiz
 * www.ahmadshahwaiz.com
 */
 
import java.util.ArrayList;  

import com.devtuts.quizhive.R;   
import com.devtuts.quizhive.models.CustomDialogBox;
import com.devtuts.quizhive.models.MovieModel;
import com.devtuts.quizhive.models.Singleton;  
 
import android.os.Bundle;  
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity; 
import android.content.Context;  
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class DashboardActivity extends Activity {

	private ListView listView;

	private ArrayList<String> title;
 
	private ArrayList<String> desc;
	private ArrayList <MovieModel> movieObject;
	private ArrayList<Integer> thumb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		
		Bundle extras = getIntent().getExtras();
		if (getIntent().hasExtra("ControllerStatus")) {
			
			String controllerStatus		= extras.getString("ControllerStatus");
			
			if(controllerStatus.equalsIgnoreCase("Error")){
		    	String controllerTitle		= extras.getString("ControllerTitle");
			    String controllerMsg		= extras.getString("ControllerMessage");
		    	 CustomDialogBox prompt = new CustomDialogBox(DashboardActivity.this);
					prompt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					prompt.show();
					prompt.setCancelable(false);
					prompt.setCanceledOnTouchOutside(false);
					prompt.title.setText(controllerTitle);
					prompt.detail.setText(controllerMsg);
		    } 
		}
		title	= 	new ArrayList<String>();
		desc 	= 	new ArrayList<String>();
		thumb	=	new ArrayList<Integer>();
		movieObject = new ArrayList<MovieModel>();
		ListFunction();
	}
	 
		private void ListFunction() {

			//copying array
			for(int i = 0; i < Singleton.getInstance().getCategoryModel().size(); i++){
				title.add(Singleton.getInstance().getCategoryModel().get(i).getCategoryName());
				desc.add(Singleton.getInstance().getCategoryModel().get(i).getCategoryCount());
				thumb.add(R.drawable.arrow);
			}
			
			listView = (ListView) findViewById(R.id.listView); 
			listView.setAdapter(new VersionAdapter(DashboardActivity.this)); 
			listView.setOnItemClickListener(new OnItemClickListener() {
 
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) { 
					int pos = arg2;

					LayoutInflater layoutInflator = getLayoutInflater();

					View layout = layoutInflator.inflate(R.layout.custom_toast,
							(ViewGroup) findViewById(R.id.toast_layout_root));

					ImageView iv = (ImageView) layout.findViewById(R.id.toast_iv);
					TextView tv = (TextView) layout.findViewById(R.id.toast_tv);

					iv.setBackgroundResource((Integer) thumb.get(pos));
					tv.setText((CharSequence) title.get(pos));
				 	
					for(int i=0; i < Singleton.getInstance().getCategoryModel().get(pos).getMovieObject().size(); i++){
						MovieModel movieModel = new MovieModel();
						movieModel.setMovieId(Singleton.getInstance().getCategoryModel().get(pos).getMovieObject().get(i).getMovieId());
						movieModel.setMovieName(Singleton.getInstance().getCategoryModel().get(pos).getMovieObject().get(i).getMovieName());
						movieModel.addMovieObject(movieModel); 
						Singleton.getInstance().addQuizList(movieModel);
					}  
					
					Intent openRegisterActivity = new Intent(DashboardActivity.this, QuizesActivity.class);
					openRegisterActivity.putExtra("MovieObjectArrayList", movieObject);
					startActivity(openRegisterActivity);
			        
				}
			}); 
		}  
	class VersionAdapter extends BaseAdapter {

		private LayoutInflater layoutInflater;

		public VersionAdapter(DashboardActivity dashboardActivity) { 
			layoutInflater = (LayoutInflater) dashboardActivity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() { 
			return title.size();
		}

		@Override
		public Object getItem(int position) { 
			return position;
		}

		@Override
		public long getItemId(int position) { 
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) { 

			View listItem = convertView;
			int pos = position;
			if (listItem == null) {
				listItem = layoutInflater.inflate(R.layout.dashboard_list_item, null);
			}

			// Initialize the views in the layout
			ImageView iv = (ImageView) listItem.findViewById(R.id.thumb);
			TextView tvTitle = (TextView) listItem.findViewById(R.id.title);
			TextView tvDesc = (TextView) listItem.findViewById(R.id.desc);

			// set the views in the layout
			iv.setBackgroundResource((Integer) thumb.get(pos));
			tvTitle.setText((CharSequence) title.get(pos));
			tvDesc.setText((CharSequence) desc.get(pos));

			return listItem;
		} 
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.home:
	    	Toast.makeText(getApplicationContext(), "This is Home", Toast.LENGTH_LONG).show();
	        return true;
	        
	    case R.id.profile:
	    	Toast.makeText(getApplicationContext(), "This is Profile", Toast.LENGTH_LONG).show();
	        return true;
	        
	    case R.id.friends:
	    	Toast.makeText(getApplicationContext(), "This is Friends", Toast.LENGTH_LONG).show();
	        return true;
	        
	    case R.id.settings:
	    	Toast.makeText(getApplicationContext(), "This is Settings", Toast.LENGTH_LONG).show();
	        return true;
	    
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
 
	 
}
