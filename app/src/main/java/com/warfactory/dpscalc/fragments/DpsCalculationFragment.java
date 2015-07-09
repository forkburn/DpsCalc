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
import com.warfactory.dpscalc.model.CharacterProfile;

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
    private CharacterProfile characterProfile = new CharacterProfile();

    public DpsCalculationFragment() {
        super();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
    }

    public CharacterProfile getCharacterProfile() {
        return characterProfile;
    }

    public void setCharacterProfile(CharacterProfile characterProfile) {
        this.characterProfile = characterProfile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create layout as view
        View view = initView(inflater, container);
        initWeaponDpsBoxes(view);
        initCommonBoxes(view);
        initSpinner(view);
        restoreWeaponDpsBoxes();
        restoreCommonBoxes();
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

    private void restoreWeaponDpsBoxes() {
        weapon1DpsEdit.setText(String.valueOf(characterProfile.getWeapon1Dps()));
        weapon2DpsEdit.setText(String.valueOf(characterProfile.getWeapon2Dps()));
    }

    /**
     * Restore previous input data for common input boxes
     */
    private void restoreCommonBoxes() {
            primaryAttribEdit.setText(String.valueOf(characterProfile.getPrimaryAttribute()));
            iasEdit.setText(String.valueOf(characterProfile.getIasPercent()));
            critChanceEdit.setText(String.valueOf(characterProfile.getCritChance()));
            critDamEdit.setText(String.valueOf(characterProfile.getCritDamage()));
    }

    /**
     * Save input data before fragment is destroyed
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
            characterProfile.setWeapon1Dps(Double.valueOf(weapon1DpsEdit.getText().toString()));
            characterProfile.setWeapon2Dps(Double.valueOf(weapon2DpsEdit.getText().toString()));
            characterProfile.setPrimaryAttribute(Integer.valueOf(primaryAttribEdit.getText().toString()));
            characterProfile.setIasPercent(Double.valueOf(iasEdit.getText().toString()));
            characterProfile.setCritChance(Double.valueOf(critChanceEdit.getText().toString()) / 100.0);
            characterProfile.setCritDamage(Double.valueOf(critDamEdit.getText().toString()) / 100.0);
            calcDpsDisplay.setText(formatter.format(characterProfile.getDps()));
        } catch (NumberFormatException ex) {
            // some of the input box is empty
            calcDpsDisplay.setText("");
        }
    }

    private void recalculateDeltaDps() {
        try {
            double increasedValue = Double.valueOf(increasedValEdit.getText().toString());
            CharacterProfile modifiedCharacterProfile = new CharacterProfile(characterProfile);

            if ("Weapon 1 Dam".equals(selectedSpinnerItem)) {
                modifiedCharacterProfile.setWeapon1Dps(characterProfile.getWeapon1Dps() + increasedValue);
            } else if ("Weapon 2 Dam".equals(selectedSpinnerItem)) {
                modifiedCharacterProfile.setWeapon2Dps(characterProfile.getWeapon2Dps() + increasedValue);
            } else if ("Primary Attrib".equals(selectedSpinnerItem)) {
                modifiedCharacterProfile.setPrimaryAttribute(characterProfile.getPrimaryAttribute() + (int) increasedValue);
            } else if ("Inc Attack Speed".equals(selectedSpinnerItem)) {
                modifiedCharacterProfile.setIasPercent(characterProfile.getIasPercent() + increasedValue);
            } else if ("Crit Chance".equals(selectedSpinnerItem)) {
                modifiedCharacterProfile.setCritChance(characterProfile.getCritChance() + increasedValue / 100.0);
            } else if ("Crit Dam".equals(selectedSpinnerItem)) {
                modifiedCharacterProfile.setCritDamage(characterProfile.getCritDamage() + increasedValue / 100.0);
            }
            double deltaDps = modifiedCharacterProfile.getDps() - characterProfile.getDps();
            deltaDpsDisplay.setText(formatter.format(deltaDps));
        } catch (NumberFormatException ex) {
            deltaDpsDisplay.setText("");
        }
    }

}
