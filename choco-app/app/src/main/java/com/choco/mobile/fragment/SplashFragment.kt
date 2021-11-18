package com.choco.mobile.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.choco.R
import com.choco.core.extensions.hide
import com.choco.core.extensions.show
import com.choco.core.util.Resource
import com.choco.core.util.Resource.Status.*
import com.choco.core_ui.BaseFragment
import com.choco.databinding.FragmentSplashBinding
import com.choco.mobile.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment: BaseFragment<FragmentSplashBinding>() {
    private val viewModel: SplashViewModel by viewModel()
    override fun getLayoutId() = R.layout.fragment_splash

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.statusLiveData.observe(viewLifecycleOwner, statusObserver)
        viewModel.getLaunchStatus()
    }

    private val statusObserver = Observer<Resource<SplashViewModel.Status>> { resource ->
        when (resource?.status) {
            LOADING -> {
                binding?.loadingLayout?.root?.show()
            }
            SUCCESS -> {
                binding?.loadingLayout?.root?.hide()
                if (resource.data?.isAuthed == true)
                    proceedToMain()
                else
                    proceedToAuthFlow()
            }
            else -> {
                // Some error handling.
            }
        }
    }

    private fun proceedToMain() {
        findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
    }

    private fun proceedToAuthFlow() {
        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
    }
}