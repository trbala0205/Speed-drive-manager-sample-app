package com.xmv.fragment;

import java.security.acl.Group;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xmv.activity.CellsViewActivity;
import com.xmv.activity.GroupActivity;
import com.xmv.activity.GroupListViewActivity;
import com.xmv.activity.R;
import com.xmv.helper.TransitionHelper;

public class ParamCardFragment extends Fragment{

	View rootView;
    Context context;
    LinearLayout mConfiguration, mVisualization;
    
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
	                             Bundle savedInstanceState) {
	        rootView = inflater.inflate(R.layout.card_param, parent, false);

	        context = getActivity();
	        mConfiguration = (LinearLayout) rootView.findViewById(R.id.card_status_apprentice_parent);
	        mVisualization = (LinearLayout) rootView.findViewById(R.id.card_status_guru_parent);
	        setOnClickListeners();
	        
	        return rootView;
	    }
	 
	 private void setOnClickListeners() {
		 mConfiguration.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	            	Activity activity = getActivity();
	                startQuizActivityWithTransition(activity, view.findViewById(R.id.txt_config));
	            }
	        });
		 
		 mVisualization.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	            	Activity activity = getActivity();
	                //startQuizActivityWithTransition(activity, view.findViewById(R.id.txt_visual));
	            	activity.startActivity(CellsViewActivity.getStartIntent(activity), null);
	            }
	        });
	    }
	 
	 private void startQuizActivityWithTransition(Activity activity, View toolbar) {

	        /*final Pair[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
	                new Pair<>(toolbar, "Transistion"));
	        ActivityOptions sceneTransitionAnimation = ActivityOptions.makeSceneTransitionAnimation(activity, pairs);*/

	        // Start the activity with the participants, animating from one to the other.
	        final Bundle transitionBundle = null; //sceneTransitionAnimation.toBundle();
	        //activity.startActivity(GroupActivity.getStartIntent(activity), transitionBundle);
	        activity.startActivity(GroupListViewActivity.getStartIntent(activity), transitionBundle);
	    }
}
