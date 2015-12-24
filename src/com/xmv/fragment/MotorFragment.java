package com.xmv.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.xmv.activity.R;

public class MotorFragment extends Fragment{
	private Context context;
	View rootView;
	private ImageView mIcon;
	private static final String IMAGE_CATEGORY = "pe_launcher";
	public static final String DRAWABLE = "drawable";
	private Interpolator mInterpolator;
	
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

        rootView = paramLayoutInflater.inflate(R.layout.fragment_status, paramViewGroup, false);

        mInterpolator = AnimationUtils.loadInterpolator(getActivity(), android.R.interpolator.fast_out_slow_in);
        mIcon = (ImageView) rootView.findViewById(R.id.icon);
        int resId = getResources().getIdentifier(IMAGE_CATEGORY, DRAWABLE, getActivity().getPackageName());
        mIcon.setImageResource(resId);
        mIcon.setImageResource(resId);
        mIcon.animate()
                .scaleX(1)
                .scaleY(1)
                .alpha(1)
                .setInterpolator(mInterpolator)
                .setStartDelay(300)
                .start();
        
        return rootView;
    }
}