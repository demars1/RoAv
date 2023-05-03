package app.laccaria.laccata.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import app.laccaria.laccata.ApiService
import app.laccaria.laccata.AppLicClass
import app.laccaria.laccata.AppLicClass.Companion.afCampName
import app.laccaria.laccata.AppLicClass.Companion.al
import app.laccaria.laccata.AppLicClass.Companion.campKei
import app.laccaria.laccata.AppLicClass.Companion.domain
import app.laccaria.laccata.AppLicClass.Companion.geotaaaag
import app.laccaria.laccata.AppLicClass.Companion.offer
import app.laccaria.laccata.R
import app.laccaria.laccata.brew.WebActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named


class GetSecondFragment : Fragment() {
    private val apiService: ApiService by inject(named("GetNetRetro"))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPr = requireActivity().getSharedPreferences("DataShared", Context.MODE_PRIVATE)

        lifecycleScope.launch (Dispatchers.IO){
            try {
                val data = geotaaaag
                val response = apiService.getPost(data)

                domain = response.body()?.report!!.domain
                campKei = response.body()?.report!!.campaign_name
                al = response.body()?.report!!.alias

                offer = domain+al
                val editorOne = sharedPr.edit()
                editorOne.putString("offr", offer).apply()

                requireActivity().startActivity(Intent(requireActivity(), WebActivity::class.java))
                requireActivity().finish()

            } catch (_: Exception) {

            }
        }
    }
}