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
import app.laccaria.laccata.ApiServKlo
import app.laccaria.laccata.R
import app.laccaria.laccata.play.ZaglActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named


class GetFragment : Fragment() {

    private val apiKloService: ApiServKlo by inject(named("ApiServKl"))
    lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext=context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            val response = apiKloService.getResource()
            if (response.isSuccessful) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.contain, GetSecondFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            } else if (response.code() == 403) {
                mContext.startActivity(Intent(requireActivity(), ZaglActivity::class.java))
                requireActivity().finish()
            }
        }
    }
}