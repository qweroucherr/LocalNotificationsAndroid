package code.with.cal.localnotificationstutorial

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.View
import android.widget.Toast
import code.with.cal.localnotificationstutorial.databinding.ActivityMainBinding
import code.with.cal.localnotificationstutorial.databinding.ActivitySurvey2Binding
import code.with.cal.localnotificationstutorial.databinding.ActivitySurveyBinding
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import kotlin.system.exitProcess

class SurveyActivity2 : AppCompatActivity() {
    val CREATE_FILE = 0

    private lateinit var binding : ActivitySurvey2Binding
    var responseItem = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurvey2Binding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun submit(view: View){
        createDataFile()
        //exitProcess(0)
    }

    fun submit1(view: View){
        writeFile()
        //exitProcess(0)
    }

    private fun writeFile(){
        with(binding){
            responseItem += "\nDate, StartingTime, EndingTime, ${editTextA0.text}, ${editTextA1.text},${editTextA2.text},${checkBoxA31.isChecked}"
        }

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        val string1 = sharedPref.getString("URI", "")
        Toast.makeText(this, string1, Toast.LENGTH_LONG).show()
        val uri = Uri.parse(string1)
        val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        // Check for the freshest data.
        contentResolver.takePersistableUriPermission(uri, takeFlags)


        try {
            contentResolver.openFileDescriptor (uri!!, "wa")?.use {
                FileOutputStream(it.fileDescriptor).use {
                    it.write (
                        (responseItem)
                            .toByteArray()
                    )
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        AlertDialog.Builder(this)
            .setTitle("Title")
            .setMessage(
                uri.toString()
            )
            .setPositiveButton("Okay") { _, _ -> }
            .show()

        responseItem = ""
    }

    private fun createDataFile() {
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
            putBoolean("FirstRun",false)
            commit()
        }
        binding.buttonFirst.visibility = View.GONE
        binding.buttonSecond.visibility = View.VISIBLE
    }


}