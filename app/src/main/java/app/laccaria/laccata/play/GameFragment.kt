package app.laccaria.laccata.play

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import app.laccaria.laccata.R
import app.laccaria.laccata.databinding.FragmentGameBinding
import app.laccaria.laccata.databinding.FragmentStartBinding


class GameFragment : Fragment() {

    lateinit var binding: FragmentGameBinding
    private var score = 0
    private var scoreTwo = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false);
        hideImageView()
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPlane.setOnClickListener {
            if(binding.gamePlane.isVisible) {
                binding.victoryCounter.text = (score++).toString()
            } else {
                binding.loseCounter.text = (scoreTwo++).toString()
            }
        }

    }

    private fun hideImageView() {
        Handler().postDelayed({
            binding.gamePlane.visibility = View.GONE
            showImageView()
        }, 2000) //
    }
    private fun showImageView() {
        Handler().postDelayed({
            binding.gamePlane.visibility = View.VISIBLE
            hideImageView()
        }, 1000) // 2000 milliseconds = 2 seconds
    }


}