package com.warfactory.dpscalc;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.warfactory.dpscalc.fragments.DualWeaponSectionFragment;
import com.warfactory.dpscalc.fragments.SingleWeaponSectionFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one
 * of the primary sections of the app.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

	private Context context;

	public SectionsPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = null;
		switch (i) {
		case 0:
			// for tab 0, get single weapon view
			fragment = new SingleWeaponSectionFragment();
			break;
		case 1:
			// for tab 1, get dual wield view
			fragment = new DualWeaponSectionFragment();
			break;
		default:
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// we have 2 tabs for now
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return context.getString(R.string.title_single_weapon);
		case 1:
			return context.getString(R.string.title_dual_weapon);
		}
		return null;
	}
}
