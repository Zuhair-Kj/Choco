package com.choco.core_ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.choco.core_ui.databinding.LayoutNumberButtonBinding
import java.lang.NumberFormatException

class NumberButton @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    var minValue: Int = 0

    var maxValue: Int = 0

    var onValueChangedListener: ((Int) -> Unit)? = null

    var currentValue: Int
        get() {
            binding?.textNumber?.text?.toString()?.let {
                return Integer.parseInt(it)
            } ?: kotlin.run {
                throw NumberFormatException()
            }
        }

    set(number) {
        binding?.textNumber?.text = number.toString()
    }

    private var binding: LayoutNumberButtonBinding? = null

    init {
        initView()
    }

    private fun initView() {
        binding = LayoutNumberButtonBinding.inflate(LayoutInflater.from(context), this, true)
        binding?.let {
            it.buttonMinus.setOnClickListener {
                if (currentValue > minValue)
                    onValueChangedListener?.invoke(--currentValue)
            }

            it.buttonPlus.setOnClickListener {
                if (currentValue < maxValue)
                    onValueChangedListener?.invoke(++currentValue)
            }
            it.textNumber.text = "0"
        }
    }


    companion object {
        @BindingAdapter(value = ["app:minValue", "app:maxValue"], requireAll = true)
        @JvmStatic
        fun bindValues(numberButton: NumberButton, minValue: Int, maxValue: Int) {
            numberButton.minValue = minValue
            numberButton.maxValue = maxValue
        }

        @BindingAdapter("app:onValueChanged")
        @JvmStatic
        fun bindListener(numberButton: NumberButton, onValueChanged: ((Int) -> Unit)) {
            numberButton.onValueChangedListener = onValueChanged
        }
    }
}
