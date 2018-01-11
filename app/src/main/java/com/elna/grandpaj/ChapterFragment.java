package com.elna.grandpaj;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.samskivert.mustache.Mustache;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;

/**
 *
 * @author arash
 */
public class ChapterFragment extends Fragment {

    private WebView mWebView = null;
    private Cursor chapterCursor;
    private float mScale = 1.0f;

    private static final String SECTION_ID_ARGUMENT = "section_id";

    public static ChapterFragment newInstance(long sectionId) {
        ChapterFragment fragment = new ChapterFragment();
        Bundle args = new Bundle();
        args.putLong(SECTION_ID_ARGUMENT, sectionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        long sectionId = arguments.getLong(SECTION_ID_ARGUMENT, -1);
        if (sectionId == -1) {
            throw new IllegalArgumentException("You must provide a prayer id to this fragment");
        }
        chapterCursor = DB.get().getFirstChapter(sectionId);
        chapterCursor.moveToFirst();
        mScale = Prefs.get(App.getApp()).getBookTextScalar();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 19) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }

        mWebView = new WebView(this.getActivity());
        mWebView.getSettings().setSupportZoom(true);
        mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mWebView.setKeepScreenOn(true);
        reloadChapter();

        return mWebView;
    }

    private void reloadChapter() {
        mWebView.loadDataWithBaseURL(null, getChapterHTML(), "text/html", "UTF-8", null);
    }

    @Override
    public void onPause() {
        super.onPause();

        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.book, menu);
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // .75 to 1.60
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.action_increase_text_size:
                if (mScale < 1.6f) {
                    mScale += 0.05f;
                    Prefs.get(App.getApp()).setPrayerTextScalar(mScale);
                    reloadChapter();
                }
                break;
            case R.id.action_decrease_text_size:
                if (mScale > .75) {
                    mScale -= 0.05f;
                    com.elna.grandpaj.Prefs.get(App.getApp()).setPrayerTextScalar(mScale);
                    reloadChapter();
                }
                break;
//            case R.id.action_classic_theme:
//                boolean useClassic = !item.isChecked(); // toggle the value
//                item.setChecked(useClassic);
//                com.elna.grandpaj.Prefs.get(App.getApp()).setUseClassicTheme(useClassic);
//                reloadChapter();
//                break;
//            case R.id.action_share_prayer:
//                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//                sharingIntent.putExtra(Intent.EXTRA_TEXT, getPrayerText());
//                startActivity(Intent.createChooser(sharingIntent, "Share via"));
//                break;
//            case R.id.action_print_prayer:
//                printPrayer();
//                break;
            default:
                return false;
        }

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setTitle(null);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
        }
    }

    public String getChapterHTML() {
        float pFontWidth = 1.1f * mScale;
        float pFontHeight = 1.575f * mScale;
        float pComment = 0.8f * mScale;
        float authorWidth = 1.03f * mScale;
        float authorHeight = 1.825f * mScale;
        float versalWidth = 3.5f * mScale;
        float versalHeight = 0.75f * mScale;

        HashMap<String, String> args = new HashMap<>();
        args.put("fontWidth", String.format(Locale.US, "%f", pFontWidth));
        args.put("fontHeight", String.format(Locale.US, "%f", pFontHeight));
        args.put("commentSize", String.format(Locale.US, "%f", pComment));
        args.put("authorWidth", String.format(Locale.US, "%f", authorWidth));
        args.put("authorHeight", String.format(Locale.US, "%f", authorHeight));
        args.put("versalWidth", String.format(Locale.US, "%f", versalWidth));
        args.put("versalHeight", String.format(Locale.US, "%f", versalHeight));
        boolean useClassicTheme = com.elna.grandpaj.Prefs.get(App.getApp()).useClassicTheme();
        String bgColor;
        String versalAndAuthorColor;
        String font;
        String italicOrNothing;
        if (useClassicTheme) {
            bgColor = "#D6D2C9";
            versalAndAuthorColor = "#992222";
            font = "Georgia";
            italicOrNothing = "italic";
        } else {
            bgColor = "#ffffff";
            versalAndAuthorColor = "#33b5e5";
            font = "sans-serif";
            italicOrNothing = "";
        }
        args.put("backgroundColor", bgColor);
        args.put("versalAndAuthorColor", versalAndAuthorColor);
        args.put("font", font);
        args.put("italicOrNothing", italicOrNothing);

        int textIndex = chapterCursor.getColumnIndexOrThrow(DB.TEXT_COLUMN);
        String text = chapterCursor.getString(textIndex);
        String chapterName = chapterCursor.getString(chapterCursor.getColumnIndexOrThrow(DB.CHAPTER_NAME_COLUMN));
        args.put("chapter_name", chapterName);
        args.put("chapter_text", text);

        args.put("layoutDirection", "rtl");

        InputStream is = getResources().openRawResource(R.raw.text_template);
        InputStreamReader isr = new InputStreamReader(is);

        String result = Mustache.compiler().escapeHTML(false).compile(isr).execute(args);

        return result;
    }


    @TargetApi(19) @SuppressWarnings("deprecation")
    private void printPrayer() {
        if (mWebView == null) {
            // shouldn't happen, but just in case
            return;
        }

        PrintManager manager = (PrintManager)getActivity().getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter adapter;
        if (Build.VERSION.SDK_INT >= 21) {
            adapter = mWebView.createPrintDocumentAdapter("Prayer");
        } else {
            adapter = mWebView.createPrintDocumentAdapter();
        }

        String jobName = getString(R.string.app_name) + " " + getString(R.string.document);
        manager.print(jobName, adapter, new PrintAttributes.Builder().build());
    }
}
