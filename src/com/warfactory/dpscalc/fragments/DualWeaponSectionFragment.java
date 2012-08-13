package com.warfactory.dpscalc.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warfactory.dpscalc.R;

/**
 * A dummy fragment representing a section of the app, but that simply displays
 * dummy text.
 */
public class DualWeaponSectionFragment extends Fragment {
	public DualWeaponSectionFragment() {
	}

	public static final String ARG_SECTION_NUMBER = "section_number";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.dual_weapon, container, false);
	}
}
