package com.samra.rickandmorty.adapter

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samra.rickandmorty.R
import com.samra.rickandmorty.databinding.ItemFilterBinding
import com.samra.rickandmorty.viewModel.GenderType
import com.samra.rickandmorty.viewModel.SpeciesType
import com.samra.rickandmorty.viewModel.StatusType

class FilterAdapter(
    private val resources: Resources,
    private val listener: OnFilterClick,
) : RecyclerView.Adapter<FilterAdapter.FilterHolder>() {
    private val hints = listOf("Gender", "Status", "Species")

    class FilterHolder(var binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterHolder {
        return FilterHolder(
            ItemFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return hints.size
    }

    override fun onBindViewHolder(holder: FilterHolder, position: Int) {
        val defaultEndIconDrawable = holder.binding.textInputLayout.endIconDrawable

        with(holder.binding) {
            when (position) {
                0 -> {
                    val genderList = resources.getStringArray(R.array.gender)
                    val genderAdapter =
                        ArrayAdapter(holder.itemView.context, R.layout.dropdown_item, genderList)
                    autoCompleteText.setAdapter(genderAdapter)
                    autoCompleteText.hint = hints[position]
                    autoCompleteText.setOnItemClickListener { _, _, position, _ ->
                        listener.onClickGender(GenderType.from(genderList[position]))
                        textInputLayout.setEndIconDrawable(R.drawable.ic_x)
                    }
                    textInputLayout.setEndIconOnClickListener {
                        listener.onClickGender(GenderType.ALL)
                        autoCompleteText.setText("")
                        autoCompleteText.hint = hints[0]
                        textInputLayout.endIconDrawable = defaultEndIconDrawable
                    }
                }

                1 -> {
                    val statusList = resources.getStringArray(R.array.status)
                    val statusAdapter =
                        ArrayAdapter(root.context, R.layout.dropdown_item, statusList)
                    autoCompleteText.setAdapter(statusAdapter)
                    autoCompleteText.hint = hints[position]
                    autoCompleteText.setOnItemClickListener { _, _, position, _ ->
                        listener.onClickStatus(StatusType.from(statusList[position]))
                        textInputLayout.setEndIconDrawable(R.drawable.ic_x)
                    }
                    textInputLayout.setEndIconOnClickListener {
                        listener.onClickStatus(StatusType.ALL)
                        autoCompleteText.setText("")
                        autoCompleteText.hint = hints[1]
                        textInputLayout.endIconDrawable = defaultEndIconDrawable

                    }
                }

                2 -> {
                    val speciesList = resources.getStringArray(R.array.species)
                    val speciesAdapter =
                        ArrayAdapter(root.context, R.layout.dropdown_item, speciesList)
                    autoCompleteText.setAdapter(speciesAdapter)
                    autoCompleteText.hint = hints[position]
                    autoCompleteText.setOnItemClickListener { _, _, position, _ ->
                        listener.onCLickSpecies(SpeciesType.from(speciesList[position]))
                        textInputLayout.setEndIconDrawable(R.drawable.ic_x)
                    }
                    textInputLayout.setEndIconOnClickListener {
                        listener.onCLickSpecies(SpeciesType.ALL)
                        autoCompleteText.setText("")
                        autoCompleteText.hint = hints[2]
                        textInputLayout.endIconDrawable = defaultEndIconDrawable
                    }
                }
            }


        }
    }

}


interface OnFilterClick {
    fun onClickGender(genderType: GenderType)
    fun onClickStatus(statusType: StatusType)
    fun onCLickSpecies(speciesType: SpeciesType)
}

