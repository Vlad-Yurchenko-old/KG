package com.example.vy.trycanvas.fragments.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import com.example.vy.trycanvas.Controller;

public class FragmentChooseFractalDialog extends DialogFragment{

    private Controller controller = null;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        final String[] array = new String[] {"Папоротник","Снежинка Коха", "Мандельбор", "Ковер Серпинского", "Плазма", "Джулия", "Биоморф"};

        builder.setItems(array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        controller.fractalFernOperation();
                        break;
                    case 1:
                        controller.fractalSnowflakeOperation();
                        break;
                    case 2:
                        controller.fractalMandelborOperation();
                        break;
                    case 3:
                        controller.fractalSierpinskiOperation();
                        break;
                    case 4:
                        controller.fractalPlasmaOperation();
                        break;
                    case 5:
                        controller.fractalJuliaOperation();
                        break;
                    case 6:
                        controller.fractalBiomorphOperation();
                        break;
                }
                dialog.dismiss();
            }
        });

        return builder.create();
    }

}
