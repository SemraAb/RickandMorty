package com.samra.rickandmorty.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samra.rickandmorty.R
import com.samra.rickandmorty.databinding.ItemFilterBinding

class FilterAdapter(
    private val resources: Resources,
    private val listener: OnClickListener,
) : RecyclerView.Adapter<FilterAdapter.FilterHolder>() {
    private val hints = listOf<String>("Gender Types" , "Status" , "Species")


    class FilterHolder(var binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterHolder {
        var binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterHolder(binding)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: FilterHolder, position: Int) {
        val genders = resources.getStringArray(R.array.gender)
        val species = resources.getStringArray(R.array.species)
        val status = resources.getStringArray(R.array.status)

        val adapters = listOf(genders, species, status)

        val arrayAdapter = ArrayAdapter(holder.itemView.context, R.layout.dropdown_item, adapters[position])
        holder.binding.autoCompleteText.setAdapter(arrayAdapter)
        holder.binding.autoCompleteText.hint = hints[position]
        val defaultEndIconDrawable = holder.binding.textInputLayout.endIconDrawable

        holder.binding.autoCompleteText.setOnItemClickListener { _, _, _, _ ->
            holder.binding.textInputLayout.setEndIconDrawable(R.drawable.ic_x)
        }


        holder.binding.textInputLayout.setEndIconOnClickListener {
            holder.binding.autoCompleteText.text = null
            // Show hint again
            holder.binding.textInputLayout.hint = hints[position]
            // Reset the end icon to the default
            holder.binding.textInputLayout.endIconDrawable =
                defaultEndIconDrawable // Replace with your default icon

        }
    }

}
interface OnClick {
    fun onClick(item: String)
}

