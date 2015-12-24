package com.xmv.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Toolbar;

@SuppressLint("NewApi")
public class SingleCellActivity extends ActionBarActivity{

	private static final String IMAGE_CATEGORY = "icon_category_entertainment_raster";
	public static final String DRAWABLE = "drawable";
	private ImageView mIcon;
	private Interpolator mInterpolator;
	private android.support.v7.widget.Toolbar mToolbar;
	private ActionBar mActionBar;
	private String toolbar_title = "";
	
	public static Intent getStartIntent(Context context, String title) {
        Intent starter = new Intent(context, SingleCellActivity.class);
        starter.putExtra("toolbar_title", title);
        return starter;
    }
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate and set the enter transition for this activity.
		//getWindow().setAllowEnterTransitionOverlap(true);
        //final Transition sharedElementEnterTransition = TransitionInflater.from(this).inflateTransition(R.transition.quiz_enter);
        //getWindow().setSharedElementEnterTransition(sharedElementEnterTransition);
        super.onCreate(savedInstanceState);
        toolbar_title = getIntent().getStringExtra("toolbar_title");
        mInterpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);
        
        setTheme(R.style.AppTheme);
        initLayout();
        initToolbar();
        
    }
	
	private void initLayout() {
        setContentView(R.layout.activity_quiz);
        mIcon = (ImageView) findViewById(R.id.icon);
        int resId = getResources().getIdentifier(IMAGE_CATEGORY, DRAWABLE, getApplicationContext().getPackageName());
        mIcon.setImageResource(resId);
        mIcon.setImageResource(resId);
        mIcon.animate()
                .scaleX(1)
                .scaleY(1)
                .alpha(1)
                .setInterpolator(mInterpolator)
                .setStartDelay(300)
                .start();
    }

    private void initToolbar() {
        mToolbar = (android.support.v7.widget.Toolbar ) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle(toolbar_title);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        /*mToolbar.setNavigationOnClickListener(mOnClickListener);
        if (mSavedStateIsPlaying) {
            // the toolbar should not have more elevation than the content while playing
            mToolbar.setElevation(0);
        }*/
    }
}
