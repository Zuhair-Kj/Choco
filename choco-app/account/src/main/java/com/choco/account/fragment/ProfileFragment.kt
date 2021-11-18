package com.choco.account.fragment

import android.os.Bundle
import android.view.View
import com.choco.account.R
import com.choco.account.databinding.FragmentProfileBinding
import com.choco.account.viewmodel.ProfileViewModel
import com.choco.core.util.MainBridge
import com.choco.core_ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment: BaseFragment<FragmentProfileBinding>() {
    private val viewModel: ProfileViewModel by viewModel()
    override fun getLayoutId() = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.buttonLogout?.setOnClickListener {
            viewModel.logout()
            (requireActivity() as? MainBridge)?.logout()
        }
    }
}