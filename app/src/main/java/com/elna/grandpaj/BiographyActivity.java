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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.elna.grandpaj.entities.Category;

public class BiographyActivity extends AppCompatActivity {

    private static final String ARG_SECTION_ID = "section_id";

    public static Intent newIntent(Context context, Category category) {
        Intent intent = new Intent(context, BiographyActivity.class);
        intent.putExtra(ARG_SECTION_ID, category.getSectionLink());

        return intent;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        setContentView(R.layout.chapter_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.prayer_toolbar);

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

            ChapterFragment fragment = ChapterFragment.newInstance(sectionId);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.chapter_container, fragment);
            ft.commit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (isFinishing()) {
            overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);
        }
    }

}
