package de.hdm_tunes2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import de.hdm_tunes2.PlaylistFragment;
import de.hdm_tunes2.WikipediaFragment;

/**
 * Created by matthias on 11.05.15.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * checks for the selected Tab Item and return the corresponding fragment
     *
     * @param position index of the selected tab item
     * @return fragment of the selected tab
     */
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                return new PlayerFragment();
            case 1:
                return new PlaylistFragment();
            case 2:
                return new WikipediaFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
