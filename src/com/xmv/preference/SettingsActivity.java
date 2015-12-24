package com.xmv.preference;

import com.xmv.activity.R;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by xihsa_000 on 4/4/14.
 */
@SuppressWarnings("deprecation")
@SuppressLint("NewApi")
public class SettingsActivity extends ActionBarActivity implements View.OnClickListener {


    TextView mApiKey;
    RelativeLayout mCustomFonts;
    CheckBox mCustomFontsCheckBox;
    RelativeLayout mGeneralUseSpecificDates;
    CheckBox mGeneralUseSpecificDatesCheckBox;
    LinearLayout mDashboardRecentUnlocksNumber;
    LinearLayout mDashboardCriticalItemsPercentage;
    LinearLayout mCriticalItemsNumber;
    RelativeLayout mReviewsLessonsFullscreen;
    CheckBox mReviewsLessonsFullscreenCheckBox;
    RelativeLayout mReviewImprovements;
    CheckBox mReviewImprovementsCheckBox;
    RelativeLayout mIgnoreButton;
    CheckBox mIgnoreButtonCheckBox;
    RelativeLayout mSingleButton;
    CheckBox mSingleButtonCheckBox;
    RelativeLayout mPortraitMode;
    CheckBox mPortraitModeCheckBox;
    RelativeLayout mWaniKaniImprove;
    CheckBox mWaniKaniImproveCheckBox;
    RelativeLayout mReviewOrder;
    CheckBox mReviewOrderCheckBox;
    RelativeLayout mLessonOrder;
    CheckBox mLessonOrderCheckBox;
    RelativeLayout mExternalFramePlacer;
    CheckBox mExternalFramePlacerCheckBox;
    TextView mExternalFramePlacerDictionary;
    RelativeLayout mPartOfSpeech;
    CheckBox mPartOfSpeechCheckBox;
    RelativeLayout mAutoPopup;
    RelativeLayout mMistakeDelay;
    CheckBox mMistakeDelayCheckBox;
    CheckBox mAutoPopupCheckBox;
    RelativeLayout mRomaji;
    CheckBox mRomajiCheckBox;
    RelativeLayout mNoSuggestion;
    CheckBox mNoSuggestionCheckBox;
    RelativeLayout mMuteButton;
    CheckBox mMuteButtonCheckBox;
    RelativeLayout mSRSIndication;
    CheckBox mSRSIndicationCheckBox;
    RelativeLayout mHWAccel;
    CheckBox mHWAccelCheckBox;
    TextView mDeveloperOpenSourceLicenses;
    TextView mDeveloperAppVersionSummary;
    
    ActionBar mActionBar;
    Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        //mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);*/
        
    }
 
    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= 16) {
            super.onNavigateUp();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}