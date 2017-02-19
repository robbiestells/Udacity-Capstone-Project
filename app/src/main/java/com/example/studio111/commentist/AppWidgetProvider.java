package com.example.studio111.commentist;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by robbi on 2/19/2017.
 */

public class AppWidgetProvider extends android.appwidget.AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId : appWidgetIds){
            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.appwidget_layout);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }
}
