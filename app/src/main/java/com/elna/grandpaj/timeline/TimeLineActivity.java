package com.elna.grandpaj.timeline;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.elna.grandpaj.R;
import com.elna.grandpaj.entities.Category;
import com.elna.grandpaj.timeline.model.TimeLineModel;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class TimeLineActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<TimeLineModel> mDataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getResources().getString(R.string.timeline));

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);

        initView();
    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    private void initView() {
        setDataListItems();
        mTimeLineAdapter = new TimeLineAdapter(mDataList);
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }

    private void setDataListItems(){
        mDataList.add(new TimeLineModel("נולד בשנת תרע\"ח שנת חודש אב בכפר ווירזמעב", "1918"));
        mDataList.add(new TimeLineModel("עובר לגור עם משפחתו בוורשא", "1924"));
        mDataList.add(new TimeLineModel("באוקטובר ברח מוורשא המופצצת לכיוון רוסיה", "1939"));
        mDataList.add(new TimeLineModel("שוחרר מהכלא הרוסי וגוייס לצבא הפולני בראשות הגנרל אנדרס", "1942"));
        mDataList.add(new TimeLineModel("הגיע לארץ דרך איראן ואח\"כ עיראק כשהוא נוהג במשאית צבאית. נחת בקיבוץ משמרות", "1943"));
        mDataList.add(new TimeLineModel("ביוני התחתן עם חוה לבית ברג (ממקימי בני ברק). עבר לגור בבני ברק", "1944"));
        mDataList.add(new TimeLineModel("נולדה בתו הבכורה,אהובה. הוא עבד על משאית ה\"בקורוואי\" שקנה בעצמו", "1945"));
        mDataList.add(new TimeLineModel("נולד בנו ,אברהם יהודה,המכונה אבי", "1950"));
        mDataList.add(new TimeLineModel("עבר לדירה חדשה ברח ר' עקיבא 96, בבני ברק.על קרקע עליה עמדה הרפת של חמיו, צבי נחמן ברג ז\"ל. עבד במכבסה שקנה עם שותף בר' עקיבא 1 בבני ברק", "1959"));
        mDataList.add(new TimeLineModel(" נולדה צביה,הבת הצעירה. שמה ניתן לה לזכר הסב. מכר את זכויותיו במכבסה וקנה מכבסה בת\"א", "1961"));
        mDataList.add(new TimeLineModel("סגר את המכבסה .עבר לעבוד עם אבי.שכר דירה בקריית ביאליק", "1980"));
        mDataList.add(new TimeLineModel("חזר לגור בבני ברק", "1988"));
        mDataList.add(new TimeLineModel("נפטרה אשתו סבתא חוה בשושן פורים", "1989"));
        mDataList.add(new TimeLineModel("נישא בשנית", "1990"));
        mDataList.add(new TimeLineModel("תשס\"א ערב תשעה באב- נפטר. הותיר אחריו ילדים ונכדים. הנין לביא-יעקב מייזליש קרוי על שמו", "2001"));


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Menu
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static Intent newIntent(Context context, Category category) {
        return new Intent(context, TimeLineActivity.class);
    }
}