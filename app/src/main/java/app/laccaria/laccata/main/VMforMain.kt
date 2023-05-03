package app.laccaria.laccata.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.laccaria.laccata.AppLicClass.Companion.appId
import app.laccaria.laccata.AppLicClass.Companion.apps_key
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VMforMain(private val application: Application): ViewModel() {
    private val _appsData = MutableLiveData<Map<String, Any>>()

    val appsStat: LiveData<Map<String, Any>>
        get() = _appsData

    fun getApps(context: Context) {
        AppsFlyerLib.getInstance().init(apps_key, conversionDataListener, application)
        AppsFlyerLib.getInstance().start(context)
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getGAID()
        }
    }


    private fun getGAID() {
        val advId = AdvertisingIdClient(application)
        advId.start()
        appId = advId.info.id.toString()

    }

    private var conversionDataListener: AppsFlyerConversionListener = object :
        AppsFlyerConversionListener {
        override fun onConversionDataSuccess(conversionDataMap: Map<String, Any>) {
            _appsData.postValue(conversionDataMap)
        }
        override fun onConversionDataFail(errorMessage: String) {
        }
        override fun onAppOpenAttribution(attributionData: Map<String, String>) {
        }
        override fun onAttributionFailure(errorMessage: String) {
        }
    }

}