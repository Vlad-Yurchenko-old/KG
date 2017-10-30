package com.example.vy.trycanvas.fragments.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vy.trycanvas.Controller;
import com.example.vy.trycanvas.R;

public class FragmentChooseNodeNumDialog extends DialogFragment {

    private String message = "";

    public void setMessage(String message) {
        this.message = message;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View linearlayout = inflater.inflate(R.layout.choose_nodes_num_alertdialog, null);
        builder.setView(linearlayout);

        builder.setPositiveButton("ОК",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = (EditText) linearlayout.findViewById(R.id.chooseNodeNumber);
                        if(editText.getText().toString().compareTo("")!=0){
                            Controller.nodesNum = Integer.valueOf(editText.getText().toString());
                        }else{
                            Controller.nodesNum = 3;
                        }
                        dialog.dismiss();
                    }
                });

        if(message.compareTo("")!=0){
            ((TextView) linearlayout.findViewById(R.id.textChooseNodeNum)).setText(message);
        }

        return builder.create();
    }
}
