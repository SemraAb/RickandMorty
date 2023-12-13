package com.samra.rickandmorty.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.samra.rickandmorty.R
import com.samra.rickandmorty.data.network.model.Result
import com.samra.rickandmorty.databinding.FragmentDetailBinding
import com.samra.rickandmorty.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val receivedItem: Result = args.result

        binding.itemGender.text = receivedItem.gender
        binding.itemOrigin.text = receivedItem.origin?.name
        binding.itemSpecies.text = receivedItem.species
        binding.itemStatus.text = receivedItem.status
        binding.itemType.text = if (receivedItem.type.isNullOrEmpty()) "not known" else receivedItem.type
        Glide.with(binding.root)
            .load(receivedItem.image)
            .into(binding.itemImage)

        binding.backButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }
}