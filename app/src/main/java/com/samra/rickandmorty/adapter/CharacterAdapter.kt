import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samra.rickandmorty.data.network.model.Result
import com.samra.rickandmorty.databinding.ItemCharacterBinding

class CharacterAdapter(private val listener: OnCharacterClickListener) :
    PagingDataAdapter<Result, CharacterAdapter.CharacterHolder>(CHARACTER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class CharacterHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Result) {
            Glide.with(binding.root)
                .load(character.image)
                .into(binding.characterImage)
            binding.nameText.text = character.name
            binding.speciesText.text = character.species

           binding.root.setOnClickListener {
               listener.onClickCharacter(character)
           }
        }
    }

    companion object {
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id // Assuming Result has an ID field
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }
}

interface OnCharacterClickListener{
    fun onClickCharacter(item: Result)
}
