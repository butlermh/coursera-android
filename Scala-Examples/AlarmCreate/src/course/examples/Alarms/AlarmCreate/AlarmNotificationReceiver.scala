package course.examples.Alarms.AlarmCreate

import java.text.DateFormat
import java.util.Date

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

 class AlarmNotificationReceiver extends BroadcastReceiver {
	import AlarmNotificationReceiver._
	
	// Notification Action Elements
	private var mNotificationIntent: Intent = _
	private var mContentIntent: PendingIntent = _

	// Notification Sound and Vibration on Arrival
	private val soundURI = Uri
			.parse("android.resource://course.examples.Alarms.AlarmCreate/"
					+ R.raw.alarm_rooster)
	private val mVibratePattern = Array[Long](0L, 200L, 200L, 300L)

	override def onReceive(context: Context, intent: Intent) {

		// The Intent to be used when the user clicks on the Notification View
		mNotificationIntent = new Intent(context, classOf[AlarmCreateActivity])

		// The PendingIntent that wraps the underlying Intent
		mContentIntent = PendingIntent.getActivity(context, 0,
				mNotificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK)

		// Build the Notification
		val notificationBuilder = new Notification.Builder(
				context).setTicker(TickerText)
				.setSmallIcon(android.R.drawable.stat_sys_warning)
				.setAutoCancel(true).setContentTitle(ContentTitle)
				.setContentText(ContentText).setContentIntent(mContentIntent)
				.setSound(soundURI).setVibrate(mVibratePattern)

		// Get the NotificationManager
		val mNotificationManager = context
			.getSystemService(Context.NOTIFICATION_SERVICE)
			.asInstanceOf[NotificationManager]

		// Pass the Notification to the NotificationManager:
		mNotificationManager.notify(MyNotificationId,
				notificationBuilder.build())

		// Log occurence of notify() call
		Log.i(Tag, "Sending notification at:"
				+ DateFormat.getDateTimeInstance().format(new Date()))
	}
}

object AlarmNotificationReceiver {
	// Notification ID to allow for future updates
	private val MyNotificationId = 1
	private val Tag = "AlarmNotificationReceiver"
	
	// Notification Text Elements
	val TickerText = "Are You Playing Angry Birds Again!"
	val ContentTitle = "A Kind Reminder"
	val ContentText = "Get back to studying!!"
}
