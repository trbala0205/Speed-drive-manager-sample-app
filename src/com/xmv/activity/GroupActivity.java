package com.xmv.activity;

import com.xmv.fragment.GroupParameterFragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

@SuppressLint("NewApi")
public class GroupActivity extends Activity {

	private static final int UNDEFINED = -1;
	
	private Interpolator mInterpolator;
	private Toolbar mToolbar;
	private View view_separator;
	private Animator mCircularReveal;
	
	private GroupParameterFragment groupParamFragment;
	
	private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                /*case R.id.fab_quiz:
                    startQuizFromClickOn(v);
                    break;
                case R.id.submitAnswer:
                    submitAnswer();
                    break;
                case R.id.quiz_done:
                    finishAfterTransition();
                    break;*/
                case UNDEFINED:
                    final CharSequence contentDescription = v.getContentDescription();
                    if (contentDescription != null && contentDescription.equals(getString(R.string.up))) {
                        onBackPressed();
                        break;
                    }
                default:
                    throw new UnsupportedOperationException(
                            "OnClick has not been implemented for " + getResources().
                                    getResourceName(v.getId()));
            }
        }
    };
    
	public static Intent getStartIntent(Context context) {
        Intent starter = new Intent(context, GroupActivity.class);
        //starter.putExtra(Category.TAG, category.getId());
        return starter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate and set the enter transition for this activity.
        final Transition sharedElementEnterTransition = TransitionInflater.from(this)
                .inflateTransition(R.transition.quiz_enter);
        getWindow().setSharedElementEnterTransition(sharedElementEnterTransition);

        //mCategoryId = getIntent().getStringExtra(Category.TAG);
        mInterpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);
        /*if (null != savedInstanceState) {
            mSavedStateIsPlaying = savedInstanceState.getBoolean(STATE_IS_PLAYING);
        }*/
        //populate(mCategoryId);
        
        initLayout();
        initToolbar();
        //startQuizFromClickOn(view_separator);
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onBackPressed() {
    	startQuizFromClickOn(view_separator);
            super.onBackPressed();

    }
    private void startQuizFromClickOn(final View view) {
        //initQuizFragment();
    	groupParamFragment = new GroupParameterFragment();
        getFragmentManager().beginTransaction().replace(R.id.parameter_fragment_container, groupParamFragment, "").commit();
        final View fragmentContainer = findViewById(R.id.parameter_fragment_container);
        int centerX = (view.getLeft() + view.getRight()) / 2;
        int centerY = (view.getTop() + view.getBottom()) / 2;
        int finalRadius = Math.max(fragmentContainer.getWidth(), fragmentContainer.getHeight());
        mCircularReveal = ViewAnimationUtils.createCircularReveal(
                fragmentContainer, centerX, centerY, 0, finalRadius);
        fragmentContainer.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);

        mCircularReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //mIcon.setVisibility(View.GONE);
                mCircularReveal.removeListener(this);
            }
        });

        mCircularReveal.start();

        // the toolbar should not have more elevation than the content while playing
        mToolbar.setElevation(0);
    }
    
    private void initLayout() {
        setContentView(R.layout.activity_parameter);
        
        view_separator = (View) findViewById(R.id.view_separator);
    }
    
    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_param);
        mToolbar.setTitle("Group");
        mToolbar.setNavigationOnClickListener(mOnClickListener);
        /*if (mSavedStateIsPlaying) {
            // the toolbar should not have more elevation than the content while playing
            mToolbar.setElevation(0);
        }*/
    }
}
