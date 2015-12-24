package com.xmv.widget.adapter;

import java.util.ArrayList;

import com.xmv.activity.R;
import com.xmv.data_access_model.ParameterDataUnitInfo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListViewAdapter extends ArrayAdapter<ParameterDataUnitInfo> {
	 
    Context context;
    private ArrayList<ParameterDataUnitInfo> items;
 
    public CustomListViewAdapter(Context context, int resourceId, ArrayList<ParameterDataUnitInfo> items) {
        super(context, resourceId, items);
        this.context = context;
        this.items = items;
    }
     
    public ArrayList<ParameterDataUnitInfo> getData()
    {
    	return items;
    }
    
    /*private view holder class*/
    private class ViewHolder {
        TextView txtParamName;
        TextView txtDataUnit;
    }
     
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ParameterDataUnitInfo rowItem = getItem(position);
         
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.parameter_dataunit, null);
            holder = new ViewHolder();
            holder.txtParamName = (TextView) convertView.findViewById(R.id.txt_paramname);
            holder.txtDataUnit = (TextView) convertView.findViewById(R.id.txt_dataunit);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
                 
        holder.txtParamName.setText(rowItem.getParamName());
        holder.txtDataUnit.setText(rowItem.getDataUnit());
         
        return convertView;
    }
}
