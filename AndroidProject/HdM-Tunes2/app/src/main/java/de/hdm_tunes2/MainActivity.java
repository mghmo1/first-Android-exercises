package de.hdm_tunes2;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

/**
 *
 */
public class MainActivity extends ActionBarActivity implements ActionBar.TabListener  {

    private String[] tabs = { "Library", "Playlist", "Wikipedia" };

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupTabs();
    }

    /**
     * The three tabs for the application are created. If a tab is clicked in the UI, the tab listener loads
     * the corresponding fragment.
     */
    private void setupTabs() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        ActionBar.Tab tab1 = actionBar
                .newTab()
                .setText("Library")
                .setTabListener(new SupportFragmentTabListener<>(R.id.pager, this, "library",
                        LibraryFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        ActionBar.Tab tab2 = actionBar
                .newTab()
                .setText("Playlist")
                .setTabListener(new SupportFragmentTabListener<>(R.id.pager, this, "playlist",
                        PlaylistFragment.class));

        actionBar.addTab(tab2);


        ActionBar.Tab tab3 = actionBar
                .newTab()
                .setText("Wikipedia")
                .setTabListener(new SupportFragmentTabListener<>(R.id.pager, this, "wikipedia",
                WikipediaFragment.class));
        actionBar.addTab(tab3);
    }

    /**
     *  Handle action bar item clicks here. The action bar will
     *  automatically handle clicks on the Home/Up button, so long
     *  as you specify a parent activity in AndroidManifest.xml.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }
}
