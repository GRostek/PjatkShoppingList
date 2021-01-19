package com.example.pjatkshoppinglist.activity

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.example.pjatkshoppinglist.R

/**
 * Implementation of App Widget functionality.
 */
class PjatkAppWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        //if(intent?.action == "com.example.pjatkshoppinglist.activity.BUTTON3"){}
    }
}

internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    var requestCode = 0
    val widgetText = context.getString(R.string.pjatk_shopping_list_app)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.pjatk_app_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)

    //set button to launch pjatk website on click
    setUrlButton(context, requestCode++, views)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}


private fun setUrlButton(context: Context, requestCode: Int, views: RemoteViews){
    val intentWWW = Intent(Intent.ACTION_VIEW)
    intentWWW.data = Uri.parse("https://www.pja.edu.pl")
    val pendingIntentWWW = PendingIntent.getActivity(
        context,
        requestCode,
        intentWWW,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    views.setOnClickPendingIntent(R.id.pjaktButton, pendingIntentWWW)
}