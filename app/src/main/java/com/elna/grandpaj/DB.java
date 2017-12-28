package com.elna.grandpaj;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.HashMap;

/**
 *
 * @author arash
 */
@SuppressWarnings("WeakerAccess")
public class DB {

    private final static String BIOGRAPHY_TABLE    = "biography";
    private final static String CATEGORY_TABLE    = "category";

    public static final String BIO_BOOK_TABLE = "bio_book";

    public static final String TABLE_LINK_COLUMN = "table_link";
    public static final String SECTION_LINK_COLUMN = "section_id";
    public final static String ID_COLUMN                = "id";
    public final static String CHAPTER_COLUMN          = "chapter_name";
    public final static String TEXT_COLUMN          = "text";
    public final static String ORDER_COLUMN      = "order";

    public final static String CATEGORY_NAME_COLUMN       = "category_name";


    private static DB singleton = null;
    public static File databaseFile;

    private final SQLiteDatabase pbDatabase;


    private DB() {
        pbDatabase = SQLiteDatabase.openDatabase(databaseFile.toString(), null, SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);

    }

    public synchronized static DB get() {
        if (singleton == null) {
            singleton = new DB();
        }

        return singleton;
    }

    public Cursor getCategories() {
        String[] cols = {ID_COLUMN, CATEGORY_NAME_COLUMN, TABLE_LINK_COLUMN, SECTION_LINK_COLUMN};
        String[] selectionArgs = {};
        return pbDatabase.query(
                true,
                CATEGORY_TABLE,
                cols,
                null,
                selectionArgs,
                null,
                null,
                CATEGORY_NAME_COLUMN + " ASC",
                null);
    }

    public int getPrayerCountForCategory(String category, String language) {
        // check the cache first
//        if (prayerCountCache.containsKey(language + category)) {
//            return prayerCountCache.get(language + category);
//        }

        String[] selectionArgs = {category, language};
        Cursor cursor = pbDatabase.rawQuery(
                "SELECT COUNT(id) FROM prayers WHERE category=? and language=?",
                selectionArgs);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
//            prayerCountCache.put(language+category, count);
            return count;
        }

        // should never happen
        return 0;
    }

    public Cursor getChapter(long chapterId) {
        String[] cols = {TEXT_COLUMN};
        String selectionClause = ID_COLUMN + "=?";
        String[] selectionArgs = {Long.valueOf(chapterId).toString()};

        return pbDatabase.query(BIOGRAPHY_TABLE, cols, selectionClause, selectionArgs, null, null, null);
    }

//    public Cursor getPrayers(String category) {
//        String[] cols = {ID_COLUMN,
//                            OPENINGWORDS_COLUMN,
//                            CATEGORY_COLUMN,
//                            AUTHOR_COLUMN,
//                            LANGUAGE_COLUMN,
//                            WORDCOUNT_COLUMN};
//        String selectionClause = "category=? AND language=?";
//        String[] selectionArgs = {category};
//        return pbDatabase.query(
//                true,
//                PRAYERS_TABLE,
//                cols,
//                selectionClause,
//                selectionArgs,
//                null,
//                null,
//                OPENINGWORDS_COLUMN + " ASC",
//                null);
//    }
//
//    public Cursor getPrayersWithKeywords(String []keywords) {
//        String []cols = {ID_COLUMN, OPENINGWORDS_COLUMN, CATEGORY_COLUMN, AUTHOR_COLUMN, WORDCOUNT_COLUMN };
//        StringBuilder whereClause = new StringBuilder();
//        boolean firstKeyword = true;
//        for (String kw : keywords) {
//            if (kw.isEmpty()) {
//                continue;
//            }
//            if (!firstKeyword) {
//                whereClause.append(" AND");
//            } else {
//                firstKeyword = false;
//            }
//
//            whereClause.append(" searchText LIKE '%");
//            whereClause.append(kw);
//            whereClause.append("%'");
//        }
//
//        // build the language portion of the query
//        StringBuilder languageClause = new StringBuilder();
//
//
//        return pbDatabase.query(PRAYERS_TABLE, cols, whereClause.toString(), null, null, null, LANGUAGE_COLUMN);
//    }
//
//    public Cursor getChapter(long chapterId) {
//        String[] cols = {PRAYERTEXT_COLUMN, AUTHOR_COLUMN, CITATION_COLUMN, SEARCHTEXT_COLUMN, LANGUAGE_COLUMN};
//        String selectionClause = ID_COLUMN + "=?";
//        String[] selectionArgs = {Long.valueOf(chapterId).toString()};
//
//        return pbDatabase.query(PRAYERS_TABLE, cols, selectionClause, selectionArgs, null, null, null);
//    }
}
