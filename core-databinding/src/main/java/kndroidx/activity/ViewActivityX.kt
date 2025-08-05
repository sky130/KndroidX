package kndroidx.activity

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.github.kndroidx.BR

abstract class ViewActivityX<VB : ViewBinding>(inflate: (LayoutInflater) -> VB) :
    BaseActivityX() {

    open val binding by lazy { inflate.invoke(layoutInflater) }
    abstract val viewModel: ViewModel

    override fun onCreateView(): View {
        if (binding is ViewDataBinding) {
            with(binding as ViewDataBinding) {
                lifecycleOwner = this@ViewActivityX
                setVariable(BR.viewModel, viewModel)
                setVariable(BR.activity, this@ViewActivityX)
            }
        }
        return binding.root
    }

}