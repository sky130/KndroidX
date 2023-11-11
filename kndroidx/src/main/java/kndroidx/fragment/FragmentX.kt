package kndroidx.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class FragmentX<VB : ViewBinding, VM : ViewModel> : BaseViewFragment<VB>() {

    val viewModel: VM by lazy(mode = LazyThreadSafetyMode.NONE) { createViewModel(1) }

    private fun <VM : ViewModel> createViewModel(position: Int): VM {
        val vbClass =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<*>>()
        val viewModel = vbClass[position] as Class<VM>
        return ViewModelProvider(requireActivity())[viewModel]
    }
}