package com.warfactory.dpscalc;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
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
import com.warfactory.dpscalc.fragments.ProfileNameInputDialogFragment;
import com.warfactory.dpscalc.model.CharacterProfile;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements ProfileNameInputDialogFragment.RenameProfileDialogListener {
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
        selectDrawerItem(0);

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

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, mCharacterProfileList));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private void initCharacterProfiles() {
        mCharacterProfileList = getCharacterProfilesFromStorage();
        if (mCharacterProfileList.size() == 0) {
            mCharacterProfileList.add(0, new CharacterProfile());
        }
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

    @Override
    public void onDialogPositiveClick(String newProfileName) {
        // when user renames current profile
        // save the new profile name
        getCurrentProfile().setName(newProfileName);
        // change the names in drawer
        refreshDrawerContent();
    }

    private void refreshDrawerContent() {
        // make sure the rignt item in drawer is highlighted
        mDrawerList.setItemChecked(mCurrentProfileIndex, true);
        // refresh profile name on action bar
        this.setTitle(getCurrentProfile().getName());
        // refresh drawer content
        mDrawerList.invalidateViews();
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectDrawerItem(position);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    /**
     * update the drawer and the data pane according to selected item
     */
    private void selectDrawerItem(int position) {
        // remember the index of the currently selected profile
        mCurrentProfileIndex = position;
        refreshProfileFragmentContent();
        refreshDrawerContent();
    }

    private void refreshProfileFragmentContent() {
        // create a fragment showing the selected profile
        DpsCalculationFragment fragment = new DpsCalculationFragment();
        fragment.setCharacterProfile(getCurrentProfile());

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    private CharacterProfile getCurrentProfile() {
        return mCharacterProfileList.get(mCurrentProfileIndex);
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
            case R.id.action_add_profile:
                showAddProfileDialog();
                break;
            case R.id.action_rename_profile:
                showRenameProfileDialog();
                break;
            case R.id.action_delete_profile:
                showDeleteProfileDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddProfileDialog() {
    }

    private void showDeleteProfileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteCurrentProfile();

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setTitle(R.string.delete_profile_confirm);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteCurrentProfile() {
        if (mCharacterProfileList.size() == 1) {
            // when only 1 profile left, just clear its data
            getCurrentProfile().resetValues();
        } else if (mCharacterProfileList.size() > 1) {
            // remove current profile
            mCharacterProfileList.remove(mCurrentProfileIndex);
            if (mCurrentProfileIndex >= mCharacterProfileList.size()) {
                // switch to next profile
                mCurrentProfileIndex = mCharacterProfileList.size() - 1;
            }

        }
        selectDrawerItem(mCurrentProfileIndex);
    }

    private void showRenameProfileDialog() {
        // pop a dialog to rename current character profile
        String currentProfileName = getCurrentProfile().getName();
        ProfileNameInputDialogFragment newFragment = new ProfileNameInputDialogFragment();
        newFragment.setProfileName(currentProfileName);
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
