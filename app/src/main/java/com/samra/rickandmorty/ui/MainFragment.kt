package com.samra.rickandmorty.ui

import CharacterAdapter2
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.samra.rickandmorty.adapter.FilterAdapter
import com.samra.rickandmorty.adapter.OnClickListener
import com.samra.rickandmorty.data.network.model.Result
import com.samra.rickandmorty.databinding.FragmentMainBinding
import com.samra.rickandmorty.viewModel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val filterAdapter by lazy { FilterAdapter(requireContext().resources , object: OnClickListener{
        override fun onClick(item: Result) {

        }

    }) }
//    private val characterAdapter by lazy { CharacterAdapter(emptyList() , object : OnClickListener{
//        override fun onClick(item: Result) {
//            val action = MainFragmentDirections.actionMainFragmentToDetailFragment()
//            findNavController().navigate(action)
//        }
//    } ) }
    private val characterAdapter2 by lazy { CharacterAdapter2(object : OnClickListener{
        override fun onClick(item: Result) {
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(item)
            findNavController().navigate(action)
        }
    }) }
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
        binding.itemsRecyclerView.adapter = characterAdapter2

        characterViewModel.getCharacters()
//        observe()
        observeCharacterData()

        binding.searchEditText.setOnEditorActionListener{textView , actionId , _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                val searchText:String = textView.text.toString().trim()

                true
            }else{
                false
            }
        }



    }

//    fun observe(){
//        characterViewModel.characters.observe(viewLifecycleOwner , Observer {
//            characterAdapter.onDataChange(it.results)
//        })
//    }

    private fun observeCharacterData() {
        viewLifecycleOwner.lifecycleScope.launch {
            characterViewModel.getCharacters().collectLatest { pagingData ->
                characterAdapter2.submitData(pagingData)
            }
        }
    }

    private fun observeFilteredCharacter(name:String , status:String , gender:String){
        viewLifecycleOwner.lifecycleScope.launch {
            characterViewModel.getFilteredCharacter(name , status , gender).collectLatest { pagingData ->
                characterAdapter2.submitData(pagingData)
            }
        }
    }
}