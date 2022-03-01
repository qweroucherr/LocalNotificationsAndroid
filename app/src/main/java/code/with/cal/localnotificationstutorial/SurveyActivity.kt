package code.with.cal.localnotificationstutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import code.with.cal.localnotificationstutorial.databinding.ActivitySurveyBinding
import kotlin.system.exitProcess

class SurveyActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySurveyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
    }

    fun submit(view: View){
        exitProcess(0)
    }
}