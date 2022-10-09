package com.binar.movieapp.presentation.ui.user.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.movieapp.R
import com.binar.movieapp.data.local.model.user.UserEntity
import com.binar.movieapp.databinding.FragmentProfileBinding
import com.binar.movieapp.di.UserServiceLocator
import com.binar.movieapp.presentation.ui.user.MainActivity
import com.binar.movieapp.util.viewModelFactory
import com.binar.movieapp.wrapper.Resource

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModelFactory {
        ProfileViewModel(UserServiceLocator.provideUserRepository(requireContext()))
    }

    private val args: ProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getInitialData()
        observeData()
        setOnClickListener()
    }

    private fun getUserData() {
        val user = args.user
        bindDataToView(user)
    }

    private fun setOnClickListener() {
        binding.btnUpdateProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_updateProfileFragment)
        }
        binding.btnLogout.setOnClickListener {
            viewModel.setIfUserLogin(false)
            startActivity(Intent(requireContext(), MainActivity::class.java))
            activity?.finish()
        }
    }

    private fun getInitialData() {
        val userId = viewModel.getUserId()
        getUserById(userId)
    }

    private fun getUserById(userId: Long?) {
        if (userId != null) {
            viewModel.getUserById(userId)
        }
    }

    private fun observeData() {
        viewModel.userByIdResult.observe(viewLifecycleOwner) {
            bindDataToView(it)
        }
        viewModel.updateResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    getUserData()
                }
                else -> {}
            }
        }
    }

    private fun bindDataToView(user: UserEntity?) {
        user?.let {
            binding.apply {
                tvUsername.text = user.username
                tvEmail.text = user.email
                tvFullName.text = user.fullName
                tvDateOfBirth.text = user.dateOfBirth
                tvAddress.text = user.address
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}