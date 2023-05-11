package app.laccaria.laccata.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.laccaria.laccata.AppLicClass
import app.laccaria.laccata.AppLicClass.Companion.adID
import app.laccaria.laccata.AppLicClass.Companion.afCampGId
import app.laccaria.laccata.AppLicClass.Companion.afCampGName
import app.laccaria.laccata.AppLicClass.Companion.afCampId
import app.laccaria.laccata.AppLicClass.Companion.afCampName
import app.laccaria.laccata.AppLicClass.Companion.afChan
import app.laccaria.laccata.AppLicClass.Companion.afGroupId
import app.laccaria.laccata.AppLicClass.Companion.afGroupName
import app.laccaria.laccata.AppLicClass.Companion.afStat
import app.laccaria.laccata.AppLicClass.Companion.geotaaaag
import app.laccaria.laccata.AppLicClass.Companion.tail
import app.laccaria.laccata.AppLicClass.Companion.trackId
import app.laccaria.laccata.AppLicClass.Companion.utcTime
import app.laccaria.laccata.R
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.URLDecoder
import java.util.StringTokenizer

class AppsFragment : Fragment() {

    private val viewMode: VMforMain by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apps, container, false)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            viewMode.getApps(requireActivity())

            viewMode.appsStat.observe(requireActivity()) { it ->
                if (it != null) {
                    Log.d("AppsDataHere", it.toString())
                    afStat = it["af_status"].toString()
                    if (afStat == "Organic") {
                        geotaaaag = "ORGANIC"
                        adID = ""
                        afCampName = ""
                        afChan = ""
                        afCampId = ""
                        afCampGName = ""
                        afCampGId = ""
                        afGroupName = ""
                        afGroupId = ""
                    } else {
                        afChan = it["af_channel"].toString()
                        adID = it["ad_id"].toString()
                        afCampId = it["campaign_id"].toString()
                        afCampGName = it["campaign_group_name"].toString()
                        afCampGId = it["campaign_group_id"].toString()
                        afGroupName = it["adgroup"].toString()
                        afGroupId = it["adgroup_id"].toString()
                        val SpsString = it["campaign"].toString()
                        Log.d("SpsString", SpsString)
                        val (firstPart, secondPart) = SpsString.split("sub1")
                        trackId = firstPart.substringAfter("sub0=").substringBefore("_")
                        tail = "sub1$secondPart".replace("_", "&")
                        afCampName = it["campaign"].toString()
                        utcTime = it["install_time"].toString()
                    }
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.contain, GetFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }
    }

}