package com.xmv.fragment;

import com.cardiomood.android.controls.gauge.SpeedometerGauge;
import com.xmv.activity.R;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StatusCard extends Fragment{

	View rootView;
    Context context;
    private SpeedometerGauge speed_analogue_speedometer, load_analogue_speedometer;
    
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
	                             Bundle savedInstanceState) {
	        rootView = inflater.inflate(R.layout.card_status, parent, false);

	        context = getActivity();
	        
	        speed_analogue_speedometer = (SpeedometerGauge) rootView.findViewById(R.id.speed_analogue_speedometer);
	        speed_analogue_speedometer.setMaxSpeed(300);
	        speed_analogue_speedometer.setLabelConverter(new SpeedometerGauge.LabelConverter() {
	            @Override
	            public String getLabelFor(double progress, double maxProgress) {
	                return String.valueOf((int) Math.round(progress));
	            }
	        });
	        speed_analogue_speedometer.setCenterLabel("SPEED");
	        speed_analogue_speedometer.setMaxSpeed(300);
	        speed_analogue_speedometer.setMajorTickStep(30);
	        speed_analogue_speedometer.setMinorTicks(2);
	        speed_analogue_speedometer.addColoredRange(30, 140, Color.GREEN);
	        speed_analogue_speedometer.addColoredRange(140, 180, Color.YELLOW);
	        speed_analogue_speedometer.addColoredRange(180, 400, Color.RED);
	        speed_analogue_speedometer.setSpeed(180, 1000, 300);
	        
	        load_analogue_speedometer = (SpeedometerGauge) rootView.findViewById(R.id.load_analogue_speedometer);
	        load_analogue_speedometer.setMaxSpeed(300);
	        load_analogue_speedometer.setLabelConverter(new SpeedometerGauge.LabelConverter() {
	            @Override
	            public String getLabelFor(double progress, double maxProgress) {
	                return String.valueOf((int) Math.round(progress));
	            }
	        });
	        load_analogue_speedometer.setCenterLabel("LOAD");
	        load_analogue_speedometer.setMaxSpeed(300);
	        load_analogue_speedometer.setMajorTickStep(30);
	        load_analogue_speedometer.setMinorTicks(2);
	        load_analogue_speedometer.addColoredRange(30, 140, Color.GREEN);
	        load_analogue_speedometer.addColoredRange(140, 180, Color.YELLOW);
	        load_analogue_speedometer.addColoredRange(180, 400, Color.RED);
	        load_analogue_speedometer.setSpeed(90, 1000, 300);

	        return rootView;
	    }
	 
}
