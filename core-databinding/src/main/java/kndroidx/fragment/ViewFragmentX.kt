package kndroidx.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.github.kndroidx.BR

abstract class ViewFragmentX<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) : BaseFragmentX() {
    private var _binding: VB? = null
    val binding get() = _binding!!

    abstract val viewModel : ViewModel

    override fun onCreateView(inflater: LayoutInflater): View {
        _binding = inflate.invoke(layoutInflater)
        if (binding is ViewDataBinding) {
            with(binding as ViewDataBinding) {
                lifecycleOwner = this@ViewFragmentX
                setVariable(BR.viewModel, viewModel)
                setVariable(BR.fragment, this@ViewFragmentX)
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}