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

    override fun onCreateView(): View {
        _viewModel = createViewModel(1)
        if (binding is ViewDataBinding) {
            (binding as ViewDataBinding).lifecycleOwner = this
            try {
                val vbClass =
                    (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<Any>>()
                val set = vbClass[0].getMethod("setViewModel", vbClass[1])
                set.invoke(binding, viewModel)
            } catch (_: Exception) {
            }
            try {
                val vbClass =
                    (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<Any>>()
                val set = vbClass[0].getMethod("setActivity", this::class.java)
                set.invoke(binding, this)
            } catch (_: Exception) {
            }
        }
        return binding.root
    }

}