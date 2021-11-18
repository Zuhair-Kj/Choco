package com.choco.auth.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import com.choco.auth.R
import com.choco.auth.databinding.FragmentLoginBinding
import com.choco.auth.viewmodel.LoginViewModel
import com.choco.core.extensions.hide
import com.choco.core.extensions.show
import com.choco.core.util.CustomTextWatcher
import com.choco.core.util.MainBridge
import com.choco.core.util.Resource
import com.choco.core.util.Resource.Status.*
import com.choco.core_ui.BaseFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment: BaseFragment<FragmentLoginBinding>() {
    override fun getLayoutId() = R.layout.fragment_login
    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        viewModel.loginLiveData.observe(viewLifecycleOwner, loginObserver)
    }

    private val loginObserver = Observer<Resource<String>> { resource ->
        when(resource?.status) {
            SUCCESS -> {
                proceedToMain()
                showControls()
            }
            ERROR -> {
                showControls()
                binding?.apply {
                    Snackbar.make(root, R.string.error_login, Snackbar.LENGTH_SHORT).show()
                }
            }
            NETWORK_DISCONNECTED -> {
                showControls()
                binding?.apply {
                    Snackbar.make(root, R.string.error_no_connection, Snackbar.LENGTH_SHORT).show()
                }
            }
            LOADING -> {
                showLoading()
            }
            else -> {}
        }
    }

    private fun showControls() {
        binding?.apply {
            groupControls.show()
            loadingLayout.root.hide()
        }
    }

    private fun showLoading() {
        binding?.apply {
            groupControls.hide()
            loadingLayout.root.show()
        }
    }

    private fun initUI() {
        binding?.apply {
            textInputEditPassword.setOnEditorActionListener { _, _, _ ->
                initiateLogin()
                true
            }
            buttonLogin.setOnClickListener{
                initiateLogin()
            }
            textInputEditEmail.addTextChangedListener(object: CustomTextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.toString().isNotBlank()) textInputEmail.error = ""
                }
            })
            textInputEditPassword.addTextChangedListener(object: CustomTextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.toString().isNotBlank()) textInputPassword.error = ""
                }
            })
        }
    }

    private fun proceedToMain() {
        (requireActivity() as? MainBridge)?.actionLoginToMain()
    }

    private fun initiateLogin() {
        binding?.apply {
            val email = textInputEditEmail.text?.toString() ?: ""
            val password = textInputEditPassword.text?.toString() ?: ""

            if (email.isBlank()) {
                textInputEmail.error = getString(R.string.error_no_email)
                return
            }
            if (password.isBlank()) {
                textInputPassword.error = getString(R.string.error_no_password)
                return
            }
            viewModel.login(email, password)
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(root.windowToken, 0)
        }
    }
}