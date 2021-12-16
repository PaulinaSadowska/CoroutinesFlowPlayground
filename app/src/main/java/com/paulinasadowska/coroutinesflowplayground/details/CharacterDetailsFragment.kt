package com.paulinasadowska.coroutinesflowplayground.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.paulinasadowska.coroutinesflowplayground.databinding.CharacterDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    //private val navigationArgs: CharacterDetailsFragmentArgs by navArgs()
    private val viewModel by viewModels<CharacterDetailsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = CharacterDetailsFragmentBinding.inflate(inflater, container, false)

        viewModel.character.observe(this) {
            binding.characterDetails = it
            binding.executePendingBindings()
        }
        return binding.root
    }
}