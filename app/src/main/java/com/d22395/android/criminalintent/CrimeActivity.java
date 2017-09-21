package com.d22395.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment);
//
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
//
//        if (fragment == null) {
//            fragment = new CrimeFragment();
//            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
//        }
//
//    }
    private static final String EXTRA_CRIME_ID = "" +
        "com.d22395.android.criminalintent.crime_id";

    public static Intent newIntent(Context packageComtext, UUID crimeId) {
        Intent intent = new Intent(packageComtext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId); // 保存ID到intent里
        return intent;
    }
    @Override
    protected Fragment createFragment() {
//        return new CrimeFragment();
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }
}