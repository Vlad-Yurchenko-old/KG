package com.example.vy.trycanvas.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.vy.trycanvas.Field;
import com.example.vy.trycanvas.MainActivity;

public class FragmentField extends Fragment {

    private static Field field;

    public static Field getField() {
        return field;
    }

    public FragmentField(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
            field = new Field(MainActivity.context,displaymetrics.widthPixels,displaymetrics.heightPixels);

        return field;
    }
}
