package com.xmv.activity;

import org.xmlpull.v1.sax2.Driver;

import com.xmv.fragment.CellsFragment;
import com.xmv.fragment.DriveFragment;
import com.xmv.fragment.FaultFragment;
import com.xmv.fragment.InputOutputFragment;
import com.xmv.fragment.MotorFragment;
import com.xmv.fragment.NavigationDrawerFragment;
import com.xmv.fragment.NavigationDrawerFragment.NavigationDrawerCallbacks;
import com.xmv.fragment.PIDFragment;
import com.xmv.fragment.ParametersFragment;
import com.xmv.fragment.StatusFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks{

	public static final String STATE_ACTIONBAR_TITLE = "action_bar_title";

    public static boolean isFirstSyncDashboardDone = false;
    public static boolean isFirstSyncProfileDone = false;

    public static CharSequence mTitle;

    ActionBar mActionBar;
    Toolbar mToolbar;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //prefMan = new PrefManager(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mActionBar = getSupportActionBar();

        if (savedInstanceState != null) {
            mTitle = savedInstanceState.getString(STATE_ACTIONBAR_TITLE);
            mActionBar.setTitle(mTitle.toString());
        }

        /*if (prefMan.isFirstLaunch()) {
            startActivity(new Intent(this, FirstTimeActivity.class));
            finish();
        }*/

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer_holder, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new com.xmv.fragment.DashboardFragment();
                mTitle = getString(R.string.title_dashboard);
                break;
           /* case 1:
                fragment = new RadicalsFragment();
                mTitle = getString(R.string.title_radicals);
                break;
            case 2:
                fragment = new KanjiFragment();
                mTitle = getString(R.string.title_kanji);
                break; */
            case 1:
                fragment = new StatusFragment();
                mTitle = getString(R.string.title_status);
                break;
            case 2:
                fragment = new DriveFragment();
                mTitle = getString(R.string.title_drive);
                break;
            case 3:
                fragment = new InputOutputFragment();
                mTitle = getString(R.string.title_io);
                break;
            case 4:
                fragment = new CellsFragment();
                mTitle = getString(R.string.title_cells);
                break;
            case 5:
                fragment = new MotorFragment();
                mTitle = getString(R.string.title_motor);
                break;
            case 6:
                fragment = new PIDFragment();
                mTitle = getString(R.string.title_pid);
                break;
            case 7:
                fragment = new FaultFragment();
                mTitle = getString(R.string.title_fault);
                break;
            case 8:
                fragment = new ParametersFragment();
                mTitle = getString(R.string.title_parameters);
                break;
            case 9:
                fragment = new com.xmv.fragment.ConfigurationFragment();
                mTitle = getString(R.string.title_configuration);
                break;
        }

        if (fragment != null)
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
    }

    public void restoreActionBar() {
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        mActionBar.setTitle(mTitle.toString());
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.global, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if (requestCode == AvailableCard.BROWSER_REQUEST) {
            Intent intent = new Intent(BroadcastIntents.SYNC());
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_ACTIONBAR_TITLE, mTitle.toString());
    }
}
