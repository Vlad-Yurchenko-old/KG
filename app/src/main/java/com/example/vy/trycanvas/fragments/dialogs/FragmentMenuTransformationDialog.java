package com.example.vy.trycanvas.fragments.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.vy.trycanvas.Controller;

public class FragmentMenuTransformationDialog extends DialogFragment{

    private Controller controller = null;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        final String[] array = new String[] {"Перемещение","Масштабирование", "Поворот", "Отражение"};

        builder.setItems(array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                controller.setTransferOperation();
                                break;
                            case 1:
                                controller.setScalingOperation();
                                break;
                            case 2:
                                controller.setTurningOperation();
                                break;
                            case 3:
                                controller.setReflectOperation();
                                break;
                        }
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }

}
