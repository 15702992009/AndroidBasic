package com.example.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;


/**
 * 绑定
 */
public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;
    // fragment所属activity实现该接口,
    // 然后在fragment与activity绑定时候在onAttach()方法中将Activity对象向下转型为Callbacks接口
    // 以便后面使用Callbacks的回调函数
    private Callbacks mCallbacks;

    public static final String EXTRA_CRIME_ID =
            "com.example.android.criminalintent.crime_id";
    private static final int REQUEST_CRIME = 1;
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    public interface Callbacks {
        void onCrimeSelected(Crime crime);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //  bind layout to Fragment
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        // RecyclerView xml绑定到类对象
        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        //todo 设置 管理器，为什么是LinearLayout
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // todo 忘记下面下的什么了
        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        // 初始化LEFT RIGHT 视图?
        updateUI();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    /**
     * 视图数据更新
     */
    public void updateUI() {

        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mAdapter == null) {
            //LEFT: 第一次创建adapter,并将adapter绑定到recyclerview对象上
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
            //RIGHT 显示右侧fragment的内容
           /* if (getActivity().findViewById(R.id.detail_fragment_container) != null) {
                Crime crime = crimes.get(0);
                if (crime != null) {
                    mCallbacks.onCrimeSelected(crime);
                }
            }*/
        } else {
            // new Crime后,左边list实时更新视图,
            mAdapter.setCrimes(crimes);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();

        String subtitle = getString(R.string.subtitle_format, crimeCount);
        if (!mSubtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;
        Objects.requireNonNull(activity.getSupportActionBar()).setSubtitle(subtitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_crime:
                Crime crime = new Crime();
                //只有这一种方法添加 crime.点击menu菜单的点击函数就创建好了crime的对象并持久话了
                CrimeLab.get(getActivity()).addCrime(crime);
 /*               Intent intent = CrimePagerActivity
                        .newIntent(getActivity(), crime.getmId());
                startActivity(intent);*/
                updateUI();
                mCallbacks.onCrimeSelected(crime);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * RecyclerView.ViewHolder
     */
    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;
        private Crime mCrime;


/*        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_imageView);
            itemView.setOnClickListener(this);
        }*/
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_imageView);
            itemView.setOnClickListener(this);
        }

   /*     public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getmTitle());
            mDateTextView.setText(mCrime.getmDate().toString());
            if (crime.ismSolved()) {
                mSolvedImageView.setImageResource(R.drawable.loves);
            } else {
                mSolvedImageView.setImageResource(R.drawable.fire);
            }
        }*/

        @Override
        public void onClick(View v) {
            /*Toast.makeText(getActivity(),
                    mCrime.getmTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();*/
//            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getmId());
//            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getmId());
//            startActivity(intent);
//            startActivityForResult(intent, REQUEST_CRIME);
            // Stick a new CrimeFragment in the activity's layout
//            Fragment fragment = CrimeFragment.newInstance(mCrime.getmId());
//            FragmentManager fm = getActivity().getSupportFragmentManager();
//            fm.beginTransaction()
//                    .replace(R.id.detail_fragment_container, fragment)
//                    .commit();
            mCallbacks.onCrimeSelected(mCrime);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<Crime> mCrimes;

        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @NonNull
        @org.jetbrains.annotations.NotNull
        @Override
        public CrimeListFragment.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_crime, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull CrimeListFragment.ViewHolder holder, int position) {
            Crime positionItem = mCrimes.get(position);
            holder.mTitleTextView.setText(positionItem.getmTitle());
            holder.mDateTextView.setText(positionItem.getmDate().toString());
            if (positionItem.ismSolved()) {
                holder.mSolvedImageView.setImageResource(R.drawable.loves);
            } else {
                holder.mSolvedImageView.setImageResource(R.drawable.fire);
            }
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

}
