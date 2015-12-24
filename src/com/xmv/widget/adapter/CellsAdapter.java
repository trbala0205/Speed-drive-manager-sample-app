package com.xmv.widget.adapter;

import java.util.ArrayList;

import com.xmv.activity.R;
import com.xmv.data_access_model.CellsPropertyinfo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CellsAdapter extends ArrayAdapter{

	private int mLastPosition;
	private Context context;
    //private ArrayList<CellsPropertyinfo> items;
	private ArrayList<String> items;
    
	public CellsAdapter(Context context, int resourceId, ArrayList<String> items) {
		super(context, resourceId, items);
		this.context = context;
        this.items = items;
	}
	
	/*private view holder class*/
    private static class ViewHolder {
    	TextView txt_cellName;
        TextView txt_phaseU;
        TextView txt_phaseV;
        TextView txt_phaseW;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        //CellsPropertyinfo rowItem = getItem(position);
         
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.cells_activity_item, null);
            viewHolder = new ViewHolder();
            viewHolder.txt_cellName = (TextView) convertView.findViewById(R.id.txt_cellName);
            viewHolder.txt_phaseU = (TextView) convertView.findViewById(R.id.txt_phaseU);
            viewHolder.txt_phaseV = (TextView) convertView.findViewById(R.id.txt_phaseV);
            viewHolder.txt_phaseW = (TextView) convertView.findViewById(R.id.txt_phaseW);
            convertView.setTag(viewHolder);
        } else
        	viewHolder = (ViewHolder) convertView.getTag();
        
        //int flagResId = context.getResources().getIdentifier("ip_addr_img48x48", "drawable", context.getPackageName());
        //viewHolder.txtParamName.setCompoundDrawablesWithIntrinsicBounds(flagResId, 0, 0, 0);
        
        viewHolder.txt_cellName.setText(this.items.get(position));
        /*viewHolder.txt_phaseU.setText(rowItem.getPhaseU());
        viewHolder.txt_phaseV.setText(rowItem.getPhaseV());
        viewHolder.txt_phaseW.setText(rowItem.getPhaseW());*/
        
        // This tells the view where to start based on the direction of the scroll.
        // If the last position to be loaded is <= the current position, we want
        // the views to start below their ending point (500f further down).
        // Otherwise, we start above the ending point.
        float initialPosition = (mLastPosition <= position ? 500f : -500f);
        if(mLastPosition == 0)
        {
        	convertView.setTranslationY(initialPosition);
	        convertView.animate()
	        			.setInterpolator(new DecelerateInterpolator(1.0f))
	        			.translationY(0f)
	                    .setDuration(0l)
	                    .setListener(null);
        }else{
	        convertView.setTranslationY(initialPosition);
	        convertView.animate()
	        			.setInterpolator(new DecelerateInterpolator(1.0f))
	        			.translationY(0f)
	                    .setDuration(300l)
	                    .setListener(null);
        }
     // Keep track of the last position we loaded
        mLastPosition = position;
         
        return convertView;
    }
}
