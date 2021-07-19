package com.adg.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FirstFragment extends Fragment {
    private FirstFragmentListener listener;
    private EditText editText;
    private Button button;
    private String message = "";

    public interface FirstFragmentListener {
        void onInputFirstSent(CharSequence input);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_first, container, false);

        editText = v.findViewById(R.id.firstFrag_editText);
        button = v.findViewById(R.id.fragFirst_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence input = editText.getText();
                message = input.toString();
                listener.onInputFirstSent(input);
                Log.d("firstFragmentGotClicked", "Ok");
            }
        });

        Log.d("FirstFragmentOnCreateView", message);
        return v;
    }

    // EditText needs to be reset on onResume method
    @Override
    public void onResume() {
        super.onResume();
        editText.setText(message);
        Log.d("FirstFragmentOnResume", message);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            message = bundle.getCharSequence("message").toString();
        } else {
            message = "Insert the message here";
        }
        Log.d("FirstFragmentOnCreate", message);
    }

    // Called when the fragment is attached to the activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FirstFragmentListener) {
            listener = (FirstFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FirstFragmentListener ");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}