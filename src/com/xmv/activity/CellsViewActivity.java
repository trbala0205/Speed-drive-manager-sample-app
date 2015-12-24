package com.xmv.activity;

import java.util.ArrayList;

import com.xmv.helper.TransitionHelper;
import com.xmv.widget.adapter.CellsAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class CellsViewActivity extends ActionBarActivity{

	
	ActionBar mActionBar;
    Toolbar mToolbar;
    private ScrollView scrollView;
    private ListView cells_listView;
    private CellsAdapter cellsAdapter;
    private ArrayList<String> cellName = new ArrayList<String>();
    
	@Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public static Intent getStartIntent(Context context) {
        Intent starter = new Intent(context, CellsViewActivity.class);
        //starter.putExtra(Category.TAG, category.getId());
        return starter;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cells_activity);
        
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("Cells");
        mActionBar.setDisplayHomeAsUpEnabled(true);
        
        cellName.add("Cell 1");
        cellName.add("Cell 2");
        cellName.add("Cell 3");
        cellName.add("Cell 4");
        cellName.add("Cell 5");
        cellName.add("Cell 6");
        cells_listView = (ListView)findViewById(R.id.cells_listview);
        cellsAdapter = new CellsAdapter(getApplicationContext(), R.layout.cells_activity_item, cellName);
        
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 00.0f,Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 5.0f,Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(700);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);

        cells_listView.setLayoutAnimation(controller);
        
        cells_listView.setAdapter(cellsAdapter);
        cells_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Activity activity = CellsViewActivity.this;
				//startQuizActivityWithTransition(activity, view.findViewById(R.id.txt_cellName), cellName.get(position));
				activity.startActivity(SingleCellActivity.getStartIntent(activity, cellName.get(position)), null);
			}
		});
        
        //startAnimation(cells_listView);
        /*final CardView cell_3_cardview = (CardView)findViewById(R.id.cell_3_cardview);
        cell_3_cardview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Activity activity = CellsViewActivity.this;
                startQuizActivityWithTransition(activity, v.findViewById(R.id.txt_cell_3));
			}
		});
        
        scrollView = (ScrollView)findViewById(R.id.cells_scroll_view);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new OnScrollChangedListener() {
			
			@Override
			public void onScrollChanged() {
				int top = scrollView.getScrollY(); // Increases when scrolling up ^
				int newTop = (int) (top * .5f);
				cell_3_cardview.setTranslationY(newTop < 0 ? 0 : newTop);
			}
		});*/
        
    }
    
    private boolean isUp;
    private void startAnimation(final ListView _listView)
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
                _listView.startAnimation(anim);

                //set new params
                /*LayoutParams params = new LayoutParams(cardView.getLayoutParams());
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(layoutTopOrBottomRule);
                cardView.setLayoutParams(params);*/
            }
        });

        _listView.startAnimation(animation);

        //reverse direction
        //isUp = !isUp;
    }
    
    private float getScreenHeight() {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return (float) displaymetrics.heightPixels;

    }
    
    private void startQuizActivityWithTransition(Activity activity, View toolbar, String _cellName) {

        final Pair[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                new Pair<>(toolbar, _cellName));
        @SuppressWarnings("unchecked")
		ActivityOptions sceneTransitionAnimation = ActivityOptions.makeSceneTransitionAnimation(activity, pairs);

        // Start the activity with the participants, animating from one to the other.
        final Bundle transitionBundle = sceneTransitionAnimation.toBundle();
        activity.startActivity(SingleCellActivity.getStartIntent(activity, _cellName), transitionBundle);
    }
}
