package com.lort.mail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lort.mail.model.Rika;

import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    static FloatingActionButton fab;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, TaskEditActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action form if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action form item clicks here. The action form will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class CollectFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        SwipeRefreshLayout mSwipeRefreshLayout;
        RecyclerView rv;
        Rika rika;
        Disposable disposable;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public CollectFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static CollectFragment newInstance(int sectionNumber) {
            CollectFragment fragment = new CollectFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
            //mSwipeRefreshLayout.setOnRefreshListener(this);
            // делаем повеселее
            mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
            mSwipeRefreshLayout.setOnRefreshListener(() -> {
                disposable.dispose();
                disposable = rika.getTasks().subscribe(tasks -> {
                    TaskAdapter adapter = new TaskAdapter(tasks);
                    rv.setAdapter(adapter);
                });
                mSwipeRefreshLayout.setRefreshing(false);
            });
            rv = (RecyclerView) rootView.findViewById(R.id.rv_main);
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));

            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
            rv.setItemAnimator(itemAnimator);
            disposable = rika.getTasks().subscribe(tasks -> {
                TaskAdapter adapter = new TaskAdapter(tasks);
                rv.setAdapter(adapter);
            });

            return rootView;
        }

        @Override
        public void onRefresh() {
            // говорим о том, что собираемся начать
            //Toast.makeText(this, R.string.refresh_started, Toast.LENGTH_SHORT).show();
            // начинаем показывать прогресс
            mSwipeRefreshLayout.setRefreshing(true);
            rv.getAdapter().notifyDataSetChanged();
            mSwipeRefreshLayout.postDelayed(() -> {
                mSwipeRefreshLayout.setRefreshing(false);
                // говорим о том, что собираемся закончить
                //Toast.makeText(MainActivity.this, R.string.refresh_finished, Toast.LENGTH_SHORT).show();
            }, 3000);
        }

        public void setRika(Rika rika) {
            this.rika = rika;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

            Fragment fragment = new CollectFragment();
            Bundle args = new Bundle();
            args.putInt("Сбор", 1);
            fragment.setArguments(args);
            ((CollectFragment) fragment).setRika(((App) getApplication()).getRika());

            Fragment fragment1 = new Fragment();
            Bundle args1 = new Bundle();
            args1.putInt("Доставка", 3);
            fragment1.setArguments(args1);

            Fragment fragment2 = new ContractListFragment();
            Bundle args2 = new Bundle();
            args2.putInt("Контрольный лист", 5);
            fragment2.setArguments(args2);

            fragments = Arrays.asList(fragment, fragment1, fragment2);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a CollectFragment (defined as a static inner class below).
            //return CollectFragment.newInstance(position + 1);
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Сбор";
                case 1:
                    return "Доставка";
                case 2:
                    return "Контрольный лист";
            }
            return null;
        }
    }
}
