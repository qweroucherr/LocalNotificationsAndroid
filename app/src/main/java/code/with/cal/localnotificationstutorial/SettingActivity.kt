package code.with.cal.localnotificationstutorial

import android.app.*
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.View
import android.widget.Toast
import code.with.cal.localnotificationstutorial.databinding.ActivitySettingBinding
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.util.*

class SettingActivity : AppCompatActivity() {
    val CREATE_FILE = 0
    private lateinit var binding : ActivitySettingBinding
    private val title = "Hi there"
    private val message = "Have you been waiting for something or someone since last report?"
    private val calendar1 = Calendar.getInstance()
    private val calendar2 = Calendar.getInstance()
    private val calendar3 = Calendar.getInstance()
    private val myFile = "My file.txt"
    //private val contentResolver = applicationContext.contentResolver
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()
        binding.submitButton.setOnClickListener { scheduleNotification() }


    }

    private fun setReminderTime(){
        val time = LocalDateTime.now()
        val day = time.dayOfMonth
        val month = time.monthValue - 1
        val year = time.year
        calendar1.set(year,month,day,9,0)
        calendar2.set(year,month,day,12,0)
        calendar3.set(year,month,day,18,0)
    }

    private fun scheduleNotification()
    {
        val intent = Intent(applicationContext, Notification::class.java)
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time, title, message)
    }


    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        var ss = intent.getStringExtra("Message").toString()

        AlertDialog.Builder(this)
            .setTitle(ss)
            .setMessage(
                "Title: " + title +
                        "\nMessage: " + message +
                        "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date)
            )
            .setPositiveButton("Okay") { _, _ -> }
            .show()
    }

    private fun getTime(): Long
    {
        val time = LocalDateTime.now()
        val minute = binding.timePicker.minute
        val hour = binding.timePicker.hour
        val day = time.dayOfMonth
        val month = time.monthValue - 1
        val year = time.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    private fun createNotificationChannel()
    {
        val name = "Notification Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun createDataFile(view: View) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE,"WTAData.txt")
            putExtra(DocumentsContract.EXTRA_INITIAL_URI,"")
        }
        startActivityForResult(intent,CREATE_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var uri: Uri = Uri.parse("")
        if (requestCode == CREATE_FILE && resultCode == RESULT_OK) {
            uri = data!!.data!!
        }
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("URI",uri.toString())
            commit()
        }
        Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show()
    }
}