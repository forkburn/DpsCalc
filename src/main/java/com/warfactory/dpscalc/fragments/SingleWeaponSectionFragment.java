package com.warfactory.dpscalc.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.warfactory.dpscalc.R;
import com.warfactory.dpscalc.model.Dps;

public class SingleWeaponSectionFragment extends AbstractSectionFragment {
	private Dps dps = new Dps();
	private EditText weaponDpsEdit;

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.single_weapon, container, false);
	}

	@Override
	protected void initWeaponDpsBoxes(View view) {
		weaponDpsEdit = (EditText) view.findViewById(R.id.wpDpsEdit);
		weaponDpsEdit.addTextChangedListener(this);
	}

	@Override
	protected void recalculateDps() {
		try {
			dps.setWeaponDps(Integer.valueOf(weaponDpsEdit.getText().toString()));
			dps.setMainAttribute(Integer.valueOf(mainAttribEdit.getText().toString()));
			dps.setIasPercent(Double.valueOf(iasEdit.getText().toString()));
			dps.setCritChance(Double.valueOf(critChanceEdit.getText().toString()) / 100);
			dps.setCritDamage(Integer.valueOf(critDamEdit.getText().toString()) / 100);
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
			Dps increasedDps = new Dps(dps);
			int deltaDps = calcDeltaDps(dps, increasedDps, selectedSpinnerItem, increasedValue);
			deltaDpsDisplay.setText(String.valueOf(deltaDps));
		} catch (NumberFormatException ex) {
			deltaDpsDisplay.setText("");
		}
	}

}
