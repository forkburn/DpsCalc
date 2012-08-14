package com.warfactory.dpscalc.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
			DualDps increasedDps = new DualDps(dps);
			int deltaDps = calcDeltaDps(dps, increasedDps, selectedSpinnerItem, increasedValue);
			deltaDpsDisplay.setText(String.valueOf(deltaDps));
		} catch (NumberFormatException ex) {
			deltaDpsDisplay.setText("");
		}
	}

}
