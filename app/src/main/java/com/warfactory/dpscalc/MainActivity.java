package com.warfactory.dpscalc;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.warfactory.dpscalc.fragments.DpsCalculationFragment;
import com.warfactory.dpscalc.fragments.RenameProfileDialogFragment;
import com.warfactory.dpscalc.model.CharacterProfile;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements RenameProfileDialogFragment.RenameProfileDialogListener {
    private List<String> mDrawerItemsList;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private int mCurrentProfileIndex;
    private List<CharacterProfile> mCharacterProfileList;
    private DrawerArrowDrawable mDrawerArrow;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initDrawer();

        // click the first item in drawer
        selectItem(0);

    }

    private void initDrawer() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        mDrawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mDrawerArrow, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        initCharacterProfiles();
        mDrawerItemsList = genCharacterNameList(mCharacterProfileList);


        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mDrawerItemsList));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private void initCharacterProfiles() {
        mCharacterProfileList = getCharacterProfilesFromStorage();
        if (mCharacterProfileList.size() == 0) {
            mCharacterProfileList.add(0, new CharacterProfile());
        }
    }

    private List<String> genCharacterNameList(List<CharacterProfile> characterProfileList) {
        List<String> result = new ArrayList<>();
        for (CharacterProfile profile : characterProfileList) {
            result.add(profile.getName());
        }
        return result;
    }

    private List<CharacterProfile> getCharacterProfilesFromStorage() {
        // TODO: should try to load character data from db
        List<CharacterProfile> result = new ArrayList<>();
        CharacterProfile profile1 = new CharacterProfile();
        profile1.setName("a");
        CharacterProfile profile2 = new CharacterProfile();
        profile2.setName("b");
        result.add(0, profile1);
        result.add(1, profile2);

        return result;
    }

    // when user renames current profile
    @Override
    public void onDialogPositiveClick(String newProfileName) {
        // save the new profile name
        mCharacterProfileList.get(mCurrentProfileIndex).setName(newProfileName);
        // display new name on action bar
        this.setTitle(newProfileName);
        // change the names in drawer
        this.mDrawerItemsList.set(this.mCurrentProfileIndex, newProfileName);
        mDrawerList.invalidateViews();
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            // remember the index of the currently selected profile
            mCurrentProfileIndex = position;
            selectItem(position);
        }
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {

        DpsCalculationFragment fragment = null;
        CharacterProfile selectedCharacterProfile = mCharacterProfileList.get(position);
        // Create a new fragment and specify the planet to show based on position
        fragment = new DpsCalculationFragment();

        fragment.setCharacterProfile(selectedCharacterProfile);

        // Insert the fragment by replacing any existing fragment
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mDrawerItemsList.get(position));
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        getActionBar().setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                toggleDrawer();

                break;
            case R.id.action_rename_profile:
                popRenameProfileDialog();

        }
        return super.onOptionsItemSelected(item);
    }

    private void popRenameProfileDialog() {
        // pop a dialog to rename current character profile

        //TODO the dialog should contain the old profile name, selected
        String currentProfileName = mDrawerItemsList.get(mCurrentProfileIndex);
        RenameProfileDialogFragment newFragment = new RenameProfileDialogFragment();
        newFragment.setmProfileName(currentProfileName);
        newFragment.show(getFragmentManager(), "renameProfile");

    }

    private void toggleDrawer() {
        // toggle the drawer when button on action bar pressed
        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            mDrawerLayout.openDrawer(mDrawerList);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
