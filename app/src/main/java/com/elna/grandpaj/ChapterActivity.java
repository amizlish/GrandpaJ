package com.elna.grandpaj;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.elna.grandpaj.entities.Category;
import com.elna.grandpaj.entities.Chapter;

import java.util.ArrayList;
import java.util.List;

public class ChapterActivity extends AppCompatActivity {

    private static final String ARG_SECTION_ID = "section_id";

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private String mTitle;

    public static Intent newIntent(Context context, Category category) {
        Intent intent = new Intent(context, ChapterActivity.class);
        intent.putExtra(ARG_SECTION_ID, category.getSectionLink());

        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        setContentView(R.layout.chapter_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.chapter_toolbar);

        if (Build.VERSION.SDK_INT >= 21) {
            String appName = getString(R.string.app_name);
            Bitmap appIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            int headerColor = ContextCompat.getColor(this, R.color.task_header);
            setTaskDescription(new ActivityManager.TaskDescription(appName, appIcon, headerColor));
        }

        setSupportActionBar(toolbar);


        if (state == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                throw new IllegalArgumentException("You need to provide an 'extra' with the section id");
            }
            long sectionId = extras.getInt(ARG_SECTION_ID, 0);
            if (sectionId == 0) {
                throw new IllegalArgumentException("You need to provide an 'extra' with the section id");
            }


            prepareLeftNavigation(sectionId);

            ChapterFragment fragment = ChapterFragment.newInstance(sectionId, 0);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.chapter_container, fragment);
            ft.commit();
        }
    }

    private void prepareLeftNavigation(long sectionId) {
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                getSupportActionBar().setTitle("navigate!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        fillItemsInLeftDrawer(sectionId);
    }

    private void fillItemsInLeftDrawer(long sectionId) {
        List<Chapter> chapters = DB.get().getAllChaptersInSection(sectionId);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getChapterNames(chapters));
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setItemChecked(0, true);
        mDrawerList.setOnItemClickListener(new DrawerChapterClickListener(this, sectionId, chapters));
    }

    private String[] getChapterNames(List<Chapter> chapters) {
        List<String> result = new ArrayList<>();
        for(Chapter c: chapters)
            result.add(c.getName());
        return result.toArray(new String[0]);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (isFinishing()) {
            overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    /** Swaps fragments in the main content view */
    public void selectChapterInDrawer(int position, long sectionId, List<Chapter> chapters) {
        // Create a new fragment and specify the planet to show based on position
        ChapterFragment fragment = ChapterFragment.newInstance(sectionId, position);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.chapter_container, fragment)
                .commit();



        mDrawerList.setItemChecked(position, true);
        setTitle(chapters.get(position).getName());
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title.toString();
        getSupportActionBar().setTitle(mTitle);
    }

}
