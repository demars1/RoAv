package app.laccaria.laccata.brew

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import app.laccaria.laccata.ApiService
import app.laccaria.laccata.AppLicClass
import app.laccaria.laccata.DataInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import java.text.SimpleDateFormat
import java.util.*

class WebActivity : AppCompatActivity() {
    private val apiService: ApiService by inject(named("GetNetRetro"))

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    val currentTime = dateFormat.format(Date())

    lateinit var webSt: WebView
    lateinit var sharedPr: SharedPreferences

        override fun onBackPressed() {
            if (webSt.canGoBack()) {
                webSt.goBack()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webSt = WebView(this@WebActivity)
        sharedPr = getSharedPreferences("DataShared", Context.MODE_PRIVATE)
        setContentView(webSt)
        setOfSett()
        webSt.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                Toast.makeText(this@WebActivity, description, Toast.LENGTH_SHORT).show()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                saveUrl(url)
            }

        }

        webSt.loadUrl(setUrl())

        val PREFS_NAME = "MyPrefsFile"
        val PREF_FIRST_TIME_LAUNCH = "first_time_launch"

        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean(PREF_FIRST_TIME_LAUNCH, true)) {
            val dataToSend = DataInfo(
                AppLicClass.geotaaaag, "", "",
                AppLicClass.afStat,
                AppLicClass.afChan,
                AppLicClass.adID,
                AppLicClass.afCampId,
                AppLicClass.afCampGName,
                AppLicClass.afCampGId,
                AppLicClass.afGroupName,
                AppLicClass.afGroupId,
                AppLicClass.afCampName,
                AppLicClass.appId,
                AppLicClass.andrID,
                currentTime.toString(), "", "",
                AppLicClass.offer, "app.laccaria.laccata"
            )

            val editorOne = sharedPr.edit()

            editorOne.putString("geotag", AppLicClass.geotaaaag).apply()
            editorOne.putString("af_stat", AppLicClass.afStat).apply()
            editorOne.putString("af_chan", AppLicClass.afChan).apply()
            editorOne.putString("ad_id", AppLicClass.adID).apply()
            editorOne.putString("af_campId", AppLicClass.afCampId).apply()
            editorOne.putString("af_campGN", AppLicClass.afCampGName).apply()
            editorOne.putString("af_campGID", AppLicClass.afCampGId).apply()
            editorOne.putString("af_GrName", AppLicClass.afGroupName).apply()
            editorOne.putString("af_GrId", AppLicClass.afGroupId).apply()
            editorOne.putString("af_CampName", AppLicClass.afCampName).apply()
            editorOne.putString("WhereTo", "Webs").apply()
            editorOne.putString("app_id", AppLicClass.appId).apply()
            editorOne.putString("andro_id", AppLicClass.andrID).apply()

            sharedPreferences.edit().putBoolean(PREF_FIRST_TIME_LAUNCH, false).apply()
            Log.d("DataToSend", dataToSend.toString())


            lifecycleScope.launch (Dispatchers.IO) {
                apiService.sendData(dataToSend)
            }

        } else {

            val geo = sharedPr.getString("geotag", null)
            val statAF = sharedPr.getString("af_stat", null)
            val chanAf = sharedPr.getString("af_chan", null)
            val IDAD = sharedPr.getString("ad_id", null)
            val campIDAf = sharedPr.getString("af_campId", null)
            val campGN = sharedPr.getString("af_campGN", null)
            val campGID = sharedPr.getString("af_campGID", null)
            val adName = sharedPr.getString("af_GrName", null)
            val adId = sharedPr.getString("af_GrId", null)
            val campNameAf = sharedPr.getString("af_CampName", null)
            val IDapp = sharedPr.getString("app_id", null)
            val IDAndr = sharedPr.getString("andro_id", null)
            val Ofa = sharedPr.getString("offr", null)

            val dataToSendSecond = DataInfo(
                geo, "", "",
                statAF,
                chanAf,
                IDAD,
                campIDAf,
                campGN,
                campGID,
                adName,
                adId,
                campNameAf,
                IDapp,
                IDAndr,
                currentTime.toString(), "", "",
                Ofa, "app.laccaria.laccata"
            )
            Log.d("DataToSend", dataToSendSecond.toString())

            lifecycleScope.launch(Dispatchers.IO) {
                apiService.sendData(dataToSendSecond)
            }
        }
    }

    fun setOfSett() {
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(webSt, true)
        val webViewSet = webSt.settings
        webViewSet.javaScriptEnabled = true
        webViewSet.useWideViewPort = true
        webViewSet.loadWithOverviewMode = true
        webViewSet.allowFileAccess = true
        webViewSet.domStorageEnabled = true
        webViewSet.userAgentString = webViewSet.userAgentString.replace("; wv", "")
        webViewSet.javaScriptCanOpenWindowsAutomatically = true
        webViewSet.setSupportMultipleWindows(false)
        webViewSet.displayZoomControls = false
        webViewSet.builtInZoomControls = true
        webViewSet.allowFileAccess = true
        webViewSet.allowContentAccess = true
        webViewSet.setSupportZoom(true)
        webViewSet.pluginState = WebSettings.PluginState.ON
        webViewSet.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        webViewSet.cacheMode = WebSettings.LOAD_DEFAULT
        webViewSet.allowContentAccess = true
        webViewSet.mediaPlaybackRequiresUserGesture = false
    }

    var urlLo = ""

    private fun setUrl(): String {
        val spoon = getSharedPreferences("SP_WEBVIEW_PREFS", MODE_PRIVATE)
        val link = sharedPr.getString("offr", null).toString()
        return spoon.getString("SAVED_URL", link).toString()
    }

    fun saveUrl(savedUrl: String?) {
        if (urlLo == "") {
            urlLo = getSharedPreferences(
                "SP_WEBVIEW_PREFS",
                AppCompatActivity.MODE_PRIVATE
            ).getString(
                "SAVED_URL",
                savedUrl
            ).toString()

            getSharedPreferences("SP_WEBVIEW_PREFS", MODE_PRIVATE)
                .edit()
                .putString("SAVED_URL", savedUrl)
                .apply()
        }
    }

}