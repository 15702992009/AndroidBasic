package com.example.criminalintent;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    private static final String TAG = "SingleFragmentActivity";

    protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment);
        // 绑定视图
        setContentView(getLayoutResId());
         // 2131427359 2131427359
        Log.d(TAG,String.valueOf(getLayoutResId()));

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            // 向xml文件中FrameLayout控件绑定  Fragment实体对象
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
