package app.laccaria.laccata.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import app.laccaria.laccata.AppLicClass.Companion.andrID
import app.laccaria.laccata.R
import app.laccaria.laccata.play.ZaglActivity
import app.laccaria.laccata.brew.WebActivity
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val PREFS_NAME = "MyPrefsFile"
    val PREF_FIRST_TIME_LAUNCH = "first_time_launch"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkInternetConnection()
    }

    @SuppressLint("HardwareIds")
    private fun getDroID() {

        andrID = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )

    }

    private fun checkInternetConnection() {

        if (isInternetAvailable(this)) {
            val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

            if (sharedPreferences.getBoolean(PREF_FIRST_TIME_LAUNCH, true)) {
                // App is launched for the first time, do any necessary setup
                // Set the flag to false to indicate that the app has been launched before
                getDroID()
                val fragment = AppsFragment()
                supportFragmentManager.beginTransaction()
                    .add(R.id.contain, fragment)
                    .commit()

            } else {
                val sharedPr = getSharedPreferences("DataShared", Context.MODE_PRIVATE)
                val check = sharedPr.getString("WhereTo", null)
                Log.d("WhereTo", check.toString())

                if (check == "Webs") {
                    startActivity(Intent(this, WebActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, ZaglActivity::class.java))
                    finish()
                }
            }
        } else {
            showAlert(this, "No internet connection", "Please check your internet connection and try again.") {
                Handler().postDelayed({ checkInternetConnection() }, 2000)
            }
        }
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun showAlert(context: Context, title: String, message: String, onDismiss: () -> Unit = {}) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, which ->
            onDismiss.invoke()
            dialog.dismiss()
        }
        builder.show()
    }

}