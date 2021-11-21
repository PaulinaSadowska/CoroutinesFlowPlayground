package com.paulinasadowska.coroutinesflowplayground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.paulinasadowska.coroutinesflowplayground.databinding.CharactersFragmentBinding

class CharactersFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = CharactersFragmentBinding.inflate(inflater, container, false)
        binding.charactersRecyclerView.apply {
            adapter = CharactersAdapter(mockData)
        }
        return binding.root
    }

    private val mockData = listOf(
            CharacterToDisplay("Purus", "https://picsum.photos/400/400"),
            CharacterToDisplay("Neko", "https://picsum.photos/400/400"),
            CharacterToDisplay("Test", "https://picsum.photos/400/400"),
            CharacterToDisplay("Test 2", "https://picsum.photos/400/400"),
            CharacterToDisplay("Test 3", "https://picsum.photos/400/400"),
            CharacterToDisplay("Test 4", "https://picsum.photos/400/400"),
            CharacterToDisplay("Test 5", "https://picsum.photos/400/400"),
            CharacterToDisplay("Test 6", "https://picsum.photos/400/400"),
            CharacterToDisplay("Test 7", "https://picsum.photos/400/400"),
            CharacterToDisplay("Test 9", "https://picsum.photos/400/400"),
            CharacterToDisplay("Amazing cat", "https://picsum.photos/400/400"),
            CharacterToDisplay("Amazing cat ", "https://picsum.photos/400/400")

    )
}