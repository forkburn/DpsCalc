package com.warfactory.dpscalc.fragments;

import java.text.NumberFormat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.warfactory.dpscalc.R;

abstract public class AbstractSectionFragment extends Fragment implements TextWatcher, OnItemSelectedListener {

	protected EditText primaryAttribEdit;
	protected EditText iasEdit;
	protected EditText critChanceEdit;
	protected EditText critDamEdit;
	protected TextView calcDpsDisplay;
	protected Spinner spinner;
	protected EditText increasedValEdit;
	protected TextView deltaDpsDisplay;
	protected String selectedSpinnerItem;
	protected NumberFormat formatter = NumberFormat.getInstance();

	public AbstractSectionFragment() {
		super();
		formatter.setMaximumFractionDigits(2);
		formatter.setMinimumFractionDigits(2);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// create layout as view
		View view = initView(inflater, container);
		initWeaponDpsBoxes(view, savedInstanceState);
		initCommonBoxes(view, savedInstanceState);
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
	abstract protected void initWeaponDpsBoxes(View view, Bundle savedInstanceState);

	/**
	 * Init the other input box that are common
	 */
	private void initCommonBoxes(View view, Bundle savedInstanceState) {
		primaryAttribEdit = (EditText) view.findViewById(R.id.mainAttbEdit);
		primaryAttribEdit.addTextChangedListener(this);

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

		if (savedInstanceState != null) {
			// restore state
			primaryAttribEdit.setText(savedInstanceState.getString("primaryAttribEdit"));
			iasEdit.setText(savedInstanceState.getString("iasEdit"));
			critChanceEdit.setText(savedInstanceState.getString("critChance"));
			critDamEdit.setText(savedInstanceState.getString("critDam"));
		}
	}

	/**
	 * Init the spinner and add listener
	 */
	abstract protected void initSpinner(View view);

	/**
	 * Save input data before fragment is destroyed
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveWeaponBoxState(outState);
		saveCommonBoxState(outState);
	}

	abstract protected void saveWeaponBoxState(Bundle outState);

	private void saveCommonBoxState(Bundle outState) {
		outState.putString("primaryAttribEdit", primaryAttribEdit.getText().toString());
		outState.putString("iasEdit", iasEdit.getText().toString());
		outState.putString("critChance", critChanceEdit.getText().toString());
		outState.putString("critDam", critDamEdit.getText().toString());
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

}
