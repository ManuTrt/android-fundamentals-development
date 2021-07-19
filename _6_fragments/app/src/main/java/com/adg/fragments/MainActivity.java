package com.adg.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements FirstFragment.FirstFragmentListener, SecondFragment.SecondFragmentListener {
    private Button firstFragmentBtn, secondFragmentBtn;
    private Fragment firstFragment, secondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();

        firstFragmentBtn = findViewById(R.id.actMain_btnFragment1);
        secondFragmentBtn = findViewById(R.id.actMain_btnFragment2);

        firstFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(firstFragment);
            }

        });

        secondFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(secondFragment);
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.actMain_flFragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onInputFirstSent(CharSequence input) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence("message", input);
        secondFragment.setArguments(bundle);
    }

    @Override
    public void onInputSecondSent(CharSequence input) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence("message", input);
        firstFragment.setArguments(bundle);
    }
}