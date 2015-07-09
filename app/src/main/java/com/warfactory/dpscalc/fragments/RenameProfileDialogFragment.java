package com.warfactory.dpscalc.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.warfactory.dpscalc.R;

public class RenameProfileDialogFragment extends DialogFragment {

    private EditText mProfileNameEdit;
    private RenameProfileDialogListener mListener;

    private String mProfileName;

    public String getmProfileName() {
        return mProfileName;
    }

    public void setmProfileName(String mProfileName) {
        this.mProfileName = mProfileName;
    }


    /* The activity that creates an instance of this dialog fragment must
    * implement this interface in order to receive event callbacks.*/
    public interface RenameProfileDialogListener {
        public void onDialogPositiveClick(String newProfileName);

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.rename_profile_dialog, null);
        builder.setView(view);
        builder.setTitle(R.string.rename_profile);


        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               mListener.onDialogPositiveClick(mProfileNameEdit.getText().toString());
            }
        });

        Dialog dialog = builder.create();

        mProfileNameEdit = (EditText) view.findViewById(R.id.new_profile_name_input);
        // display the current profile name in input box
        mProfileNameEdit.setText(mProfileName);
        mProfileNameEdit.selectAll();
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
