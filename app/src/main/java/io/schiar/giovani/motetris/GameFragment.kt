package io.schiar.giovani.motetris

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.schiar.giovani.motetris.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        viewModel.startGame()
        val binding = FragmentGameBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@GameFragment
            viewModel = this@GameFragment.viewModel
            executePendingBindings()
        }
        return binding.root
    }

}