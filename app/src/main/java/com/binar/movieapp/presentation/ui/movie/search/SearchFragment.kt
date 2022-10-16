package com.binar.movieapp.presentation.ui.movie.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.movieapp.databinding.FragmentSearchBinding
import com.binar.movieapp.di.MovieServiceLocator
import com.binar.movieapp.presentation.ui.movie.search.adapter.SearchAdapter
import com.binar.movieapp.wrapper.Resource

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by lazy {
        SearchViewModel(MovieServiceLocator.provideMovieRepository(requireContext()))
    }

    private val adapter: SearchAdapter by lazy {
        SearchAdapter {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onInputTextChanged()
        initList()
        observeData()
    }

    private fun onInputTextChanged() {
        binding.tilSearch.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(query: Editable?) {
                viewModel.searchMovie(query.toString())
            }
        })
    }

    private fun observeData() {
        viewModel.searchResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Error -> {}
                is Resource.Empty -> {}
                is Resource.Success -> {
                    adapter.submitList(it.payload)
                }
            }
        }
    }

    private fun initList() {
        binding.rvSearchMovies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchFragment.adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}