package com.devtuts.quizhive.views;
/**
 * author: Ahmad Shahwaiz
 * www.ahmadshahwaiz.com
 */

import com.devtuts.quizhive.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ImageAdapter extends ArrayAdapter
{
    Activity context;
    String[] items; 
    int layoutId;
    int textId;
    int imageId;
 
    ImageAdapter(Activity context, int layoutId, int textId, int imageId, String[] items)
    {
        super(context, layoutId, items);
 
        this.context = context;
        this.items = items; 
        this.layoutId = layoutId;
        this.textId = textId;
        this.imageId = imageId;
    }
 
    @Override
	public View getView(int pos, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater=context.getLayoutInflater();
        View row=inflater.inflate(layoutId, null);
        TextView label=(TextView)row.findViewById(textId);
 
        label.setText(items[pos]);
          
        ImageView icon=(ImageView)row.findViewById(imageId); 
        icon.setImageResource(R.drawable.arrow); 
 
        return(row);
    }
}
