package com.elna.grandpaj;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.elna.grandpaj.entities.Chapter;

import java.util.List;

/**
 * Created by amitm on 20/02/18.
 */

class DrawerChapterClickListener implements ListView.OnItemClickListener {

    private final ChapterActivity chapterActivity;
    private long sectionId;
    private List<Chapter> chapters;

    public DrawerChapterClickListener(ChapterActivity chapterActivity, long sectionId, List<Chapter> chapters) {
        this.chapterActivity = chapterActivity;
        this.sectionId = sectionId;
        this.chapters = chapters;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        chapterActivity.selectChapterInDrawer(position, sectionId, chapters);
    }



}
