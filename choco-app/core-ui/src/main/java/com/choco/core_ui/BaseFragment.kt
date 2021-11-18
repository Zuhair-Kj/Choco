package com.choco.core_ui

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StyleRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B: ViewDataBinding>: Fragment() {
    protected var binding: B? = null

    abstract fun getLayoutId(): Int
    @StyleRes
    protected open var customStyleId: Int? = null
    protected open val pressBackCallback: OnBackPressedCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        pressBackCallback?.let {
            it.isEnabled = true
            requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val styledInflater = customStyleId?.let {
            activity?.let { activity -> inflater.cloneInContext(ContextThemeWrapper(activity, it)) }
        }
        binding = DataBindingUtil.inflate(styledInflater
            ?: inflater, getLayoutId(), container, false)
        binding?.lifecycleOwner = this
        return binding?.root
    }

    override fun onDetach() {
        pressBackCallback?.isEnabled = false
        pressBackCallback?.remove()
        super.onDetach()
    }

    override fun onDestroyView() {
        pressBackCallback?.isEnabled = false
        super.onDestroyView()
    }
}