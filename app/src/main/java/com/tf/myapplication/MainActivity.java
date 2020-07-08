package com.tf.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    FragmentManager fm;
    FragmentTransaction ft;
    NoteFragment noteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteFragment = new NoteFragment();

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.main_frame, noteFragment);
        ft.commit();

    }

    public void replaceFragment(Fragment fragment) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.main_frame, fragment);
        ft.commit();
    }
}
