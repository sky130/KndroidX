package kndroidx.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

class ViewFragmentX<VB : ViewBinding, VM : ViewModel> : BaseFragmentX() {
    private var _binding: VB? = null
    val binding get() = _binding!!
    private var _viewModel: VM? = null
    val viewModel get() = _viewModel!!

    override fun onCreateView(inflater: LayoutInflater): View {
        _binding = createViewBinding(inflater)
        _viewModel = createViewModel()
        if (binding is ViewDataBinding) {
            (binding as ViewDataBinding).lifecycleOwner = this@ViewFragmentX
            try {
                val vbClass =
                    (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
                val vmClass =
                    (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VM>>()
                val set = vbClass[0].getMethod("setViewModel", vmClass[0])
                set.invoke(binding, viewModel)
            } catch (_: Exception) {
            }
            try {
                val vbClass =
                    (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
                val set = vbClass[0].getMethod("setFragment", this@ViewFragmentX::class.java)
                set.invoke(binding, this@ViewFragmentX)
            } catch (_: Exception) {
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}