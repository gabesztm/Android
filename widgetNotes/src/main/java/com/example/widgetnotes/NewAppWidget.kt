package com.example.widgetnotes

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.io.FileInputStream
import java.io.InputStream


/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {

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

    companion object {

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent=PendingIntent.getActivity(context,0, intent, 0)
            val views = RemoteViews(context.packageName, R.layout.new_app_widget)
            views.setOnClickPendingIntent(R.id.tvTitle, pendingIntent)
            appWidgetManager.updateAppWidget(appWidgetId, views)



            val intentUpdate = Intent(context, NewAppWidget::class.java)
            intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            val idArray = intArrayOf(appWidgetId)
            intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray)
            val pendingUpdate=PendingIntent.getBroadcast(context,appWidgetId,intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setOnClickPendingIntent(R.id.btnRefresh,pendingUpdate)





            views.setTextViewText(R.id.tvNotes, readFromWidget(context, appWidgetManager, appWidgetId))
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
        fun readFromWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int):String{
            val charset=Charsets.UTF_8
            val fileName="widgetNotes.txt"
            val fIn= context.openFileInput(fileName)
            val notesByteArray=fIn.readBytes()
            val notesToRead=notesByteArray.toString(charset)
            fIn.close()
            return notesToRead
            }


    }

}

