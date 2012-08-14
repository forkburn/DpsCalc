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

abstract public class AbstractSectionFragment extends Fragment implements TextWatcher, OnItemSelectedListener {

	protected EditText mainAttribEdit;
	protected EditText iasEdit;
	protected EditText critChanceEdit;
	protected EditText critDamEdit;
	protected TextView calcDpsDisplay;
	protected Spinner spinner;
	protected EditText increasedValEdit;
	protected TextView deltaDpsDisplay;
	protected String selectedSpinnerItem;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// create layout as view
		View view = initView(inflater, container);
		initWeaponDpsBoxes(view);
		initOtherBoxes(view);
		initSpinner(view);
		return view;
	}

	/**
	 * Inflate the view using different xml layouts
	 */
	abstract protected View initView(LayoutInflater inflater, ViewGroup container);

	/**
	 * Init the weapon DPS input boxes
	 */
	abstract protected void initWeaponDpsBoxes(View view);

	/**
	 * Init the other input box that are common
	 */
	private void initOtherBoxes(View view) {
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
	}

	/**
	 * Init the spinner used to choose delta variable
	 */
	private void initSpinner(View view) {
		// populate spinner
		spinner = (Spinner) view.findViewById(R.id.increaseVarSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
				R.array.inputvars_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		// set listener for spinner
		spinner.setOnItemSelectedListener(this);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// do nothing
	}

	/**
	 * Handle text changed event for EditTexts
	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		recalculateDps();
		recalculateDeltaDps();
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

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		deltaDpsDisplay.setText("");
	}

	/**
	 * Read input and recalculate the total dps
	 */
	abstract protected void recalculateDps();

	/**
	 * Read the delta input and calculate the delta dps
	 */
	abstract protected void recalculateDeltaDps();

	protected int calcDeltaDps(Dps originalDps, Dps increasedDps, String increasedVar, double increasedValue) {
		if ("Main Attrib.".equals(increasedVar)) {
			increasedDps.setMainAttribute(originalDps.getMainAttribute() + (int) increasedValue);
		} else if ("I.A.S".equals(increasedVar)) {
			increasedDps.setIasPercent(originalDps.getIasPercent() + increasedValue);
		} else if ("Crit.Chance".equals(increasedVar)) {
			increasedDps.setCritChance(originalDps.getCritChance() + increasedValue / 100.0);
		} else if ("Crit.Dam".equals(increasedVar)) {
			increasedDps.setCritDamage(originalDps.getCritDamage() + increasedValue / 100.0);
		}
		return increasedDps.getDps() - originalDps.getDps();
	}

}
