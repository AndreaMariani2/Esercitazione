package com.example.esercitazione;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    Calendar data;
    public static DatePickerFragment newInstance() {
        DatePickerFragment frag = new DatePickerFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final DatePicker datepicker =new DatePicker(getActivity());
        data=Calendar.getInstance();

        return new AlertDialog.Builder(getActivity())
                .setView(datepicker)
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                data.set(Calendar.YEAR,datepicker.getYear());
                                data.set(Calendar.MONTH,datepicker.getMonth());
                                data.set(Calendar.DAY_OF_MONTH,datepicker.getDayOfMonth());
                                ((Registrati)getActivity()).doPositiveClick(data);
                            }
                        }
                )
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((Registrati)getActivity()).doNegativeClick();
                            }
                        }
                )
                .create();
    }
}
