package com.elna.grandpaj;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.elna.grandpaj.entities.BioPicture;
import com.elna.grandpaj.entities.Category;
import com.elna.grandpaj.entities.Chapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class DB {

    private final static String BIOGRAPHY_TABLE    = "bio_book";
    private final static String CATEGORY_TABLE    = "category";
    public static final String BIO_PICS_TABLE = "bio_pics";
    public static final String BIO_TIMELINE_TABLE = "bio_timeline";
    public static final String BIO_LOCATIONS_TABLE = "bio_locations";
    public static final String BIO_BOOK_TABLE = "bio_book";

    public static final String TABLE_LINK_COLUMN = "table_link";
    public static final String SECTION_LINK_COLUMN = "section_id";
    public final static String ID_COLUMN                = "id";
    public final static String CHAPTER_NAME_COLUMN          = "chapter_name";
    public final static String TEXT_COLUMN          = "text";
    public final static String CHAPTER_ID_COLUMN      = "chapter_id";
    public final static String CATEGORY_NAME_COLUMN       = "category_name";

    public final static String PIC_ID      = "pic_id";
    public final static String PIC_TITLE      = "title";
    public final static String PIC_IMAGE_BLOB      = "image";

    private static DB singleton = null;
    public static File databaseFile;

    private SQLiteDatabase liteDatabase;

    private List<Category> categoriesCached;

    public DB() {
        liteDatabase = openDB();
    }

    private SQLiteDatabase openDB() {
        return SQLiteDatabase.openDatabase(databaseFile.toString(), null, SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
    }

    public synchronized static DB get() {
        if (singleton == null) {
            singleton = new DB();
        }

        return singleton;
    }


    private List<Category> loadAllCategories() {
        if (!liteDatabase.isOpen())
            liteDatabase = openDB();
        List<Category> result = new ArrayList<>();
        String[] cols = {ID_COLUMN, CATEGORY_NAME_COLUMN, TABLE_LINK_COLUMN, SECTION_LINK_COLUMN};
        String[] selectionArgs = {};

        Cursor cursor = liteDatabase.query(
                false,
                CATEGORY_TABLE,
                cols,
                "",
                selectionArgs,
                null,
                null,
                ID_COLUMN + " ASC",
                null);

        
        while (cursor.moveToNext()) {
            Category category = createCategory(cursor);
            result.add(category);
        }

        liteDatabase.close();
        return result;
    }

    private Category createCategory(Cursor cursor) {
        Integer id = cursor.getInt(cursor.getColumnIndex(DB.ID_COLUMN));
        String tableLink = cursor.getString(cursor.getColumnIndex(DB.TABLE_LINK_COLUMN));
        Integer sectionLink = cursor.getInt(cursor.getColumnIndex(DB.SECTION_LINK_COLUMN));
        String name = cursor.getString(cursor.getColumnIndex(DB.CATEGORY_NAME_COLUMN));
        return new Category(id, name, tableLink, sectionLink);
    }


    public List<Category> getCategories() {
        if (categoriesCached == null) {
            categoriesCached = loadAllCategories();
        }
        return  categoriesCached;
    }

    public Cursor getChapter(Long sectionId, Long chapterId) {
        String[] cols = {TEXT_COLUMN, CHAPTER_NAME_COLUMN};
        String selectionClause = SECTION_LINK_COLUMN + "=? and "+  CHAPTER_ID_COLUMN + " =?";
        String[] selectionArgs = {sectionId.toString(), chapterId.toString()};
        if (!liteDatabase.isOpen())
            liteDatabase = openDB();
        return liteDatabase.query(BIOGRAPHY_TABLE, cols, selectionClause, selectionArgs, null, null, null);

    }


    public List<Chapter> getAllChaptersInSection(Long sectionId) {
        List<Chapter> result = new ArrayList<>();

        String[] cols = {CHAPTER_NAME_COLUMN,SECTION_LINK_COLUMN};
        String selectionClause = SECTION_LINK_COLUMN + "=? ";
        String[] selectionArgs = {sectionId.toString()};

        if (!liteDatabase.isOpen())
            liteDatabase = openDB();
        Cursor cursor = liteDatabase.query(BIOGRAPHY_TABLE, cols, selectionClause, selectionArgs, null, null, ID_COLUMN + " ASC");
        while (cursor.moveToNext()) {
            Chapter chapter = createChapter(cursor);
            result.add(chapter);
        }

        liteDatabase.close();
        return result;
    }

    private Chapter createChapter(Cursor cursor) {
        Integer sectionLink = cursor.getInt(cursor.getColumnIndex(DB.SECTION_LINK_COLUMN));
        String name = cursor.getString(cursor.getColumnIndex(DB.CHAPTER_NAME_COLUMN));
        return new Chapter(sectionLink, name);
    }


    // Getting single contact
    BioPicture getContact(int id) {
        if (!liteDatabase.isOpen())
            liteDatabase = openDB();
        Cursor cursor = liteDatabase.query(BIO_PICS_TABLE,
                new String[] { PIC_ID, PIC_TITLE, PIC_IMAGE_BLOB }, PIC_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        BioPicture contact = new BioPicture(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getBlob(1));
        liteDatabase.close();
        // return contact
        return contact;

    }

    // Getting All Contacts
    public List<BioPicture> getAllPictures() {
        List<BioPicture> picList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM bio_pics ORDER BY pic_id DESC";
        if (!liteDatabase.isOpen())
            liteDatabase = openDB();

        Cursor cursor = liteDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BioPicture bioPicture = new BioPicture();
                bioPicture.setID(cursor.getInt(0));
                bioPicture.setTitle(cursor.getString(1));
                bioPicture.setImage(cursor.getBlob(2));
                // Adding contact to list
                picList.add(bioPicture);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        liteDatabase.close();
        // return contact list
        return picList;

    }


}
