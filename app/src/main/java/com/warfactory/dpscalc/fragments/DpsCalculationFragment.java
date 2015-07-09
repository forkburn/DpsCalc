package com.warfactory.dpscalc.fragments;

import java.text.NumberFormat;

import android.os.Bundle;
import android.app.Fragment;
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

public class DpsCalculationFragment extends Fragment implements TextWatcher, OnItemSelectedListener {

    public static final String ARG_DPS = "arg_dps";
    private EditText primaryAttribEdit;
    private EditText iasEdit;
    private EditText critChanceEdit;
    private EditText critDamEdit;
    private TextView calcDpsDisplay;
    private Spinner deltaAttribSpinner;
    private EditText increasedValEdit;
    private TextView deltaDpsDisplay;
    private String selectedSpinnerItem;
    private NumberFormat formatter = NumberFormat.getInstance();
    private EditText weapon1DpsEdit;
    private EditText weapon2DpsEdit;
    private Dps dps = new Dps();

    public DpsCalculationFragment() {
        super();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
    }

    public Dps getDps() {
        return dps;
    }

    public void setDps(Dps dps) {
        this.dps = dps;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // create layout as view
        View view = initView(inflater, container);
        initWeaponDpsBoxes(view);
        initCommonBoxes(view);
        initSpinner(view);
        restoreWeaponDpsBoxes(savedInstanceState);
        restoreCommonBoxes(savedInstanceState);
        return view;
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.input_form, container, false);
    }

    private void initWeaponDpsBoxes(View view) {
        weapon1DpsEdit = (EditText) view.findViewById(R.id.wp1DpsEdit);
        weapon1DpsEdit.addTextChangedListener(this);
        weapon2DpsEdit = (EditText) view.findViewById(R.id.wp2DpsEdit);
        weapon2DpsEdit.addTextChangedListener(this);

    }

    /**
     * Init the other input box that are common
     */
    private void initCommonBoxes(View view) {
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

    }

    private void initSpinner(View view) {
        // populate deltaAttribSpinner
        deltaAttribSpinner = (Spinner) view.findViewById(R.id.increaseVarSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.dual_weapon_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deltaAttribSpinner.setAdapter(adapter);
        // set listener for deltaAttribSpinner
        deltaAttribSpinner.setOnItemSelectedListener(this);
    }

    private void restoreWeaponDpsBoxes(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // restore saved state
            weapon1DpsEdit.setText(savedInstanceState.getString("weapon1DpsEdit", ""));
            weapon2DpsEdit.setText(savedInstanceState.getString("weapon2DpsEdit", ""));
        }
    }

    /**
     * Restore previous input data for common input boxes
     */
    private void restoreCommonBoxes(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            primaryAttribEdit.setText(savedInstanceState.getString("primaryAttribEdit", ""));
            iasEdit.setText(savedInstanceState.getString("iasEdit", ""));
            critChanceEdit.setText(savedInstanceState.getString("critChance", ""));
            critDamEdit.setText(savedInstanceState.getString("critDam", ""));
        }
    }

    /**
     * Save input data before fragment is destroyed
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveWeaponBoxState(outState);
        saveCommonBoxState(outState);
    }

    private void saveWeaponBoxState(Bundle outState) {
        outState.putString("weapon1DpsEdit", weapon1DpsEdit.getText().toString());
        outState.putString("weapon2DpsEdit", weapon2DpsEdit.getText().toString());
    }

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
     * Handle deltaAttribSpinner item selected event
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

    private void recalculateDps() {
        try {
            dps.setWeapon1Dps(Double.valueOf(weapon1DpsEdit.getText().toString()));
            dps.setWeapon2Dps(Double.valueOf(weapon2DpsEdit.getText().toString()));
            dps.setPrimaryAttribute(Integer.valueOf(primaryAttribEdit.getText().toString()));
            dps.setIasPercent(Double.valueOf(iasEdit.getText().toString()));
            dps.setCritChance(Double.valueOf(critChanceEdit.getText().toString()) / 100.0);
            dps.setCritDamage(Double.valueOf(critDamEdit.getText().toString()) / 100.0);
            calcDpsDisplay.setText(formatter.format(dps.getDps()));
        } catch (NumberFormatException ex) {
            // some of the input box is empty
            calcDpsDisplay.setText("");
        }
    }

    private void recalculateDeltaDps() {
        try {
            double increasedValue = Double.valueOf(increasedValEdit.getText().toString());
            Dps increasedDps = new Dps(dps);

            if ("Weapon 1 Dam".equals(selectedSpinnerItem)) {
                increasedDps.setWeapon1Dps(dps.getWeapon1Dps() + increasedValue);
            } else if ("Weapon 2 Dam".equals(selectedSpinnerItem)) {
                increasedDps.setWeapon2Dps(dps.getWeapon2Dps() + increasedValue);
            } else if ("Primary Attrib".equals(selectedSpinnerItem)) {
                increasedDps.setPrimaryAttribute(dps.getPrimaryAttribute() + (int) increasedValue);
            } else if ("Inc Attack Speed".equals(selectedSpinnerItem)) {
                increasedDps.setIasPercent(dps.getIasPercent() + increasedValue);
            } else if ("Crit Chance".equals(selectedSpinnerItem)) {
                increasedDps.setCritChance(dps.getCritChance() + increasedValue / 100.0);
            } else if ("Crit Dam".equals(selectedSpinnerItem)) {
                increasedDps.setCritDamage(dps.getCritDamage() + increasedValue / 100.0);
            }
            double deltaDps = increasedDps.getDps() - dps.getDps();
            deltaDpsDisplay.setText(formatter.format(deltaDps));
        } catch (NumberFormatException ex) {
            deltaDpsDisplay.setText("");
        }
    }

}
