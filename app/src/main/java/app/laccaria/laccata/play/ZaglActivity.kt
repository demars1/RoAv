package app.laccaria.laccata.play

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.laccaria.laccata.R
import app.laccaria.laccata.main.GetFragment

class ZaglActivity : AppCompatActivity() {
    lateinit var sharedPr: SharedPreferences
    val PREFS_NAME = "MyPrefsFile"
    val PREF_FIRST_TIME_LAUNCH = "first_time_launch"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zagl)

        sharedPr = getSharedPreferences("DataShared", Context.MODE_PRIVATE)

        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(PREF_FIRST_TIME_LAUNCH, false).apply()

        val fragment = StartFrag()

        supportFragmentManager.beginTransaction()
            .add(R.id.containGame, fragment)
            .commit()

    }
}