package app.laccaria.laccata.play

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.laccaria.laccata.R
import app.laccaria.laccata.databinding.FragmentStartBinding


class StartFrag : Fragment() {

    lateinit var mContext: Context
    lateinit var binding: FragmentStartBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStartBinding.inflate(inflater, container, false);
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startGame.setOnClickListener {
            val fragment = GameFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containGame, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        binding.rulesGame.setOnClickListener {
            val fragment = RulesOfGameFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.containGame, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        binding.endGame.setOnClickListener {
            AlertDialog.Builder(mContext).setTitle("Are you sure?")
                .setMessage("Are you sure you want to exit the game?")
                .setPositiveButton("Yes"){_,_ ->
                    requireActivity().finish()
                }
                .setNegativeButton("Cancel"){ dialog, _ ->
                    dialog.dismiss() // dismiss the dialog
                }
                .show()
        }
    }

}