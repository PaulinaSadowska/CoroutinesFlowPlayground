package com.paulinasadowska.coroutinesflowplayground.charactersList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
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
        }

        viewModel.filters.observe(this) { filters ->
            when (filters) {
                CharactersFilter.ALL -> binding.allRadioButton.isChecked = true
                CharactersFilter.STAFF -> binding.staffRadioButton.isChecked = true
                CharactersFilter.STUDENT -> binding.studentsRadioButton.isChecked = true
            }
        }

        binding.apply {
            staffRadioButton.setOnSelectedListener {
                viewModel.setStaffChecked()
            }
            studentsRadioButton.setOnSelectedListener {
                viewModel.setStudentsChecked()
            }
            allRadioButton.setOnSelectedListener {
                viewModel.setAllChecked()
            }
        }

        return binding.root
    }

    private fun RadioButton.setOnSelectedListener(action: () -> Unit) {
        setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                action()
            }
        }
    }
}