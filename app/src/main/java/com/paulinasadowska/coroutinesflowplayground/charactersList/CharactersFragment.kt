package com.paulinasadowska.coroutinesflowplayground.charactersList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.paulinasadowska.coroutinesflowplayground.databinding.CharactersFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

// todo - testy

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private val viewModel by viewModels<CharactersViewModel>()
    private lateinit var binding: CharactersFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CharactersFragmentBinding.inflate(inflater, container, false)

        setupCharactersList()
        observeFiltersChange()
        setFiltersAndSearchListeners()

        viewModel.spinner.observe(this) { isVisible ->
            binding.progressBar.visibility = if (isVisible) VISIBLE else GONE
        }
        viewModel.snackbar.observe(this) { message ->
            if (message.isNotBlank()) {
                Snackbar.make(binding.charactersRecyclerView, message, Snackbar.LENGTH_LONG).show()
                viewModel.clearSnackbarMessage()
            }
        }

        return binding.root
    }

    private fun setupCharactersList() {
        val charactersAdapter = CharactersAdapter()
        binding.charactersRecyclerView.apply {
            adapter = charactersAdapter
        }
        viewModel.characters.observe(this) { characters ->
            charactersAdapter.submitList(characters)
        }
    }

    private fun observeFiltersChange() {
        viewModel.filters.observe(this) { filters ->
            when (filters) {
                CharactersFilter.STAFF -> binding.staffRadioButton.isChecked = true
                CharactersFilter.STUDENT -> binding.studentsRadioButton.isChecked = true
                else -> binding.allRadioButton.isChecked = true
            }
        }
    }

    private fun setFiltersAndSearchListeners() {
        binding.apply {
            searchByName.addTextChangedListener { editable ->
                viewModel.setSearchedName(editable.toString())
            }
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
    }

    private fun RadioButton.setOnSelectedListener(action: () -> Unit) {
        setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                action()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //TODO - binding.searchByName.removeTextChangedListener()
    }
}