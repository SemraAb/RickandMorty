package com.samra.rickandmorty.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samra.rickandmorty.data.network.model.Result
import com.samra.rickandmorty.databinding.ItemCharacterBinding

class CharacterAdapter(private var characterList: List<Result> , var listener:OnClickListener): RecyclerView.Adapter<CharacterAdapter.CharacterHolder>() {
    class CharacterHolder( val binding: ItemCharacterBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return CharacterHolder(binding)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        Glide.with(holder.binding.root)
            .load(characterList[position].image)
            .into(holder.binding.characterImage)
        holder.binding.nameText.setText(characterList[position].name)
        holder.binding.speciesText.setText(characterList[position].species)
        holder.binding.root.setOnClickListener {
            listener.onClick(characterList[position])
        }
        println(characterList[position].id)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun onDataChange(data : List<Result>){
        characterList = data
        notifyDataSetChanged()
    }

}

interface OnClickListener{
    fun onClick(item: Result)
}