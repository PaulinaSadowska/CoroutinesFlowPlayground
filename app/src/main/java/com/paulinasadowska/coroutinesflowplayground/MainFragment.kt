package com.paulinasadowska.coroutinesflowplayground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.paulinasadowska.coroutinesflowplayground.databinding.CharactersFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private val viewModel by viewModels<CharactersViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = CharactersFragmentBinding.inflate(inflater, container, false)
        val charactersAdapter = CharactersAdapter()

        binding.charactersRecyclerView.apply {
            adapter = charactersAdapter
        }
        viewModel.characters.observe(this) { characters ->
            charactersAdapter.submitList(characters)
            binding.charactersRecyclerView.smoothScrollToPosition(0)
        }

        binding.apply {
            staffRadioButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.setStaffChecked()
                }
            }
            studentsRadioButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.setStudentsChecked()
                }
            }
            allRadioButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.setAllChecked()
                }
            }
        }

        return binding.root
    }
}