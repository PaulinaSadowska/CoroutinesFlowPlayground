package com.paulinasadowska.coroutinesflowplayground

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paulinasadowska.coroutinesflowplayground.databinding.CharacterItemBinding

class CharactersAdapter : ListAdapter<CharacterToDisplay, CharactersAdapter.CharacterViewHolder>(CharactersDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
                CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }

    override fun onViewRecycled(holder: CharacterViewHolder) {
        holder.unbind()
    }

    class CharacterViewHolder(
            private val binding: CharacterItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(characterToBind: CharacterToDisplay) {
            binding.apply {
                character = characterToBind
                executePendingBindings()
            }
        }

        fun unbind() {
            binding.apply {
                character = CharacterToDisplay.EMPTY
                executePendingBindings()
            }
        }

    }
}

class CharactersDiffCallback : DiffUtil.ItemCallback<CharacterToDisplay>() {
    override fun areItemsTheSame(oldItem: CharacterToDisplay, newItem: CharacterToDisplay): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: CharacterToDisplay, newItem: CharacterToDisplay): Boolean {
        return oldItem == newItem
    }

}

data class CharacterToDisplay(val name: String, val imageUrl: String) {
    companion object {
        val EMPTY = CharacterToDisplay(name = "", imageUrl = "")
    }
}