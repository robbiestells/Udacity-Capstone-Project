package com.prime.perspective.commentist;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.prime.perspective.commentist.Utilities.PlayerService;

/**
 * Created by robbi on 2/19/2017.
 */

public class CommentistWidgetProvider extends android.appwidget.AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_layout);

        Intent intent = new Intent(context, PlayerService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        // Get the layout for the App Widget and attach an on-click listener to the button
        views.setOnClickPendingIntent(R.id.widgetPlayButton, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (PlayerService.ACTION_DATA_UPDATED.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            String showTitle = extras.getString("showTitle");
            String show = extras.getString("show");
            int showLogo;
            //find show and get logo
            switch (show) {
                case "The Bearded Vegans":
                    showLogo = R.drawable.vegans;
                    break;
                case "Roll to Hit (5th Ed. Dungeons and Dragons)":
                    showLogo = R.drawable.rth;
                    break;
                case "The Unwind (Tech, Games, Gadgets, and Geek Culture)":
                    showLogo = R.drawable.unwind;
                    break;
                case "Sky on Fire: A Star Wars RPG":
                    showLogo = R.drawable.sky;
                    break;
                default:
                    showLogo = R.drawable.unwind;
            }
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName myappWidget = new ComponentName(context.getPackageName(), CommentistWidgetProvider.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(myappWidget);

            //update widgets with show logo and title
            for (int appWidgetId : appWidgetIds) {
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_layout);
                views.setTextViewText(R.id.widgetEpisodeName, showTitle);
                views.setImageViewResource(R.id.widgetLogo, showLogo);
                appWidgetManager.updateAppWidget(appWidgetId, views);

                Intent playButtonIntent = new Intent(PlayerService.ACTION_PAUSE);
                PendingIntent pendingIntent = PendingIntent.getService(context, 0, playButtonIntent, 0);
                views.setOnClickPendingIntent(R.id.widgetPlayButton, pendingIntent);

            }
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}
