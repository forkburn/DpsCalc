package com.warfactory.dpscalc;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.warfactory.dpscalc.fragments.DpsCalculationFragment;
import com.warfactory.dpscalc.model.CharacterProfile;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private List<String> mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private List<CharacterProfile> mCharacterProfileList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCharacterProfileList = getCharacterProfilesFromStorage();
        if (mCharacterProfileList.size()==0) {
            mCharacterProfileList.add(0, new CharacterProfile());
        }
        mDrawerItems = genCharacterNameList(mCharacterProfileList);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mDrawerItems));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // click the first item in drawer
        selectItem(0);

    }

    private List<String> genCharacterNameList(List<CharacterProfile> characterProfileList) {
        List<String> result = new ArrayList<>();
        for (CharacterProfile profile:characterProfileList) {
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
        result.add(0,profile1);
        result.add(1,profile2);

        return result;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
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
        setTitle(mDrawerItems.get(position));
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        getActionBar().setTitle(title);
    }
}
