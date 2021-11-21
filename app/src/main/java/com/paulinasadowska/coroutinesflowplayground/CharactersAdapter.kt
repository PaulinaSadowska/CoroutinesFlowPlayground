package com.paulinasadowska.coroutinesflowplayground

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paulinasadowska.coroutinesflowplayground.databinding.CharacterItemBinding

class CharactersAdapter(
        private val charactersToDisplay: List<CharacterToDisplay>
) : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
                CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = charactersToDisplay[position]
        holder.bind(character)
    }

    override fun getItemCount() = charactersToDisplay.size

    class CharacterViewHolder(
            private val binding: CharacterItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(characterToBind: CharacterToDisplay) {
            binding.apply {
                character = characterToBind
                executePendingBindings()
            }
        }

    }
}

data class CharacterToDisplay(val name: String, val imageUrl: String = "")