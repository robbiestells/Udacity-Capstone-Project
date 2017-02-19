package com.example.studio111.commentist;

import android.app.IntentService;
import android.appwidget.*;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.RemoteViews;

import static com.example.studio111.commentist.R.id.widgetEpisodeName;

/**
 * Created by robbi on 2/19/2017.
 */

public class AppWidgetIntentService extends IntentService {
    public AppWidgetIntentService() {
        super("AppWidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Retrieve all of the Today widget ids: these are the widgets we need to update
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                android.appwidget.AppWidgetProvider.class));

        //get playing episode and mediaplayer

        for (int appWidgetId : appWidgetIds){
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.appwidget_layout);

            views.setTextViewText(R.id.widgetEpisodeName, "new episode name");
            views.setImageViewResource(R.id.widgetLogo, R.drawable.unwind);

            //set playbutton clicked event

            appWidgetManager.updateAppWidget(appWidgetId, views);

        }

    }
}
