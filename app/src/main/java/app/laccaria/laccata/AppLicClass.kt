package app.laccaria.laccata

import android.app.Application
import com.onesignal.OneSignal
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppLicClass: Application() {

    val os_key = "c0c6fef6-4107-4154-b87b-b4f5d8c8b82f"

    companion object{
        val apps_key = "WZCvQTVbZZ8hgb5ddgnxYV"
        var afStat: String = "null"
        var appId: String = "null"
        var afChan: String = "null"
        var afCampId: String = "null"
        var afCampGName: String = "null"
        var afCampGId: String = "null"
        var afGroupName: String = "null"
        var afGroupId: String = "null"
        var afCampName: String = "null"
        var andrID: String = "null"
        var adID: String = "null"
        var domain: String = "null"
        var al: String = "null"
        var campKei = "null"
        var offer = "null"
        var geotaaaag = "null"
        var utcTime: String = "null"
    }

    override fun onCreate() {
        super.onCreate()
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(os_key);

        startKoin{
            androidLogger()
            androidContext(this@AppLicClass)
            modules(appModule, networkModule, networkforKloModule)
        }
    }

}