package com.warfactory.dpscalc.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.warfactory.dpscalc.R;

public class ProfileNameInputDialogFragment extends DialogFragment {

    private EditText mProfileNameEdit;
    private RenameProfileDialogListener mListener;

    public enum Mode {RENAME, ADD};

    private Mode mMode;

    private String mProfileName;

    public Mode getMode() {
        return mMode;
    }

    public void setMode(Mode mMode) {
        this.mMode = mMode;
    }

    public String getProfileName() {
        return mProfileName;
    }

    public void setProfileName(String mProfileName) {
        this.mProfileName = mProfileName;
    }


    /* The activity that creates an instance of this dialog fragment must
    * implement this interface in order to receive event callbacks.*/
    public interface RenameProfileDialogListener {
        public void onRenameDialogPositiveClick(String newProfileName);
        public void onAddDialogPositiveClick(String newProfileName);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.rename_profile_dialog, null);
        builder.setView(view);
        if (mMode == Mode.RENAME)
            builder.setTitle(R.string.rename_profile);
        else if (mMode == Mode.ADD)
            builder.setTitle(R.string.add_profile);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(mMode==Mode.RENAME)
                    mListener.onRenameDialogPositiveClick(mProfileNameEdit.getText().toString());
                else if (mMode==Mode.ADD)
                    mListener.onAddDialogPositiveClick(mProfileNameEdit.getText().toString());
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });

        final Dialog dialog = builder.create();

        //setup the input text box
        mProfileNameEdit = (EditText) view.findViewById(R.id.new_profile_name_input);
        mProfileNameEdit.setText(mProfileName);
        mProfileNameEdit.selectAll();
        // pop up soft keyboard on focus
        mProfileNameEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });


        return dialog;
    }


    // Override the Fragment.onAttach() method to instantiate the listener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (RenameProfileDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
