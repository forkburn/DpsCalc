package com.warfactory.dpscalc.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.warfactory.dpscalc.R;
import com.warfactory.dpscalc.model.DualDps;

public class DualWeaponSectionFragment extends AbstractSectionFragment {
	private DualDps dps = new DualDps();
	private EditText weapon1DpsEdit;
	private EditText weapon2DpsEdit;

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.dual_weapon, container, false);
	}

	@Override
	protected void initSpinner(View view) {
		// populate spinner
		spinner = (Spinner) view.findViewById(R.id.increaseVarSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
				R.array.dual_weapon_spinner_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		// set listener for spinner
		spinner.setOnItemSelectedListener(this);
	}

	@Override
	protected void initWeaponDpsBoxes(View view) {
		weapon1DpsEdit = (EditText) view.findViewById(R.id.wp1DpsEdit);
		weapon1DpsEdit.addTextChangedListener(this);
		weapon2DpsEdit = (EditText) view.findViewById(R.id.wp2DpsEdit);
		weapon2DpsEdit.addTextChangedListener(this);
	}

	@Override
	protected void recalculateDps() {
		try {
			dps.setWeaponDps(Integer.valueOf(weapon1DpsEdit.getText().toString()));
			dps.setWeapon2Dps(Integer.valueOf(weapon2DpsEdit.getText().toString()));
			dps.setPrimaryAttribute(Integer.valueOf(mainAttribEdit.getText().toString()));
			dps.setIasPercent(Double.valueOf(iasEdit.getText().toString()));
			dps.setCritChance(Double.valueOf(critChanceEdit.getText().toString()) / 100.0);
			dps.setCritDamage(Integer.valueOf(critDamEdit.getText().toString()) / 100.0);
			calcDpsDisplay.setText(String.valueOf(dps.getDps()));
		} catch (NumberFormatException ex) {
			// some of the input box is empty
			calcDpsDisplay.setText("");
		}
	}

	@Override
	protected void recalculateDeltaDps() {
		try {
			double increasedValue = Double.valueOf(increasedValEdit.getText().toString());
			DualDps increasedDps = new DualDps(dps);

			if ("Wp.1 Dam.".equals(selectedSpinnerItem)) {
				increasedDps.setWeaponDps(dps.getWeaponDps() + (int) increasedValue);
			} else if ("Wp.2 Dam.".equals(selectedSpinnerItem)) {
				increasedDps.setWeapon2Dps(dps.getWeapon2Dps() + (int) increasedValue);
			} else if ("Pri. Attrib.".equals(selectedSpinnerItem)) {
				increasedDps.setPrimaryAttribute(dps.getPrimaryAttribute() + (int) increasedValue);
			} else if ("I.A.S".equals(selectedSpinnerItem)) {
				increasedDps.setIasPercent(dps.getIasPercent() + increasedValue);
			} else if ("Crit.Chance".equals(selectedSpinnerItem)) {
				increasedDps.setCritChance(dps.getCritChance() + increasedValue / 100.0);
			} else if ("Crit.Dam.".equals(selectedSpinnerItem)) {
				increasedDps.setCritDamage(dps.getCritDamage() + increasedValue / 100.0);
			}
			int deltaDps = increasedDps.getDps() - dps.getDps();
			deltaDpsDisplay.setText(String.valueOf(deltaDps));
		} catch (NumberFormatException ex) {
			deltaDpsDisplay.setText("");
		}
	}

}
