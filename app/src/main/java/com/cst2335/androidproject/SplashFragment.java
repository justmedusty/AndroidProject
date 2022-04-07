package com.cst2335.androidproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A very simple fragment containing the layout to be used as a placeholder in the fragment space
 * used to display recipe details. If no details are being displayed then this will be used as
 * a splash screen for the application
 */
public class SplashFragment extends Fragment {


    /**
     * required empty public constructor
     */
    public SplashFragment() {
        // Required empty public constructor
    }


    /**
     * inflates the layout to be used by this fragment.
     * @param inflater the inflater used to inflate the fragment layout
     * @param container the container in which to inflate this fragment layout
     * @param savedInstanceState any Bundle information if used by this fragment.
     * @return the View containing the inflated layout for this fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }
}