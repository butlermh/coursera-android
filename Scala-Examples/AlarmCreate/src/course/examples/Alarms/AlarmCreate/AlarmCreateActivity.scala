package course.examples.Alarms.AlarmCreate

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast
import android.content.Context.ALARM_SERVICE

 class AlarmCreateActivity extends Activity {
	import AlarmCreateActivity._

	private var mAlarmManager: AlarmManager = _
	private var mNotificationReceiverIntent: Intent = _
	private var mLoggerReceiverIntent: Intent = _
	private var mNotificationReceiverPendingIntent: PendingIntent = _
	private var mLoggerReceiverPendingIntent: PendingIntent = _

	override def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)

		setContentView(R.layout.main)

		// Get the AlarmManager Service
		mAlarmManager = getSystemService(ALARM_SERVICE).asInstanceOf[AlarmManager]

		// Create an Intent to broadcast to the AlarmNotificationReceiver
		mNotificationReceiverIntent = new Intent(AlarmCreateActivity.this,
				classOf[AlarmNotificationReceiver])

		// Create an PendingIntent that holds the NotificationReceiverIntent
		mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(
				AlarmCreateActivity.this, 0, mNotificationReceiverIntent, 0)

		// Create an Intent to broadcast to the AlarmLoggerReceiver
		mLoggerReceiverIntent = new Intent(AlarmCreateActivity.this,
				classOf[AlarmLoggerReceiver])

		// Create PendingIntent that holds the mLoggerReceiverPendingIntent
		mLoggerReceiverPendingIntent = PendingIntent.getBroadcast(
				AlarmCreateActivity.this, 0, mLoggerReceiverIntent, 0)

		// Set up single alarm Button
		val singleAlarmButton = findViewById(R.id.single_alarm_button).asInstanceOf[Button]
		singleAlarmButton.setOnClickListener(new OnClickListener() {
			override def onClick(v: View) {

				// Set single alarm
				mAlarmManager.set(AlarmManager.RTC_WAKEUP,
						System.currentTimeMillis() + INITIAL_ALARM_DELAY,
						mNotificationReceiverPendingIntent)

				// Set single alarm to fire shortly after previous alarm
				mAlarmManager.set(AlarmManager.RTC_WAKEUP,
						System.currentTimeMillis() + INITIAL_ALARM_DELAY
								+ JITTER, mLoggerReceiverPendingIntent)

				// Show Toast message
				Toast.makeText(getApplicationContext(), "Single Alarm Set",
						Toast.LENGTH_LONG).show()
			}
		})

		// Set up repeating Alarm Button
		val repeatingAlarmButton = findViewById(R.id.repeating_alarm_button).asInstanceOf[Button]
		repeatingAlarmButton.setOnClickListener(new OnClickListener() {
			override def onClick(v: View) {

				// Set repeating alarm
				mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
						SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY,
						AlarmManager.INTERVAL_FIFTEEN_MINUTES,
						mNotificationReceiverPendingIntent)

				// Set repeating alarm to fire shortly after previous alarm
				mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
						SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY
								+ JITTER,
						AlarmManager.INTERVAL_FIFTEEN_MINUTES,
						mLoggerReceiverPendingIntent)

				// Show Toast message
				Toast.makeText(getApplicationContext(), "Repeating Alarm Set",
						Toast.LENGTH_LONG).show()
			}
		})

		// Set up inexact repeating alarm Button
		val inexactRepeatingAlarmButton = findViewById(R.id.inexact_repeating_alarm_button).asInstanceOf[Button]
		inexactRepeatingAlarmButton.setOnClickListener(new OnClickListener() {

			override def onClick(v: View) {

				// Set inexact repeating alarm
				mAlarmManager.setInexactRepeating(
						AlarmManager.ELAPSED_REALTIME,
						SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY,
						AlarmManager.INTERVAL_FIFTEEN_MINUTES,
						mNotificationReceiverPendingIntent)
				// Set inexact repeating alarm to fire shortly after previous alarm
				mAlarmManager.setInexactRepeating(
						AlarmManager.ELAPSED_REALTIME,
						SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY
								+ JITTER,
						AlarmManager.INTERVAL_FIFTEEN_MINUTES,
						mLoggerReceiverPendingIntent)

				Toast.makeText(getApplicationContext(),
						"Inexact Repeating Alarm Set", Toast.LENGTH_LONG)
						.show()
			}
		})

		// Set up cancel repeating alarm Button
		val cancelRepeatingAlarmButton = findViewById(R.id.cancel_repeating_alarm_button).asInstanceOf[Button]
		cancelRepeatingAlarmButton.setOnClickListener(new OnClickListener() {

			override def onClick(v: View) {

				// Cancel all alarms using mNotificationReceiverPendingIntent
				mAlarmManager.cancel(mNotificationReceiverPendingIntent)

				// Cancel all alarms using mLoggerReceiverPendingIntent
				mAlarmManager.cancel(mLoggerReceiverPendingIntent)

				// Show Toast message
				Toast.makeText(getApplicationContext(),
						"Repeating Alarms Cancelled", Toast.LENGTH_LONG).show()
			}
		})
	}
}

object AlarmCreateActivity {
	val INITIAL_ALARM_DELAY = 2 * 60 * 1000L
	val JITTER = 5000L
}