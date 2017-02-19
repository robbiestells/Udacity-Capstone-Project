package com.example.studio111.commentist;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;

/**
 * Created by robbi on 2/19/2017.
 */

public class CommentistWidgetProvider extends android.appwidget.AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
//        if (PlayerService.ACTION_DATA_UPDATED.equals(intent.getAction())){
//            context.startService(new Intent(context, PlayWidgetIntentService.class));
//        }
        context.startService(new Intent(context, AppWidgetIntentService.class));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        for(int appWidgetId : appWidgetIds){
//            RemoteViews views = new RemoteViews(context.getPackageName(),
//                    R.layout.appwidget_layout);
//            appWidgetManager.updateAppWidget(appWidgetId, views);
//        }
        context.startService(new Intent(context, AppWidgetIntentService.class));
    }
}
