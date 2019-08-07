package com.elna.grandpaj.timeline;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.elna.grandpaj.R;
import com.github.vipulasri.timelineview.TimelineView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class TimeLineViewHolder extends RecyclerView.ViewHolder {


    TextView mDate;

    TextView mMessage;

    TimelineView mTimelineView;

    public TimeLineViewHolder(View itemView, int viewType) {

        super(itemView);

        mDate = (TextView) itemView.findViewById(R.id.text_timeline_date);

        mMessage = (TextView) itemView.findViewById(R.id.text_timeline_title);

        mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
        mTimelineView.initLine(viewType);

    }
}

