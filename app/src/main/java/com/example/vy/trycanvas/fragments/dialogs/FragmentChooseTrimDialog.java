package com.example.vy.trycanvas.fragments.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.vy.trycanvas.Controller;

public class FragmentChooseTrimDialog extends DialogFragment{

    private Controller controller = null;
    private FragmentManager fragmentManager = null;

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        final String[] array = new String[] {"Коэна-Сазерленда","Сазерленда-Хогдмана", "Вейлера-Азертона",
                                        "Коэна-Сазерленда (голова)","Сазерленда-Хогдмана (голова)", "Вейлера-Азертона (голова)"};

        builder.setItems(array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        controller.setTrimKoenOperation();
                        break;
                    case 1:
                        Toast.makeText(Controller.context, "Число вершин отсекаемого многоугольника", Toast.LENGTH_LONG).show();
                        new FragmentChooseNodeNumDialog().show(fragmentManager,"custom");
                        controller.setTrimSHOperation();
                        break;
                    case 2:
                        FragmentChooseNodeNumDialog fragmentChooseNodeNumDialog = new FragmentChooseNodeNumDialog();
                        fragmentChooseNodeNumDialog.setMessage("Число вершин отсекаемого многоугольника");
                        fragmentChooseNodeNumDialog.show(fragmentManager,"custom");
                        FragmentChooseNodeNum2Dialog fragmentChooseNodeNum2Dialog = new FragmentChooseNodeNum2Dialog();
                        fragmentChooseNodeNum2Dialog.setMessage("Число вершин отсекаемой области");
                        fragmentChooseNodeNum2Dialog.show(fragmentManager,"custom");
                        controller.setTrimVAOperation();
                        break;
                    case 3:
                        controller.setTrimKoenForHeadOperation();
                        break;
                    case 4:
                        controller.setTrimSHForHeadOperation();
                        break;
                    case 5:
                        FragmentChooseNodeNum2Dialog fragmentChooseNodeNum2Dialog2 = new FragmentChooseNodeNum2Dialog();
                        fragmentChooseNodeNum2Dialog2.setMessage("Число вершин отсекаемой области");
                        fragmentChooseNodeNum2Dialog2.show(fragmentManager,"custom");
                        controller.setTrimVAForHeadOperation();
                        break;
                }
                dialog.dismiss();
            }
        });

        return builder.create();
    }

}
