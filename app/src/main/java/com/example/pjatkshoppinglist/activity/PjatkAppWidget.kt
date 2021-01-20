package com.example.pjatkshoppinglist.activity

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.Toast
import com.example.pjatkshoppinglist.R
import java.util.*

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
        if(intent?.action == context?.getString(R.string.image_pressed)){
            val views = RemoteViews(context?.packageName, R.layout.pjatk_app_widget)
            changeImage(views)


            if(context!=null) {
                AppWidgetManager.getInstance(context).updateAppWidget(
                        ComponentName(context, PjatkAppWidget::class.java),
                        views
                )
            }

            Toast.makeText(context, "XD", Toast.LENGTH_SHORT).show()
        }
        if(intent?.action == context?.getString(R.string.button_stop)){
            Toast.makeText(context, "XDD", Toast.LENGTH_SHORT).show()
        }
        if(intent?.action == context?.getString(R.string.button_pause)){
            Toast.makeText(context, "XDDD", Toast.LENGTH_SHORT).show()
        }
        if(intent?.action == context?.getString(R.string.button_play)){
            Toast.makeText(context, "XDDDD", Toast.LENGTH_SHORT).show()
        }
        if(intent?.action == context?.getString(R.string.button_next)){
            Toast.makeText(context, "XDDDDD", Toast.LENGTH_SHORT).show()
        }
    }


}

var imageQueue = LinkedList<Int>()

internal fun initImageQueue(){
    imageQueue.add(R.drawable.yoda)
    imageQueue.add(R.drawable.sudoku)
}


internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    var requestCode = 0
    println("JESTEM TU")
    if(imageQueue.isEmpty())
        initImageQueue()
    val widgetText = context.getString(R.string.pjatk_shopping_list_app)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.pjatk_app_widget)

    //set button to launch pjatk website on click
    setUrlButton(context, requestCode++, views)
    //ImageView
    setImageView(context, requestCode++, views, R.id.imageView, R.string.image_pressed)

    //MediaPlayer
    setButton(context, requestCode++, views, R.id.stopButton, R.string.button_stop)
    setButton(context, requestCode++, views, R.id.pauseButton, R.string.button_pause)
    setButton(context, requestCode++, views, R.id.playButton, R.string.button_play)
    setButton(context, requestCode++, views, R.id.nextButton, R.string.button_next)


    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}


internal fun setUrlButton(context: Context, requestCode: Int, views: RemoteViews){
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

internal fun setButton(context: Context, requestCode: Int, views: RemoteViews, buttonId: Int, actionId: Int){
    val intent = Intent(context.getString(actionId))
    intent.component = ComponentName(context, PjatkAppWidget::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(buttonId, pendingIntent)
}

internal fun setImageView(context: Context, requestCode: Int, views: RemoteViews, buttonId: Int, actionId: Int){
    val intent = Intent(context.getString(actionId))
    intent.component = ComponentName(context, PjatkAppWidget::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    /*val first = queue.first
    imageQueue = changeQueue(queue)


    */
    changeImage(views)
    //imageQueue = changeQueue(imageQueue)
    //views.setImageViewResource(R.id.imageView, R.drawable.yoda)
    views.setOnClickPendingIntent(buttonId, pendingIntent)

}

internal fun changeQueue(queue: LinkedList<Int>): LinkedList<Int> {
    val first = queue.first
    queue.removeFirst()
    queue.addLast(first)
    println(queue)
    return queue
}

internal fun changeImage(views: RemoteViews){
    val first = imageQueue.first
    imageQueue = changeQueue(imageQueue)
    views.setImageViewResource(R.id.imageView, first)
}