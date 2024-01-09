package kndroidx.activity

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class ViewActivityX<VB : ViewBinding, VM : ViewModel> : BaseActivityX() {

    open val binding by lazy { createViewBinding<VB>(layoutInflater) }
    private var _viewModel: VM? = null
    val viewModel get() = _viewModel!!

    override fun onCreateView(): View = binding.apply {
        if (binding is ViewDataBinding) {
            (binding as ViewDataBinding).lifecycleOwner = this@ViewActivityX
        }
        _viewModel = createViewModel()
    }.root

}