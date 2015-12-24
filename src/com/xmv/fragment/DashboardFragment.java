package com.xmv.fragment;

import com.xmv.activity.R;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.webkit.WebView.FindListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class DashboardFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

	private Context context;
	private View rootView;
	private ActionBarActivity activity;
	
	private LinearLayout mStatusHolder;
	private LinearLayout mParamHolder;
	private SwipeRefreshLayout mSwipeToRefreshLayout;
	private CardView cardView_ip, cardView_status, cardView_param;
	
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

        rootView = paramLayoutInflater.inflate(R.layout.fragment_dashboard, paramViewGroup, false);

        mSwipeToRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.dashboard_swipe_refresh);
        mSwipeToRefreshLayout.setOnRefreshListener(this);
        mSwipeToRefreshLayout.setColorSchemeResources(R.color.swipe_refresh);
        
        mStatusHolder = (LinearLayout) rootView.findViewById(R.id.fragment_dashboard_status_holder);
        //mParamHolder = (LinearLayout) rootView.findViewById(R.id.fragment_dashboard_param_holder);
        
        activity = (ActionBarActivity) getActivity();
        
        cardView_ip = (CardView)rootView.findViewById(R.id.dashboard_cardview_ip);
        cardView_status = (CardView)rootView.findViewById(R.id.dashboard_cardview_status);
        cardView_param = (CardView)rootView.findViewById(R.id.dashboard_cardview_param);
        
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        
        StatusCard statusCard = new StatusCard();
        ParamCardFragment paramCard = new ParamCardFragment();
        
        transaction.replace(R.id.fragment_dashboard_status_card, statusCard);
        transaction.replace(R.id.fragment_dashboard_param_card, paramCard);
        transaction.commit();
        
        startAnimation(cardView_ip);
        startAnimation(cardView_status);
        startAnimation(cardView_param);
        
        //setRefreshing();
        return rootView;

    }

    private void setRefreshing() {
        if (mSwipeToRefreshLayout != null)
            mSwipeToRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                	System.out.println("REFRSHING-------------------------");
                    mSwipeToRefreshLayout.setRefreshing(true);
                }
            });
    }
    
	@Override
	public void onRefresh() {
		//setRefreshing();
		new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
            	
                mSwipeToRefreshLayout.setRefreshing(false);
            }

        }, 10000);
		
		//mSwipeToRefreshLayout.setRefreshing(false);
	}
	
	private boolean isUp;
    private void startAnimation(final CardView _cardView)
    {
    	final float direction = (isUp) ? -1 : 1;
        final float yDelta = getScreenHeight() + 10; //(2 * cardView.getHeight());
        final int layoutTopOrBottomRule = (isUp) ? RelativeLayout.ALIGN_PARENT_TOP : RelativeLayout.ALIGN_PARENT_BOTTOM;
        final Animation animation = new TranslateAnimation(0,0,yDelta * direction,0);

        animation.setDuration(500);
        animation.setAnimationListener(new AnimationListener() {

            public void onAnimationStart(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {

                // fix flicking
                // Source : http://stackoverflow.com/questions/9387711/android-animation-flicker
                TranslateAnimation anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
                anim.setDuration(1);
                _cardView.startAnimation(anim);

                //set new params
                /*LayoutParams params = new LayoutParams(cardView.getLayoutParams());
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(layoutTopOrBottomRule);
                cardView.setLayoutParams(params);*/
            }
        });

        _cardView.startAnimation(animation);

        //reverse direction
        //isUp = !isUp;
    }
    
    private float getScreenHeight() {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return (float) displaymetrics.heightPixels;

    }
}
