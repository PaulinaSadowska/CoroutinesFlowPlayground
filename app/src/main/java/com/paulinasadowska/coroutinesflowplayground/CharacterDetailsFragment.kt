package com.paulinasadowska.coroutinesflowplayground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.paulinasadowska.coroutinesflowplayground.databinding.CharacterDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = CharacterDetailsFragmentBinding.inflate(inflater, container, false)

        binding.characterName.text = args.name
        return binding.root
    }
}