package com.d22395.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

/**
 * Created by D22395 on 2017/7/27.
 */

public class CrimePagerActivity extends AppCompatActivity
    implements CrimeFragment.Callbacks{

    private ViewPager mViewPager;
    private Button mJumpFirst;
    private Button mJumpLast;
    private List<Crime> mCrimes;
    private static final String EXTRA_CRIME_ID =
            "com.d22395.android.criminalIntent.crime_id";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId); // 保存ID到intent里
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mCrimes = CrimeLab.get(this).getCrimes();


        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        /*
            FragmengtStatePagerAdapter会销毁不需要的fragment，fragment的Bundle
         则保存在onSaveInstanceState方法中，用户切换回来时，保存的实例状态可用来生成新的fragment
            而FragmentPagerAdapter只是销毁了fragment的视图，而fragment实例
         还保留在FragmentManager中
         */
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                // 返回CrimeFragment来显示指定的Crime
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        // 设置初始分页显示项 显示当前项为crime数组中指定位置的列表项
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {

    }
}
