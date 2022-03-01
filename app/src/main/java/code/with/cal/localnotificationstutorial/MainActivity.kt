package code.with.cal.localnotificationstutorial

import android.app.*
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import code.with.cal.localnotificationstutorial.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity()
{
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun amWaiting(view: View) {
        val intent = Intent(this, SurveyActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun wasWaiting(view: View){
        val intent = Intent(this, SurveyActivity2::class.java)
        startActivity(intent)
        finish()
    }

    fun setting(view: View){
        val string1 = "From MainActivity"
        val intent = Intent(this, SettingActivity::class.java)
        intent.putExtra("Message",string1)
        startActivity(intent)
    }
}














