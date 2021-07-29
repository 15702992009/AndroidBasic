package com.example.criminalintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.UUID;

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks {
    private static final String TAG = "CrimeListActivity";


    /**
     * 通过别名系统实现 不同设备的不同布局
     * 自定义方法
     *
     * @return
     */
    @Override
    protected int getLayoutResId() {
        //todo  不同设备 这儿个值的答案是有不同吗? 2131427359  2131427359 2131427359 2131427359
        return R.layout.activity_masterdetail;
    }

    /**
     * 自定义方法
     *
     * @return
     */
    @Override
    protected Fragment createLeftFragment() {
        return new CrimeListFragment();
//        return new CrimeFragment();

    }

    @Override
    protected Fragment createRightFragment() {
        List<Crime> crimes = CrimeLab.get(getApplication()).getCrimes();
        if (crimes.isEmpty())
            return null;
        return CrimeFragment.newInstance(crimes.get(0).getmId());
    }


    @Override
    public void onCrimeSelected(Crime crime) {
        /**
         * todo 这里为什么要新建Activity CrimePagerActivity.newInstance. fragment 直接覆盖fragment怎么实现?
         */
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent newIntent = CrimePagerActivity.newIntent(this, crime.getmId());
            startActivity(newIntent);
        } else {
            Fragment newDetail = CrimeFragment.newInstance(crime.getmId());
            // Fragment的操作需要通过replace实现?
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();

        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment = (CrimeListFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}