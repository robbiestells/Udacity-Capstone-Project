package com.example.studio111.commentist;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.studio111.commentist.Utilities.PlayerService;

/**
 * Created by robbi on 2/19/2017.
 */

public class CommentistWidgetProvider extends android.appwidget.AppWidgetProvider {
   static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
       RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_layout);
       views.setTextViewText(R.id.widgetEpisodeName, "vegans");
       views.setImageViewResource(R.id.widgetLogo, R.drawable.vegans);
       appWidgetManager.updateAppWidget(appWidgetId, views);
   }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
//        if (PlayerService.ACTION_DATA_UPDATED.equals(intent.getAction())){
//            context.startService(new Intent(context, PlayWidgetIntentService.class));
//        }
      //  context.startService(new Intent(context, AppWidgetIntentService.class));

        if(PlayerService.ACTION_DATA_UPDATED.equals(intent.getAction())){

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName myappWidget = new ComponentName(context.getPackageName(), CommentistWidgetProvider.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(myappWidget);

            onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        for(int appWidgetId : appWidgetIds){
//            RemoteViews views = new RemoteViews(context.getPackageName(),
//                    R.layout.appwidget_layout);
//            appWidgetManager.updateAppWidget(appWidgetId, views);
//        }

     //   context.startService(new Intent(context, AppWidgetIntentService.class));
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}
