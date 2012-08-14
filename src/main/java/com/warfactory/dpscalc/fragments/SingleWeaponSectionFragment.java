package com.warfactory.dpscalc.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.warfactory.dpscalc.R;
import com.warfactory.dpscalc.model.Dps;

/**
 * A dummy fragment representing a section of the app, but that simply displays
 * dummy text.
 */
public class SingleWeaponSectionFragment extends Fragment implements TextWatcher, OnItemSelectedListener {
	private Dps dps = new Dps();
	private EditText weaponDpsEdit;
	private EditText mainAttribEdit;
	private EditText iasEdit;
	private EditText critChanceEdit;
	private EditText critDamEdit;
	private TextView calcDpsDisplay;
	private Spinner spinner;
	private EditText increasedValEdit;
	private TextView deltaDpsDisplay;
	private String selectedSpinnerItem;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// create layout as view
		View view = inflater.inflate(R.layout.single_weapon, container, false);

		// get ref to TextEdits, and set listener
		weaponDpsEdit = (EditText) view.findViewById(R.id.wpDpsEdit);
		weaponDpsEdit.addTextChangedListener(this);

		mainAttribEdit = (EditText) view.findViewById(R.id.mainAttbEdit);
		mainAttribEdit.addTextChangedListener(this);

		iasEdit = (EditText) view.findViewById(R.id.iasEdit);
		iasEdit.addTextChangedListener(this);

		critChanceEdit = (EditText) view.findViewById(R.id.critChanceEdit);
		critChanceEdit.addTextChangedListener(this);

		critDamEdit = (EditText) view.findViewById(R.id.critDamEdit);
		critDamEdit.addTextChangedListener(this);

		calcDpsDisplay = (TextView) view.findViewById(R.id.calcDpsDisplay);

		increasedValEdit = (EditText) view.findViewById(R.id.increaseValEdit);
		increasedValEdit.addTextChangedListener(this);

		deltaDpsDisplay = (TextView) view.findViewById(R.id.deltaDpsDisplay);

		// populate spinner
		spinner = (Spinner) view.findViewById(R.id.increaseVarSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
				R.array.inputvars_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		// set listener for spinner
		spinner.setOnItemSelectedListener(this);

		return view;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// do nothing
	}

	/**
	 * Handle text changed event for EditText
	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		recalculateDps();
		recalculateDeltaDps();
	}

	/**
	 * Read input and recalculate the total dps
	 */
	private void recalculateDps() {
		try {
			dps.setWeaponDps(Integer.valueOf(weaponDpsEdit.getText().toString()));
			dps.setMainAttribute(Integer.valueOf(mainAttribEdit.getText().toString()));
			dps.setIasPercent(Double.valueOf(iasEdit.getText().toString()) / 100);
			dps.setCritChance(Double.valueOf(critChanceEdit.getText().toString()) / 100);
			dps.setCritDamage(Integer.valueOf(critDamEdit.getText().toString()) / 100);

			calcDpsDisplay.setText(String.valueOf(dps.getDps()));
		} catch (NumberFormatException ex) {
			// some of the input box is empty
			calcDpsDisplay.setText("");
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// do nothing
	}

	/**
	 * Handle spinner item selected event
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		selectedSpinnerItem = (String) parent.getItemAtPosition(position);
		recalculateDeltaDps();
	}

	private void recalculateDeltaDps() {
		try {
			double increasedValue = Double.valueOf(increasedValEdit.getText().toString());
			Dps increasedDps = new Dps(dps);
			// check which spinner item is selected
			if ("Main Attrib.".equals(selectedSpinnerItem)) {
				increasedDps.setMainAttribute(dps.getMainAttribute() + (int) increasedValue);
			} else if ("I.A.S".equals(selectedSpinnerItem)) {
				increasedDps.setIasPercent(dps.getIasPercent() + increasedValue / 100);
			} else if ("Crit.Chance".equals(selectedSpinnerItem)) {
				increasedDps.setCritChance(dps.getCritChance() + increasedValue / 100);
			} else if ("Crit.Dam".equals(selectedSpinnerItem)) {
				increasedDps.setCritDamage(dps.getCritDamage() + increasedValue / 100);
			}
			int deltaDps = increasedDps.getDps() - dps.getDps();
			deltaDpsDisplay.setText(String.valueOf(deltaDps));
		} catch (NumberFormatException ex) {
			deltaDpsDisplay.setText("");
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		deltaDpsDisplay.setText("");
	}

}
