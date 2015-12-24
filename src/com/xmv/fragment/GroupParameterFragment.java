package com.xmv.fragment;

import com.xmv.activity.R;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GroupParameterFragment extends Fragment {
	
	private Context context;
	View rootView;
	
	@Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreate(Bundle paramBundle) {
        this.context = getActivity();
        super.onCreate(paramBundle);
    }
    
    @Override
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {

        rootView = paramLayoutInflater.inflate(R.layout.group_param_fragment, paramViewGroup, false);

        return rootView;

    }
}
