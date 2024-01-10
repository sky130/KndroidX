package kndroidx.activity

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class ViewActivityX<VB : ViewBinding, VM : ViewModel> : BaseActivityX() {

    open val binding by lazy { createViewBinding<VB>(layoutInflater) }
    private var _viewModel: VM? = null
    val viewModel get() = _viewModel!!

    override fun onCreateView(): View = binding.apply {
        _viewModel = createViewModel(position = 1)
        if (binding is ViewDataBinding) {
            (binding as ViewDataBinding).lifecycleOwner = this@ViewActivityX
            try {
                val vbClass =
                    (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
                val vmClass =
                    (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VM>>()
                val set = vbClass[0].getMethod("setViewModel", vmClass[1])
                set.invoke(binding, viewModel)
            } catch (_: Exception) {
            }
            try {
                val vbClass =
                    (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
                val set = vbClass[0].getMethod("setActivity", this@ViewActivityX::class.java)
                set.invoke(binding, this@ViewActivityX)
            } catch (_: Exception) {
            }
        }
    }.root

}