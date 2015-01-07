package course.examples.Alarms.AlarmCreate

import java.text.DateFormat
import java.util.Date

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

 class AlarmLoggerReceiver extends BroadcastReceiver {
	import AlarmLoggerReceiver._
	override def onReceive(context: Context, intent: Intent) {
		// Log receipt of the Intent with timestamp
		Log.i(Tag,"Logging alarm at:" + DateFormat.getDateTimeInstance().format(new Date()))
	}
}

object AlarmLoggerReceiver {
	val Tag = "AlarmLoggerReceiver"
}