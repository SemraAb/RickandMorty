package com.samra.rickandmorty.ui

import CharacterAdapter
import OnCharacterClickListener
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.samra.rickandmorty.adapter.FilterAdapter
import com.samra.rickandmorty.adapter.OnFilterClick
import com.samra.rickandmorty.data.network.model.Result
import com.samra.rickandmorty.databinding.FragmentMainBinding
import com.samra.rickandmorty.viewModel.CharacterViewModel
import com.samra.rickandmorty.viewModel.GenderType
import com.samra.rickandmorty.viewModel.SpeciesType
import com.samra.rickandmorty.viewModel.StatusType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val filterAdapter by lazy {
        FilterAdapter(requireContext().resources, object : OnFilterClick {
            override fun onClickGender(genderType: GenderType) {
                characterViewModel.updateGender(genderType)
                observeCharacterData()
            }
            override fun onClickStatus(statusType: StatusType) {
                characterViewModel.updateStatus(statusType)
                observeCharacterData()
            }
            override fun onCLickSpecies(speciesType: SpeciesType) {
                characterViewModel.updateSpecies(speciesType)
                observeCharacterData()
            }
        })
    }
    private val characterAdapter by lazy {
        CharacterAdapter(object : OnCharacterClickListener {
            override fun onClickCharacter(item: Result) {
                val action = MainFragmentDirections.actionMainFragmentToDetailFragment(item)
                findNavController().navigate(action)
            }
        })
    }
    private val characterViewModel: CharacterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filterRecyclerVIew.adapter = filterAdapter
        binding.itemsRecyclerView.adapter = characterAdapter

        observeCharacterData()

        binding.searchEditText.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchText: String = textView.text.toString().trim()
                characterViewModel.updateName(searchText)
                true
            } else {
                false
            }
        }
    }
    private fun observeCharacterData() {
            lifecycleScope.launch {
                characterViewModel.characterPagingData
                    .flowWithLifecycle(lifecycle).collectLatest {
                        characterAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                    }
            }

        lifecycleScope.launch {
            characterViewModel.filterState.flowWithLifecycle(lifecycle).collectLatest {
                Log.e("TEST 3 ", "observeCharacterData: $it", )
                characterViewModel.getCharacters(
                    name = it.name,
                    gender= it.gender,
                    status = it.status,
                    species = it.species
                )
            }
        }
    }
}
